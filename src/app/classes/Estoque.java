package app.classes;

import app.classes.exceptions.ProdutoNaoEncontradoException;
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
    private static ObservableList<Receitas> estoqueR;

    private Estoque() {
        estoque = FXCollections.observableArrayList();
        estoqueR = FXCollections.observableArrayList();
    }

    private static void atualizarEstoqueR() {
        Receitas receitas;

        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "SELECT * FROM softmarketdb.receitas;";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet resultados = stmt.executeQuery();
            estoqueR.clear();
            while (resultados.next()) {
                String nome = resultados.getString("nome");
                double pcusto = resultados.getDouble("pCusto");
                double pvenda = resultados.getDouble("pVenda");
                String codbarras = resultados.getString("codBarras");
                receitas = new Receitas(nome, pcusto, pvenda, codbarras);
                estoqueR.add(receitas);
            }


        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }
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
        atualizarEstoqueR();
        return instancia;
    }

    public ObservableList<Produto> getEstoque() {
        return estoque;
    }

    public ObservableList<Receitas> getEstoqueR() {
        return estoqueR;
    }

    public void adicionarProduto(Produto p) {
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
        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "DELETE FROM softmarketdb.produtos WHERE codBarras = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, codBarras);
            stmt.execute();
        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }

        atualizarEstoque();
    }

    public Produto pesquisarProduto(String codBarras) throws ProdutoNaoEncontradoException {
        Produto p = null;
        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "SELECT * FROM softmarketdb.produtos WHERE codBarras = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, codBarras);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next()) {
                String nome = resultados.getString("nome");
                double pcusto = resultados.getDouble("pCusto");
                double pvenda = resultados.getDouble("pVenda");
                String codbarras = resultados.getString("codBarras");
                int quantidade = resultados.getInt("quantidade");
                double peso = resultados.getDouble("peso");
                String pesavel = resultados.getString("pesavel");
                p = new Produto(nome, quantidade, pcusto, pvenda, codbarras, peso, pesavel);
            }

        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }
        if (p == null) throw new ProdutoNaoEncontradoException("ao pesquisar produto");
        return p;
    }

    public void editarProduto(Produto p) throws ProdutoNaoEncontradoException {
        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "UPDATE softmarketdb.produtos SET produtos.pCusto = ?, produtos.peso = ?, produtos.pVenda = ?, produtos.nome = ?, produtos.pesavel = ?, produtos.quantidade = ? WHERE produtos.codBarras = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setDouble(1, p.getValorCusto());
            stmt.setDouble(2, p.getPeso());
            stmt.setDouble(3, p.getValorVenda());
            stmt.setString(4, p.getNome());
            stmt.setString(5, p.getPesavel());
            stmt.setInt(6, p.getQuantidade());
            stmt.setString(7, p.getCodigo());
            stmt.execute();
        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }
        atualizarEstoque();
    }
}
