package service;

import entity.Servico;
import entity.UsuarioProfissional;
import repository.ServicoRepository;

import entity.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Esta service será utilizada nos RT10 e RT11

public class ServicoService {

    ServicoRepository servicoRepository;

    private List<String> listaServicosMaisPrestados;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
        this.listaServicosMaisPrestados = new ArrayList<>();
    }

    private Map<String, Integer> mapaServicosMaisPrestados = new HashMap<>();

    public boolean atualizarLista() {
        int qtdMarcenaria = servicoRepository.contarServicosPorAreaAtuacao("marcenaria");
        int qtdConstrucao = servicoRepository.contarServicosPorAreaAtuacao("construção civil");
        int qtdEncanador = servicoRepository.contarServicosPorAreaAtuacao("encanador");
        int qtdEletricista = servicoRepository.contarServicosPorAreaAtuacao("eletricista");
        int qtdJardineiro = servicoRepository.contarServicosPorAreaAtuacao("jardineiro");

        Map<String, Integer> novaContagem = new HashMap<>();
        novaContagem.put("marcenaria", qtdMarcenaria);
        novaContagem.put("construção civil", qtdConstrucao);
        novaContagem.put("encanador", qtdEncanador);
        novaContagem.put("eletricista", qtdEletricista);
        novaContagem.put("jardineiro", qtdJardineiro);

        this.listaServicosMaisPrestados = novaContagem.entrySet().stream()
                .filter(entry -> entry.getValue() > 0) // Opcional: para não mostrar serviços com 0
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(Map.Entry::getKey)
                .toList();

        return true;
    }

    public Servico realizarContratacao(UsuarioProfissional profissional, Usuario contratante, String dataServico, String horaServico) {
        if (!profissional.isDisponivelParaServico() || !profissional.getHorarioAtuacao().equalsIgnoreCase(horaServico)) {
            throw new IllegalArgumentException("Profissional indisponível no horário solicitado.");
        }

        Servico servico = new Servico(contratante, profissional, dataServico, horaServico);
        contratante.adicionarServico(servico);
        profissional.adicionarServico(servico);

        return servicoRepository.salvarServico(servico);
    }

    public UsuarioProfissional questionarioServico(){
        return null;
    }

    public Servico realizarContratacao(){
        return null;
    }

    public List<String> getListaServicosMaisPrestados() {
        return this.listaServicosMaisPrestados;
    }

    public void resetarLista() {
        this.servicoRepository.getServicos().clear();
    }
}
