package model;

import java.util.ArrayList;
import java.util.List;

public class UsuarioProfissional {
    private String nome;
    private String CPF;
    private String email;
    private String telefone;
    private String senha;
    private String areaAtuacao;
    private List<Servico> servicosList;
    private List<Integer> avaliacoes;

    public UsuarioProfissional(String nome, String CPF, String email, String telefone, String senha, String areaAtuacao) {
        this.setNome(nome);
        this.setCPF(CPF);
        this.setEmail(email);
        this.setTelefone(telefone);
        this.setSenha(senha);
        this.setAreaAtuacao(areaAtuacao);
        this.servicosList = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
    }

    public UsuarioProfissional() {
        this.servicosList = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
    }

    public void receberAvaliacaoString(Servico servico, String notaString) {
        try {
            int nota = Integer.parseInt(notaString);
            receberAvaliacao(servico, nota);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("A nota deve ser um número inteiro entre 1 e 5.");
        }
    }

    public void receberAvaliacaoDouble(Servico servico, double nota) {
        if (nota % 1 != 0) {
            throw new IllegalArgumentException("A nota deve ser um número inteiro entre 1 e 5.");
        }

        int notaInteira = (int) nota;

        if (notaInteira < 1 || notaInteira > 5) {
            throw new IllegalArgumentException("A nota deve ser um número inteiro entre 1 e 5.");
        }

        // Chama o método principal com int
        receberAvaliacao(servico, notaInteira);
    }


    public void receberAvaliacao(Servico servico, int nota) {

        if (servico.getStatus() != Status.FINALIZADO) {
            throw new IllegalArgumentException("Serviço precisa estar finalizado para ser avaliado.");
        }
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("A nota deve ser um valor entre 1 e 5.");
        }

        this.avaliacoes.add(nota);
    }


    public List<Integer> getAvaliacoes() {
        return avaliacoes;
    }

    public void adicionarServico(Servico servico) {
        this.servicosList.add(servico);
    }

    public List<Servico> getServicosList() {
        return servicosList;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCPF() {
        return this.CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getAreaAtuacao() {
        return this.areaAtuacao;
    }

    public void setAreaAtuacao(String areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
    }
}
