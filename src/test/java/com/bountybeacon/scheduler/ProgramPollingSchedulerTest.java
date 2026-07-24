package com.bountybeacon.scheduler;

import com.bountybeacon.notification.NotificationService;
import com.bountybeacon.notification.NotificationType;
import com.bountybeacon.program.entity.Program;
import com.bountybeacon.program.repository.ProgramRepository;
import com.bountybeacon.program.service.ProgramService;
import com.bountybeacon.provider.Provider;
import com.bountybeacon.provider.ProviderRegistry;
import com.bountybeacon.provider.ProviderType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProgramPollingSchedulerTest {

    @Mock
    private ProviderRegistry providerRegistry;
    @Mock
    private ProgramService programService;
    @Mock
    private ProgramRepository programRepository;
    @Mock
    private NotificationService notificationService;
    @Mock
    private Provider provider;

    private ProgramPollingScheduler scheduler;

    @BeforeEach
    void setUp() {
        scheduler = new ProgramPollingScheduler(providerRegistry, programService, programRepository, notificationService);
    }

    @Test
    void pollPrograms_ShouldProcessFetchedPrograms() {
        // Given
        Program program = Program.builder()
                .handle("test")
                .provider("HACKERONE")
                .name("Test Program")
                .build();
        
        when(providerRegistry.getAllProviders()).thenReturn(List.of(provider));
        when(provider.fetchPrograms()).thenReturn(List.of(program));
        when(provider.getType()).thenReturn(ProviderType.HACKERONE);
        when(programRepository.findByHandleAndProvider("test", "HACKERONE")).thenReturn(Optional.empty());

        // When
        scheduler.pollPrograms();

        // Then
        verify(programService).saveOrUpdate(program);
        verify(notificationService).sendNotification(program, NotificationType.NEW_PROGRAM);
    }

    @Test
    void pollPrograms_ShouldNotifyOnUpdate() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Program existing = Program.builder()
                .handle("test")
                .provider("HACKERONE")
                .updatedAt(now.minusDays(1))
                .build();
        Program fetched = Program.builder()
                .handle("test")
                .provider("HACKERONE")
                .updatedAt(now)
                .build();

        when(providerRegistry.getAllProviders()).thenReturn(List.of(provider));
        when(provider.fetchPrograms()).thenReturn(List.of(fetched));
        when(provider.getType()).thenReturn(ProviderType.HACKERONE);
        when(programRepository.findByHandleAndProvider("test", "HACKERONE")).thenReturn(Optional.of(existing));

        // When
        scheduler.pollPrograms();

        // Then
        verify(programService).saveOrUpdate(fetched);
        verify(notificationService).sendNotification(fetched, NotificationType.PROGRAM_UPDATED);
    }
}
