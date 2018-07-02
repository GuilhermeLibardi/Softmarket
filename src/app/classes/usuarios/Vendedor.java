package app.classes.usuarios;

import app.classes.Venda;

public class Vendedor extends Usuario {

    public Vendedor (String nome, String login, String senha) {
        super(nome, login, senha);
    }

    public Venda iniciarVenda() {
        Venda venda;

        return venda;
    }

    public void fecharVenda(Venda v){

    }

}
