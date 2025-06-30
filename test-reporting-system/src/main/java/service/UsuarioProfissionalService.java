package service;

import model.UsuarioProfissional;
import repository.UsuarioProfissionalRepository;

public class UsuarioProfissionalService {
    private UsuarioProfissionalRepository usuarioProfissionalRepository;

    public UsuarioProfissionalService(UsuarioProfissionalRepository usuarioProfissionalRepository) {
        this.usuarioProfissionalRepository = usuarioProfissionalRepository;
    }

    public boolean create(UsuarioProfissional usuarioProfissional) {
        if (usuarioProfissional.getCPF() == null || usuarioProfissional.getCPF().length() != 11) {
            return false;
        }

        if (usuarioProfissional.getTelefone() == null || usuarioProfissional.getTelefone().length() != 11) {
            return false;
        }

        if (usuarioProfissional.getSenha() == null || usuarioProfissional.getSenha().isEmpty()){
            throw new IllegalArgumentException("Senha é obrigatória.");
        }

        if (usuarioProfissional.getAreaAtuacao() == null || usuarioProfissional.getAreaAtuacao().isEmpty()){
            throw new IllegalArgumentException("Área de atuação inválida! Insira uma área de atuação válida para prosseguir.");
        }

        usuarioProfissionalRepository.create(usuarioProfissional);
        return true;
    }

    public UsuarioProfissional findByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
        return usuarioProfissionalRepository.findByEmail(email);
    }

    public UsuarioProfissional findBySenha(String senha) {
        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("Senha inválida.");
        }
        return usuarioProfissionalRepository.findBySenha(senha);
    }
}