package br.com.softmarket.classes.RH;

import br.com.softmarket.classes.RH.util.CPF;
import br.com.softmarket.dao.ApiController;
import io.sentry.Sentry;

public class Funcionario {
    private String nome;
    private String login;
    private String password;

    public Funcionario(String login, String senha) {
        this.nome="";
        this.login = login;
        this.password = senha;
    }

    public boolean verificaLogin(){
        try{
            this.nome = ApiController.verificaLogin(this);
            return true;
        } catch (Exception e){
            Sentry.capture(e);
        }
        System.out.println("Apareceu aqui");
        return false;
    }

}