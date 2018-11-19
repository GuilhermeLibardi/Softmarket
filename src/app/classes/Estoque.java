package app.classes;

import app.dao.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Estoque {
    private static Estoque instancia;
    private static ObservableList<Produto> estoque;

    private Estoque() {
        estoque = FXCollections.observableArrayList();
    }

    private static void atualizarEstoque() {
        Produto produto;

        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "SELECT * FROM softmarketdb.produtos;";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet resultados = stmt.executeQuery();
            estoque.clear();
            while (resultados.next()) {
                String nome = resultados.getString("nome");
                double pcusto = resultados.getDouble("pCusto");
                double pvenda = resultados.getDouble("pVenda");
                String codbarras = resultados.getString("codBarras");
                int quantidade = resultados.getInt("quantidade");
                double peso = resultados.getDouble("peso");
                String pesavel = resultados.getString("pesavel");
                produto = new Produto(nome, quantidade, pcusto, pvenda, codbarras, peso, pesavel);
                estoque.add(produto);
            }


        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }
    }

    public static synchronized Estoque getInstance() {
        if (instancia == null) instancia = new Estoque();
        atualizarEstoque();
        return instancia;
    }

    public ObservableList<Produto> getEstoque() {
        return estoque;
    }

    public void adicionarProduto(Produto p) {
        Produto produto;

        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "INSERT INTO softmarketdb.produtos (codBarras, pCusto, pVenda, nome, peso, quantidade, pesavel, codIngrediente) VALUES(?,?,?,?,?,?,?, NULL)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, p.getCodigo());
            stmt.setDouble(2, p.getValorCusto());
            stmt.setDouble(3, p.getValorVenda());
            stmt.setString(4, p.getNome());
            stmt.setDouble(5, p.getPeso());
            stmt.setInt(6, p.getQuantidade());
            stmt.setString(7, p.getPesavel());
            //stmt.setString(8, p.getCodigo());
            stmt.execute();

        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }

        atualizarEstoque();
    }

    public void removerProduto(String codBarras) {

    }
}
