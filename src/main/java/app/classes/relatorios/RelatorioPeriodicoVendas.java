package app.classes.relatorios;

import app.classes.Venda;
import app.classes.util.Periodo;
import net.sf.jasperreports.engine.JRException;

import java.util.ArrayList;

public class RelatorioPeriodicoVendas extends Relatorio implements Datable {
    private ArrayList<Venda> vendas;
    private Periodo periodo;

    public RelatorioPeriodicoVendas(Periodo periodo, ArrayList<Venda> vendas) {
        super();
        this.vendas = vendas;
        this.definePeriodo(periodo);
    }


    @Override
    public void definePeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    @Override
    public void gerarRelatorio() throws JRException {

    }
}
