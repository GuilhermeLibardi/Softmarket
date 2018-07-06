package app.classes.relatorios;

import app.classes.Produto;
import javafx.collections.ObservableList;

public class RelatorioDeEstoque extends Relatorio {
    private ObservableList<Produto> produtos;

    public RelatorioDeEstoque(ObservableList<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    protected void gerarRelatorio() {
        //Estrutura do relatório
        System.out.println("Gerando relatório de estoque...");
    }


}
