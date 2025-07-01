package service;

import repository.UsuarioProfissionalRepository;

public class UsuarioService {
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
}
