package model;



public class Servico{

    private int id;
    private String descricao;
    private String categoria;
    private Status status;
    private Usuario cliente;
    private UsuarioProfissional profissional;

    public Servico(){

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
    }


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
    }

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
}
