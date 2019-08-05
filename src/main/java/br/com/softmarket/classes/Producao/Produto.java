package br.com.softmarket.classes.Producao;

public class Produto {
    private String codigoProduto;
    private String codigoBarras;
    private String tipo;
    private String nome;
    private String grupo;
    private String subgrupo;
    private String descricao;
    private double peso;
    private int minimoEstoque;
    private Double precoCusto;
    private Double precoVenda;
    private Double precoPromocional;
    private boolean promocaoAtiva;
    private int quantidade;

    public Produto(String codigoProduto, String codigoBarras, String tipo, String nome, String grupo, String subgrupo, String descricao, double peso, int minimoEstoque, Double precoCusto, Double precoVenda, Double precoPromocional, boolean promocaoAtiva) {
        this.codigoProduto = codigoProduto;
        this.codigoBarras = codigoBarras;
        this.tipo = tipo;
        this.nome = nome;
        this.grupo = grupo;
        this.subgrupo = subgrupo;
        this.descricao = descricao;
        this.peso = peso;
        this.minimoEstoque = minimoEstoque;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
        this.precoPromocional = precoPromocional;
        this.promocaoAtiva = promocaoAtiva;
    }

    public Produto(String nome, Double precoCusto, Double precoVenda, String codigoBarras, Double peso, int quantidade) {
        this.codigoBarras = codigoBarras;
        this.nome = nome;
        this.peso = peso;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
        this.quantidade = quantidade;
    }

    public Produto(Produto p){
        this.codigoBarras = p.codigoBarras;
        this.nome = p.nome;
        this.peso = p.peso;
        this.precoCusto = p.precoCusto;
        this.precoVenda = p.precoVenda;
    }

    public Produto(String nome, Double precoCusto, Double precoVenda, String codigoBarras, Double peso) {
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getSubgrupo() {
        return subgrupo;
    }

    public void setSubgrupo(String subgrupo) {
        this.subgrupo = subgrupo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getMinimoEstoque() {
        return minimoEstoque;
    }

    public void setMinimoEstoque(int minimoEstoque) {
        this.minimoEstoque = minimoEstoque;
    }

    public Double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(Double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Double getPrecoPromocional() {
        return precoPromocional;
    }

    public void setPrecoPromocional(Double precoPromocional) {
        this.precoPromocional = precoPromocional;
    }

    public boolean isPromocaoAtiva() {
        return promocaoAtiva;
    }

    public void setPromocaoAtiva(boolean promocaoAtiva) {
        this.promocaoAtiva = promocaoAtiva;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
