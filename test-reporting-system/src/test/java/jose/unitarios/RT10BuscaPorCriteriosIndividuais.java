package jose.unitarios;

import entity.UsuarioProfissional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repository.UsuarioProfissionalRepository;
import service.UsuarioProfissionalService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class RT10BuscaPorCriteriosIndividuais {

    UsuarioProfissionalService usuarioProfissionalService;

    UsuarioProfissional profissional1 = new UsuarioProfissional("Ana", "Limpeza", "Limpeza geral", "Blumenau");
    UsuarioProfissional profissional3 = new UsuarioProfissional("Carlos", "Jardinagem", "Corte de grama", "Blumenau");
    UsuarioProfissional profissional2 = new UsuarioProfissional("Bia", "Limpeza", "Limpeza de sofás", "Gaspar");


    @BeforeEach
    public void setUp() {
        UsuarioProfissionalRepository usuarioProfissionalRepository= Mockito.mock(UsuarioProfissionalRepository.class);



        List<UsuarioProfissional> listaMock = Arrays.asList(
                profissional1,
                profissional2,
                profissional3
        );

        when(usuarioProfissionalRepository.ListaUsuariosProfissionais()).thenReturn(listaMock);
        usuarioProfissionalService = new UsuarioProfissionalService(usuarioProfissionalRepository);
    }

    @Test
    public void buscaPorPalavraChaveTest(){
        //Arrange
        String palavraChave = "limpeza";
        String categoria = null;
        String localizacao = null;

        //Act
        List<UsuarioProfissional> resultado = usuarioProfissionalService.buscarServicos(palavraChave, categoria, localizacao);

        //Assert
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(profissional1));
        assertTrue(resultado.contains(profissional2));
        System.out.println(resultado);

    }

    @Test
    public void buscaPorCategoriaTest(){
        //Arrange
        String palavraChave = null;
        String categoria = "jardinagem";
        String localizacao = null;

        //Act
        List<UsuarioProfissional> resultado = usuarioProfissionalService.buscarServicos(palavraChave, categoria, localizacao);

        //Assert
        assertEquals(1, resultado.size());
        assertEquals(profissional3, resultado.getFirst());
        System.out.println(resultado);

    }

    @Test
    public void buscaPorLocalizacao(){
        //Arrange
        String palavraChave = null;
        String categoria = null;
        String localizacao = "Blumenau";

        //Act
        List<UsuarioProfissional> resultado = usuarioProfissionalService.buscarServicos(palavraChave, categoria, localizacao);

        //Assert
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(profissional1));
        assertTrue(resultado.contains(profissional3));
        System.out.println(resultado);

    }

}
