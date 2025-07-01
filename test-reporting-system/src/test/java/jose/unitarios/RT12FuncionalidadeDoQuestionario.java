package jose.unitarios;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import model.UsuarioProfissional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repository.UsuarioProfissionalRepository;
import service.CepService;
import service.UsuarioProfissionalService;

import java.util.Arrays;
import java.util.List;

public class RT12FuncionalidadeDoQuestionario {

    private UsuarioProfissionalService usuarioProfissionalService;

    @BeforeEach
    public void setUp() {
        UsuarioProfissionalRepository usuarioProfissionalRepository = Mockito.mock(UsuarioProfissionalRepository.class);
        CepService cepServiceMock = Mockito.mock(CepService.class);

        UsuarioProfissional profissional1 = new UsuarioProfissional("João", "Jardinagem", "Poda de árvore", "Ibirama");

        profissional1.setDisponivelParaServico(true);
        profissional1.setHorarioAtuacao("Qualquer");
        profissional1.setNivelServico("Qualquer");

        UsuarioProfissional profissional2 = new UsuarioProfissional("Maria", "Jardinagem", "Poda de árvore", "Ibirama");
        profissional2.setDisponivelParaServico(true);
        profissional2.setHorarioAtuacao("Manhã");
        profissional2.setNivelServico("Serviço simples de curta duração");

        UsuarioProfissional profissional3 = new UsuarioProfissional("Carlos", "Jardinagem", "Capina", "Ibirama");
        profissional3.setDisponivelParaServico(true);
        profissional3.setHorarioAtuacao("Tarde");
        profissional3.setNivelServico("Qualquer");

        List<UsuarioProfissional> listaMock = Arrays.asList(profissional1, profissional2, profissional3);


        when(usuarioProfissionalRepository.ListaUsuariosProfissionais()).thenReturn(listaMock);
        when(cepServiceMock.buscarLocalizacaoPorCep("89140000")).thenReturn("Ibirama");


        usuarioProfissionalService = new UsuarioProfissionalService(usuarioProfissionalRepository, cepServiceMock);
    }


    @Test
    public void QuestionarioTodasRespostasPreenchidasTest(){
        //Arrange
        String cep = "89140000";
        String nivelServico = "Serviço simples de curta duração";
        String tipoServico = "Poda de árvore";
        String diaServico = "10/07/2025";
        String horarioServico = "Manhã";

        //Act
        Object resultado = usuarioProfissionalService.questionarioServico(cep, nivelServico, tipoServico, diaServico, horarioServico);

        //Assert
        assertInstanceOf(List.class, resultado);
        List<?> lista = (List<?>) resultado;
        assertEquals(1, lista.size());
        UsuarioProfissional profissionalResultado = (UsuarioProfissional) lista.getFirst();
        assertEquals("Maria", profissionalResultado.getNome());
        System.out.println(profissionalResultado.getNome());
    }

    @Test
    public void QuestionarioRespostasIncompletasTest(){
        // Arrange
        String cep = "89140000";
        String nivelServico = " ";
        String tipoServico = " ";
        String diaServico = " ";
        String horarioServico = "Manhã";

        // Act
        Object resultado = usuarioProfissionalService.questionarioServico(cep, nivelServico, tipoServico, diaServico, horarioServico);

        // Assert
        assertInstanceOf(String.class, resultado);
        assertEquals("Complete o questionário para prosseguir. Há uma ou mais respostas em branco.", resultado);
        System.out.println(resultado);
    }
}
