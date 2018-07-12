package app.classes.relatorios;

import app.Main;
import app.classes.Produto;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.IOException;
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
        StringBuilder sb = new StringBuilder();
        String s;
        for (Produto p : Main.estoqueProdutos)
        {
            s = "Produto: " + p.getNome() + " Código: " + p.getCodigo() + " Quantidade: " +  p.getQuantidade() + "\n";
            sb.append(s);
            quant += p.getQuantidade();
            valorc += p.getQuantidade() * p.getValorCusto();
            valorl += p.getQuantidade() * p.getValorVenda();
        }

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Relatório de estoque");
        alerta.setHeaderText("Relatório de estoque atual (" + LocalDate.now().toString() + ")");
        alerta.setContentText("Quantidade total de produtos: " + quant + "\n" +
                "Custo total dos itens no estoque: " + valorc + "\n" +
                "Valor total de venda dos itens: " + valorl + "\nLucro total após vendas: " + (valorl-valorc) + "\n\n" + sb.toString());

        alerta.showAndWait();



    }


}
