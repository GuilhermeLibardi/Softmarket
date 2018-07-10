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
        System.out.println("Gerando relatório de estoque...");
        FileWriter writer = new FileWriter("Relatorio.csv");
        for (Produto p : Main.estoqueProdutos) writer.append(p.toCSV());
        writer.flush();
        writer.close();



    }


}
