package model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nome;
    private String CPF;
    private String email;
    private String telefone;
    private String senha;
    private List<Servico> servicoList;
    private List<Integer> avaliacoes;


    public void receberAvaliacao(Servico servico, int nota) {
        if (servico.getStatus() == Status.EM_ANDAMENTO || servico.getStatus() == Status.CANCELADO) {
            throw new IllegalArgumentException("Serviço precisa estar finalizado para o cliente ser avaliado.");
        }
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("A nota deve ser um valor entre 1 e 5.");
        }
        this.avaliacoes.add(nota);
    }

    public Usuario(String nome, String CPF, String email, String telefone, String senha) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Campo nome é obrigatório");
        }
        if (!CPF.matches("\\d+")) {
            throw new IllegalArgumentException("CPF deve conter apenas números");
        }
        if (CPF.length() < 11) {
            throw new IllegalArgumentException("CPF deve conter 11 números");
        }
        if (CPF.length() > 11) {
            throw new IllegalArgumentException("CPF deve conter apenas 11 números");
        }
        if (telefone.length() < 11) {
            throw new IllegalArgumentException("O número de telefone deve conter 11 dígitos");
        }
        if (telefone.length() > 11) {
            throw new IllegalArgumentException("O número de telefone deve conter 11 dígitos");
        }
        if (!telefone.matches("\\d+")) {
            throw new IllegalArgumentException("O número de telefone não deve conter letras");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("E-mail não contém @");
        }

        this.nome = nome;
        this.CPF = CPF;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;

        this.servicoList = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
    }

    public void adicionarServico(Servico servico) {
        this.servicoList.add(servico);
        this.avaliacoes = new ArrayList<>();
    }

    public List<Servico> getServicosList() {
        return servicoList;
    }

    public Usuario() {
        this.servicoList = new ArrayList<>();
        this.avaliacoes =  new ArrayList<>();
    }

    public List<Integer> getAvaliacoes() {
        return avaliacoes;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCPF() {
        return this.CPF;
    }

    public String getEmail() {
        return this.email;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

