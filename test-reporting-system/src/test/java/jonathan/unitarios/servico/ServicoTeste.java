package jonathan.unitarios.servico;

import entity.Servico;
import entity.UsuarioProfissional;
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

public class ServicoTeste {
    @Mock
    private ServicoRepository servicoRepository;

    @InjectMocks
    private ServicoService servicoService;

    private UsuarioProfissional profissionalPadrao;
    private Servico servicoPadrao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        profissionalPadrao = new UsuarioProfissional();
        profissionalPadrao.setCPF("14375329692");
        profissionalPadrao.setTelefone("37993574479");
        profissionalPadrao.setAreaAtuacao("Pedreiro");
        profissionalPadrao.setEmail("jonathan@gmail.com");
        profissionalPadrao.setSenha("jonathan123");

        servicoPadrao = new Servico();
        servicoPadrao.setTipoServico("Tipo Padrão");
    }

    @Test
    public void testServicoServiceCalculaRankingComDadosDoRepositorio() {
        // Arrange
        when(servicoRepository.contarServicosPorAreaAtuacao("marcenaria")).thenReturn(1);
        when(servicoRepository.contarServicosPorAreaAtuacao("construção civil")).thenReturn(2);
        when(servicoRepository.contarServicosPorAreaAtuacao("encanador")).thenReturn(3);
        when(servicoRepository.contarServicosPorAreaAtuacao("eletricista")).thenReturn(4);
        when(servicoRepository.contarServicosPorAreaAtuacao("jardineiro")).thenReturn(5);

        // Act
        servicoService.updateList();

        // Assert
        verify(servicoRepository, times(1)).contarServicosPorAreaAtuacao("marcenaria");
        verify(servicoRepository, times(1)).contarServicosPorAreaAtuacao("construção civil");
        verify(servicoRepository, times(1)).contarServicosPorAreaAtuacao("encanador");
        verify(servicoRepository, times(1)).contarServicosPorAreaAtuacao("eletricista");
        verify(servicoRepository, times(1)).contarServicosPorAreaAtuacao("jardineiro");

        List<String> ranking = servicoService.getListaServicosMaisPrestados();
        assertNotNull(ranking);
        assertFalse(ranking.isEmpty());

        assertEquals("jardineiro", ranking.get(0));
        assertEquals("eletricista", ranking.get(1));
        assertEquals("encanador", ranking.get(2));
    }

    @Test
    public void testRetornoListaRankingVaziaSemRegistrosDeServicos() {
        when(servicoRepository.contarServicosPorAreaAtuacao(anyString())).thenReturn(0);

        // Act
        servicoService.updateList();
        List<String> listaDeRankingRetornada = servicoService.getListaServicosMaisPrestados();

        // Assert
        assertNotNull(listaDeRankingRetornada, "A lista de ranking de serviços não deve ser nula, mesmo que vazia.");
        assertTrue(listaDeRankingRetornada.isEmpty(), "A lista de ranking de serviços deve estar vazia.");
        assertEquals(0, listaDeRankingRetornada.size(), "O tamanho da lista de ranking de serviços deve ser 0.");

        verify(servicoRepository, times(1)).contarServicosPorAreaAtuacao("marcenaria");
        verify(servicoRepository, times(1)).contarServicosPorAreaAtuacao("construção civil");
        verify(servicoRepository, times(1)).contarServicosPorAreaAtuacao("encanador");
        verify(servicoRepository, times(1)).contarServicosPorAreaAtuacao("eletricista");
        verify(servicoRepository, times(1)).contarServicosPorAreaAtuacao("jardineiro");
    }
}
