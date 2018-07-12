package app.classes.relatorios;

import app.Main;
import app.classes.Produto;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.URL;

public class RelatorioDeEstoque extends Relatorio {
    private ObservableList<Produto> produtos;

    public RelatorioDeEstoque(ObservableList<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public void gerarRelatorio() throws IOException {
        //Estrutura do relatório
        int quant = 0;
        float valorc = 0, valorl = 0;
        System.out.println("Gerando relatório de estoque...");

        for (Produto p : Main.estoqueProdutos)
        {
            System.out.println("Produto:" + p.getNome() + " Código:" + p.getCodigo() + " Quantidade:" +  p.getQuantidade() + "\n");
            quant += p.getQuantidade();
            valorc += p.getQuantidade() * p.getValorCusto();
            valorl += p.getQuantidade() * p.getValorVenda();
        }
        System.out.println("Quantidade total de produtos é: " + quant);
        System.out.println("Valor total de custo dos itens do estoque: " + valorc);
        System.out.println("Valor total de venda dos itens do estoque: " + valorl);
        System.out.println("Lucro total dos itens do estoque após venda: " + (valorl-valorc));




    }


}
