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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}