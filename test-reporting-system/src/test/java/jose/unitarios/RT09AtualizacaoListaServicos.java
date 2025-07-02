package jose.unitarios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repository.ServicoRepository;
import service.ServicoService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class RT09AtualizacaoListaServicos {

    private ServicoRepository servicoRepository;


    private ServicoService servicoService;

    @BeforeEach
    void setUp() {
        servicoRepository = Mockito.mock(ServicoRepository.class);
        servicoService = new ServicoService(servicoRepository);

        servicoService.resetarLista();

        when(servicoRepository.contarServicosPorAreaAtuacao("encanador")).thenReturn(3);
        when(servicoRepository.contarServicosPorAreaAtuacao("eletricista")).thenReturn(3);
        when(servicoRepository.contarServicosPorAreaAtuacao("jardineiro")).thenReturn(3);



        servicoService.atualizarLista();
        System.out.println("Lista inicial: " + servicoService.getListaServicosMaisPrestados());
    }





    @Test
    public void aumentoDemandaServicoEspecificoTest() {
        // Arrange
        when(servicoRepository.contarServicosPorAreaAtuacao("encanador")).thenReturn(6);
        when(servicoRepository.contarServicosPorAreaAtuacao("eletricista")).thenReturn(3);
        when(servicoRepository.contarServicosPorAreaAtuacao("jardineiro")).thenReturn(3);

        // Act
        boolean atualizado = servicoService.atualizarLista();

        // Assert
        assertTrue(atualizado);
        assertEquals("encanador", servicoService.getListaServicosMaisPrestados().get(0));
        System.out.println("Lista final: " + servicoService.getListaServicosMaisPrestados());
    }




    @Test
    public void aumentoDemandaServicoVariadaTest() {
        // Arrange
        when(servicoRepository.contarServicosPorAreaAtuacao("encanador")).thenReturn(5);
        when(servicoRepository.contarServicosPorAreaAtuacao("eletricista")).thenReturn(10);
        when(servicoRepository.contarServicosPorAreaAtuacao("jardineiro")).thenReturn(3);

        // Act
        boolean atualizado = servicoService.atualizarLista();

        // Assert
        assertTrue(atualizado);
        List<String> lista = servicoService.getListaServicosMaisPrestados();
        assertEquals("eletricista", lista.get(0));
        assertEquals("encanador", lista.get(1));
        assertEquals("jardineiro", lista.get(2));
        System.out.println("Lista final: " + lista);
    }


    @Test
    public void aumentoDemandaServicoNuloTest(){
        //Arrange
        when(servicoRepository.contarServicosPorAreaAtuacao("encanador")).thenReturn(3);
        when(servicoRepository.contarServicosPorAreaAtuacao("eletricista")).thenReturn(3);
        when(servicoRepository.contarServicosPorAreaAtuacao("jardineiro")).thenReturn(3);

        //Act
        boolean atualizado = servicoService.atualizarLista();

        //Assert
        assertFalse(atualizado);
        List<String> lista = servicoService.getListaServicosMaisPrestados();
        assertEquals("eletricista", lista.get(0));
        System.out.println("Lista final: " + lista);
    }

}
