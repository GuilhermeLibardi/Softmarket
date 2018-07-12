package app.classes.relatorios;

import app.Main;
import app.classes.Venda;
import app.classes.util.Periodo;

import java.util.ArrayList;

public class RelatorioDeVendas extends Relatorio implements Datable{
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
        System.out.println("Gerando relatório de vendas");
        float arrecadado = 0;
        int quant = 0;
        for (Venda v : Main.vendasFechadas)
        {
            if(v.getDate().isAfter(periodo.getsInicio()) && v.getDate().isBefore(periodo.getsFim()))
            {
                System.out.println("ID: " + v.getId() + " Data:" + v.getDate() + " Tipo de pagamento: " + v.getTipoPag() + " Valor: " + v.getValor() + "\n");
                arrecadado += v.getValor();
                quant++;
            }
        }
        System.out.println("Valor arrecadado com as vendas: " + arrecadado);
        System.out.println("Quantidade de vendas: " + quant);
    }
}
