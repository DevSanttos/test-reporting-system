package nataniel.unitarios;

import entity.Usuario;
import org.junit.jupiter.api.Test;
import repository.UsuarioRepository;
import service.UsuarioService;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;


public class RT01CadastroUsuario {

    @Test
    public void deveCriarUsuario() {
        // Arrange
            String nome = "Nataniel";
            String CPF = "12345678909";
            String email = "nataniel@gmail.com";
            String telefone = "47912341234";
            String senha = "1234";
        // Act
            Usuario usuario = new Usuario(nome, CPF, email, telefone, senha);
        // Assert
            assertNotNull(usuario);
            assertEquals(nome, usuario.getNome());
            assertEquals(CPF, usuario.getCPF());
            assertEquals(email, usuario.getEmail());
            assertEquals(telefone, usuario.getTelefone());
            assertEquals(senha, usuario.getSenha());
    }

    @Test
    public void verificarDadoVazio() {
        // Arrange
            Exception exception = null;
            UsuarioService service = new UsuarioService(new UsuarioRepository());
            String nome = "";
            String CPF = "12345678909";
            String email = "nataniel@gmail.com";
            String telefone = "47912341234";
            String senha = "1234";
        // Act
            try {
                Usuario usuario = new Usuario(nome, CPF, email, telefone, senha);
                service.create(usuario);
            } catch (IllegalArgumentException e) {
                exception = e;
            }

        // Assert
            assertNotNull(exception);
            assertEquals("Campo nome é obrigatório", exception.getMessage());
    }

    @Test
    public void verificarSeRejeitaCpfComPontoEHifen() {
        // Arrange
            Exception exception = null;
            UsuarioService service = new UsuarioService(new UsuarioRepository());
            String nome = "Nataniel";
            String CPF = "123.456.789-09";
            String email = "nataniel@gmail.com";
            String telefone = "47912341234";
            String senha = "1234";

         // Act
            try {
                Usuario usuario = new Usuario(nome, CPF, email, telefone, senha);
                service.create(usuario);
            } catch (IllegalArgumentException e) {
                exception = e;
            }

         // Assert
            assertNotNull(exception);
            assertEquals("CPF deve conter apenas números", exception.getMessage());
    }

    @Test
    public void verificarSeRejeitaCpfComMenosDe11digitos() {
        // Arrange
            Exception exception = null;
            UsuarioService service = new UsuarioService(new UsuarioRepository());
            String nome = "Nataniel";
            String CPF = "123456789";
            String email = "nataniel@gmail.com";
            String telefone = "47912341234";
            String senha = "1234";

         // Act
            try {
                Usuario usuario = new Usuario(nome, CPF, email, telefone, senha);
                service.create(usuario);
            } catch (IllegalArgumentException e) {
                exception = e;
            }

         // Assert
            assertNotNull(exception);
            assertEquals("CPF deve conter 11 números", exception.getMessage());
    }

    @Test
    public void verificarSeRejeitaCpfComMaisDe11Digitos() {
        // Arrange
            Exception exception = null;
            UsuarioService service = new UsuarioService(new UsuarioRepository());
            String nome = "Nataniel";
            String CPF = "11122233344499";
            String email = "nataniel@gmail.com";
            String telefone = "47912341234";
            String senha = "1234";
        // Act
            try {
                Usuario usuario = new Usuario(nome, CPF, email, telefone, senha);
                service.create(usuario);
            } catch (IllegalArgumentException e) {
                exception = e;
            }
        // Assert
            assertNotNull(exception);
            assertEquals("CPF deve conter apenas 11 números", exception.getMessage());
    }

    @Test
    public void telefoneMenorQue11Digitos() {
        // Arrange
            Exception exception = null;
            UsuarioService service = new UsuarioService(new UsuarioRepository());
            String nome = "Nataniel";
            String CPF = "12345678909";
            String email = "nataniel@gmail.com";
            String telefone = "4712341234";
            String senha = "1234";
        // Act
            try {
                Usuario usuario = new Usuario(nome, CPF, email, telefone, senha);
                service.create(usuario);
            } catch (IllegalArgumentException e) {
                exception = e;
            }
        // Assert
            assertNotNull(exception);
            assertEquals("O número de telefone deve conter 11 dígitos", exception.getMessage());
    }

    @Test
    public void telefoneMaiorQue11Digitos() {
        // Arrange
            Exception exception = null;
            UsuarioService service = new UsuarioService(new UsuarioRepository());
            String nome = "Nataniel";
            String CPF = "12345678909";
            String email = "nataniel@gmail.com";
            String telefone = "447912341234";
            String senha = "1234";
        // Act
            try {
                Usuario usuario = new Usuario(nome, CPF, email, telefone, senha);
                service.create(usuario);
            } catch (IllegalArgumentException e) {
                exception = e;
            }
        // Assert
            assertNotNull(exception);
            assertEquals("O número de telefone deve conter 11 dígitos", exception.getMessage());
    }

    @Test
    public void verificarSeTelefoneContemLetra() {
        // Arrange
            Exception exception = null;
            UsuarioService service = new UsuarioService(new UsuarioRepository());
            String nome = "Nataniel";
            String CPF = "12345678909";
            String email = "nataniel@gmail.com";
            String telefone = "4791U34G234";
            String senha = "1234";
        // Act
            try {
                Usuario usuario = new Usuario(nome, CPF, email, telefone, senha);
                service.create(usuario);
            } catch (IllegalArgumentException e) {
                exception = e;
        }
        // Assert
            assertNotNull(exception);
            assertEquals("O número de telefone não deve conter letras", exception.getMessage());
    }

    @Test
    public void verificarSeRejeitaEmailSemArroba() {
        // Arrange
            Exception exception = null;
            UsuarioService service = new UsuarioService(new UsuarioRepository());
            String nome = "Nataniel";
            String CPF = "12345678909";
            String email = "nataniel.gmail.com";
            String telefone = "47912341234";
            String senha = "1234";
        // Act
            try {
                Usuario usuario = new Usuario(nome, CPF, email, telefone, senha);
                service.create(usuario);
            } catch (IllegalArgumentException e) {
                exception = e;
            }
        // Assert
            assertNotNull(exception);
            assertEquals("E-mail não contém @", exception.getMessage());
    }

    @Test
    public void verificarSeCpfEhValido() {
        // Arrange
            UsuarioRepository mockUsuarioRepository = mock(UsuarioRepository.class);
            UsuarioService service = new UsuarioService(mockUsuarioRepository);
            String nome = "Nataniel";
            String CPF = "12345678909";
            String email = "nataniel@gmail.com";
            String telefone = "47123412345";
            String senha = "1234";
            Exception exception = null;
        // Act
            try {
                Usuario usuario = new Usuario(nome, CPF, email, telefone, senha);
                service.create(usuario);
            } catch (IllegalArgumentException e) {
                exception = e;
            }

        // Assert
            assertNull(exception);
    }

}
