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

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RT15VerificarSeOCadastroEhCriado {
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

    @Test
    public void deveCriarEArmazenarUsuarioComSucesso() {
        // Arrange
            Usuario usuario = new Usuario("Nataniel", "12345678909", "nataniel@gmail.com", "47912341234", "1234");

        // Act
            em.getTransaction().begin();
            service.create(usuario);
            em.getTransaction().commit();

            Usuario usuarioSalvo = usuarioRepository.findByEmail("nataniel@gmail.com");

        // Assert
            assertNotNull(usuarioSalvo);
            assertEquals("Nataniel", usuarioSalvo.getNome());
            assertEquals("12345678909", usuarioSalvo.getCPF());
            assertEquals("nataniel@gmail.com", usuarioSalvo.getEmail());
            assertEquals("47912341234", usuarioSalvo.getTelefone());
            assertEquals("1234", usuarioSalvo.getSenha());
    }

    @AfterAll
    public static void tearDown() {
        postgres.stop();
    }
}
