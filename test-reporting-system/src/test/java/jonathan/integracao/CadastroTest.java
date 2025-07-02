package jonathan.integracao;

import entity.UsuarioProfissional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.UsuarioProfissionalRepository;
import service.Autenticador;
import service.CepService;
import service.UsuarioProfissionalService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CadastroTest {
    @Mock
    private UsuarioProfissionalRepository usuarioProfissionalRepository;

    @Mock
    private CepService cepService;

    @InjectMocks
    private UsuarioProfissionalService usuarioProfissionalService;

    private Autenticador autenticador;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.autenticador = new Autenticador(usuarioProfissionalService);
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
        boolean resultado = usuarioProfissionalService.create(novoProfissional);

        // Assert
        assertTrue(resultado, "O cadastro do usuário profissional deveria retornar true.");

        verify(usuarioProfissionalRepository, times(1)).create(novoProfissional);
    }

    //CT47
    @Test
    public void testAutenticacaoLoginSucesso() {
        // Arrange
        String email = "jonathan@gmail.com";
        String senha = "Senha123";

        UsuarioProfissional profissionalMock = new UsuarioProfissional();
        profissionalMock.setEmail(email);
        profissionalMock.setSenha(senha);

        when(usuarioProfissionalService.findByEmail(email)).thenReturn(profissionalMock);

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

        when(usuarioProfissionalRepository.findByCPF(cpfDoProfissional)).thenReturn(profissionalExistente);

        // Act
        boolean resultado = usuarioProfissionalService.updatePerfilEHabilidades(cpfDoProfissional, novaAreaAtuacao, novaHabilidade);

        // Assert
        assertTrue(resultado, "A atualização do perfil deveria ter retornado true.");
        assertEquals(novaAreaAtuacao, profissionalExistente.getAreaAtuacao(), "A área de atuação deveria ter sido atualizada.");
        assertTrue(profissionalExistente.getHabilidadesList().contains(novaHabilidade), "A nova habilidade deveria ter sido adicionada.");
        assertEquals(2, profissionalExistente.getHabilidadesList().size(), "A lista de habilidades deveria conter 2 itens.");
    }
}
