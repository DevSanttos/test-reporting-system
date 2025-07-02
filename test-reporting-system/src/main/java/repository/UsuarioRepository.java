package repository;

import entity.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class UsuarioRepository {
    private EntityManager em;

    public UsuarioRepository() {

    }

    public UsuarioRepository(EntityManager em) {
        this.em = em;
    }

    public void create(Usuario usuario) {
        em.persist(usuario);
    }

    public Usuario findByCPF(String CPF) {
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.CPF = :cpf", Usuario.class)
                    .setParameter("cpf", CPF)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Usuario findByEmail(String email) {
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Usuario findByTelefone(String telefone) {
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.telefone = :telefone", Usuario.class)
                    .setParameter("telefone", telefone)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
