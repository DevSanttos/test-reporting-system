package jonathan.unitarios;

import model.UsuarioProfissional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.UsuarioProfissionalRepository;
import service.Autenticador;
import service.UsuarioProfissionalService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestaUsuarioProfissional {
    UsuarioProfissionalRepository usuarioProfissionalRepository;
    UsuarioProfissionalService usuarioProfissionalService;

    @BeforeEach
    public void setUp() {
        this.usuarioProfissionalRepository = new UsuarioProfissionalRepository();
        this.usuarioProfissionalService = new UsuarioProfissionalService(usuarioProfissionalRepository);
    }

    //CT11
    @Test
    public void testaValidadeCPFUserProfissional() {
        //Arrange
        UsuarioProfissional usuarioProfissional = new UsuarioProfissional();
        usuarioProfissional.setCPF("14375329692");
        usuarioProfissional.setTelefone("37993574479");
        usuarioProfissional.setSenha("jon");
        usuarioProfissional.setAreaAtuacao("");

        //Act
        boolean resultado = usuarioProfissionalService.create(usuarioProfissional);

        //Assert
        assertEquals(true, resultado);
    }

    //CT12
    @Test
    public void testaValidadeTelefoneUserProfissional() {
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
    }

    //CT13
    @Test
    public void testaValidadeSenhaUserProfissional() {
        //Arrange
        UsuarioProfissional usuarioProfissional = new UsuarioProfissional();
        usuarioProfissional.setCPF("14375329692");
        usuarioProfissional.setTelefone("37993574479");
        usuarioProfissional.setSenha("jon");
        usuarioProfissional.setAreaAtuacao("Pedreiro");

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

    //CT15
    @Test
    public void testaValidadeLoginSenhaUserProfissionalComEntradasCorretas() {
        //Arrange
        UsuarioProfissional usuarioProfissional = new UsuarioProfissional();
        Autenticador autenticador = new Autenticador();

        usuarioProfissional.setEmail("jonathan@gmail.com");
        usuarioProfissional.setSenha("jonathan123");
        usuarioProfissionalService.create(usuarioProfissional);

        //Act
        boolean resultado = autenticador.autenticar(usuarioProfissional.getEmail(), usuarioProfissional.getSenha());

        //Assert
        assertEquals(true, resultado);
    }

    //CT16
    @Test
    public void testaValidadeLoginSenhaUserProfissionalComEntradasIncorretas() {
        //Arrange
        UsuarioProfissional usuarioProfissional = new UsuarioProfissional();
        Autenticador autenticador = new Autenticador();

        usuarioProfissional.setEmail("jonathan@gmail.com");
        usuarioProfissional.setSenha("jonathan123");
        usuarioProfissionalService.create(usuarioProfissional);
        String senhaInvalida = "natan123";

        //Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            autenticador.autenticar(usuarioProfissional.getEmail(), senhaInvalida);
        });

        //Assert
        assertEquals("E-mail ou senha incorretos.", exception.getMessage());
    }

    //CT17
    @Test
    public void testaValidadeLoginSenhaUserProfissionalComEntradasInvalidas() {
        //Arrange
        UsuarioProfissional usuarioProfissional = new UsuarioProfissional();
        Autenticador autenticador = new Autenticador();

        String emailInvalido = "";
        String senhaInvalida = "";

        //Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            autenticador.autenticar(emailInvalido, senhaInvalida);
        });

        //Assert
        assertEquals("E-mail incorreto.", exception.getMessage());
    }

    //CT18
    
}
