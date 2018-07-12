package app.classes.util;
import java.time.LocalDate;

public class Periodo {
    private LocalDate inicio;
    private LocalDate fim;

    public Periodo(LocalDate inicio, LocalDate fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public LocalDate getsInicio()
    {
        return inicio;
    }
    public LocalDate getsFim()
    {
        return fim;
    }
}
