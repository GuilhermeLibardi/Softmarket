package app.classes;

public class Itens {

    private String nome;
    private String codigo;
    private double valorCusto;
    private double valorVenda;
    private int quantidade;

    public Itens(String nome, String codigo, double valorCusto, double valorVenda, int quantidade) {
        this.nome = nome;
        this.codigo = codigo;
        this.valorCusto = valorCusto;
        this.valorVenda = valorVenda;
        this.quantidade = quantidade;
    }

    public Itens(String nome, String codigo, double valorCusto, double valorVenda) {
        this.nome = nome;
        this.codigo = codigo;
        this.valorCusto = valorCusto;
        this.valorVenda = valorVenda;
    }

    public Itens(String nome, String codigo, int quantidade) {
        this.nome = nome;
        this.codigo = codigo;
        this.quantidade = quantidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getValorCusto() {
        return valorCusto;
    }

    public void setValorCusto(double valorCusto) {
        this.valorCusto = valorCusto;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
