package jose.unitarios;

import entity.Status;
import entity.Usuario;
import service.ServicoService;
import entity.UsuarioProfissional;
import repository.ServicoRepository;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import entity.Servico;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class RT13ProcessoDeContratacao {

    private ServicoService servicoService;
    private ServicoRepository servicoRepositoryMock;

    private UsuarioProfissional profissional1;
    private Usuario user1;

    @BeforeEach
    public void setUp() {
        servicoRepositoryMock = mock(ServicoRepository.class);
        servicoService = new ServicoService(servicoRepositoryMock);

        profissional1 = new UsuarioProfissional(
                "Osvaldo",
                "89089089078",
                "osvaldo980@gmail.com",
                "34989899090",
                "osvaldo2145",
                "eletricista"
        );
        profissional1.setDisponivelParaServico(true);
        profissional1.setHorarioAtuacao("Tarde");

        user1 = new Usuario(
                "Gerson",
                "56445645678",
                "gerson890@gmail.com",
                "34989896767",
                "nosreg540"
        );
    }

    @Test
    public void contratacaoEletricistaTest(){
        //Arrange
        String dataServico = "12/07/2025";
        String horaServico = "Tarde";

        Servico servicoEsperado = new Servico();
        servicoEsperado.setCliente(user1);
        servicoEsperado.setProfissional(profissional1);
        servicoEsperado.setCategoria(profissional1.getAreaAtuacao());
        servicoEsperado.setDescricao("Data: " + dataServico + ", Hora: " + horaServico);
        servicoEsperado.setStatus(Status.EM_ANDAMENTO);

        when(servicoRepositoryMock.salvarServico(any(Servico.class))).thenReturn(servicoEsperado);

        //Act
        Servico servico = servicoService.realizarContratacao(profissional1, user1, dataServico, horaServico);

        //Assert
        assertNotNull(servico);
        assertEquals(user1, servico.getCliente());
        assertEquals(profissional1, servico.getProfissional());
        assertEquals("eletricista", servico.getCategoria());
        assertEquals("Data: " + dataServico + ", Hora: " + horaServico, servico.getDescricao());
        assertEquals(Status.EM_ANDAMENTO, servico.getStatus());
        System.out.println(servico);
    }
}
