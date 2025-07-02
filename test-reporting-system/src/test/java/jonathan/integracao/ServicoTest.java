package jonathan.integracao;

import entity.Usuario;
import entity.UsuarioProfissional;
import org.junit.jupiter.api.BeforeEach;
import entity.Servico;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServicoTest {
    private Usuario clientePadrao;
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

//    @Test
//    public void testRetornoListaServicosMaisPrestadosEOrdenacao() {
//        // Arrange
//        Servico gerenciadorDeServicos = new Servico();
//
//        for (int i = 0; i < 5; i++) {
//            gerenciadorDeServicos.addServico(new Servico("Marcenaria"));
//        }
//
//        for (int i = 0; i < 3; i++) {
//            gerenciadorDeServicos.addServico(new Servico("Construção civil"));
//        }
//
//        for (int i = 0; i < 2; i++) {
//            gerenciadorDeServicos.addServico(new Servico("Encanador"));
//        }
//
//        List<String> listaRankingServicos = gerenciadorDeServicos.getListaServicosMaisPrestadosOrdenando();
//
//        // Assert
//        assertNotNull(listaRankingServicos, "A lista de ranking de serviços não deve ser nula.");
//        assertFalse(listaRankingServicos.isEmpty(), "A lista de ranking de serviços não deve estar vazia.");
//
//        // Verifica se a lista contém as 3 categorias esperadas
//        assertEquals(3, listaRankingServicos.size(), "A lista deve conter 3 tipos de serviços ranqueados.");
//
//        // Verifica a ordem dos tipos de serviço
//        assertEquals("Marcenaria", listaRankingServicos.get(0), "O primeiro serviço ranqueado deve ser 'Marcenaria'.");
//        assertEquals("Construção civil", listaRankingServicos.get(1), "O segundo serviço ranqueado deve ser 'Construção civil'.");
//        assertEquals("Encanador", listaRankingServicos.get(2), "O terceiro serviço ranqueado deve ser 'Encanador'.");
//    }
}
