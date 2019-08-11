package br.com.softmarket.dao;

import br.com.softmarket.classes.PDV.Caixa;

public class TokenApi {
    private String token;
    private Caixa caixa;

    public TokenApi(String token, Caixa caixa) {
        this.token = token;
        this.caixa = caixa;
    }

    public String getToken() {
        return token;
    }

    public Caixa getCaixa() {
        return caixa;
    }
}
