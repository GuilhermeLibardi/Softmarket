package app.classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

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

    public Double calculaTroco() {
        return this.pagamento-this.valor;
    }

    public String notaFiscal(){
        StringBuilder nota = new StringBuilder();
        nota.append("Lista de Produtos\n\n");
        for(Produto produtosI : produtos){
            nota.append(produtosI.getCodigo()).append(" - ").append(produtosI.getNome()).append(" - ").append(produtosI.getQuantidade()).append(String.format("x R$ %.2f", produtosI.getValorVenda()));
            nota.append("\n");
        }
        nota.append("\nForma de Pagamento: ");
        if(tipoPag=='c'){
            nota.append("Cartão\n");
        }else{
            nota.append("Dinheiro\n");
        }
        nota.append("Data: " + LocalDate.now().toString() + "\n");
        nota.append("Valor Total: " + String.format("R$ %.2f", valor) + "\n");
        nota.append("Valor Pago: " + String.format("R$ %.2f", pagamento) + "\n");
        nota.append("Troco: " + String.format("R$ %.2f", troco) + "\n\n");

        return String.valueOf(nota);
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
        for (Iterator<Produto> i = produtos.iterator(); i.hasNext();) {
            Produto produto = i.next();
            if (produto.getCodigo().equals(produto1.getCodigo())) {
                i.remove();
                break;
            }
        }
    }

    public void cancelarVenda() {
        produtos.clear();
    }

    public String toCSV() {
        return this.valor + ',' + this.troco + ',' + this.pagamento + ',' + this.tipoPag + ',' + this.id + ',' + this.date.toString() + ',' + this.produtos + '\n';
    }

}
