package app.classes.relatorios;

import app.Main;
import app.classes.Produto;
import app.classes.Venda;
import app.classes.util.Periodo;
import javafx.scene.control.Alert;

import java.util.ArrayList;

public class RelatorioDeVendas extends Relatorio implements Datable {
    private ArrayList<Venda> vendas;
    private Periodo periodo;

    public RelatorioDeVendas(Periodo periodo, ArrayList<Venda> vendas) {
        super();
        this.vendas = vendas;
        this.definePeriodo(periodo);
    }


    @Override
    public void definePeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    @Override
    public void gerarRelatorio() {
        //Estrutura do relatório
        double arrecadado = 0;
        int quant = 0;
        StringBuilder sb = new StringBuilder();
        String s;
        for (Venda v : Main.vendasFechadas) {
            if ((v.getDate().isAfter(periodo.getInicio()) && v.getDate().isBefore(periodo.getFim())) || (v.getDate().isEqual(periodo.getInicio()) && v.getDate().isEqual(periodo.getFim()))) {
                for (Produto p : v.getProdutos()) {
                    sb.append(String.valueOf(p.getCodigo()) + " - " + p.getNome() + " Quantidade: " + String.valueOf(p.getQuantidade()) + "\n");
                }
                s = (String) "ID: " + String.valueOf(v.getId()) + " Data:" + String.valueOf(v.getDate()) + " Tipo de pagamento: " + String.valueOf(v.getTipoPag()) + " Valor: " + String.valueOf(v.getValor()) + "\n";
                sb.append(s);
                arrecadado += v.getValor();
                quant++;
            }
        }
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Relatório de vendas");
        alerta.setHeaderText("Vendas no período de " + this.periodo.getInicio() + " a " + this.periodo.getInicio());
        alerta.setContentText("Valor arrecadado com as vendas: " + arrecadado + "\n" +
                "Quantidade de vendas: " + quant + "\n" + sb.toString());
        alerta.showAndWait();
    }
}
