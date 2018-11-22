package app.classes;

import java.util.ArrayList;

public class Receitas {
    private ArrayList<Ingredientes> ingredientes;
    private String nome;
    private double valorCusto;
    private double valorVenda;
    private String codigo;


    public Receitas (String nome, double valorC, double valorV, String codigo){
        this.ingredientes = new ArrayList<Ingredientes>();
        this.nome = nome;
        this.valorCusto = valorC;
        this.valorVenda = valorV;
        this.codigo = codigo;
    }

    public ArrayList<Ingredientes> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<Ingredientes> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
}
