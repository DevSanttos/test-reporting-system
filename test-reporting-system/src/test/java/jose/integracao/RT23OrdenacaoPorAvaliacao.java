package jose.integracao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import repository.UsuarioProfissionalRepository;
import service.UsuarioProfissionalService;

import java.util.Map;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RT23OrdenacaoPorAvaliacao {
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
        usuarioProfissionalService = new UsuarioProfissionalService(usuarioProfissionalRepository);
    }

    @Test
    public void deveRetornarListaOrdenadaPorAvaliacao(){
        //Arrange

        //Act

        //Act

    }

    @AfterAll
    public static void teardownAll() {
        postgres.stop();
    }


}
