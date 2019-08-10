package br.com.softmarket.classes.PDV;

import br.com.softmarket.classes.Estoque.Estoque;
import br.com.softmarket.classes.Producao.Produto;
import br.com.softmarket.dao.ApiController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.sentry.Sentry;
import javafx.collections.ObservableList;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Venda {
    private Map<String, Integer> produtosVenda;
    private String codigoVenda;
    private Double valorVenda;
    private Double valorTroco;
    private Double valorPago;
    private String TipoPagamento;
    private String OrigemVenda;

    public Venda(String codigoVenda, Double valorVenda, Double valorTroco, Double valorPago, String tipoPagamento, String origemVenda) {
        this.codigoVenda = codigoVenda;
        this.valorVenda = valorVenda;
        this.valorTroco = valorTroco;
        this.valorPago = valorPago;
        TipoPagamento = tipoPagamento;
        OrigemVenda = origemVenda;
    }

    public Venda(){
        this.codigoVenda = "";
        this.valorVenda = 0.0;
        this.valorTroco = 0.0;
        this.valorPago = 0.0;
        TipoPagamento = "";
        OrigemVenda = "";
        produtosVenda = new HashMap<>();
    }

    public static Venda iniciarVenda() {
        return new Venda();
    }

    public void fecharVenda() {
        try {
            ApiController.fecharVenda(this);
        }catch (Exception e){
            Sentry.capture(e);
            System.out.println(e);
        }
    }

    public void cancelarVenda() {
        produtosVenda.clear();
        valorVenda = 0.0;
    }

    public boolean foiVendido(String codProduto){
        return produtosVenda.containsKey(codProduto);
    }

    public int qntVendida(String codProduto){
        return produtosVenda.get(codProduto);
    }

    public Double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(Double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public Double getValorTroco() {
        return valorTroco;
    }

    public void setValorTroco(Double valorTroco) {
        this.valorTroco = valorTroco;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    public void setOrigemVenda(String origemVenda) {
        OrigemVenda = origemVenda;
    }

    public void inserirProdutoVenda(String codBarras, int quantidade) {
        produtosVenda.put(codBarras, quantidade);
    }

    public void removerProdutoVenda(String codBarras){ produtosVenda.remove(codBarras); }

    public Double calcularTroco() {
        return valorPago-valorVenda;
    }

    public Map<String, Integer> getProdutosVenda() {
        return produtosVenda;
    }

    public void setProdutosVenda(Map<String, Integer> produtosVenda) {
        this.produtosVenda = produtosVenda;
    }
}
