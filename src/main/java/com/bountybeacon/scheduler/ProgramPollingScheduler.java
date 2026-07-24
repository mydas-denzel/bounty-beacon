package com.bountybeacon.scheduler;

import com.bountybeacon.notification.NotificationService;
import com.bountybeacon.notification.NotificationType;
import com.bountybeacon.program.entity.Program;
import com.bountybeacon.program.repository.ProgramRepository;
import com.bountybeacon.program.service.ProgramService;
import com.bountybeacon.provider.Provider;
import com.bountybeacon.provider.ProviderRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProgramPollingScheduler {
    private final ProviderRegistry providerRegistry;
    private final ProgramService programService;
    private final ProgramRepository programRepository;
    private final NotificationService notificationService;

    @Scheduled(fixedRateString = "${app.polling.rate-ms:3600000}")
    public void pollPrograms() {
        log.info("Starting program polling at {}", LocalDateTime.now());
        List<Provider> providers = providerRegistry.getAllProviders();
        
        for (Provider provider : providers) {
            log.info("Polling provider: {}", provider.getType());
            try {
                List<Program> fetchedPrograms = provider.fetchPrograms();
                log.info("Fetched {} programs from {}", fetchedPrograms.size(), provider.getType());
                
                for (Program fetched : fetchedPrograms) {
                    processProgram(fetched);
                }
            } catch (Exception e) {
                log.error("Error polling provider {}", provider.getType(), e);
            }
        }
        log.info("Finished program polling");
    }

    private void processProgram(Program fetched) {
        Optional<Program> existingOpt = programRepository.findByHandleAndProvider(fetched.getHandle(), fetched.getProvider());
        
        fetched.setLastPolledAt(LocalDateTime.now());
        
        if (existingOpt.isEmpty()) {
            log.info("New program found: {} ({})", fetched.getName(), fetched.getProvider());
            programService.saveOrUpdate(fetched);
            notificationService.sendNotification(fetched, NotificationType.NEW_PROGRAM);
        } else {
            Program existing = existingOpt.get();
            // Simple update check: if updatedAt changed or if we want to periodically update
            // For now, let's update and notify if something significant changed (simplified)
            boolean updated = false;
            if (fetched.getUpdatedAt() != null && existing.getUpdatedAt() != null 
                    && fetched.getUpdatedAt().isAfter(existing.getUpdatedAt())) {
                updated = true;
            }
            
            programService.saveOrUpdate(fetched);
            
            if (updated) {
                log.info("Program updated: {} ({})", fetched.getName(), fetched.getProvider());
                notificationService.sendNotification(fetched, NotificationType.PROGRAM_UPDATED);
            }
        }
    }
}
