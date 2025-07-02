package jonathan.unitarios.usuarioProfissional;

import model.UsuarioProfissional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.UsuarioProfissionalRepository;
import service.CepService;
import service.UsuarioProfissionalService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestaUsuarioProfissional {

    @Mock
    private UsuarioProfissionalRepository usuarioProfissionalRepository;

    @Mock
    private CepService cepService;

    UsuarioProfissionalService usuarioProfissionalService;

    UsuarioProfissional usuarioProfissional;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.usuarioProfissionalService = new UsuarioProfissionalService(usuarioProfissionalRepository, cepService);

        usuarioProfissional = new UsuarioProfissional();
        usuarioProfissional.setCPF("14375329692");
        usuarioProfissional.setTelefone("37993574479");
        usuarioProfissional.setSenha("jonathan123");
        usuarioProfissional.setAreaAtuacao("Pedreiro");
        usuarioProfissional.setEmail("jonathan@gmail.com");
    }

    //CT11
    @Test
    public void testaValidadeCPFUserProfissional() {
        //Arrange
        UsuarioProfissional usuarioProfissional = new UsuarioProfissional();
        usuarioProfissional.setCPF("14375329692");
        usuarioProfissional.setTelefone("37993574479");
        usuarioProfissional.setSenha("jon");
        usuarioProfissional.setAreaAtuacao("Pedreiro");

        //Act
        boolean resultado = usuarioProfissionalService.create(usuarioProfissional);

        //Assert
        assertEquals(true, resultado);
        verify(usuarioProfissionalRepository, times(1)).create(usuarioProfissional);
    }

    //CT12
    @Test
    public void testaValidadeTelefoneUserProfissional() {
        //Arrange
        UsuarioProfissional usuarioProfissional = new UsuarioProfissional();
        usuarioProfissional.setCPF("14375329692");
        usuarioProfissional.setSenha("jon");
        usuarioProfissional.setAreaAtuacao("Pedreiro");

        usuarioProfissional.setTelefone("37993574479");

        //Act
        boolean resultado = usuarioProfissionalService.create(usuarioProfissional);

        //Assert
        assertEquals(true, resultado);
        verify(usuarioProfissionalRepository, times(1)).create(usuarioProfissional);
    }

    //CT13
    @Test
    public void testaValidadeSenhaUserProfissional() {
        //Arrange
        UsuarioProfissional usuarioProfissional = new UsuarioProfissional();
        usuarioProfissional.setCPF("14375329692");
        usuarioProfissional.setTelefone("37993574479");
        usuarioProfissional.setAreaAtuacao("Pedreiro");

        usuarioProfissional.setSenha("");

        //Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioProfissionalService.create(usuarioProfissional);
        });

        //Assert
        assertEquals("Senha é obrigatória.", exception.getMessage());
    }

    //CT14
    @Test
    public void testaValidadeAreaAtuacaoUserProfissional() {
        //Arrange
        UsuarioProfissional usuarioProfissional = new UsuarioProfissional();
        usuarioProfissional.setCPF("14375329692");
        usuarioProfissional.setTelefone("37993574479");
        usuarioProfissional.setSenha("jon");

        usuarioProfissional.setAreaAtuacao("");

        //Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioProfissionalService.create(usuarioProfissional);
        });

        //Assert
        assertEquals("Área de atuação inválida! Insira uma área de atuação válida para prosseguir.", exception.getMessage());
    }

    //CT18
    @Test
    public void testaAlteracaoAreaAtuacaoUserProfissional() {
        // Arrange
        UsuarioProfissional usuarioProfissional = new UsuarioProfissional();
        usuarioProfissional.setCPF("14375329692");
        usuarioProfissional.setTelefone("37993574479");
        usuarioProfissional.setSenha("jonathan123");
        usuarioProfissional.setAreaAtuacao("Eletricista");

        usuarioProfissionalService.create(usuarioProfissional);

        String novaAreaAtuacao = "Pedreiro";

        // Act
        boolean resultado = usuarioProfissionalService.update(usuarioProfissional.getCPF(), novaAreaAtuacao);

        // Assert
        assertEquals(true, resultado);
        verify(usuarioProfissionalRepository, times(1)).create(usuarioProfissional);
    }

    //CT19
    @Test
    public void testaAdicaoDeHabilidadesUserProfissional() {
        // Arrange
        UsuarioProfissional usuarioProfissional = new UsuarioProfissional();
        usuarioProfissional.setCPF("14375329692");
        usuarioProfissional.setTelefone("37993574479");
        usuarioProfissional.setEmail("jonathan@gmail.com");
        usuarioProfissional.setSenha("jonathan123");
        usuarioProfissional.setAreaAtuacao("Eletricista");

        String novaHabilidade = "Agilidade";

        when(usuarioProfissionalRepository.findByCPF(usuarioProfissional.getCPF()))
                .thenReturn(usuarioProfissional);
        // Act
        boolean resultado = usuarioProfissionalService.adicionarHabilidade(usuarioProfissional.getCPF(), novaHabilidade);

        // Assert
        assertEquals(true, resultado);
        verify(usuarioProfissionalRepository, times(1)).findByCPF(usuarioProfissional.getCPF());
    }
}
