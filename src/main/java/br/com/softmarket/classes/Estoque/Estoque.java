package  br.com.softmarket.classes.Estoque;

import br.com.softmarket.classes.Producao.Produto;
import br.com.softmarket.dao.ApiController;

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
        try {
            for(Produto produto : ApiController.index()){
                estoqueP.put(produto.getCodigo_interno(), produto.getQuantidade());
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public Produto retornarProduto(String codInterno, String quantidade){
        try {
            for(Produto produto : ApiController.index()){
                if(produto.getCodigo_interno().equals(codInterno)){
                    return new Produto(produto.getNome(), Integer.parseInt(quantidade), produto.getCodigo_interno(),produto.getGrupo(),produto.getPreco_venda(),produto.getPreco_promocional(),produto.isEm_promocao());
                }
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
