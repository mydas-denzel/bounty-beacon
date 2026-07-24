package com.bountybeacon.program.controller;

import com.bountybeacon.program.dto.ProgramDto;
import com.bountybeacon.program.dto.ProgramResponse;
import com.bountybeacon.program.service.ProgramService;
import com.bountybeacon.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.PROGRAMS_ENDPOINT)
@RequiredArgsConstructor
public class ProgramController {
    private final ProgramService programService;

    @GetMapping
    public ResponseEntity<ProgramResponse> getAllPrograms(Pageable pageable) {
        return ResponseEntity.ok(programService.getAllPrograms(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramDto> getProgramById(@PathVariable Long id) {
        return ResponseEntity.ok(programService.getProgramById(id));
    }
}
