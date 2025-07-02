package service;

import model.Usuario;
import model.UsuarioProfissional;
import repository.UsuarioProfissionalRepository;

public class Autenticador {
    private UsuarioProfissionalService usuarioProfissionalService;

    public Autenticador(UsuarioProfissionalService usuarioProfissionalService) {
        this.usuarioProfissionalService = usuarioProfissionalService;
    }

    public boolean autenticar(String email, String senha) {
        UsuarioProfissional usuarioProfissional = usuarioProfissionalService.findByEmail(email);

        if (usuarioProfissional == null) {
            throw new IllegalArgumentException("E-mail incorreto.");
        }

        if (usuarioProfissional.getSenha() == null || !usuarioProfissional.getSenha().equals(senha)) {
            throw new IllegalArgumentException("Senha incorreta.");
        }
        return true;
    }
}
