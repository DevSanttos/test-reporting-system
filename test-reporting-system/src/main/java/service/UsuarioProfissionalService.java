package service;

import model.UsuarioProfissional;
import repository.UsuarioProfissionalRepository;

import java.util.ArrayList;
import java.util.List;

public class UsuarioProfissionalService {
    private UsuarioProfissionalRepository usuarioProfissionalRepository;
    private CepService cepService;

    public UsuarioProfissionalService(UsuarioProfissionalRepository usuarioProfissionalRepository) {
        this.usuarioProfissionalRepository = usuarioProfissionalRepository;
    }

    public UsuarioProfissionalService(UsuarioProfissionalRepository usuarioProfissionalRepository, CepService cepService){
        this.usuarioProfissionalRepository = usuarioProfissionalRepository;
        this.cepService = cepService;
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

    public boolean update(String cpf, String areaAtuacao) {
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF inválido.");
        }
        if (areaAtuacao == null || areaAtuacao.isEmpty()) {
            throw new IllegalArgumentException("Área de atuação inválida.");
        }
        usuarioProfissionalRepository.update(cpf, areaAtuacao);
        return true;
    }

    public List<UsuarioProfissional> buscarServicos(String palavraChave, String categoria, String localizacao) {
        return usuarioProfissionalRepository.ListaUsuariosProfissionais().stream()
                .filter(p -> palavraChave == null || p.getEspecializacao() != null &&
                        p.getEspecializacao().toLowerCase().contains(palavraChave.toLowerCase()))
                .filter(p -> categoria == null || p.getAreaAtuacao() != null &&
                        p.getAreaAtuacao().equalsIgnoreCase(categoria))
                .filter(p -> localizacao == null || p.getLocalizacao() != null &&
                        p.getLocalizacao().equalsIgnoreCase(localizacao))
                .toList();
    }

    public Object questionarioServico(String cep, String nivelServico, String tipoServico, String diaServico, String horarioServico) {
        if (cep == null || cep.isBlank() || nivelServico == null || nivelServico.isBlank() ||
                tipoServico == null || tipoServico.isBlank() || horarioServico == null || horarioServico.isBlank()) {
            return "Complete o questionário para prosseguir. Há uma ou mais respostas em branco.";
        }

        String localizacao = cepService.buscarLocalizacaoPorCep(cep);
        if (localizacao == null || localizacao.isBlank()) {
            return "CEP inválido.";
        }

        List<UsuarioProfissional> profissionais = usuarioProfissionalRepository.ListaUsuariosProfissionais();

        return profissionais.stream()
                .filter(p -> p.getLocalizacao() != null && p.getLocalizacao().equalsIgnoreCase(localizacao))
                .filter(p -> p.getEspecializacao() != null && p.getEspecializacao().equalsIgnoreCase(tipoServico))
                .filter(UsuarioProfissional::isDisponivelParaServico)
                .filter(p -> p.getHorarioAtuacao() != null &&
                        (p.getHorarioAtuacao().equalsIgnoreCase("Qualquer") || p.getHorarioAtuacao().equalsIgnoreCase(horarioServico)))
                .filter(p -> p.getNivelServico() != null && p.getNivelServico().equalsIgnoreCase(nivelServico))
                .toList();
    }

    public boolean adicionarHabilidade(String cpf, String habilidade) {
        if (cpf == null || cpf.isEmpty() || habilidade == null || habilidade.isEmpty()) {
            throw new IllegalArgumentException("CPF e habilidade são obrigatórios.");
        }

        UsuarioProfissional user = usuarioProfissionalRepository.findByCPF(cpf);
        if (user == null) {
            return false;
        }

        if (user.getHabilidadesList() == null) {
            user.setHabilidades(new ArrayList<>());
        }
        if (!user.getHabilidadesList().contains(habilidade)) {
            user.getHabilidadesList().add(habilidade);
            return true;
        }
        return false;
    }
}