package com.bountybeacon.program.service;

import com.bountybeacon.exception.ProgramNotFoundException;
import com.bountybeacon.program.dto.ProgramDto;
import com.bountybeacon.program.dto.ProgramResponse;
import com.bountybeacon.program.entity.Program;
import com.bountybeacon.program.mapper.ProgramMapper;
import com.bountybeacon.program.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository programRepository;
    private final ProgramMapper programMapper;

    @Transactional(readOnly = true)
    public ProgramResponse getAllPrograms(Pageable pageable) {
        Page<Program> programPage = programRepository.findAll(pageable);
        List<ProgramDto> dtos = programPage.getContent().stream()
                .map(programMapper::toDto)
                .collect(Collectors.toList());
        
        return ProgramResponse.builder()
                .programs(dtos)
                .total(programPage.getTotalElements())
                .build();
    }

    @Transactional(readOnly = true)
    public ProgramDto getProgramById(Long id) {
        return programRepository.findById(id)
                .map(programMapper::toDto)
                .orElseThrow(() -> new ProgramNotFoundException("Program not found with id: " + id));
    }

    @Transactional
    public Program saveOrUpdate(Program program) {
        return programRepository.findByHandleAndProvider(program.getHandle(), program.getProvider())
                .map(existing -> {
                    existing.setName(program.getName());
                    existing.setDescription(program.getDescription());
                    existing.setUrl(program.getUrl());
                    existing.setLogoUrl(program.getLogoUrl());
                    existing.setBounty(program.isBounty());
                    existing.setUpdatedAt(program.getUpdatedAt());
                    existing.setLastPolledAt(program.getLastPolledAt());
                    return programRepository.save(existing);
                })
                .orElseGet(() -> programRepository.save(program));
    }
}
