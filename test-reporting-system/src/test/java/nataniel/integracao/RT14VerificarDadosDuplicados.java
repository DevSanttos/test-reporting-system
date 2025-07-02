package nataniel.integracao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import repository.UsuarioRepository;
import service.UsuarioService;
import entity.Usuario;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RT14VerificarDadosDuplicados {
    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("postgres");

    private static EntityManager em;
    private static UsuarioRepository usuarioRepository = new UsuarioRepository(em);
    private static UsuarioService service = new UsuarioService(usuarioRepository);

    @BeforeAll
    public static void setupAll() {
        Map<String, String> props = Map.of(
                "jakarta.persistence.jdbc.url", postgres.getJdbcUrl(),
                "jakarta.persistence.jdbc.user", postgres.getUsername(),
                "jakarta.persistence.jdbc.password", postgres.getPassword()
        );

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default", props);
        em = emf.createEntityManager();

        usuarioRepository = new UsuarioRepository(em);
        service = new UsuarioService(usuarioRepository);
    }

    @BeforeEach
    public void setupEach() {
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Usuario").executeUpdate();

        Usuario usuario = new Usuario("Nataniel", "00000000191", "nataniel@gmail.com", "47912341234", "1234");
        service.create(usuario);

        em.getTransaction().commit();
    }

    @Test
    @Order(1)
    public void deveRejeitarEmailDuplicado() {
        Exception exception = null;

        try {
            Usuario duplicado = new Usuario("Natan", "12345678909", "nataniel@gmail.com", "47912341111", "1234");
            service.create(duplicado);
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals("Este e-mail já está cadastrado", exception.getMessage());
    }

    @Test
    @Order(2)
    public void deveRejeitarCpfDuplicado() {
        Exception exception = null;

        try {
            Usuario duplicado = new Usuario("Natan", "00000000191", "nataniel1@gmail.com", "47912341111", "1234");
            service.create(duplicado);
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals("Este CPF já está cadastrado", exception.getMessage());
    }

    @Test
    @Order(3)
    public void deveRejeitarTelefoneDuplicado() {
        Exception exception = null;

        try {
            Usuario duplicado = new Usuario("Natan", "12345678909", "nataniel1@gmail.com", "47912341234", "1234");
            service.create(duplicado);
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals("Este telefone já está cadastrado", exception.getMessage());
    }

    @AfterAll
    public static void tearDown() {
        postgres.stop();
    }
}
