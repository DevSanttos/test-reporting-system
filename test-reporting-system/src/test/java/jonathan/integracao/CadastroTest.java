package jonathan.integracao;

import entity.UsuarioProfissional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.UsuarioProfissionalRepository;
import service.Autenticador;
import service.CepService;
import service.UsuarioProfissionalService;

import static org.junit.jupiter.api.Assertions.*;

public class CadastroTest {
    private UsuarioProfissionalRepository usuarioProfissionalRepository;
    @Mock
    private CepService cepService;

    private UsuarioProfissionalService usuarioProfissionalService;

    private Autenticador autenticador;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        this.usuarioProfissionalRepository = new UsuarioProfissionalRepository();

        this.usuarioProfissionalRepository.getListaUsuarioProfissinal().clear();

        this.usuarioProfissionalService = new UsuarioProfissionalService(usuarioProfissionalRepository, cepService);

        this.autenticador = new Autenticador(usuarioProfissionalService);
    }

    //CT46
    @Test
    public void testCadastroUsuarioProfissionalCriadoEArmazenadoCorretamente() {
        // Arrange
        UsuarioProfissional novoProfissional = new UsuarioProfissional();

        novoProfissional.setNome("Jonathan Rezende");
        novoProfissional.setCPF("12345678900");
        novoProfissional.setEmail("jonathan@gmail.com");
        novoProfissional.setTelefone("47999999999");
        novoProfissional.setSenha("Senha123");
        novoProfissional.setAreaAtuacao("Pequenos Reparos");

        // Act
        boolean resultado = usuarioProfissionalService.create(novoProfissional);

        // Assert
        assertTrue(resultado, "O cadastro do usuário profissional deveria ter retornado true.");

        assertEquals(1, usuarioProfissionalRepository.getListaUsuarioProfissinal().size(),
                "A lista do repositório deveria conter 1 usuário após o cadastro.");


        UsuarioProfissional profissionalPersistido = usuarioProfissionalRepository.findByCPF(novoProfissional.getCPF());

        assertNotNull(profissionalPersistido, "O profissional deveria ter sido encontrado no repositório pelo CPF.");

        assertEquals(novoProfissional.getNome(), profissionalPersistido.getNome());
        assertEquals(novoProfissional.getCPF(), profissionalPersistido.getCPF());
        assertEquals(novoProfissional.getEmail(), profissionalPersistido.getEmail());
        assertEquals(novoProfissional.getTelefone(), profissionalPersistido.getTelefone());
        assertEquals(novoProfissional.getSenha(), profissionalPersistido.getSenha());
        assertEquals(novoProfissional.getAreaAtuacao(), profissionalPersistido.getAreaAtuacao());
    }

    //CT47
    @Test
    public void testAutenticacaoLoginSucesso() {
        // Arrange
        UsuarioProfissional profissionalParaAutenticar = new UsuarioProfissional();
        profissionalParaAutenticar.setNome("Jonathan Rezende");
        profissionalParaAutenticar.setCPF("12345678900");
        profissionalParaAutenticar.setEmail("jonathan@gmail.com");
        profissionalParaAutenticar.setTelefone("47999999999");
        profissionalParaAutenticar.setSenha("Senha123");
        profissionalParaAutenticar.setAreaAtuacao("Pequenos Reparos");

        usuarioProfissionalService.create(profissionalParaAutenticar);

        String email = "jonathan@gmail.com";
        String senha = "Senha123";

        // Act
        boolean autenticado = autenticador.autenticar(email, senha);

        // Assert
        assertTrue(autenticado, "O login com credenciais corretas deveria ter retornado true.");
    }

    //CT48
    @Test
    public void testAlteracaoPerfilEAdicaoHabilidadeProfissional() {
        // Arrange
        UsuarioProfissional profissionalExistente = new UsuarioProfissional();
        profissionalExistente.setNome("Profissional Original");
        profissionalExistente.setCPF("98765432100");
        profissionalExistente.setEmail("original@email.com");
        profissionalExistente.setTelefone("11111111111");
        profissionalExistente.setSenha("senhaOriginal");
        profissionalExistente.setAreaAtuacao("Manutenção Geral");
        profissionalExistente.getHabilidadesList().add("Conserto Básico");

        usuarioProfissionalService.create(profissionalExistente);

        String cpfDoProfissional = profissionalExistente.getCPF();
        String novaAreaAtuacao = "Construção civil";
        String novaHabilidade = "Especialização em construção de telhados";

        // Act
        boolean resultado = usuarioProfissionalService.updatePerfilEHabilidades(cpfDoProfissional, novaAreaAtuacao, novaHabilidade);

        // Assert
        assertTrue(resultado, "A atualização do perfil e adição de habilidade deveriam ter retornado true.");

        UsuarioProfissional profissionalAtualizado = usuarioProfissionalRepository.findByCPF(cpfDoProfissional);

        assertNotNull(profissionalAtualizado, "O profissional atualizado deveria ter sido encontrado no repositório.");
        assertEquals(novaAreaAtuacao, profissionalAtualizado.getAreaAtuacao(), "A área de atuação não foi atualizada corretamente.");
        assertNotNull(profissionalAtualizado.getHabilidadesList(), "A lista de habilidades não deve ser nula.");
        assertTrue(profissionalAtualizado.getHabilidadesList().contains(novaHabilidade), "A nova habilidade deve ter sido adicionada.");
        assertEquals(2, profissionalAtualizado.getHabilidadesList().size(), "A lista de habilidades deve ter 2 itens.");
    }

    //CT49

}
