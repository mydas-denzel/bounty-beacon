package com.bountybeacon.provider.hackerone;

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
class HackerOneProviderTest {

    @Mock
    private HackerOneClient client;

    @Mock
    private HackerOneMapper mapper;

    private HackerOneProvider provider;

    @BeforeEach
    void setUp() {
        provider = new HackerOneProvider(client, mapper);
    }

    @Test
    void getType_ShouldReturnHackerOne() {
        assertThat(provider.getType()).isEqualTo(ProviderType.HACKERONE);
    }

    @Test
    void fetchPrograms_ShouldReturnMappedPrograms() {
        // Given
        HackerOneResponse response = new HackerOneResponse();
        Program program = Program.builder().name("Test Program").build();
        
        when(client.fetchPrograms()).thenReturn(Mono.just(response));
        when(mapper.toPrograms(response)).thenReturn(List.of(program));

        // When
        List<Program> result = provider.fetchPrograms();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test Program");
        verify(client).fetchPrograms();
        verify(mapper).toPrograms(response);
    }

    @Test
    void fetchPrograms_ShouldReturnEmptyList_WhenExceptionOccurs() {
        // Given
        when(client.fetchPrograms()).thenThrow(new RuntimeException("API Error"));

        // When
        List<Program> result = provider.fetchPrograms();

        // Then
        assertThat(result).isEmpty();
    }
}
