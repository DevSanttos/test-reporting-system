package entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
public class Servico{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String descricao;
    private String categoria;
    private Status status;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "profissional_cpf")
    private UsuarioProfissional profissional;

//    private List<Servico> servicosList;

    private String tipoServico;

    public Servico(){

    }

    public Servico(String tipoServico) {
        this();
        this.categoria = tipoServico;
        this.tipoServico = tipoServico;
    }

    public Servico(String areaAtuacaoProfissional, UsuarioProfissional profissional){
        this.profissional = profissional;
        this.profissional.setAreaAtuacao(areaAtuacaoProfissional);
    }

    public Servico(String descricao, String categoria, Status status, Usuario cliente, UsuarioProfissional profissional){
        this.descricao = descricao;
        this.categoria = categoria;
        this.status = status;
        this.cliente = cliente;
        this.profissional = profissional;
//        this.servicosList = new ArrayList<>();
    }

    public Servico(Usuario contratante, UsuarioProfissional profissional, String dataServico, String horaServico) {
        this.cliente = contratante;
        this.profissional = profissional;
        this.descricao = "Data: " + dataServico + ", Hora: " + horaServico;
        this.status = Status.EM_ANDAMENTO;
        this.categoria = profissional.getAreaAtuacao();
    }

//    public void addServico(Servico servico) {
//        if (this.servicosList == null) {
//            this.servicosList = new java.util.ArrayList<>();
//        }
//        this.servicosList.add(servico);
//    }
//
//    public void removeServico(Servico servico) {
//        if (this.servicosList != null) {
//            this.servicosList.remove(servico);
//        }
//    }
//
//    public List<Servico> getListaServicosMaisPrestados() {
//        if (this.servicosList == null) {
//            this.servicosList = new ArrayList<>();
//        }
//        return servicosList;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
        this.tipoServico = categoria;
    }

    public String getTipoServico() {
        return tipoServico;
    }

//    public List<String> getListaServicosMaisPrestadosOrdenando() {
//        if (this.servicosList == null || this.servicosList.isEmpty()) {
//            return Collections.emptyList();
//        }
//
//        Map<String, Long> contagemPorTipoServico = this.servicosList.stream()
//                .filter(s -> s.getTipoServico() != null && !s.getTipoServico().isEmpty())
//                .collect(Collectors.groupingBy(Servico::getTipoServico, Collectors.counting()));
//
//        if (contagemPorTipoServico.isEmpty()) {
//            return Collections.emptyList();
//        }

//        return contagemPorTipoServico.entrySet().stream()
//                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
//                .map(Map.Entry::getKey)
//                .collect(Collectors.toList());
//    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public UsuarioProfissional getProfissional() {
        return profissional;
    }

    public void setProfissional(UsuarioProfissional profissional) {
        this.profissional = profissional;
    }

    @Override
    public String toString() {
        return "Servico{" +
                ", descricao='" + descricao + '\'' +
                ", status=" + status +
                ", cliente=" + cliente +
                ", profissional=" + profissional +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
