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
        if (!validarCPF(usuario.getCPF())) {
            throw new IllegalArgumentException("CPF inválido");
        }

        usuarioRepository.create(usuario);
        return true;
    }

    private boolean validarCPF(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) return false;
        if (cpf.matches("(\\d)\\1{10}")) return false;

        int soma1 = 0, soma2 = 0;
        for (int i = 0; i < 9; i++) {
            int num = Character.getNumericValue(cpf.charAt(i));
            soma1 += num * (10 - i);
            soma2 += num * (11 - i);
        }

        int digito1 = soma1 % 11 < 2 ? 0 : 11 - (soma1 % 11);
        soma2 += digito1 * 2;
        int digito2 = soma2 % 11 < 2 ? 0 : 11 - (soma2 % 11);

        return digito1 == Character.getNumericValue(cpf.charAt(9)) &&
                digito2 == Character.getNumericValue(cpf.charAt(10));
    }

}
