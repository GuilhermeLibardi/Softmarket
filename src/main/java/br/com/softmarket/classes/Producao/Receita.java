package br.com.softmarket.classes.Producao;

public class Receita extends Produto {
    int tempoProducao; //Tempo de produção é dado em minutos
    Double pesoBruto;
    Double pesoLiquido;
    Double fatorCoccao;

    public Receita(String nome, int quantidade, String codigo_interno, String grupo, Double preco_venda, Double preco_promocional, boolean em_promocao, int tempoProducao, Double pesoBruto, Double pesoLiquido, Double fatorCoccao) {
        super(nome, quantidade, codigo_interno, grupo, preco_venda, preco_promocional, em_promocao);
        this.tempoProducao = tempoProducao;
        this.pesoBruto = pesoBruto;
        this.pesoLiquido = pesoLiquido;
        this.fatorCoccao = fatorCoccao;
    }

    public Receita(String nome, Double precoVenda, String codigoInterno, int quantidade, int tempoProducao, Double pesoBruto, Double pesoLiquido, Double fatorCoccao) {
        super(nome, precoVenda, codigoInterno, quantidade);
        this.tempoProducao = tempoProducao;
        this.pesoBruto = pesoBruto;
        this.pesoLiquido = pesoLiquido;
        this.fatorCoccao = fatorCoccao;
    }

    public Receita(Produto produto, int tempoProducao, Double pesoBruto, Double pesoLiquido, Double fatorCoccao) {
        super(produto);
        this.tempoProducao = tempoProducao;
        this.pesoBruto = pesoBruto;
        this.pesoLiquido = pesoLiquido;
        this.fatorCoccao = fatorCoccao;
    }
}
