package com.bountybeacon.program.repository;

import com.bountybeacon.program.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    Optional<Program> findByHandleAndProvider(String handle, String provider);
}
