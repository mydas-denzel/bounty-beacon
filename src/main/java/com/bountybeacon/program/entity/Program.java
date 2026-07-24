package com.bountybeacon.program.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "programs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String handle;

    @Column(nullable = false)
    private String provider;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String url;

    private String logoUrl;

    private boolean bounty;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime lastPolledAt;
}
