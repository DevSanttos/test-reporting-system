package repository;

import model.Usuario;
import model.UsuarioProfissional;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    private List<Usuario> listaUsuario = new ArrayList<>();

    public void create(Usuario usuario) {
        listaUsuario.add(usuario);
    }

    public Usuario findByCPF(String CPF) {
        for (Usuario usuario : listaUsuario) {
            if (usuario.getCPF().equalsIgnoreCase(CPF)) {
                return usuario;
            }
        } return null;
    }

    public Usuario findByEmail(String email) {
        for (Usuario usuario : listaUsuario) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return usuario;
            }
        } return null;
    }

    public Usuario findByTelefone(String telefone) {
        for (Usuario usuario : listaUsuario) {
            if (usuario.getTelefone().equalsIgnoreCase(telefone)) {
                return usuario;
            }
        } return null;
    }
}
