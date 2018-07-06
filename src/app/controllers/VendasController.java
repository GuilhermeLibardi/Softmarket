package app.controllers;

import app.Main;
import app.classes.Produto;
import app.classes.Venda;
import app.classes.usuarios.Usuario;
import app.classes.usuarios.Vendedor;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

public class VendasController {

    @FXML
    private Label lblQuantidade;

    @FXML
    private Label lblNomeProduto;

    @FXML
    private Label lblSubtotal;

    @FXML
    private Label lblTotal;

    @FXML
    private TableView<Produto> tableProdutos;

    @FXML
    private TextField txtCodBarras;

    @FXML
    private TextField txtQuantidade;

    private ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Produto, String> colCodBarras, colNome;

    @FXML
    private TableColumn<Produto, Double> colVenda;

    @FXML
    private TableColumn<Produto, Integer> colEstoque;

    private Integer verificador=0;

    @FXML
    private Button inserir;

    private String statusVenda=("Ocioso");

    private Vendedor vendedor;

    private Venda venda;



    @FXML
    void digitarCdb(KeyEvent event){

        txtCodBarras.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCodBarras.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        if(event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
            for(Produto procurar : Main.estoqueProdutos) {
                if (procurar.getCodigo().equals(txtCodBarras.getText())) {
                    if (verificador == 0) {
                        verificador++;
                    }
                    txtQuantidade.requestFocus();
                    return;
                }
            }
            Alert erroCdB = new Alert(Alert.AlertType.ERROR);
            erroCdB.setTitle("Produto não encontrado!");
            erroCdB.setHeaderText("Código de barras não encontrado");
            erroCdB.setContentText("Digite um código de barras de um produto cadastrado no sistema.");
            erroCdB.showAndWait();
            txtCodBarras.requestFocus();
        }
    }

