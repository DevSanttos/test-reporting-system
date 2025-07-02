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

public class RT11BuscaComCriteriosCombinados {

    UsuarioProfissionalService usuarioProfissionalService;

    UsuarioProfissional profissional1 = new UsuarioProfissional("João", "Encanador", "Reparos hidráulicos", "Ibirama");
    UsuarioProfissional profissional2 = new UsuarioProfissional("Ângela", "Eletricista", "Reparos elétricos", "Ibirama");
    UsuarioProfissional profissional3 = new UsuarioProfissional("Carlos", "Jardineiro", "Jardinagem", "Ibirama");
    UsuarioProfissional profissional4 = new UsuarioProfissional("Bruna", "Encanador", "Reparos hidráulicos", "Blumenau");

    @BeforeEach
    public void setUp() {
        UsuarioProfissionalRepository usuarioProfissionalRepository= Mockito.mock(UsuarioProfissionalRepository.class);

        List<UsuarioProfissional> listaMock = Arrays.asList(
                profissional1,
                profissional2,
                profissional3,
                profissional4
        );

        when(usuarioProfissionalRepository.ListaUsuariosProfissionais()).thenReturn(listaMock);
        usuarioProfissionalService = new UsuarioProfissionalService(usuarioProfissionalRepository);
    }

    @Test
    public void buscaCombinadaTest(){
        //Arrange
        String palavraChave = "Reparos";
        String categoria = "Encanador";
        String localizacao = "Ibirama";

        //Act
        List<UsuarioProfissional> resultado = usuarioProfissionalService.buscarServicos(palavraChave, categoria, localizacao);



        //Assert
        assertEquals(1, resultado.size());
        assertEquals("João", resultado.getFirst().getNome());
        resultado.forEach(p -> System.out.println(p.getNome()));
    }
}
