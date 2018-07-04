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
                    if (!verificaLista(listaProdutos)){
                        if (verificador == 0) {
                            verificador++;
                        }
                        txtQuantidade.requestFocus();
                        return;
                    }else{
                        Alert erro1 = new Alert(Alert.AlertType.ERROR);
                        erro1.setTitle("Produto já inserido!");
                        erro1.setHeaderText("Você está tentando inserir um produto já inserido");
                        erro1.setContentText("Caso queira aumentar a quantidade remova o produto e insira novamente.");
                        erro1.showAndWait();
                        return;
                    }
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
        txtQuantidade.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtQuantidade.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        if(event.getCode().equals(KeyCode.ENTER)) {
            for(Produto procurar : Main.estoqueProdutos) {
                if (procurar.getCodigo().equals(txtCodBarras.getText())) {
                    if(procurar.getQuantidade()>=Integer.parseInt(txtQuantidade.getText())){
                        inserir.requestFocus();
                        if (verificador==1){
                            verificador++;
                        }
                        return;
                    }

                }
            }

            Alert erroEst = new Alert(Alert.AlertType.ERROR);
            erroEst.setTitle("Estoque Indisponível!");
            erroEst.setHeaderText("Quantidade desejada indisponível no estoque.");
            erroEst.setContentText("Você pode inserir até " + listaProdutos.get(listaProdutos.size()-1).getQuantidade() + " unidades.");
            erroEst.showAndWait();
        }
    }

    @FXML
    public void inserirProduto(){
        if (verificador == 2) {
            statusVenda = ("Ativa");
            verificador=0;
            for(Produto procurar : Main.estoqueProdutos){
                if (procurar.getCodigo().equals(txtCodBarras.getText())){
                    procurar.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
                    listaProdutos.add(procurar);
                }
            }
            txtQuantidade.clear();
            txtCodBarras.clear();
            creatTable();
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
}


