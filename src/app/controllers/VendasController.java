package app.controllers;

import app.Main;
import app.classes.Produto;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
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



    @FXML
    void digitarCdb(KeyEvent event){
        txtCodBarras.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCodBarras.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });


        if(event.getCode().equals(KeyCode.ENTER)) {
            Main.estoqueProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
            for(Produto procurar : Main.estoqueProdutos) {
                if (procurar.getCodigo().equals(txtCodBarras.getText())) {
                    listaProdutos.add(procurar);
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
        txtCodBarras.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCodBarras.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        if(event.getCode().equals(KeyCode.ENTER)) {
            if(listaProdutos.get(listaProdutos.size()-1).getQuantidade() >= Integer.parseInt(txtQuantidade.getText())){
                creatTable();
                return;
            }
            Alert erroEst = new Alert(Alert.AlertType.ERROR);
            erroEst.setTitle("Estoque Indisponível!");
            erroEst.setHeaderText("Quantidade desejada indisponível no estoque.");
            erroEst.setContentText("Você pode inserir até " + listaProdutos.get(listaProdutos.size()-1).getQuantidade() + " unidades.");
            erroEst.showAndWait();
        }
    }


    private void creatTable() {
        colCodBarras.setCellValueFactory(new PropertyValueFactory<Produto, String>("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory<Produto, String>("nome"));
        colEstoque.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("quantidade"));
        colVenda.setCellValueFactory(new PropertyValueFactory<Produto, Double>("valorVenda"));

        tableProdutos.setItems(listaProdutos);
    }


}


