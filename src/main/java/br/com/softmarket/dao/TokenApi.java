package br.com.softmarket.dao;

public class TokenApi {
    private String token;
    private String nome;

    public TokenApi(String token, String nome) {
        this.token = token;
        this.nome = nome;
    }

    public TokenApi() {
        this.token = "";
        this.nome = "";
    }


    public String getToken() {
        return token;
    }

    public String getNome() {
        return nome;
    }
}
