package jonathan.unitarios.servico;

import model.Servico;
import model.UsuarioProfissional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServicoTeste {
    private UsuarioProfissional profissionalPadrao;

    @BeforeEach
    public void setUp() {
        profissionalPadrao = new UsuarioProfissional();
        profissionalPadrao.setCPF("14375329692");
        profissionalPadrao.setTelefone("37993574479");
        profissionalPadrao.setAreaAtuacao("Pedreiro");
        profissionalPadrao.setEmail("jonathan@gmail.com");
        profissionalPadrao.setSenha("jonathan123");
    }

    @Test
    public void testRetornoListaPreenchidaComRegistrosDeServicos() {
        // Arrange
        Servico gerenciadorDeServicos = new Servico();

        Servico servico1 = new Servico();
        servico1.setId(1);

        Servico servico2 = new Servico();
        servico2.setId(2);

        gerenciadorDeServicos.addServico(servico1);
        gerenciadorDeServicos.addServico(servico2);

        List<Servico> listaDeServicosRetornada = gerenciadorDeServicos.getListaServicosMaisPrestados();

        // Assert
        assertNotNull(listaDeServicosRetornada, "A lista de serviços não deve ser nula.");
        assertEquals(2, listaDeServicosRetornada.size(), "A lista deve conter 2 serviços.");
        assertTrue(listaDeServicosRetornada.contains(servico1), "A lista deve conter o serviço 1.");
        assertTrue(listaDeServicosRetornada.contains(servico2), "A lista deve conter o serviço 2.");
    }

    @Test
    public void testRetornoListaVaziaSemRegistrosDeServicos() {
        // Arrange
        Servico gerenciadorDeServicosVazio = new Servico();

        //Act
        List<Servico> listaDeServicosRetornada = gerenciadorDeServicosVazio.getListaServicosMaisPrestados();

        // Assert
        assertNotNull(listaDeServicosRetornada, "A lista de serviços não deve ser nula, mesmo que vazia.");
        assertTrue(listaDeServicosRetornada.isEmpty(), "A lista de serviços deve estar vazia.");
    }
}
