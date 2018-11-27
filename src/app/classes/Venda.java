package app.classes;

import app.dao.ConnectionFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class Venda {
    private ArrayList <Itens> itens;
    private double valor;
    private double troco;
    private double pagamento;
    private char tipoPag;
    private int id;
    private LocalDate date;

    public Venda() {
        this.itens = new ArrayList<Itens>();
        this.valor = 0;
        this.troco = 0;
        this.pagamento = 0;
        this.tipoPag = ' ';
        this.id = 1;
        this.date = LocalDate.now();
    }

    public Double calculaTroco() {
        return this.pagamento-this.valor;
    }

    public String notaFiscal(){
        StringBuilder nota = new StringBuilder();
        nota.append("Lista de Produtos\n\n");
        for(Itens produtosI : itens){
            nota.append(produtosI.getCodigo()).append(" - ").append(produtosI.getNome()).append(" - ").append(produtosI.getQuantidade()).append(String.format("x R$ %.2f", produtosI.getValorVenda()));
            nota.append("\n");
        }
        nota.append("\nForma de Pagamento: ");
        if(tipoPag=='c'){
            nota.append("Cartão\n");
        }else{
            nota.append("Dinheiro\n");
        }
        nota.append("Data: " + LocalDate.now().toString() + "\n");
        nota.append("Valor Total: " + String.format("R$ %.2f", valor) + "\n");
        nota.append("Valor Pago: " + String.format("R$ %.2f", pagamento) + "\n");
        nota.append("Troco: " + String.format("R$ %.2f", troco) + "\n\n");

        return String.valueOf(nota);
    }

    public void inserirVenda(){
        int cod=0;

        try (Connection con = new ConnectionFactory().getConnection()) {
            String sql = "INSERT INTO vendas (formaPagamento, total) VALUES(?,?)";
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if(this.tipoPag == 'c'){
                stmt.setString(1,"Cartão");
            }else if(this.tipoPag == 'd'){
                stmt.setString(1,"Dinheiro");
            }
            stmt.setDouble(2, this.getValor());
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                cod = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }

        for(Itens item : this.itens){
            if(item instanceof Produto){
                try (Connection con = new ConnectionFactory().getConnection()) {
                    String sq2 = "INSERT INTO jpacon92_softmarketdb.vendas_contem_produtos (vendas_cod, produtos_codBarras, quantidade) VALUES(?,?,?)";
                    PreparedStatement stmt2 = con.prepareStatement(sq2);
                    stmt2.setInt(1, cod);
                    stmt2.setString(2, item.getCodigo());
                    stmt2.setInt(3, item.getQuantidade());
                    stmt2.execute();
                } catch (SQLException e) {
                    System.out.print("Erro ao preparar STMT: ");
                    System.out.println(e.getMessage());
                }
            } else if(item instanceof Receitas){
                try (Connection con = new ConnectionFactory().getConnection()) {
                    String sq3 = "INSERT INTO jpacon92_softmarketdb.vendas_contem_receitas (vendas_cod, receitas_codBarras, quantidade) VALUES(?,?,?)";
                    PreparedStatement stmt3 = con.prepareStatement(sq3);
                    stmt3.setInt(1, cod);
                    stmt3.setString(2, item.getCodigo());
                    stmt3.setInt(3, item.getQuantidade());
                    stmt3.execute();
                } catch (SQLException e) {
                    System.out.print("Erro ao preparar STMT: ");
                    System.out.println(e.getMessage());
                }
            }
        }
        Estoque.getInstance().atualizarEstoqueVenda(this);
    }

    public ArrayList<Itens> getItens() {
        return itens;
    }

    public void setItens(ArrayList<Itens> itens) {
        this.itens = itens;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getTroco() {
        return troco;
    }

    public void setTroco(Double troco) {
        this.troco = troco;
    }

    public Double getPagamento() {
        return pagamento;
    }

    public void setPagamento(Double pagamento) {
        this.pagamento = pagamento;
    }

    public char getTipoPag() {
        return tipoPag;
    }

    public void setTipoPag(char tipoPag) {
        this.tipoPag = tipoPag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void inserirItem(Itens item) {
        itens.add(item);
    }

    public void cancelarProduto(Itens item) {
        for (Iterator<Itens> i = itens.iterator(); i.hasNext();) {
            Itens item1 = i.next();
            if (item1.getCodigo().equals(item.getCodigo())) {
                i.remove();
                break;
            }
        }
    }

    public void cancelarVenda() {
        itens.clear();
    }

    public String toCSV() {
        StringBuilder vendasLista = new StringBuilder();

        vendasLista.append(String.valueOf(this.itens.size()) + ',');
        for (Itens p: this.itens){
            vendasLista.append(p.getCodigo() + ',' + p.getNome() + ',' + p.getQuantidade() + ',');
        }
        vendasLista.append(this.date.toString() + ',' + this.troco + ',' + this.pagamento + ',' + this.tipoPag + ',' + this.id + ',' + this.valor + ',' + "\n\n");

        return String.valueOf(vendasLista);
    }

}
