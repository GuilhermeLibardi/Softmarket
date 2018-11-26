package app.dao;

import app.classes.Ingredientes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public ReceitaContemIngredientes() {

    }

    public ObservableList<Ingredientes> IngredientesFromReceitaCod() {
        ObservableList<Ingredientes> lista = FXCollections.observableArrayList();
        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "select ingredientes_cod as codigo, nome, i.peso from receitas_contem_ingredientes join ingredientes i on receitas_contem_ingredientes.ingredientes_cod = i.cod where receitas_codBarras = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, this.codReceita);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next()) {
                String nome = resultados.getString("nome");
                String codigo = resultados.getString("codigo");
                double peso = resultados.getDouble("peso");
                lista.add(new Ingredientes(nome, peso, codigo));
            }

        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }
        return lista;
    }

    public ObservableList<Ingredientes> IngredientesEscolhidosFromReceitaCod() {
        ObservableList<Ingredientes> lista = FXCollections.observableArrayList();
        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "select ingredientes_cod as codigo, nome, receitas_contem_ingredientes.peso from receitas_contem_ingredientes join ingredientes i on receitas_contem_ingredientes.ingredientes_cod = i.cod where receitas_codBarras = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, this.codReceita);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next()) {
                String nome = resultados.getString("nome");
                String codigo = resultados.getString("codigo");
                double peso = resultados.getDouble("peso");
                lista.add(new Ingredientes(nome, peso, codigo));
            }

        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }
        return lista;
    }

    public void DeletarPorReceita() {
        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "DELETE FROM receitas_contem_ingredientes WHERE receitas_codBarras = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, this.codReceita);
            stmt.execute();
        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }
    }

    public void Inserir() {
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
