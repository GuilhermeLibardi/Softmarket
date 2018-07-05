package app.controllers;

import app.Main;
import app.classes.Produto;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

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
    private ArrayList<Produto> vendaProdutos = new ArrayList<Produto>();

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



    @FXML
    void digitarCdb(KeyEvent event){

        txtCodBarras.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCodBarras.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        if(event.getCode().equals(KeyCode.ENTER)) {
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
        }
    }

    @FXML
    void qntVenda(KeyEvent event) {
        Produto produtoaux;

        txtQuantidade.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtQuantidade.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        if(event.getCode().equals(KeyCode.ENTER)) {
            for (Produto procurar1 : Main.estoqueProdutos) {
                if (procurar1.getCodigo().equals(txtCodBarras.getText())) {
                    if (procurar1.getQuantidade() >= (Integer.parseInt(txtQuantidade.getText()))) {
                        if (verificaLista(vendaProdutos)) {
                            for (Produto vendasP : vendaProdutos) {
                                if(vendasP.getCodigo().equals(txtCodBarras.getText())) {
                                    if (vendasP.getQuantidade() < Integer.parseInt(txtQuantidade.getText())) {
                                        txtQuantidade.clear();
                                        Alert erroEst = new Alert(Alert.AlertType.ERROR);
                                        erroEst.setTitle("Estoque Indisponível!1");
                                        erroEst.setHeaderText("Quantidade desejada indisponível no estoque.");
                                        erroEst.setContentText("Você pode inserir até " + vendasP.getQuantidade() + " unidades.");
                                        erroEst.showAndWait();
                                        return;
                                    }
                                }
                            }
                        } else {
                            produtoaux = new Produto(procurar1.getNome(),procurar1.getQuantidade(),procurar1.getValorCusto(),procurar1.getValorVenda(),procurar1.getCodigo());
                            vendaProdutos.add(produtoaux);
                        }
                        inserir.requestFocus();
                        if (verificador == 1) {
                            verificador++;
                        }
                        return;
                    } else {
                        for (Produto produtoEst : vendaProdutos){
                            if(produtoEst.getCodigo().equals(txtCodBarras.getText())){
                                txtQuantidade.clear();
                                Alert erroEst = new Alert(Alert.AlertType.ERROR);
                                erroEst.setTitle("Estoque Indisponível!");
                                erroEst.setHeaderText("Quantidade desejada indisponível no estoque.");
                                erroEst.setContentText("Você pode inserir até " + produtoEst.getQuantidade() + " unidades.");
                                erroEst.showAndWait();
                                return;
                            }
                        }

                        txtQuantidade.clear();
                        Alert erroEst = new Alert(Alert.AlertType.ERROR);
                        erroEst.setTitle("Estoque Indisponível!");
                        erroEst.setHeaderText("Quantidade desejada indisponível no estoque.");
                        erroEst.setContentText("Você pode inserir até " + procurar1.getQuantidade() + " unidades.");
                        erroEst.showAndWait();
                        return;
                    }
                }
            }
        }
    }

    @FXML
    public void inserirProduto(){
        Produto produto1;
        int aux=0;

        if (verificador == 2) {
            statusVenda = ("Ativa");
            verificador = 0;
            for (Produto procurar : vendaProdutos) {
                if (procurar.getCodigo().equals(txtCodBarras.getText())) {
                    if (procurar.getQuantidade() >= Integer.parseInt(txtQuantidade.getText())) {
                        for (Produto tableP : listaProdutos) {
                            if (tableP.getCodigo().equals(procurar.getCodigo())) {
                                aux=1;
                                produto1 = new Produto(tableP.getNome(), tableP.getQuantidade(), tableP.getValorCusto(), tableP.getValorVenda(), tableP.getCodigo());
                                produto1.setQuantidade(produto1.getQuantidade() + Integer.parseInt(txtQuantidade.getText()));
                                listaProdutos.remove(tableP);
                                listaProdutos.add(produto1);
                                procurar.setQuantidade(procurar.getQuantidade() - Integer.parseInt(txtQuantidade.getText()));
                                txtQuantidade.clear();
                                txtCodBarras.clear();
                                return;
                            }
                        }
                        if (aux==0) {
                            produto1 = new Produto(procurar.getNome(), procurar.getQuantidade(), procurar.getValorCusto(), procurar.getValorVenda(), procurar.getCodigo());
                            produto1.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
                            procurar.setQuantidade(procurar.getQuantidade() - Integer.parseInt(txtQuantidade.getText()));
                            listaProdutos.add(produto1);
                            txtQuantidade.clear();
                            txtCodBarras.clear();
                            creatTable();
                            return;
                        }
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
                break;
        }
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
        }
        for (Iterator<Produto> i = vendaProdutos.iterator(); i.hasNext();) {
            Produto produto = i.next();
            if (produto.getCodigo().equals(cDb)) {
                i.remove();
            }
        }

        for (Iterator<Produto> it = listaProdutos.iterator(); it.hasNext();) {
            Produto produto1 = it.next();
            if (produto1.getCodigo().equals(cDb)) {
                it.remove();
            }
        }
    }

    public void pressF4(){
        Alert cancelarVenda = new Alert(Alert.AlertType.CONFIRMATION);
        cancelarVenda.setTitle("Cancelamento de Venda");
        cancelarVenda.setHeaderText("Você está prestes a cancelar uma venda.");
        cancelarVenda.setContentText("Você tem certeza disso?");

        Optional<ButtonType> result = cancelarVenda.showAndWait();
        if(result.get() ==  ButtonType.OK){
            listaProdutos.clear();
            vendaProdutos.clear();
        }
    }

}


