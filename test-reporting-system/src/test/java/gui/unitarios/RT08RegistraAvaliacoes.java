package gui.unitarios;

import model.Servico;
import model.Status;
import model.Usuario;
import model.UsuarioProfissional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RT08RegistraAvaliacoes {

    @Test
    public void clienteAvaliaProfissional(){
        // Arrange
        Usuario cliente = new Usuario();
        UsuarioProfissional profissional = new UsuarioProfissional();
        Servico servico = new Servico("Reforma elétrica", "Elétrica", Status.FINALIZADO, cliente, profissional);

        int nota = 5;

        // Act
        profissional.receberAvaliacao(servico, nota);

        // Assert
        assertEquals(nota, profissional.getAvaliacoes().get(0), "A nota registrada deve ser a mesma informada.");

    }

    @Test
    public void profissionalAvaliaCliente(){
        // Arrange
        Usuario cliente = new Usuario();
        UsuarioProfissional profissional = new UsuarioProfissional();
        Servico servico = new Servico("Reforma elétrica", "Elétrica", Status.FINALIZADO, cliente, profissional);

        int nota = 4;

        // Act
        cliente.receberAvaliacao(servico, nota);

        // Assert
        assertEquals(nota, cliente.getAvaliacoes().get(0), "A nota registrada deve ser a mesma informada.");

    }

    @Test
    public void deveFalharAvaliacaoSemServicoConcluido(){

        Usuario cliente = new Usuario();
        UsuarioProfissional profissional = new UsuarioProfissional();
        Servico servico = new Servico("Reforma elétrica", "Elétrica", Status.EM_ANDAMENTO, cliente, profissional);

        int nota = 5;

        Exception exception = null;
        try {
            profissional.receberAvaliacao(servico, nota);
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        assertEquals("Serviço precisa estar finalizado para ser avaliado.", exception.getMessage());

    }

    @Test
    public void deveFalharAvaliacaoComNumeroNegativo(){
        // Arrange
        Usuario cliente = new Usuario();
        UsuarioProfissional profissional = new UsuarioProfissional();
        Servico servico = new Servico("Pintura", "Reformas", Status.FINALIZADO, cliente, profissional);

        int notaNegativa = -3;

        // Act
        Exception exception = null;
        try {
            profissional.receberAvaliacao(servico, notaNegativa);
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        // Assert
        assertEquals("A nota deve ser um valor entre 1 e 5.", exception.getMessage());

    }

    @Test
    public void deveFalharAvaliacaoComValorDecimal(){
        // Arrange
        Usuario cliente = new Usuario();
        UsuarioProfissional profissional = new UsuarioProfissional();
        Servico servico = new Servico("Pintura", "Reformas", Status.FINALIZADO, cliente, profissional);

        double notaDecimal = 3.7;

        Exception exception = null;
        try {
            profissional.receberAvaliacaoDouble(servico, notaDecimal);
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        assertEquals("A nota deve ser um número inteiro entre 1 e 5.", exception.getMessage());

    }

    @Test
    public void deveFalharAvaliacaoComValorNaoNumerico(){

        Usuario cliente = new Usuario();
        UsuarioProfissional profissional = new UsuarioProfissional();
        Servico servico = new Servico("Consultoria", "Negócios", Status.FINALIZADO, cliente, profissional);

        String notaInvalida = "cinco";

        Exception exception = null;
        try {
            profissional.receberAvaliacaoString(servico, notaInvalida);
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        assertEquals("A nota deve ser um número inteiro entre 1 e 5.", exception.getMessage());

    }

    @Test
    public void deveFalharAvaliacaoComValorForaDoLimite(){
        Usuario cliente = new Usuario();
        UsuarioProfissional profissional = new UsuarioProfissional();
        Servico servico = new Servico("Reforma elétrica", "Elétrica", Status.FINALIZADO, cliente, profissional);

        int notaInvalida = 0;

        Exception exception = null;
        try {
            profissional.receberAvaliacao(servico, notaInvalida);
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        assertEquals("A nota deve ser um valor entre 1 e 5.", exception.getMessage());

    }


}
