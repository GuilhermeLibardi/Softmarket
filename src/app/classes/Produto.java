package app.classes;

public class Produto extends  Itens{
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
        super(nome, codigo,valorCusto,valorVenda, quantidade);
        this.ingredienteId = ingredienteId;
    }

    public Produto(String nome, int quantidade, double valorCusto, double valorVenda, String codigo, double peso, String pesavel, String ingredienteId) {
        super(nome, codigo,valorCusto,valorVenda,quantidade);
        this.peso = peso;
        this.pesavel = pesavel;
        this.ingredienteId = ingredienteId;
    }

    public Produto(String cod, String nome, int quantidade, double preco) {
        super(nome, cod, 0, preco, quantidade);
    }

    public Produto(String nome, int quantidade, String codigo) {
        super(nome, codigo, quantidade);
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
        return getNome();
    }

    public String toCSV() {
        return getCodigo() + ',' + getNome() + ',' + getValorCusto() + ',' + getValorVenda() + ',' + getQuantidade() + '\n';
    }
}
