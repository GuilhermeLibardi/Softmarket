package app.classes;

public class Produto {
    private String nome;
    private int quantidade;
    private double valorCusto;
    private double valorVenda;
    private String codigo;

    public Produto(String nome, int quantidade, double valorCusto, double valorVenda, String codigo) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.valorCusto = valorCusto;
        this.valorVenda = valorVenda;
        this.codigo = codigo;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return this.nome;
    }

    public String toCSV() {
        return this.codigo + ',' + this.nome + ',' + this.valorCusto + ',' + this.valorVenda + ',' + this.quantidade + '\n';
    }
}
