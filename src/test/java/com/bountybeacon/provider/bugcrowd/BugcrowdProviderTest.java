package com.bountybeacon.provider.bugcrowd;

import com.bountybeacon.program.entity.Program;
import com.bountybeacon.provider.ProviderType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BugcrowdProviderTest {

    @Mock
    private BugcrowdClient client;

    @Mock
    private BugcrowdMapper mapper;

    private BugcrowdProvider provider;

    @BeforeEach
    void setUp() {
        provider = new BugcrowdProvider(client, mapper);
    }

    @Test
    void getType_ShouldReturnBugcrowd() {
        assertThat(provider.getType()).isEqualTo(ProviderType.BUGCROWD);
    }

    @Test
    void fetchPrograms_ShouldReturnMappedPrograms() {
        // Given
        BugcrowdResponse response = new BugcrowdResponse();
        Program program = Program.builder().name("Test Bugcrowd").build();
        
        when(client.fetchPrograms()).thenReturn(Mono.just(response));
        when(mapper.toPrograms(response)).thenReturn(List.of(program));

        // When
        List<Program> result = provider.fetchPrograms();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test Bugcrowd");
        verify(client).fetchPrograms();
        verify(mapper).toPrograms(response);
    }

    @Test
    void fetchPrograms_ShouldReturnEmptyList_WhenExceptionOccurs() {
        // Given
        when(client.fetchPrograms()).thenThrow(new RuntimeException("Bugcrowd Error"));

        // When
        List<Program> result = provider.fetchPrograms();

        // Then
        assertThat(result).isEmpty();
    }
}
