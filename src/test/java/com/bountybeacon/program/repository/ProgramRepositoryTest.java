package com.bountybeacon.program.repository;

import com.bountybeacon.program.entity.Program;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProgramRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProgramRepository programRepository;

    @Test
    void findByHandleAndProvider_ShouldReturnProgram() {
        // Given
        Program program = Program.builder()
                .name("Test Program")
                .handle("test-handle")
                .provider("H1")
                .build();
        entityManager.persistAndFlush(program);

        // When
        Optional<Program> found = programRepository.findByHandleAndProvider("test-handle", "H1");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Test Program");
    }

    @Test
    void findByHandleAndProvider_ShouldReturnEmpty_WhenNotFound() {
        // When
        Optional<Program> found = programRepository.findByHandleAndProvider("non-existent", "H1");

        // Then
        assertThat(found).isEmpty();
    }
}
