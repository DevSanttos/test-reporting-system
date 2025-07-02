package entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
public class UsuarioProfissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String CPF;
    private String email;
    private String telefone;
    private String senha;
    private String areaAtuacao;
    private String especializacao;
    private String localizacao;
    private String nivelServico;
    private String horarioAtuacao;
    private boolean disponivelParaServico;
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setHabilidadesList(List<String> habilidadesList) {
        this.habilidadesList = habilidadesList;
    }

    @ElementCollection
    private List<String> habilidadesList;

    private double mediaAvaliacoes;

    @OneToMany(mappedBy = "profissional", cascade = CascadeType.ALL)
    private List<Servico> servicosList;

    @ElementCollection
    private List<Integer> avaliacoes;

    public UsuarioProfissional(String nome, String areaAtuacao, String especializacao, String localizacao){
        this.nome = nome;
        this.areaAtuacao = areaAtuacao;
        this.especializacao = especializacao;
        this.localizacao = localizacao;
    }

    public UsuarioProfissional(String nome, String CPF, String email, String telefone, String senha, String areaAtuacao) {
        this.setNome(nome);
        this.setCPF(CPF);
        this.setEmail(email);
        this.setTelefone(telefone);
        this.setSenha(senha);
        this.setAreaAtuacao(areaAtuacao);
        this.mediaAvaliacoes = 0.0;
        this.servicosList = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
    }

    public UsuarioProfissional() {
        this.servicosList = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
        this.habilidadesList = new ArrayList<>();
    }

    public UsuarioProfissional(String areaAtuacao, Status status){
        this.areaAtuacao = areaAtuacao;
        this.status = status;
    }


    public List<String> getHabilidadesList() {
        return habilidadesList;
    }

    public void addHabilidade(String habilidade) {
        this.habilidadesList.add(habilidade);
    }

    public void removeHabilidade(String habilidade) {
        this.habilidadesList.remove(habilidade);
    }

    public void setHabilidades(List<String> habilidadesList) {
        this.habilidadesList = habilidadesList;
    }

    public String getNivelServico() {
        return nivelServico;
    }

    public void setNivelServico(String nivelServico) {
        this.nivelServico = nivelServico;
    }

    public String getHorarioAtuacao() {
        return horarioAtuacao;
    }

    public void setHorarioAtuacao(String horarioAtuacao) {
        this.horarioAtuacao = horarioAtuacao;
    }

    public boolean isDisponivelParaServico() {
        return disponivelParaServico;
    }

    public void setDisponivelParaServico(boolean disponivelParaServico) {
        this.disponivelParaServico = disponivelParaServico;
    }

    public String getEspecializacao() {
        return especializacao;
    }

    public void setEspecializacao(String especializacao) {
        this.especializacao = especializacao;
    }



    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void setServicosList(List<Servico> servicosList) {
        this.servicosList = servicosList;
    }

    public void setAvaliacoes(List<Integer> avaliacoes) {
        this.avaliacoes = avaliacoes;
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
        atualizarMedia();
    }

    public void atualizarMedia(){
        if (!avaliacoes.isEmpty()){

            int soma = 0;
            for( int i = 0; i < avaliacoes.size(); i++){
                soma = soma + avaliacoes.get(i);
            }
            this.mediaAvaliacoes = (double) soma/avaliacoes.size();
        }else{
            this.mediaAvaliacoes = 0.0;
        }
    }

    public double getMediaAvaliacoes() {
        return mediaAvaliacoes;
    }

    public void setMediaAvaliacoes(double mediaAvaliacoes) {
        this.mediaAvaliacoes = mediaAvaliacoes;
    }

    public static void ordenarPorMediaAvaliacoes(List<UsuarioProfissional> profissionais){
        profissionais.sort(Comparator.comparingDouble(UsuarioProfissional::getMediaAvaliacoes).reversed());
    }//sort é da interface do List, ele ordena a própria lista
    // O sort recebe um comparator que define a regra de comparação
    // comparingDouble diz que os valores comparados serão Double
    // UsuarioProfissional::getMediaAvaliacoes diz que para cada item da lista será chamado o getMediaAvaliacoes
    // reversed deixa em ordem do maior pro menor

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

    @Override
    public String toString() {
        return "UsuarioProfissional{" +
                "nome='" + nome + '\'' +
                ", CPF='" + CPF + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", senha='" + senha + '\'' +
                ", areaAtuacao='" + areaAtuacao + '\'' +
                ", especializacao='" + especializacao + '\'' +
                ", localizacao='" + localizacao + '\'' +
                ", mediaAvaliacoes=" + mediaAvaliacoes +
                ", avaliacoes=" + avaliacoes +
                ", servicosList=" + servicosList +
                ", habilidadesList=" + habilidadesList +
                '}';
    }
}

