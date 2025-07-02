package jose.integracao;

import entity.UsuarioProfissional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import repository.UsuarioProfissionalRepository;
import service.UsuarioProfissionalService;
import java.util.List;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        em.getTransaction().begin();

        UsuarioProfissional p1 = new UsuarioProfissional("Jefferson", "Eletricista", "fiação", "Ibirama");
        p1.setMediaAvaliacoes(4.0);

        UsuarioProfissional p2 = new UsuarioProfissional("Sérgio", "Jardinagem", "poda", "Ibirama");
        p2.setMediaAvaliacoes(5.0);

        UsuarioProfissional p3 = new UsuarioProfissional("Marcos", "Pedreiro", "reforma", "Ibirama");
        p3.setMediaAvaliacoes(3.0);

        em.persist(p1);
        em.persist(p2);
        em.persist(p3);

        em.getTransaction().commit();

        //Act
        List<UsuarioProfissional> ordenados = usuarioProfissionalService.ordenarListaProfissionaisAvaliacao();

        //Assert
        assertEquals(3, ordenados.size());
        assertEquals("Sérgio", ordenados.get(0).getNome());
        assertEquals("Jefferson", ordenados.get(1).getNome());
        assertEquals("Marcos", ordenados.get(2).getNome());
        System.out.println(ordenados);
    }

    @AfterAll
    public static void teardownAll() {
        postgres.stop();
    }


}
