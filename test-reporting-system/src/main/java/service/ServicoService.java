package service;

import model.Servico;
import model.UsuarioProfissional;
import repository.ServicoRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        int qtdEncanador = servicoRepository.contarServicosPorAreaAtuacao("encanador");
        int qtdEletricista = servicoRepository.contarServicosPorAreaAtuacao("eletricista");
        int qtdJardineiro = servicoRepository.contarServicosPorAreaAtuacao("jardineiro");

        Map<String, Integer> novaContagem = new HashMap<>();
        novaContagem.put("encanador", qtdEncanador);
        novaContagem.put("eletricista", qtdEletricista);
        novaContagem.put("jardineiro", qtdJardineiro);

        // Compara contagens antigas e novas
        if (novaContagem.equals(mapaServicosMaisPrestados)) {
            return false;
        }

        mapaServicosMaisPrestados = novaContagem;

        // Atualiza a lista de nomes ordenada
        this.listaServicosMaisPrestados = novaContagem.entrySet().stream()
                .sorted((e1, e2) -> {
                    int cmp = e2.getValue().compareTo(e1.getValue());
                    if (cmp != 0) return cmp;
                    return e1.getKey().compareTo(e2.getKey());
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return true;
    }







    public List<UsuarioProfissional> buscarServicos(){
        return null;
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
