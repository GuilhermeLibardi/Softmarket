package app.controllers;
import app.classes.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class gerenteController implements Initializable {

    @FXML
    private Button btnGerenciarEstoque;

    @FXML
    private Button btnGerarRelatorios;

    @FXML
    private TextField txtDataInicial;

    @FXML
    private TextField txtDataFinal;

    @FXML
    private SplitMenuButton dropTipoRelatorio;

    @FXML
    private Button btnRelatorio;

    @FXML
    private TableView<Produto> tabelaProdutos;

    @FXML
    private TextField txtPesquisa;

    @FXML
    private Pane painelBemvindo;

    @FXML
    private Pane painelEstoque;

    @FXML
    private Pane painelRelatorios;

    @FXML
    private TableColumn<Produto, String> colCodBarras;

    @FXML
    private TableColumn<Produto, String> colNome;

    @FXML
    private TableColumn<Produto, Double> colCompra;

    @FXML
    private TableColumn<Produto, Double> colVenda;

    @FXML
    private TableColumn<Produto, Integer> colEstoque;

    private ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();

    @FXML
    void gerarRelatorio(ActionEvent event) {

    }

    @FXML
    void updateTable(KeyEvent event) {

    }

    @FXML
    void handleGerenciarEstoque(ActionEvent event){
        painelBemvindo.setVisible(false);
        painelRelatorios.setVisible(false);
        painelEstoque.setVisible(true);
    }

    @FXML
    void handleGerarRelatorios(ActionEvent event){
        painelBemvindo.setVisible(false);
        painelRelatorios.setVisible(true);
        painelEstoque.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCodBarras.setCellValueFactory(new PropertyValueFactory<Produto, String>("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory<Produto, String>("nome"));
        colEstoque.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("quantidade"));
        colVenda.setCellValueFactory(new PropertyValueFactory<Produto, Double>("valorVenda"));
        colCompra.setCellValueFactory(new PropertyValueFactory<Produto, Double>("valorCusto"));


        listaProdutos.add(new Produto("Coca-cola", 3, 5.5, 6.5, "7891236313182"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        listaProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        tabelaProdutos.setItems(listaProdutos);
    }
}
