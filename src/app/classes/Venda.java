package app.classes;
import java.util.Date;

public class Venda {
    private ArrayList <Produto> produtos;
    private float valor;
    private float troco;
    private float pagamento;
    private char tipoPag;
    private int id;
    private Date date;

    public Venda(ArrayList<Produto> produtos, Date date) {
        this.produtos = new AraryList<Produto>;
        this.valor = 0;
        this.troco = 0;
        this.pagamento = 0;
        this.tipoPag = null;
        this.id = 1;
        this.date = date;
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
