package repository;

import model.Usuario;
import model.UsuarioProfissional;

import java.util.ArrayList;
import java.util.List;

public class UsuarioProfissionalRepository {
    private List<UsuarioProfissional> listaUsuarioProfissinal = new ArrayList<>();

    public void create(UsuarioProfissional usuarioProfissional) {
        listaUsuarioProfissinal.add(usuarioProfissional);
    }

    public UsuarioProfissional findByCPF(String CPF) {
        for (UsuarioProfissional usuarioProfissional : listaUsuarioProfissinal) {
            if (usuarioProfissional.getCPF().equalsIgnoreCase(CPF)) {
                return usuarioProfissional;
            }
        } return null;
    }

    public UsuarioProfissional findByEmail(String email) {
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
        return null;
    }

    public void delete(UsuarioProfissional usuarioProfissional) {
        listaUsuarioProfissinal.remove(usuarioProfissional);
    }
}
