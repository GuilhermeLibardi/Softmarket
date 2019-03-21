package app.classes;

public class Ingredientes {
    private String nome;
    private double peso;
    private String codigo;

    public Ingredientes(String nome, double peso, String codigo) {
        this.nome = nome;
        this.peso = peso;
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}