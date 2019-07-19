package br.com.softmarket.classes.PDV;

import br.com.softmarket.classes.Producao.Produto;

public class Venda {
    String codigoVenda;
    Double valorVenda;
    Double valorTroco;
    Double valorPago;
    String TipoPagamento;
    String OrigemVenda;

    protected void InserirProdutoVenda(Produto produto, int Quantidade) {
    }

    protected Double CalcularTroco(Double valorVenda, Double valorPago) {
        return 0.0;
    }

    protected void IniciarVenda() {
    }

    protected void FecharVenda() {
    }

    protected void CancelarVenda() {
    }

    protected void RemoverProdutoVenda(Produto produto, int quantidade) {
    }


}
