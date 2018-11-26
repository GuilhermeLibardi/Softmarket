package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReceitaContemIngredientes {
    private String codIngrediente;
    private String codReceita;
    private double peso;

    public ReceitaContemIngredientes(String codIngrediente, String codReceita, double peso) {
        this.codIngrediente = codIngrediente;
        this.codReceita = codReceita;
        this.peso = peso;
    }

    public ReceitaContemIngredientes(){

    }

    public void Inserir(){
        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "INSERT INTO receitas_contem_ingredientes (ingredientes_cod, receitas_codBarras, peso) VALUES (?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, this.codIngrediente);
            stmt.setString(2, this.codReceita);
            stmt.setDouble(3, this.peso);
            stmt.execute();

        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }
    }

    public String getCodIngrediente() {
        return codIngrediente;
    }

    public void setCodIngrediente(String codIngrediente) {
        this.codIngrediente = codIngrediente;
    }

    public String getCodReceita() {
        return codReceita;
    }

    public void setCodReceita(String codReceita) {
        this.codReceita = codReceita;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
}
