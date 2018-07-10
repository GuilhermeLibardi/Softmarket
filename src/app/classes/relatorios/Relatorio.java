package app.classes.relatorios;

import java.io.IOException;
import java.time.LocalDate;

public abstract class Relatorio {
    private LocalDate date;

    protected Relatorio() {
        this.date = LocalDate.now();
    }

    protected abstract void gerarRelatorio() throws IOException;
}
