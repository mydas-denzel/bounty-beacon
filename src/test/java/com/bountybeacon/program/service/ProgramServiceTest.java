package com.bountybeacon.program.service;

import com.bountybeacon.exception.ProgramNotFoundException;
import com.bountybeacon.program.dto.ProgramDto;
import com.bountybeacon.program.dto.ProgramResponse;
import com.bountybeacon.program.entity.Program;
import com.bountybeacon.program.mapper.ProgramMapper;
import com.bountybeacon.program.repository.ProgramRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProgramServiceTest {

    @Mock
    private ProgramRepository programRepository;

    @Mock
    private ProgramMapper programMapper;

    private ProgramService programService;

    @BeforeEach
    void setUp() {
        programService = new ProgramService(programRepository, programMapper);
    }

    @Test
    void getAllPrograms_ShouldReturnProgramResponse() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Program program = Program.builder().id(1L).name("Test Program").build();
        Page<Program> page = new PageImpl<>(List.of(program));
        ProgramDto dto = ProgramDto.builder().id(1L).name("Test Program").build();

        when(programRepository.findAll(pageable)).thenReturn(page);
        when(programMapper.toDto(program)).thenReturn(dto);

        // When
        ProgramResponse response = programService.getAllPrograms(pageable);

        // Then
        assertThat(response.getPrograms()).hasSize(1);
        assertThat(response.getPrograms().get(0).getName()).isEqualTo("Test Program");
        assertThat(response.getTotal()).isEqualTo(1);
    }

    @Test
    void getProgramById_ShouldReturnDto_WhenExists() {
        // Given
        Long id = 1L;
        Program program = Program.builder().id(id).name("Test").build();
        ProgramDto dto = ProgramDto.builder().id(id).name("Test").build();

        when(programRepository.findById(id)).thenReturn(Optional.of(program));
        when(programMapper.toDto(program)).thenReturn(dto);

        // When
        ProgramDto result = programService.getProgramById(id);

        // Then
        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    void getProgramById_ShouldThrowException_WhenNotFound() {
        // Given
        when(programRepository.findById(1L)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> programService.getProgramById(1L))
                .isInstanceOf(ProgramNotFoundException.class);
    }

    @Test
    void saveOrUpdate_ShouldUpdateExistingProgram() {
        // Given
        Program existing = Program.builder().id(1L).handle("test").provider("H1").name("Old Name").build();
        Program fetched = Program.builder().handle("test").provider("H1").name("New Name").build();

        when(programRepository.findByHandleAndProvider("test", "H1")).thenReturn(Optional.of(existing));
        when(programRepository.save(any(Program.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Program result = programService.saveOrUpdate(fetched);

        // Then
        assertThat(result.getName()).isEqualTo("New Name");
        verify(programRepository).save(existing);
    }

    @Test
    void saveOrUpdate_ShouldCreateNewProgram() {
        // Given
        Program fetched = Program.builder().handle("new").provider("H1").name("New Program").build();

        when(programRepository.findByHandleAndProvider("new", "H1")).thenReturn(Optional.empty());
        when(programRepository.save(fetched)).thenReturn(fetched);

        // When
        Program result = programService.saveOrUpdate(fetched);

        // Then
        assertThat(result).isEqualTo(fetched);
        verify(programRepository).save(fetched);
    }
}
