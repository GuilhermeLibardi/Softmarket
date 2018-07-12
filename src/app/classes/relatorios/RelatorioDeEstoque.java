package app.classes.relatorios;

import app.Main;
import app.classes.Produto;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;

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

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Relatório de estoque");
        alerta.setHeaderText("Vendas de estoque atual (" + LocalDate.now().toString() + ")");
        alerta.setContentText("Quantidade total de produtos: " + quant + "\n" +
                "Custo total dos itens no estoque: " + valorc + "\n" +
                "Valor total de venda dos itens: " + valorl + "\nLucro total após vendas: " + (valorl-valorc));
        for (Produto p : Main.estoqueProdutos) {
            alerta.setContentText("Produto:" + p.getNome() + " Código:" + p.getCodigo() + " Quantidade:" + p.getQuantidade() + "\n");
        }
        alerta.showAndWait();



    }


}
