package app.classes;

import java.util.ArrayList;

public class Receitas extends Itens {
    private ArrayList<Ingredientes> ingredientes;


    public Receitas (String nome, double valorC, double valorV, String codigo, int quantidade){
        super(nome, codigo, valorC, valorV, quantidade);
        this.ingredientes = new ArrayList<Ingredientes>();
    }

    public Receitas (String nome, double valorC, double valorV, String codigo){
        super(nome, codigo, valorC, valorV);
        this.ingredientes = new ArrayList<Ingredientes>();
    }

    public ArrayList<Ingredientes> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<Ingredientes> ingredientes) {
        this.ingredientes = ingredientes;
    }
}
