package app.classes.relatorios;

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
    protected void gerarRelatorio() {
        //Estrutura do relatório
        System.out.println("Gerando relatório de vendas");
    }
}
