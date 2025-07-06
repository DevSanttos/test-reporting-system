package repository;

import entity.Servico;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class ServicoRepository {
    EntityManager em;
    private List<Servico> servicos;

    public ServicoRepository(EntityManager em) {
        servicos = new ArrayList<>();
        this.em = em;
    }

    public void addServico(Servico servico) {
        this.servicos.add(servico);
    }

    public List<Servico> getServicos(){
        return this.servicos;
    }

    public int contServicosPorAreaAtuacao(String areaAtuacao) {
        Long count = em
                .createQuery(
                        "SELECT COUNT(s) FROM Servico s WHERE s.categoria = :categoria", Long.class)
                .setParameter("categoria", areaAtuacao)
                .getSingleResult();
        return count.intValue();
    }

    public int contarServicosPorAreaAtuacao(String areaAtuacao) {
        return (int) servicos.stream()
                .filter(s -> s.getProfissional() != null)
                .filter(s -> areaAtuacao.equalsIgnoreCase(s.getProfissional().getAreaAtuacao()))
                .count();
    }

    public Servico saveServico(Servico servico) {
        if (servico.getId() == 0) {
            em.persist(servico);
            return servico;
        } else {
            return em.merge(servico);
        }
    }

    public Servico salvarServico(Servico servico) {
        return null;
    }
}
