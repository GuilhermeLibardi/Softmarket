package app.classes.usuarios;

import app.Main;
import app.classes.Produto;
import app.classes.Venda;

import java.util.Date;

public class Vendedor extends Usuario {

    public Vendedor (String nome, String login, String senha)
    {
        super(nome, login, senha);
    }

    public Venda iniciarVenda() {
        return new Venda();
    }

    public void fecharVenda(Venda v){
        Main.vendasFechadas.add(v);
        for (Produto produto : v.getProdutos()){
            for(Produto mainP : Main.estoqueProdutos){
                if(produto.getCodigo().equals(mainP.getCodigo())){
                    mainP.setQuantidade(mainP.getQuantidade()-produto.getQuantidade());
                }
            }
        }
    }

}
