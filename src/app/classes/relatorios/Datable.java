package app.classes.relatorios;

import app.classes.util.Periodo;


//Interface que define quais tipos de relatórios terão o campo data
public interface Datable {
    public void definePeriodo(Periodo periodo);
}
