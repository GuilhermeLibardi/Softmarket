package  br.com.softmarket.classes.Estoque;

import br.com.softmarket.classes.Producao.Produto;
import br.com.softmarket.classes.Producao.Receita;
import br.com.softmarket.dao.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Estoque {
    private static Estoque instancia;
    private static Map<String, Integer> estoqueP;
    private static Map<String, Integer> estoqueR;

    private Estoque() {
        estoqueP = new HashMap<>();
        estoqueR = new HashMap<>();
    }

    public static synchronized Estoque getInstance() {
        if (instancia == null)
            instancia = new Estoque();
            atualizarEstoque();
        return instancia;
    }

    public boolean existeCodBarras(String codBarras){
        for (String procurar : estoqueP.keySet()){
            if (procurar.equals(codBarras)){
                return true;
            }
        }
        return false;
    }

    public int qntProduto(String codProduto){
        if(estoqueP.containsKey(codProduto)){
            return estoqueP.get(codProduto);
        }
        return -1;
    }

    private static void atualizarEstoque() {
        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "SELECT * FROM produtos ORDER BY produtos.nome;";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet resultados = stmt.executeQuery();
            estoqueP.clear();
            while (resultados.next()) {
                String codbarras = resultados.getString("codBarras");
                int quantidade = resultados.getInt("quantidade");
                estoqueP.put(codbarras, quantidade);
            }
        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }
    }

    public Produto retornarProduto(String codBarras, String quantidade){
        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "SELECT * FROM produtos WHERE produtos.codBarras=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, codBarras);
            ResultSet resultados = stmt.executeQuery();

            //Atualizar isso com o novo BD
            if(resultados != null && resultados.next()) {
                String nome = resultados.getString("nome");
                double pcusto = resultados.getDouble("pCusto");
                double pvenda = resultados.getDouble("pVenda");
                String codbarras = resultados.getString("codBarras");
                double peso = resultados.getDouble("peso");
                return new Produto(nome, pcusto, pvenda, codbarras, peso, Integer.parseInt(quantidade));
            }

        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }
        return null;
    }
}
