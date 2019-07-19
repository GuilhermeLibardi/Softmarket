package br.com.softmarket.classes.RH.util;

public class CPF {
    String cpf; //Armazena-se somente os números

    protected String cpfCompleto(){
        return "";
    }
    protected String cpfSomenteNumeros(){
        return "";
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    private boolean validaCPF(String cpf){
        return false;
    }
}
