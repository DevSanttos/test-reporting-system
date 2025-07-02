package repository;

import entity.Servico;

import java.util.ArrayList;
import java.util.List;

public class ServicoRepository {

    private List<Servico> servicos;

    public ServicoRepository() {
        servicos = new ArrayList<>();
    }

    public void addServico(Servico servico) {
        this.servicos.add(servico);
    }

    public List<Servico> getServicos(){
        return this.servicos;
    }


    public int contarServicosPorAreaAtuacao(String areaAtuacao) {
        return (int) servicos.stream()
                .filter(s -> s.getProfissional() != null)
                .filter(s -> areaAtuacao.equalsIgnoreCase(s.getProfissional().getAreaAtuacao()))
                .count();
    }

    public Servico salvarServico(Servico servico) {
        return null;
    }
}
