package jose.integracao;

import entity.UsuarioProfissional;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import repository.UsuarioProfissionalRepository;
import repository.UsuarioRepository;
import service.UsuarioProfissionalService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import service.UsuarioService;
import util.JpaUtil;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RT21ListaProfPosBusca {



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
    @Order(1)
    public void deveRetornarProfissionalComBaseNaCategoria() {
        //Arrange
        em.getTransaction().begin();
        em.createQuery("DELETE FROM UsuarioProfissional").executeUpdate();

        UsuarioProfissional profissional1 = new UsuarioProfissional("Alberto", "Pintor", "Pintura residencial", "Ibirama");
        UsuarioProfissional profissional2 = new UsuarioProfissional("Simone", "Jardinagem", "Poda de grama", "Ibirama");

        em.persist(profissional1);
        em.persist(profissional2);
        em.getTransaction().commit();

        //Act
        List<UsuarioProfissional> resultado = usuarioProfissionalService.buscarServicos(null, "Pintor", null);

        //Assert
        assertEquals(1, resultado.size());
        UsuarioProfissional profissional = resultado.getFirst();
        assertEquals("Alberto", profissional.getNome());
        assertEquals("Pintor", profissional.getAreaAtuacao());
        System.out.println(resultado);
    }

    @AfterAll
    public static void tearDown() {
        postgres.stop();
    }
}
