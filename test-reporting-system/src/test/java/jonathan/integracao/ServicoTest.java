package jonathan.integracao;

import entity.Servico;
import entity.Status;
import entity.Usuario;
import entity.UsuarioProfissional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import repository.ServicoRepository;
import repository.UsuarioProfissionalRepository;
import service.ServicoService;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
public class ServicoTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("postgres");

    private EntityManager em;
    private static EntityManagerFactory entityManagerFactory;

    private ServicoRepository servicoRepository;
    private UsuarioProfissionalRepository usuarioProfissionalRepository;
    private ServicoService servicoService;

    private UsuarioProfissional profissionalPadrao;

    @BeforeAll
    public static void setupAll() {
        Map<String, String> props = Map.of(
                "jakarta.persistence.jdbc.url", postgres.getJdbcUrl(),
                "jakarta.persistence.jdbc.user", postgres.getUsername(),
                "jakarta.persistence.jdbc.password", postgres.getPassword(),
                "jakarta.persistence.schema-generation.database.action", "drop-and-create"
        );

        entityManagerFactory = Persistence.createEntityManagerFactory("default", props);
        System.out.println("EntityManagerFactory inicializado com sucesso para Testcontainers.");
    }

    @BeforeEach
    void setUp() {
        em = entityManagerFactory.createEntityManager();

        clearDatabase();

        servicoRepository = new ServicoRepository(em);
        usuarioProfissionalRepository = new UsuarioProfissionalRepository(em);

        servicoService = new ServicoService(servicoRepository);

        profissionalPadrao = new UsuarioProfissional();
        profissionalPadrao.setCPF("14375329692");
        profissionalPadrao.setTelefone("37993574479");
        profissionalPadrao.setAreaAtuacao("Pedreiro");
        profissionalPadrao.setEmail("jonathan@gmail.com");
        profissionalPadrao.setSenha("jonathan123");
    }

    //CT49
    @Test
    public void testRetornoListaServicosMaisPrestadosEOrdenacao() {
        // Arrange
        em.getTransaction().begin();
        em.persist(profissionalPadrao);
        em.getTransaction().commit();

        em.getTransaction().begin();
        // Marcenaria = 5
        for (int i = 0; i < 5; i++) {
            Servico s = new Servico("Marcenaria");
            s.setProfissional(profissionalPadrao);
            s.setProfissional(profissionalPadrao);
            s.setDescricao("Servico de Marcenaria " + i);
            em.persist(s);
        }
        // Construção civil = 3
        for (int i = 0; i < 3; i++) {
            Servico s = new Servico("Construção civil");
            s.setProfissional(profissionalPadrao);
            s.setDescricao("Servico de Construção Civil " + i);
            s.setStatus(Status.FINALIZADO);
            em.persist(s);
        }
        // Encanador = 2
        for (int i = 0; i < 2; i++) {
            Servico s = new Servico("Encanador");
            s.setProfissional(profissionalPadrao);
            s.setDescricao("Servico de Encanador " + i);
            s.setStatus(Status.FINALIZADO);
            em.persist(s);
        }

        em.getTransaction().commit();

        // Act
        boolean atualizou = servicoService.updateList();
        List<String> listaRankingServicos = servicoService.getListaServicosMaisPrestados();

        // Assert
        assertTrue(atualizou, "A lista deveria ter sido atualizada.");
        assertNotNull(listaRankingServicos, "A lista de ranking não deve ser nula.");
        assertEquals(3, listaRankingServicos.size(), "A lista deve conter 3 tipos de serviços.");

        assertEquals("marcenaria", listaRankingServicos.get(0), "O primeiro serviço do ranking deve ser 'marcenaria'.");
        assertEquals("construção civil", listaRankingServicos.get(1), "O segundo serviço do ranking deve ser 'construção civil'.");
        assertEquals("encanador", listaRankingServicos.get(2), "O terceiro serviço do ranking deve ser 'encanador'.");
    }

    private void clearDatabase() {
        em.getTransaction().begin();
        try {
            em.createQuery("DELETE FROM Servico").executeUpdate();
            em.createQuery("DELETE FROM UsuarioProfissional").executeUpdate();
            em.createQuery("DELETE FROM Usuario").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Erro ao limpar o banco de dados: " + e.getMessage());
            e.printStackTrace();
            fail("Falha ao limpar o banco de dados antes do teste.");
        }
    }

    @AfterAll
    public static void tearDown() {
        postgres.stop();
    }
}