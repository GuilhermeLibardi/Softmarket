package app.classes.usuarios;

import app.Main;
import app.classes.Estoque;
import app.classes.Itens;
import app.classes.Produto;
import app.classes.Venda;
import app.classes.util.CSVParser;

public class Vendedor extends Usuario {

    public Vendedor (String nome, String login, String senha)
    {
        super(nome, login, senha);
    }

    public Venda iniciarVenda() {
        return new Venda();
    }

    public void fecharVenda(Venda v){
        v.inserirVenda();
    }

}
