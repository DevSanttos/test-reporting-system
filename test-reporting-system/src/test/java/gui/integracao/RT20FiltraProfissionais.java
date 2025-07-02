package gui.integracao;

import entity.Status;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import repository.UsuarioProfissionalRepository;
import service.UsuarioProfissionalService;
import entity.UsuarioProfissional;
import org.junit.jupiter.api.Test;
import entity.Usuario;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RT20FiltraProfissionais {
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
    void deveRetornarProfissionaisAtivos() {

        // Arrange
        String areaPesquisa = "Estética";
        UsuarioProfissional profissional1 = new UsuarioProfissional("Estética", Status.ATIVO);
        UsuarioProfissional profissional2 = new UsuarioProfissional("Estética", Status.ATIVO);
        UsuarioProfissional profissional3 = new UsuarioProfissional("Estética", Status.ATIVO);

        em.getTransaction().begin();
        usuarioProfissionalRepository.createDB(profissional1);
        usuarioProfissionalRepository.createDB(profissional2);
        usuarioProfissionalRepository.createDB(profissional3);
        em.getTransaction().commit();

        String query = "SELECT p FROM UsuarioProfissional p WHERE p.areaAtuacao = :area AND p.status = :status";

        List<UsuarioProfissional> profissionais = em.createQuery(query, UsuarioProfissional.class)
                .setParameter("area", "Estética")
                .setParameter("status", Status.ATIVO)
                .getResultList();

        assertEquals(3, profissionais.size());

    }

    @Test
    void deveRetornarSomenteProfissionaisAtivos() {

        // Arrange
        String areaPesquisa = "Estética";
        UsuarioProfissional profissional1 = new UsuarioProfissional("Estética", Status.ATIVO);
        UsuarioProfissional profissional2 = new UsuarioProfissional("Estética", Status.ATIVO);
        UsuarioProfissional profissional3 = new UsuarioProfissional("Estética", Status.INATIVO);

        em.getTransaction().begin();
        usuarioProfissionalRepository.createDB(profissional1);
        usuarioProfissionalRepository.createDB(profissional2);
        usuarioProfissionalRepository.createDB(profissional3);
        em.getTransaction().commit();

        String query = "SELECT p FROM UsuarioProfissional p WHERE p.areaAtuacao = :area AND p.status = :status";

        List<UsuarioProfissional> profissionais = em.createQuery(query, UsuarioProfissional.class)
                .setParameter("area", "Estética")
                .setParameter("status", Status.ATIVO)
                .getResultList();

        assertEquals(2, profissionais.size());

    }

    @Test
    void deveAtualizarStatus() {

        // Arrange
        String areaPesquisa = "Estética";
        UsuarioProfissional profissional1 = new UsuarioProfissional("Estética", Status.ATIVO);
        UsuarioProfissional profissional2 = new UsuarioProfissional("Estética", Status.ATIVO);
        UsuarioProfissional profissional3 = new UsuarioProfissional("Estética", Status.ATIVO);

        em.getTransaction().begin();
        usuarioProfissionalRepository.createDB(profissional1);
        usuarioProfissionalRepository.createDB(profissional2);
        usuarioProfissionalRepository.createDB(profissional3);
        em.getTransaction().commit();

        String query = "SELECT p FROM UsuarioProfissional p WHERE p.areaAtuacao = :area AND p.status = :status";

        List<UsuarioProfissional> profissionais = em.createQuery(query, UsuarioProfissional.class)
                .setParameter("area", "Estética")
                .setParameter("status", Status.ATIVO)
                .getResultList();

        assertEquals(3, profissionais.size());

        profissional1.setStatus(Status.INATIVO);
        usuarioProfissionalRepository.update(profissional1);

        String queryUpdate = "SELECT p FROM UsuarioProfissional p WHERE p.areaAtuacao = :area AND p.status = :status";

        List<UsuarioProfissional> profissionaisUpdate = em.createQuery(query, UsuarioProfissional.class)
                .setParameter("area", "Estética")
                .setParameter("status", Status.ATIVO)
                .getResultList();
        assertEquals(2, profissionaisUpdate.size());

    }
}
