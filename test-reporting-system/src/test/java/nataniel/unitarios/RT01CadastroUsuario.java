package nataniel.unitarios;

import model.Usuario;
import org.junit.jupiter.api.Test;
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
            String nome = "";
            String CPF = "12345678909";
            String email = "nataniel@gmail.com";
            String telefone = "47912341234";
            String senha = "1234";

        // Act
        Exception exception = null;
        try {
            new Usuario(nome, CPF, email, telefone, senha);
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
            String nome = "Nataniel";
            String CPF = "123.456.789-09";
            String email = "nataniel@gmail.com";
            String telefone = "47912341234";
            String senha = "1234";

        // Act
            Exception exception = null;
            try {
                new Usuario(nome, CPF, email, telefone, senha);
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
            String nome = "Nataniel";
            String CPF = "123456789";
            String email = "nataniel@gmail.com";
            String telefone = "47912341234";
            String senha = "1234";
        // Act
            Exception exception = null;
            try {
                new Usuario(nome, CPF, email, telefone, senha);
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
            String nome = "Nataniel";
            String CPF = "11122233344499";
            String email = "nataniel@gmail.com";
            String telefone = "47912341234";
            String senha = "1234";
        // Act
            Exception exception = null;
            try {
                new Usuario(nome, CPF, email, telefone, senha);
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
            String nome = "Nataniel";
            String CPF = "12345678909";
            String email = "nataniel@gmail.com";
            String telefone = "4712341234";
            String senha = "1234";
        // Act
            Exception exception = null;
            try {
                new Usuario(nome, CPF, email, telefone, senha);
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
            String nome = "Nataniel";
            String CPF = "12345678909";
            String email = "nataniel@gmail.com";
            String telefone = "447912341234";
            String senha = "1234";
        // Act
            Exception exception = null;
            try {
                new Usuario(nome, CPF, email, telefone, senha);
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
            String nome = "Nataniel";
            String CPF = "12345678909";
            String email = "nataniel@gmail.com";
            String telefone = "4791U34G234";
            String senha = "1234";
        // Act
            Exception exception = null;
            try {
                new Usuario(nome, CPF, email, telefone, senha);
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
            String nome = "Nataniel";
            String CPF = "12345678909";
            String email = "nataniel.gmail.com";
            String telefone = "47912341234";
            String senha = "1234";
        // Act
            Exception exception = null;
            try {
                new Usuario(nome, CPF, email, telefone, senha);
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

        // Act

        // Assert
    }
}
