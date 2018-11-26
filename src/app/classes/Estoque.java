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
    private static ObservableList<Ingredientes> estoqueI;

    private Estoque() {
        estoque = FXCollections.observableArrayList();
        estoqueR = FXCollections.observableArrayList();
        estoqueI = FXCollections.observableArrayList();
    }

    private static void atualizarEstoqueI() {
        Ingredientes ingredientes;

        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "SELECT * FROM softmarketdb.ingredientes;";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet resultados = stmt.executeQuery();
            estoqueI.clear();
            while (resultados.next()) {
                String nome = resultados.getString("nome");
                double peso = resultados.getDouble("peso");
                String codigo = resultados.getString("cod");
                ingredientes = new Ingredientes(nome, peso, codigo);
                estoqueI.add(ingredientes);
            }


        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }
    }

    public static void atualizarEstoqueR() {
        Receitas receitas;
        Ingredientes ing;

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
            for (Receitas rec : estoqueR) {
                try (Connection con1 = new ConnectionFactory().getConnection()) {
                    String sq2 = "select i.cod, i.nome, receitas_contem_ingredientes.peso from receitas_contem_ingredientes join ingredientes i on receitas_contem_ingredientes.ingredientes_cod = i.cod where receitas_codBarras = ?";
                    PreparedStatement stmt2 = con.prepareStatement(sq2);
                    stmt2.setString(1, rec.getCodigo());
                    ResultSet resultados2 = stmt2.executeQuery();
                    rec.getIngredientes().clear();
                    while (resultados2.next()) {
                        String cod2 = resultados2.getString("cod");
                        String nome2 = resultados2.getString("nome");
                        double peso = resultados2.getDouble("peso");
                        ing = new Ingredientes(nome2, peso, cod2);
                        rec.getIngredientes().add(ing);
                    }
                } catch (SQLException e) {
                    System.out.print("Erro ao preparar STMT: ");
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }
    }

    private static void atualizarEstoque() {
        Produto produto;

        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "SELECT * FROM softmarketdb.produtos ORDER BY produtos.nome;";
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
                String codIng = resultados.getString("codIngrediente");
                produto = new Produto(nome, quantidade, pcusto, pvenda, codbarras, peso, codIng);
                estoque.add(produto);
            }


        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }
    }

    public static synchronized Estoque getInstance() {
        if (instancia == null)
            instancia = new Estoque();
        atualizarEstoque();
        atualizarEstoqueR();
        atualizarEstoqueI();
        return instancia;
    }

    public static synchronized Estoque getInstance1() {
        if (instancia == null) {
            instancia = new Estoque();
        }
        return instancia;
    }

    public ObservableList<Produto> getEstoque() {
        return estoque;
    }

    public ObservableList<Receitas> getEstoqueR() {
        return estoqueR;
    }

    public ObservableList<Ingredientes> getEstoqueI() {
        return estoqueI;
    }

    public void adicionarProduto(Produto p) {
        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "INSERT INTO softmarketdb.produtos (codBarras, pCusto, pVenda, nome, peso, quantidade, codIngrediente) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, p.getCodigo());
            stmt.setDouble(2, p.getValorCusto());
            stmt.setDouble(3, p.getValorVenda());
            stmt.setString(4, p.getNome());
            stmt.setDouble(5, p.getPeso());
            stmt.setInt(6, p.getQuantidade());
            stmt.setString(7, p.getIngredienteId());
            stmt.execute();

        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }

        atualizarEstoque();
    }

    public void adicionarIngredientes(Ingredientes i) {
        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "INSERT INTO softmarketdb.ingredientes VALUES(?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, i.getCodigo());
            stmt.setString(2, i.getNome());
            stmt.setDouble(3, i.getPeso());
            stmt.execute();

        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }

        atualizarEstoqueI();
    }

    public void adicionarReceita(Receitas r) {
        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "INSERT INTO softmarketdb.receitas VALUES(?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, r.getCodigo());
            stmt.setDouble(2, r.getValorCusto());
            stmt.setDouble(3, r.getValorVenda());
            stmt.setString(4, r.getNome());
            stmt.execute();

        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }

        atualizarEstoqueR();
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

    public void removerIngredientes(String codigo) {
        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "DELETE FROM softmarketdb.ingredientes WHERE cod = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, codigo);
            stmt.execute();
        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }

        atualizarEstoqueI();
    }

    public void removerReceitas(String codBarras) {
        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "DELETE FROM softmarketdb.receitas WHERE codBarras = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, codBarras);
            stmt.execute();
        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }

        atualizarEstoqueR();
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
                String codIng = resultados.getString("codIngrediente");
                p = new Produto(nome, quantidade, pcusto, pvenda, codbarras, peso, codIng);
            }

        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }
        if (p == null) throw new ProdutoNaoEncontradoException("ao pesquisar produto");
        return p;
    }

    public Receitas pesquisarReceita(String codBarras) throws ProdutoNaoEncontradoException {
        Receitas r = null;
        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "SELECT * FROM receitas WHERE codBarras = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, codBarras);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next()) {
                String nome = resultados.getString("nome");
                String codigo = resultados.getString("codBarras");
                double pCusto = resultados.getDouble("pCusto");
                double pVenda = resultados.getDouble("pVenda");
                r = new Receitas(nome, pCusto, pVenda, codigo);
            }

        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }
        if (r == null) throw new ProdutoNaoEncontradoException("ao pesquisar receita");
        return r;
    }

    public Ingredientes pesquisarIngrediente(String codBarras) throws ProdutoNaoEncontradoException {
        Produto p = null;
        Ingredientes i = null;
        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "SELECT * FROM softmarketdb.ingredientes WHERE codBarras = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, codBarras);
            ResultSet resultados = stmt.executeQuery();

            while (resultados.next()) {
                String codigo = resultados.getString("cod");
                String nome = resultados.getString("nome");
                double peso = resultados.getDouble("peso");
                String codIng = resultados.getString("codIngrediente");
                i = new Ingredientes(nome, peso, codigo);
            }

        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }
        if (i == null) throw new ProdutoNaoEncontradoException("ao pesquisar produto");
        return i;
    }

    public void editarProduto(Produto p) throws ProdutoNaoEncontradoException {
        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "UPDATE softmarketdb.produtos SET produtos.pCusto = ?, produtos.peso = ?, produtos.pVenda = ?, produtos.nome = ?, produtos.quantidade = ? WHERE produtos.codBarras = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setDouble(1, p.getValorCusto());
            stmt.setDouble(2, p.getPeso());
            stmt.setDouble(3, p.getValorVenda());
            stmt.setString(4, p.getNome());
            stmt.setInt(5, p.getQuantidade());
            stmt.setString(6, p.getCodigo());
            stmt.execute();
        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }
        atualizarEstoque();
    }

    public void editarIngrediente(Ingredientes i) {
        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "UPDATE softmarketdb.ingredientes SET ingredientes.cod = ?, ingredientes.nome = ?, ingredientes.peso = ?  WHERE ingredientes.cod = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, i.getCodigo());
            stmt.setString(2, i.getNome());
            stmt.setDouble(3, i.getPeso());
            stmt.setString(4, i.getCodigo());
            stmt.execute();
        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }
        atualizarEstoqueI();
    }

    public void editarReceitas(Receitas r) {
        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "UPDATE softmarketdb.receitas SET receitas.codBarras = ?, receitas.nome = ?, receitas.pCusto = ?, receitas.pVenda = ?  WHERE receitas.codBarras = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, r.getCodigo());
            stmt.setString(2, r.getNome());
            stmt.setDouble(3, r.getValorCusto());
            stmt.setDouble(4, r.getValorVenda());
            stmt.setString(5, r.getCodigo());
            stmt.execute();
        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }
        atualizarEstoqueR();
    }
}
