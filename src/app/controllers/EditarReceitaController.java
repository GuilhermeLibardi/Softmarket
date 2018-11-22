package app.controllers;

import app.classes.Estoque;
import app.classes.Ingredientes;
import app.classes.Produto;
import app.classes.exceptions.ProdutoNaoEncontradoException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditarReceitaController{

    @FXML
    private TextField txtCodBarras, txtNome, txtVdc, txtVdv;

    @FXML
    private Label lblCodigo, lblNome, lblVdc, lblVdv;

    @FXML
    private Button bntEditar;

    @FXML
    void searchButton() {

    }

    @FXML
    void handleSearch() {
        searchButton();
    }

    @FXML
    void cancel() {

    }

    @FXML
    void edit() {

    }

}
