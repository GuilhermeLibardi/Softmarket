package app.classes.relatorios;

import app.dao.ConnectionFactory;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;

public class RelatorioMensalVendas extends Relatorio {

    @Override
    public void gerarRelatorio() throws JRException {
        String src = "relatorios/relatorio_mensal_vendas.jasper";
        JasperPrint jasperPrint = null;
        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.getConnection();
        jasperPrint = JasperFillManager.fillReport(src, null, conn);
        JasperViewer viewer = new JasperViewer(jasperPrint, false);
        viewer.setVisible(true);
    }
}
