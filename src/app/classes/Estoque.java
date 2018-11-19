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
                // Double peso = resultados.getDouble("peso");
                // String pesavel = resultados.getString("pesavel");
                produto = new Produto(nome, quantidade, pcusto, pvenda, codbarras);
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

    }

    public void removerProduto() {

    }
}
