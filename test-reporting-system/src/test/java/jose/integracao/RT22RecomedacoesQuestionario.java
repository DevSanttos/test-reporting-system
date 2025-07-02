package jose.integracao;

import entity.UsuarioProfissional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import repository.UsuarioProfissionalRepository;
import service.CepService;
import service.UsuarioProfissionalService;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RT22RecomedacoesQuestionario {
    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("postgres");

    private static EntityManager em;
    private static UsuarioProfissionalRepository usuarioProfissionalRepository;
    private static UsuarioProfissionalService usuarioProfissionalService;

    @BeforeAll
    public static void setupAll() {
        Map<String, String> props = Map.of(
                "jakarta.persistence.jdbc.url", postgres.getJdbcUrl(),
                "jakarta.persistence.jdbc.user", postgres.getUsername(),
                "jakarta.persistence.jdbc.password", postgres.getPassword()
        );

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default", props);
        em = emf.createEntityManager();

        usuarioProfissionalRepository = new UsuarioProfissionalRepository(em);
        CepService cepServiceMock = Mockito.mock(CepService.class);
        when(cepServiceMock.buscarLocalizacaoPorCep("89140000")).thenReturn("Ibirama");
        usuarioProfissionalService = new UsuarioProfissionalService(usuarioProfissionalRepository, cepServiceMock);
    }

    @Test
    @Order(1)
    public void deveRecomendarProfissionalDeJardinagemParaPodaDeArvorePelaManha() {
        //Arrange
        em.getTransaction().begin();


        UsuarioProfissional carlos = new UsuarioProfissional(
                "Carlos",
                "Jardinagem",
                "Poda de árvore",
                "Ibirama"
        );
        carlos.setDisponivelParaServico(true);
        carlos.setHorarioAtuacao("Manhã");
        carlos.setNivelServico("Qualquer");


        UsuarioProfissional thays = new UsuarioProfissional(
                "Thays",
                "Eletricista",
                "Qualquer",
                "Rio do Sul"
        );
        thays.setDisponivelParaServico(false);
        thays.setHorarioAtuacao("Qualquer");
        thays.setNivelServico("Qualquer");

        em.persist(carlos);
        em.persist(thays);
        em.getTransaction().commit();

        String cep = "89140000";
        String nivelServico = "Serviço simples de curta duração";
        String tipoServico = "Poda de árvore";
        String diaServico = "10/07/2025";
        String horarioServico = "Manhã";

        //Act
        Object recomendados = usuarioProfissionalService.questionarioServico(
                cep,
                nivelServico,
                tipoServico,
                diaServico,
                horarioServico
        );


        //Assert
        assertInstanceOf(List.class, recomendados);
        List<?> lista = (List<?>) recomendados;
        assertEquals(1, lista.size());
        UsuarioProfissional profissionalResultado = (UsuarioProfissional) lista.getFirst();
        assertEquals("Carlos", profissionalResultado.getNome());
        System.out.println(profissionalResultado);
    }

    @AfterAll
    public static void tearDown() {
        postgres.stop();
    }

}
