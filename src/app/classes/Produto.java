package app.classes;

public class Produto {
    private String nome;
    private int quantidade;
    private double valorCusto;
    private double valorVenda;
    private String codigo;
    private double peso;
    private String pesavel;
    private String ingredienteId;

    public String getIngredienteId() {
        return ingredienteId;
    }

    public void setIngredienteId(String ingredienteId) {
        this.ingredienteId = ingredienteId;
    }

    public Produto(String nome, int quantidade, double valorCusto, double valorVenda, String codigo, String ingredienteId) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.valorCusto = valorCusto;
        this.valorVenda = valorVenda;
        this.codigo = codigo;
        this.ingredienteId = ingredienteId;
    }

    public Produto(String nome, int quantidade, double valorCusto, double valorVenda, String codigo, double peso, String pesavel, String ingredienteId) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.valorCusto = valorCusto;
        this.valorVenda = valorVenda;
        this.codigo = codigo;
        this.peso = peso;
        this.pesavel = pesavel;
        this.ingredienteId = ingredienteId;
    }

    public Produto(String nome, int quantidade, String codigo) {
        this.nome = nome;
        this.quantidade = quantidade;
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

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getPesavel() {
        return pesavel;
    }

    public void setPesavel(String pesavel) {
        this.pesavel = pesavel;
    }

    @Override
    public String toString() {
        return this.nome;
    }

    public String toCSV() {
        return this.codigo + ',' + this.nome + ',' + this.valorCusto + ',' + this.valorVenda + ',' + this.quantidade + '\n';
    }
}
