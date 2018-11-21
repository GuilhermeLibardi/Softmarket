package app.controllers;

import app.classes.Estoque;
import app.classes.Ingredientes;
import app.classes.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class AdicionarIngredienteController {

    @FXML
    private TextField txtCodBarras, txtNome, txtPdc, txtPdv;

    @FXML
    private Button btnCadastrar;

    @FXML
    void submit() {

    }
}
