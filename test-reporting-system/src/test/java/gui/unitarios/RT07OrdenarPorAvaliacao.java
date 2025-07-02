package gui.unitarios;

import entity.Servico;
import entity.Status;
import entity.UsuarioProfissional;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RT07OrdenarPorAvaliacao {

    @Test
    public void priorizaProfissionaisComAvaliacoesDiversas(){
        // Arrange
        UsuarioProfissional p1 = new UsuarioProfissional();
        p1.receberAvaliacao(new Servico("Servico 1", "Categoria", Status.FINALIZADO, null, p1), 5);
        p1.receberAvaliacao(new Servico("Servico 2", "Categoria", Status.FINALIZADO, null, p1), 4);

        UsuarioProfissional p2 = new UsuarioProfissional();
        p2.receberAvaliacao(new Servico("Servico 3", "Categoria", Status.FINALIZADO, null, p2), 2);
        p2.receberAvaliacao(new Servico("Servico 4", "Categoria", Status.FINALIZADO, null, p2), 3);

        UsuarioProfissional p3 = new UsuarioProfissional();
        p3.receberAvaliacao(new Servico("Servico 5", "Categoria", Status.FINALIZADO, null, p3), 4);
        p3.receberAvaliacao(new Servico("Servico 6", "Categoria", Status.FINALIZADO, null, p3), 4);

        List<UsuarioProfissional> lista = Arrays.asList(p1, p2, p3);

        // Act
        UsuarioProfissional.ordenarPorMediaAvaliacoes(lista);

        // Assert
        assertEquals(3, lista.size());
        assertEquals(p1, lista.get(0));  // Média 4.5
        assertEquals(p3, lista.get(1));  // Média 4.0
        assertEquals(p2, lista.get(2));  // Média 2.5
    }

    @Test
    public void priorizaProfissionaisComAvaliacoesIguais(){
        // Arrange
        UsuarioProfissional p1 = new UsuarioProfissional();
        p1.receberAvaliacao(new Servico("Servico 1", "Categoria", Status.FINALIZADO, null, p1), 4);
        p1.receberAvaliacao(new Servico("Servico 2", "Categoria", Status.FINALIZADO, null, p1), 4);

        UsuarioProfissional p2 = new UsuarioProfissional();
        p2.receberAvaliacao(new Servico("Servico 3", "Categoria", Status.FINALIZADO, null, p2), 4);
        p2.receberAvaliacao(new Servico("Servico 4", "Categoria", Status.FINALIZADO, null, p2), 4);

        UsuarioProfissional p3 = new UsuarioProfissional();
        p3.receberAvaliacao(new Servico("Servico 5", "Categoria", Status.FINALIZADO, null, p3), 4);
        p3.receberAvaliacao(new Servico("Servico 6", "Categoria", Status.FINALIZADO, null, p3), 2);

        List<UsuarioProfissional> lista = Arrays.asList(p1, p2, p3);

        // Act
        UsuarioProfissional.ordenarPorMediaAvaliacoes(lista);

        // Assert
        assertEquals(3, lista.size());
        assertEquals(4.0, lista.get(0).getMediaAvaliacoes());
        assertEquals(4.0, lista.get(1).getMediaAvaliacoes());
        assertEquals(3.0, lista.get(2).getMediaAvaliacoes());
        assertEquals(p1, lista.get(0));
        assertEquals(p2, lista.get(1));
        assertEquals(p3, lista.get(2));
    }

}
