package br.com.softmarket.classes.Producao;

public class Produto {
    private String nome;
    private int quantidade;
    private String codigo_interno;
    private String grupo;
    private Double preco_venda;
    private Double preco_promocional;
    private boolean em_promocao;

    public Produto(String nome, int quantidade, String codigo_interno, String grupo, Double preco_venda, Double preco_promocional, boolean em_promocao) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.codigo_interno = codigo_interno;
        this.grupo = grupo;
        this.preco_venda = preco_venda;
        this.preco_promocional = preco_promocional;
        this.em_promocao = em_promocao;
    }

    public Produto(String nome, Double precoVenda, String codigoInterno, int quantidade) {
        this.codigo_interno = codigoInterno;
        this.nome = nome;
        this.preco_venda = precoVenda;
        this.quantidade = quantidade;
    }

    public Produto(Produto produto) {
        this.nome = produto.nome;
        this.quantidade = produto.quantidade;
        this.codigo_interno = produto.codigo_interno;
        this.grupo = produto.grupo;
        this.preco_venda = produto.preco_venda;
        this.preco_promocional = produto.preco_promocional;
        this.em_promocao = produto.em_promocao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getCodigo_interno() {
        return codigo_interno;
    }

    public void setCodigo_interno(String codigo_interno) {
        this.codigo_interno = codigo_interno;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public Double getPreco_venda() {
        return preco_venda;
    }

    public void setPreco_venda(Double preco_venda) {
        this.preco_venda = preco_venda;
    }

    public Double getPreco_promocional() {
        return preco_promocional;
    }

    public void setPreco_promocional(Double preco_promocional) {
        this.preco_promocional = preco_promocional;
    }

    public boolean isEm_promocao() {
        return em_promocao;
    }

    public void setEm_promocao(boolean em_promocao) {
        this.em_promocao = em_promocao;
    }
}
