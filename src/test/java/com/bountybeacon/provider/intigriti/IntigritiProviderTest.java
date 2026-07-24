package com.bountybeacon.provider.intigriti;

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
class IntigritiProviderTest {

    @Mock
    private IntigritiClient client;

    @Mock
    private IntigritiMapper mapper;

    private IntigritiProvider provider;

    @BeforeEach
    void setUp() {
        provider = new IntigritiProvider(client, mapper);
    }

    @Test
    void getType_ShouldReturnIntigriti() {
        assertThat(provider.getType()).isEqualTo(ProviderType.INTIGRITI);
    }

    @Test
    void fetchPrograms_ShouldReturnMappedPrograms() {
        // Given
        IntigritiResponse response = new IntigritiResponse();
        Program program = Program.builder().name("Test Intigriti").build();
        
        when(client.fetchPrograms()).thenReturn(Mono.just(response));
        when(mapper.toPrograms(response)).thenReturn(List.of(program));

        // When
        List<Program> result = provider.fetchPrograms();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test Intigriti");
        verify(client).fetchPrograms();
        verify(mapper).toPrograms(response);
    }

    @Test
    void fetchPrograms_ShouldReturnEmptyList_WhenExceptionOccurs() {
        // Given
        when(client.fetchPrograms()).thenThrow(new RuntimeException("Intigriti Error"));

        // When
        List<Program> result = provider.fetchPrograms();

        // Then
        assertThat(result).isEmpty();
    }
}
