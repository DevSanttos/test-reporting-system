package jonathan.unitarios.autenticacao;

import model.UsuarioProfissional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.Autenticador;
import service.UsuarioProfissionalService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AutenticadorTest {
    @Mock
    UsuarioProfissionalService usuarioProfissionalService;

    @InjectMocks
    private Autenticador autenticador;

    @Mock
    private UsuarioProfissional usuarioProfissional;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        usuarioProfissional = new UsuarioProfissional();
        usuarioProfissional.setCPF("14375329692");
        usuarioProfissional.setTelefone("37993574479");
        usuarioProfissional.setAreaAtuacao("Pedreiro");
        usuarioProfissional.setEmail("jonathan@gmail.com");
        usuarioProfissional.setSenha("jonathan123");
    }

    //CT15
    @Test
    public void testaValidadeLoginSenhaUserProfissionalComEntradasCorretas() {
        // Arrange
        when(usuarioProfissionalService.findByEmail(usuarioProfissional.getEmail()))
                .thenReturn(usuarioProfissional);

        //Act
        boolean resultado = autenticador.autenticar(usuarioProfissional.getEmail(), usuarioProfissional.getSenha());

        //Assert
        assertEquals(true, resultado);
        verify(usuarioProfissionalService, times(1)).findByEmail(usuarioProfissional.getEmail());
    }

    //CT16
    @Test
    public void testaValidadeLoginSenhaUserProfissionalComEntradasIncorretas() {
        //Arrange
        String senhaInvalida = "natan123";
        when(usuarioProfissionalService.findByEmail(usuarioProfissional.getEmail()))
                .thenReturn(usuarioProfissional);

        //Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            autenticador.autenticar(usuarioProfissional.getEmail(), senhaInvalida);
        });

        //Assert
        assertEquals("Senha incorreta.", exception.getMessage());
        verify(usuarioProfissionalService, times(1)).findByEmail(usuarioProfissional.getEmail());
    }

    //CT17
    @Test
    public void testaValidadeLoginSenhaUserProfissionalComEntradasInvalidas() {
        //Arrange
        String emailInvalido = "";
        when(usuarioProfissionalService.findByEmail(emailInvalido))
                .thenThrow(new IllegalArgumentException("E-mail inválido."));

        //Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            autenticador.autenticar(emailInvalido, "qualquerSenha");
        });

        //Assert
        assertEquals("E-mail inválido.", exception.getMessage());
        verify(usuarioProfissionalService, times(1)).findByEmail(emailInvalido);
    }
}
