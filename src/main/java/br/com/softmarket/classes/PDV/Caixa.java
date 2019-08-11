package br.com.softmarket.classes.PDV;

import br.com.softmarket.classes.RH.Funcionario;
import br.com.softmarket.dao.ApiController;
import io.sentry.Sentry;

import java.util.List;

public class Caixa {

    private Double montante_inicial;
    private Double montante_final;
    private String funcionario;
    private int id;

    public Caixa(){

    }

    public Caixa AbrirCaixa(Double montanteInicial){
        try{
            return ApiController.abrirCaixa(montanteInicial);
        } catch (Exception e){
            Sentry.capture(e);
        }
        return null;
    }

    public void FecharCaixa(){
        try {
            ApiController.fecharCaixa(this.montante_final);
        } catch (Exception e){
            Sentry.capture(e);
        }
    }
    public void RealizarSangria(Double quantidade){}

    public Double getMontante_inicial() {
        return montante_inicial;
    }

    public Double getMontante_final() {
        return montante_final;
    }

    public void setMontante_final(Double montante_final) {
        this.montante_final = montante_final;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Caixa{" +
                "montante_inicial=" + montante_inicial +
                ", montante_final=" + montante_final +
                ", funcionario='" + funcionario + '\'' +
                '}';
    }
}
