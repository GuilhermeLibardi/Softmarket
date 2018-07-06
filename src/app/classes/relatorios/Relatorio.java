package app.classes.relatorios;
import app.classes.util.Periodo;

import java.time.LocalDate;

public abstract class Relatorio {
    private LocalDate date;
    private Periodo periodo;

    public Relatorio(Periodo periodo) {
        this.date = LocalDate.now();
        this.periodo = periodo;
    }

    /*public Relatorio gerarRelatorio() {
        Relatorio relatorio;

        return relatorio;
    }*/
}
