package jonathan.integracao;

import entity.UsuarioProfissional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import repository.UsuarioProfissionalRepository;
import service.Autenticador;
import service.UsuarioProfissionalService;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class CadastroTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("postgres");

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    private UsuarioProfissionalRepository usuarioProfissionalRepository;
    private UsuarioProfissionalService usuarioProfissionalService;
    private Autenticador autenticador;
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
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        clearDatabase();

        usuarioProfissionalRepository = new UsuarioProfissionalRepository(entityManager);
        usuarioProfissionalService = new UsuarioProfissionalService(usuarioProfissionalRepository);
        autenticador = new Autenticador(usuarioProfissionalService);

        profissionalPadrao = new UsuarioProfissional();
        profissionalPadrao.setCPF("14375329692");
        profissionalPadrao.setTelefone("37993574479");
        profissionalPadrao.setAreaAtuacao("Pedreiro");
        profissionalPadrao.setEmail("jonathan@gmail.com");
        profissionalPadrao.setSenha("jonathan123");
        profissionalPadrao.setNome("Jonathan Profissional");

        if (profissionalPadrao.getHabilidadesList() == null) {
            profissionalPadrao.setHabilidadesList(new java.util.ArrayList<>());
        }
        if (profissionalPadrao.getServicosList() == null) {
            profissionalPadrao.setServicosList(new java.util.ArrayList<>());
        }
    }

    private void clearDatabase() {
        entityManager.getTransaction().begin();
        try {
            entityManager.createQuery("DELETE FROM Servico").executeUpdate();
            entityManager.createQuery("DELETE FROM UsuarioProfissional").executeUpdate();
            entityManager.createQuery("DELETE FROM Usuario").executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Erro ao limpar o banco de dados: " + e.getMessage());
            e.printStackTrace();
            fail("Falha ao limpar o banco de dados antes do teste.");
        }
    }

    //CT46
    @Test
    public void testCadastroUsuarioProfissionalCriadoCorretamente() {
        // Arrange
        UsuarioProfissional novoProfissional = new UsuarioProfissional();
        novoProfissional.setNome("Jonathan Rezende");
        novoProfissional.setCPF("12345678900");
        novoProfissional.setEmail("jonathan@gmail.com");
        novoProfissional.setTelefone("47999999999");
        novoProfissional.setSenha("Senha123");
        novoProfissional.setAreaAtuacao("Pequenos Reparos");

        // Act
        entityManager.getTransaction().begin();
        usuarioProfissionalRepository.createDB(novoProfissional);
        entityManager.getTransaction().commit();

        // Assert
        entityManager.clear();
        UsuarioProfissional profissionalPersistido = entityManager.find(UsuarioProfissional.class, novoProfissional.getId());
        assertNotNull(profissionalPersistido, "O profissional deveria ter sido encontrado no banco de dados.");
        assertEquals(novoProfissional.getNome(), profissionalPersistido.getNome());
    }

    //CT47
    @Test
    public void testAutenticacaoLoginSucesso() {
        // Arrange
        String email = "jonathan@gmail.com";
        String senha = "Senha123";

        UsuarioProfissional usuarioProfissional = new UsuarioProfissional();
        usuarioProfissional.setEmail(email);
        usuarioProfissional.setSenha(senha);

        entityManager.getTransaction().begin();
        entityManager.persist(usuarioProfissional);
        entityManager.getTransaction().commit();
        entityManager.clear();

        // Act
        boolean autenticado = autenticador.autenticar(email, senha);

        // Assert
        assertTrue(autenticado, "A autenticação com credenciais corretas deveria ter retornado true.");
    }

    //CT48
    @Test
    public void testAlteracaoPerfilEAdicaoHabilidadeProfissional() {
        // Arrange
        String cpfDoProfissional = "98765432100";
        String novaAreaAtuacao = "Construção civil";
        String novaHabilidade = "Especialização em construção de telhados";

        UsuarioProfissional profissionalExistente = new UsuarioProfissional();
        profissionalExistente.setCPF(cpfDoProfissional);
        profissionalExistente.setAreaAtuacao("Manutenção Geral");
        profissionalExistente.getHabilidadesList().add("Conserto Básico");

        entityManager.getTransaction().begin();
        entityManager.persist(profissionalExistente);
        entityManager.getTransaction().commit();
        entityManager.clear();

        // Act
        entityManager.getTransaction().begin();
        boolean resultado = usuarioProfissionalService.updatePerfilEHabilidades(cpfDoProfissional, novaAreaAtuacao, novaHabilidade);
        entityManager.getTransaction().commit();
        entityManager.clear();

        // Assert
        assertTrue(resultado, "A atualização do perfil deveria ter retornado true.");

        UsuarioProfissional profissionalAtualizado = entityManager.find(UsuarioProfissional.class, profissionalExistente.getId());

        assertNotNull(profissionalAtualizado, "O profissional atualizado deveria ter sido encontrado no banco de dados.");
        assertEquals(novaAreaAtuacao, profissionalAtualizado.getAreaAtuacao(), "A área de atuação não foi atualizada corretamente.");
        assertNotNull(profissionalAtualizado.getHabilidadesList(), "A lista de habilidades não deve ser nula.");
        assertTrue(profissionalAtualizado.getHabilidadesList().contains("Conserto Básico"), "A habilidade original deve permanecer.");
        assertTrue(profissionalAtualizado.getHabilidadesList().contains(novaHabilidade), "A nova habilidade deve ter sido adicionada.");
        assertEquals(2, profissionalAtualizado.getHabilidadesList().size(), "A lista de habilidades deveria conter 2 itens.");
    }
}
