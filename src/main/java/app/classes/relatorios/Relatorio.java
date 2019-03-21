package app.classes.relatorios;

import net.sf.jasperreports.engine.JRException;

import java.time.LocalDate;

public abstract class Relatorio {
    private LocalDate date;

    public Relatorio() {
        this.date = LocalDate.now();
    }

    protected abstract void gerarRelatorio() throws JRException;
}
