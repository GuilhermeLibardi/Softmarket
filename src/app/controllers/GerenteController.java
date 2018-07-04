package app.controllers;

import app.Main;
import app.classes.Produto;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GerenteController implements Initializable {

    @FXML
    private Button btnGerenciarEstoque, btnGerarRelatorios, btnRelatorio,
            btnAddProd, btnEditProd, btnRemoveProd;

    @FXML
    private TextField txtDataInicial, txtDataFinal;

    @FXML
    private SplitMenuButton dropTipoRelatorio;

    @FXML
    private TableView<Produto> tabelaProdutos;

    @FXML
    private TextField txtPesquisa;

    @FXML
    private Pane painelEstoque, painelRelatorios;

    @FXML
    private TableColumn<Produto, String> colCodBarras, colNome;

    @FXML
    private TableColumn<Produto, Double> colCompra, colVenda;

    @FXML
    private TableColumn<Produto, Integer> colEstoque;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCodBarras.setCellValueFactory(new PropertyValueFactory<Produto, String>("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory<Produto, String>("nome"));
        colEstoque.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("quantidade"));
        colVenda.setCellValueFactory(new PropertyValueFactory<Produto, Double>("valorVenda"));
        colCompra.setCellValueFactory(new PropertyValueFactory<Produto, Double>("valorCusto"));

        txtPesquisa.setPromptText("Procure por produtos");

        Main.estoqueProdutos.add(new Produto("Coca-cola", 3, 5.5, 6.5, "7891236313182"));
        Main.estoqueProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));

        FilteredList<Produto> filteredData = new FilteredList<>(Main.estoqueProdutos, p -> true);

        txtPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(produto -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                return produto.getNome().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<Produto> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tabelaProdutos.comparatorProperty());

        tabelaProdutos.setItems(sortedData);
    }

    @FXML
    void gerarRelatorio() {

    }

    @FXML
    void handleGerenciarEstoque() {
        painelRelatorios.setVisible(false);
        painelEstoque.setVisible(true);
    }

    @FXML
    void handleGerarRelatorios() {
        painelRelatorios.setVisible(true);
        painelEstoque.setVisible(false);
    }

    @FXML
    void addProduto() {
        try {
            changeScreen("../resources/fxml/telaAdicionarProduto.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void editProduto() {
        try {
            changeScreen("../resources/fxml/telaEditarProduto.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void removeProduto() {
        try {
            changeScreen("../resources/fxml/telaRemoverProduto.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeScreen(String fxml) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

}
