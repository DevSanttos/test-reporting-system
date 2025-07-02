package jonathan.integracao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.ServicoRepository;
import service.ServicoService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicoTest {

    @Mock
    private ServicoRepository servicoRepository;

    @InjectMocks
    private ServicoService servicoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //CT49
    @Test
    public void testRetornoListaServicosMaisPrestadosEOrdenacao() {
        // Arrange
        when(servicoRepository.contarServicosPorAreaAtuacao("encanador")).thenReturn(2);
        when(servicoRepository.contarServicosPorAreaAtuacao("construção civil")).thenReturn(3);
        when(servicoRepository.contarServicosPorAreaAtuacao("marcenaria")).thenReturn(5);

        // Act
        boolean atualizou = servicoService.atualizarLista();
        List<String> listaRankingServicos = servicoService.getListaServicosMaisPrestados();

        // Assert
        assertTrue(atualizou, "A lista deveria ter sido atualizada.");
        assertNotNull(listaRankingServicos, "A lista de ranking não deve ser nula.");
        assertEquals(3, listaRankingServicos.size(), "A lista deve conter 3 tipos de serviços.");

        assertEquals("marcenaria", listaRankingServicos.get(0), "O primeiro serviço do ranking deve ser 'marcenaria'.");
        assertEquals("construção civil", listaRankingServicos.get(1), "O segundo serviço do ranking deve ser 'construção civil'.");
        assertEquals("encanador", listaRankingServicos.get(2), "O terceiro serviço do ranking deve ser 'encanador'.");

        verify(servicoRepository, times(1)).contarServicosPorAreaAtuacao("marcenaria");
        verify(servicoRepository, times(1)).contarServicosPorAreaAtuacao("construção civil");
        verify(servicoRepository, times(1)).contarServicosPorAreaAtuacao("encanador");
    }
}