package jose.unitarios;

import model.Servico;
import model.UsuarioProfissional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repository.ServicoRepository;
import service.ServicoService;
import org.mockito.Mock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RT10BuscaPorCriteriosIndividuais {

    private ServicoRepository servicoRepository;
    private ServicoService servicoService;

    @BeforeEach
    public void setUp() {
        servicoRepository = Mockito.mock(ServicoRepository.class);
        servicoService = new ServicoService(servicoRepository);


        UsuarioProfissional profissional1 = new UsuarioProfissional("Ana", "Limpeza", "Limpeza geral", "Blumenau");
        UsuarioProfissional profissional2 = new UsuarioProfissional("Carlos", "Jardinagem", "Corte de grama", "Blumenau");
        UsuarioProfissional profissional3 = new UsuarioProfissional("Bia", "Limpeza", "Limpeza de sofás", "Gaspar");

        List<Servico> listaMock = Arrays.asList(
                new Servico("Limpeza geral em casas", profissional1),
                new Servico("Corte e manutenção de jardins", profissional2),
                new Servico("Limpeza profunda de estofados", profissional3)
        );
    }

    @BeforeEach
    void setUp(){

    }

    @Test
    public void buscaPorPalavraChaveTest(){
        //Arrange

        //Act

        //Assert
    }

    @Test
    public void buscaPorCategoriaTest(){
        //Arrange

        //Act

        //Assert
    }

    @Test
    public void buscaPorLocalizacao(){
        //Arrange

        //Act

        //Assert
    }

}
