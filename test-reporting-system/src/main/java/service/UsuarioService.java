package service;

import model.Usuario;
import repository.UsuarioProfissionalRepository;
import repository.UsuarioRepository;

public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public boolean create(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Campo nome é obrigatório");
        }

        if (!usuario.getCPF().matches("\\d+")) {
            throw new IllegalArgumentException("CPF deve conter apenas números");
        }

        if (usuario.getCPF().length() < 11) {
            throw new IllegalArgumentException("CPF deve conter 11 números");
        }

        if (usuario.getCPF().length() > 11) {
            throw new IllegalArgumentException("CPF deve conter apenas 11 números");
        }

        if (!usuario.getTelefone().matches("\\d+")) {
            throw new IllegalArgumentException("O número de telefone não deve conter letras");
        }

        if (usuario.getTelefone().length() < 11) {
            throw new IllegalArgumentException("O número de telefone deve conter 11 dígitos");
        }

        if (usuario.getTelefone().length() > 11) {
            throw new IllegalArgumentException("O número de telefone deve conter 11 dígitos");
        }

        if (!usuario.getEmail().contains("@")) {
            throw new IllegalArgumentException("E-mail não contém @");
        }

        usuarioRepository.create(usuario);
        return true;
    }
}
