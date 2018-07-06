package app.classes;

import java.time.LocalDate;
import java.util.ArrayList;

public class Venda {
    private ArrayList <Produto> produtos;
    private double valor;
    private double troco;
    private double pagamento;
    private char tipoPag;
    private int id;
    private LocalDate date;

    public Venda() {
        this.produtos = new ArrayList<Produto>();
        this.valor = 0;
        this.troco = 0;
        this.pagamento = 0;
        this.tipoPag = ' ';
        this.id = 1;
        this.date = LocalDate.now();
    }

    public Double CalculaTroco() {
        return this.troco;
    }

    public void notaFiscal(){

    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getTroco() {
        return troco;
    }

    public void setTroco(Double troco) {
        this.troco = troco;
    }

    public Double getPagamento() {
        return pagamento;
    }

    public void setPagamento(Double pagamento) {
        this.pagamento = pagamento;
    }

    public char getTipoPag() {
        return tipoPag;
    }

    public void setTipoPag(char tipoPag) {
        this.tipoPag = tipoPag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void inserirProduto(Produto produto1) {
        produtos.add(produto1);
    }

    public void cancelarProduto(Produto produto1) {

    }

    public void cancelarVenda() {
        produtos.clear();
    }


}
