package app.classes;
import java.util.Date;
import java.util.ArrayList;
import java.time.LocalDate;

public class Venda {
    private ArrayList <Produto> produtos;
    private float valor;
    private float troco;
    private float pagamento;
    private char tipoPag;
    private int id;
    private LocalDate date;

    public Venda() {
        this.produtos = new ArrayList<Produto>();
        this.valor = 0;
        this.troco = 0;
        this.pagamento = 0;
        this.tipoPag = ' ';
        this.id = 1;
        this.date = LocalDate.now();
    }

    public float CalculaTroco() {
        return this.troco;
    }

    public void notaFiscal(){

    }

    public void inserirProduto() {

    }

    public void cancelarProduto() {

    }

    public void cancelarVenda() {

    }


}
