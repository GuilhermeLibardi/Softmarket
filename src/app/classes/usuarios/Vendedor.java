package app.classes.usuarios;

import app.Main;
import app.classes.Produto;
import app.classes.Venda;
import app.classes.util.CSVParser;

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
        CSVParser parser = new CSVParser();
        for (Produto produto : v.getProdutos()){
            for(Produto mainP : Main.estoqueProdutos){
                if(produto.getCodigo().equals(mainP.getCodigo())){
                    mainP.setQuantidade(mainP.getQuantidade()-produto.getQuantidade());
                }
            }
        }
        try {
            parser.writeVenda(Main.vendasFechadas);
            parser.writeEstoque(Main.estoqueProdutos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
