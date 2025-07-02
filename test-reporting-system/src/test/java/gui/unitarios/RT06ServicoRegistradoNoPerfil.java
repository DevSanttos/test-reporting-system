package gui.unitarios;

import entity.Servico;
import entity.Status;
import entity.Usuario;
import entity.UsuarioProfissional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RT06ServicoRegistradoNoPerfil {

    @Test
    public void deveRegistrarServicoNoPerfil(){

        String descricaoServico = "Reforma elétrica";
        Status statusInicial = Status.EM_ANDAMENTO;

        Usuario cliente = new Usuario();
        UsuarioProfissional profissional = new UsuarioProfissional(); // Supondo construtor Profissional(String nome)
        Servico servico = new Servico(descricaoServico, "Elétrica", statusInicial, cliente, profissional);

        profissional.adicionarServico(servico);  // Método responsável por adicionar o serviço na lista do profissional
        cliente.adicionarServico(servico);   // Supondo que o cliente pode ter um campo para armazenar o serviço contratado

        assertTrue(profissional.getServicosList().contains(servico), "O serviço deve estar registrado no perfil do profissional");
        assertTrue(cliente.getServicosList().contains(servico), "O serviço deve estar registrado no perfil do cliente");
    }
}
