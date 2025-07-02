package jonathan.integracao;

import model.UsuarioProfissional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.UsuarioProfissionalRepository;
import service.CepService;
import service.UsuarioProfissionalService;

import static org.junit.jupiter.api.Assertions.*;

public class CadastroTest {
    private UsuarioProfissionalRepository usuarioProfissionalRepository;
    @Mock
    private CepService cepService;

    private UsuarioProfissionalService usuarioProfissionalService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.usuarioProfissionalRepository = new UsuarioProfissionalRepository();

        this.usuarioProfissionalRepository.getListaUsuarioProfissinal().clear();

        this.usuarioProfissionalService = new UsuarioProfissionalService(usuarioProfissionalRepository, cepService);
    }


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
}
