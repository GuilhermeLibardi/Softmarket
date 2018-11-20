package app.classes.relatorios;

import app.dao.ConnectionFactory;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;

public class RelatorioAnualVendas extends Relatorio {
    @Override
    public void gerarRelatorio() throws JRException {
        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.getConnection();
        String src = "./relatorios/relatorio_anual_vendas.jasper";
        JasperPrint jasperPrint = null;
        jasperPrint = JasperFillManager.fillReport(src, null, conn);
        JasperViewer viewer = new JasperViewer(jasperPrint, false);
        viewer.setVisible(true);
    }
}
