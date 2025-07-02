package repository;

import entity.UsuarioProfissional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public class UsuarioProfissionalRepository {
    private List<UsuarioProfissional> listaUsuarioProfissinal = new ArrayList<>();
    private EntityManager em;

    public UsuarioProfissionalRepository(EntityManager em) {
        this.em = em;
    }

    public UsuarioProfissionalRepository() {
        listaUsuarioProfissinal = new ArrayList<>();
    }

    public void create(UsuarioProfissional usuarioProfissional) {
        listaUsuarioProfissinal.add(usuarioProfissional);
    }

    public void createDB(UsuarioProfissional usuarioProfissional) {
        em.persist(usuarioProfissional);
    }

    public void update(UsuarioProfissional usuarioProfissional) {
        try {
            em.getTransaction().begin();

            UsuarioProfissional usuarioProfissionalDB = em.find(UsuarioProfissional.class, usuarioProfissional.getId());

            if (usuarioProfissionalDB != null) {
                usuarioProfissionalDB.setStatus(usuarioProfissional.getStatus());
            } else {
                System.out.println("Profissional não encontrado!");
                throw new RuntimeException("Erro ao realizar a consulta por ID.");
            }
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao realizar a consulta por ID." + e.getMessage());
        }
    }

    public UsuarioProfissional findByCPF(String CPF) {
        for (UsuarioProfissional usuarioProfissional : listaUsuarioProfissinal) {
            if (usuarioProfissional.getCPF().equalsIgnoreCase(CPF)) {
                return usuarioProfissional;
            }
        } return null;
    }


    public UsuarioProfissional findByEmail(String email) {
        if (email == null || email.isEmpty()) {
            return null;
        }
        for (UsuarioProfissional usuarioProfissional : listaUsuarioProfissinal) {
            if (usuarioProfissional.getEmail().equalsIgnoreCase(email)) {
                return usuarioProfissional;
            }
        } return null;
    }

    public UsuarioProfissional findBySenha(String senha) {
        for (UsuarioProfissional usuarioProfissional : listaUsuarioProfissinal) {
            if (usuarioProfissional.getSenha().equalsIgnoreCase(senha)) {
                return usuarioProfissional;
            }
        } return null;
    }

    public UsuarioProfissional update(String cpf, String AreaAtuacao) {
        UsuarioProfissional userProfissional = findByCPF(cpf);

        if (userProfissional != null) {
            userProfissional.setAreaAtuacao(AreaAtuacao);
            return userProfissional;
        }
        return null;
    }

    public void delete(UsuarioProfissional usuarioProfissional) {
        listaUsuarioProfissinal.remove(usuarioProfissional);
    }

    public List<UsuarioProfissional> getListaUsuarioProfissinal() {
        return this.listaUsuarioProfissinal;
    }

    public List<UsuarioProfissional> ListaUsuariosProfissionais() {
        try {
            return em.createQuery("SELECT up FROM UsuarioProfissional up", UsuarioProfissional.class).getResultList();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao buscar todos os profissionais: " + e.getMessage());
        }
    }

    public List<UsuarioProfissional> buscarTodosOrdenadosPorAvaliacao() {
        return em.createQuery(
                "SELECT up FROM UsuarioProfissional up ORDER BY up.mediaAvaliacoes DESC", UsuarioProfissional.class
        ).getResultList();
    }

}