    @FXML
    void qntVenda(KeyEvent event) {

        txtQuantidade.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtQuantidade.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        if(event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
            if(!txtQuantidade.getText().equals("")) {
                for (Produto procurar1 : Main.estoqueProdutos)
                    if (procurar1.getCodigo().equals(txtCodBarras.getText())) {
                        if (procurar1.getQuantidade() >= (Integer.parseInt(txtQuantidade.getText()))) {
                            if(statusVenda.equals("Ativa")) {
                                if (verificaLista(venda.getProdutos())) {
                                    for (Produto vendasP : venda.getProdutos()) {
                                        if (vendasP.getCodigo().equals(txtCodBarras.getText())) {
                                            if (vendasP.getQuantidade() < Integer.parseInt(txtQuantidade.getText())) {
                                                txtQuantidade.clear();
                                                alertaEstoque(vendasP);
                                                txtQuantidade.requestFocus();
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                            inserir.requestFocus();
                            if (verificador == 1) {
                                verificador++;
                            }
                            return;
                        } else {
                            for (Produto produtoEst : venda.getProdutos()) {
                                if (produtoEst.getCodigo().equals(txtCodBarras.getText())) {
                                    txtQuantidade.clear();
                                    alertaEstoque(produtoEst);
                                    txtQuantidade.requestFocus();
                                    return;
                                }
                            }
                            txtQuantidade.clear();
                            alertaEstoque(procurar1);
                            txtQuantidade.requestFocus();
                            return;
                        }
                    }
            }else if(!txtCodBarras.getText().equals("")){
                Alert erroEst = new Alert(Alert.AlertType.ERROR);
                erroEst.setTitle("Digite um valor");
                erroEst.setHeaderText("Nenhum valor foi inserido");
                erroEst.setContentText("Informe a Quantidade desejada");
                erroEst.showAndWait();
                txtQuantidade.requestFocus();
            }else{
                Alert erroEst = new Alert(Alert.AlertType.ERROR);
                erroEst.setTitle("Código Barras Não Digitado");
                erroEst.setHeaderText("Você não informou o produto desejado");
                erroEst.setContentText("Digite o código de barras.");
                erroEst.showAndWait();
                txtCodBarras.requestFocus();
            }
        }
    }

    @FXML
    public void inserirProduto(){
        Produto produto1, produtoE;

        if(verificador == 2) {
            verificador=0;
            if (statusVenda.equals("Ocioso")) {
                statusVenda = ("Ativa");
                venda = vendedor.iniciarVenda();
                for (Produto mainP : Main.estoqueProdutos) {
                    if (mainP.getCodigo().equals(txtCodBarras.getText())) {
                        produto1 = new Produto(mainP.getNome(), Integer.parseInt(txtQuantidade.getText()), mainP.getValorCusto(), mainP.getValorVenda(), mainP.getCodigo());
                        produtoE = new Produto(mainP.getNome(), mainP.getQuantidade() - Integer.parseInt(txtQuantidade.getText()), mainP.getValorCusto(), mainP.getValorVenda(), mainP.getCodigo());
                        venda.getProdutos().add(produtoE);
                        venda.setValor(venda.getValor() + mainP.getValorVenda() * Double.parseDouble(txtQuantidade.getText()));
                        listaProdutos.add(produto1);
                        txtQuantidade.clear();
                        txtCodBarras.clear();
                        lblQuantidade.setText(Integer.toString(produto1.getQuantidade()));
                        lblNomeProduto.setText(produto1.getNome());
                        lblSubtotal.setText(String.format("%.2f", produto1.getValorVenda() * produto1.getQuantidade()));
                        lblTotal.setText(String.format("%.2f", venda.getValor()));
                        txtCodBarras.clear();
                        txtQuantidade.clear();
                        txtCodBarras.requestFocus();
                        creatTable();
                    }
                }
            }else if(statusVenda.equals("Ativa")){
                for (Produto procurar : venda.getProdutos()) {
                    if (procurar.getCodigo().equals(txtCodBarras.getText())) {
                        for (Produto tableP : listaProdutos) {
                            if (tableP.getCodigo().equals(procurar.getCodigo())) {
                                produto1 = new Produto(tableP.getNome(), tableP.getQuantidade(), tableP.getValorCusto(), tableP.getValorVenda(), tableP.getCodigo());
                                produto1.setQuantidade(produto1.getQuantidade() + Integer.parseInt(txtQuantidade.getText()));
                                listaProdutos.remove(tableP);
                                listaProdutos.add(produto1);
                                procurar.setQuantidade(procurar.getQuantidade() - Integer.parseInt(txtQuantidade.getText()));
                                venda.setValor(venda.getValor()+produto1.getValorVenda() * Double.parseDouble(txtQuantidade.getText()));
                                txtQuantidade.clear();
                                txtCodBarras.clear();
                                txtCodBarras.requestFocus();
                                lblQuantidade.setText(Integer.toString(produto1.getQuantidade()));
                                lblNomeProduto.setText(produto1.getNome());
                                lblSubtotal.setText(String.format("%.2f", produto1.getValorVenda() * produto1.getQuantidade()));
                                lblTotal.setText(String.format("%.2f", venda.getValor()));

                                return;
                            }
                        }
                    }
                }
                for (Produto mainP : Main.estoqueProdutos) {
                    if (mainP.getCodigo().equals(txtCodBarras.getText())) {
                        produto1 = new Produto(mainP.getNome(), Integer.parseInt(txtQuantidade.getText()), mainP.getValorCusto(), mainP.getValorVenda(), mainP.getCodigo());
                        produtoE = new Produto(mainP.getNome(), mainP.getQuantidade() - Integer.parseInt(txtQuantidade.getText()), mainP.getValorCusto(), mainP.getValorVenda(), mainP.getCodigo());
                        venda.getProdutos().add(produtoE);
                        venda.setValor(venda.getValor() + mainP.getValorVenda() * Double.parseDouble(txtQuantidade.getText()));
                        listaProdutos.add(produto1);
                        txtQuantidade.clear();
                        txtCodBarras.clear();
                        lblQuantidade.setText(Integer.toString(produto1.getQuantidade()));
                        lblNomeProduto.setText(produto1.getNome());
                        lblSubtotal.setText(String.format("%.2f", produto1.getValorVenda() * produto1.getQuantidade()));
                        lblTotal.setText(String.format("%.2f", venda.getValor()));
                        txtCodBarras.clear();
                        txtQuantidade.clear();
                        txtCodBarras.requestFocus();
                    }
                }
            }
        }
    }


    public void creatTable() {
        colCodBarras.setCellValueFactory(new PropertyValueFactory<Produto, String>("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory<Produto, String>("nome"));
        colEstoque.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("quantidade"));
        colVenda.setCellValueFactory(new PropertyValueFactory<Produto, Double>("valorVenda"));
        
        tableProdutos.setItems(listaProdutos);
    }

    public boolean verificaLista(ObservableList<Produto> listaP){
        for (Produto produtos : listaP){
            if(produtos.getCodigo().equals(txtCodBarras.getText())){
                return true;
            }
        }
        return false;
    }

    public boolean verificaLista(ArrayList<Produto> listaP){
        for (Produto produtos : listaP){
            if(produtos.getCodigo().equals(txtCodBarras.getText())){
                return true;
            }
        }
        return false;
    }

    public void fPress (KeyEvent event){

        switch (event.getCode()){
            case F3:
                pressF3();
                break;
            case F4:
                pressF4();
                break;
            case F5:
                pressF5();
                break;
        }
    }

    public void alertaEstoque(Produto produto) {
        Alert erroEst = new Alert(Alert.AlertType.ERROR);
        erroEst.setTitle("Estoque Indisponível!");
        erroEst.setHeaderText("Quantidade desejada indisponível no estoque.");
        erroEst.setContentText("Você pode inserir até: " + produto.getQuantidade() + " unidades.");
        erroEst.showAndWait();
    }

    public void pressF3(){
        TextInputDialog dialogoRemocao = new TextInputDialog();


        dialogoRemocao.setTitle("Cancelamento de venda de produto");
        dialogoRemocao.setHeaderText("Informe o código de barras do produto a ser removido.");
        dialogoRemocao.setContentText("Código de Barras:");
        Optional<String> result = dialogoRemocao.showAndWait();
        String cDb = "none";

        if(result.isPresent()) {
            cDb = result.get();
            for (Iterator<Produto> it = listaProdutos.iterator(); it.hasNext();) {
                Produto produto1 = it.next();
                if (produto1.getCodigo().equals(cDb)) {
                    it.remove();
                    for (Iterator<Produto> i = venda.getProdutos().iterator(); i.hasNext();) {
                        Produto produto = i.next();
                        if (produto.getCodigo().equals(cDb)) {
                            i.remove();
                            return;
                        }
                    }
                }
            }
            Alert erroEst = new Alert(Alert.AlertType.ERROR);
            erroEst.setTitle("Código de barras inválido!");
            erroEst.setHeaderText("O código de barras informado não foi inserido nessa venda.");
            erroEst.setContentText("Insira o código de barras novamente.");
            erroEst.showAndWait();
            pressF3();
        }


        for (Iterator<Produto> it = listaProdutos.iterator(); it.hasNext();) {
            Produto produto1 = it.next();
            if (produto1.getCodigo().equals(cDb)) {
                it.remove();
            }
        }
        txtCodBarras.requestFocus();
    }

    public void pressF4(){
        Alert cancelarVenda = new Alert(Alert.AlertType.CONFIRMATION);
        cancelarVenda.setTitle("Cancelamento de Venda");
        cancelarVenda.setHeaderText("Você está prestes a cancelar uma venda.");
        cancelarVenda.setContentText("Você tem certeza disso?");

        Optional<ButtonType> result = cancelarVenda.showAndWait();
        if(result.get() ==  ButtonType.OK){
            listaProdutos.clear();
            venda.cancelarVenda();
        }
        txtCodBarras.requestFocus();
    }

    public void pressF5(){

    }

    public void changeUser(Vendedor user) {
        this.vendedor = user;
    }
}