package br.com.softmarket.classes.Producao;

public class Receita extends Produto {
    int tempoProducao; //Tempo de produção é dado em minutos
    Double pesoBruto;

    public Receita(String codigoProduto, String codigoBarras, String tipo, String nome, String grupo, String subgrupo, String descricao, double peso, int minimoEstoque, Double precoCusto, Double precoVenda, Double precoPromocional, boolean promocaoAtiva, int tempoProducao, Double pesoBruto, Double pesoLiquido, Double fatorCoccao) {
        super(codigoProduto, codigoBarras, tipo, nome, grupo, subgrupo, descricao, peso, minimoEstoque, precoCusto, precoVenda, precoPromocional, promocaoAtiva);
        this.tempoProducao = tempoProducao;
        this.pesoBruto = pesoBruto;
        this.pesoLiquido = pesoLiquido;
        this.fatorCoccao = fatorCoccao;
    }

    public Receita(String nome, Double precoCusto, Double precoVenda, String codigoBarras, Double peso, int tempoProducao, Double pesoBruto, Double pesoLiquido, Double fatorCoccao) {
        super(nome, precoCusto, precoVenda, codigoBarras, peso);
        this.tempoProducao = tempoProducao;
        this.pesoBruto = pesoBruto;
        this.pesoLiquido = pesoLiquido;
        this.fatorCoccao = fatorCoccao;
    }

    Double pesoLiquido;
    Double fatorCoccao;


}
