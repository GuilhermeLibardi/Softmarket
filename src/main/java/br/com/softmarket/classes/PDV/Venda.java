package br.com.softmarket.classes.PDV;

import java.util.HashMap;
import java.util.Map;

public class Venda {
    private static Map<String, Integer> produtosVenda;
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

    public static Venda iniciarVenda() {
        return new Venda();
    }

    public void fecharVenda() {
        //gerar nota e atualizar estoque
    }

    public void cancelarVenda() {
        produtosVenda.clear();
        valorVenda = 0.0;
    }
}
