package app.classes;

public class Produto {
    private String nome;
    private int quantidade;
    private float valorCusto;
    private float valorVenda;
    private int codigo;

    public Produto(String nome, int quantidade, float valorCusto, float valorVenda, int codigo) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.valorCusto = valorCusto;
        this.valorVenda = valorVenda;
        this.codigo = codigo;
    }
}
