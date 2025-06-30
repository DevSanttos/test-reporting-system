package service;

import repository.UsuarioProfissionalRepository;

public class Autenticador {
    private UsuarioProfissionalRepository usuarioProfissionalRepository;
    private UsuarioProfissionalService usuarioProfissionalService;

    public Autenticador() {
        usuarioProfissionalRepository = new UsuarioProfissionalRepository();
        usuarioProfissionalService = new UsuarioProfissionalService(usuarioProfissionalRepository);
    }

    public boolean autenticar(String email, String senha) {
        if (usuarioProfissionalService.findByEmail(email) == null) {
            throw new IllegalArgumentException("E-mail incorreto.");
        }

        if (usuarioProfissionalService.findBySenha(senha) == null) {
            throw new IllegalArgumentException("Senha incorreta.");
        }
        return true;
    }
}
