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
    private TextField txtCodBarras, txtNome, txtPeso;

    @FXML
    private Button btnCadastrar;

    @FXML
    void submit() {
        for(Ingredientes i : Estoque.getInstance1().getEstoqueI())
        {
            if(i.getCodigo().equals(txtCodBarras.getText()))
            {

            }
        }

        double peso = Double.parseDouble(txtPeso.getText().replace(",","."));

        Estoque.getInstance1().adicionarIngredientes(new Ingredientes(txtNome.getText(), peso, txtCodBarras.getText()));
        Stage stage = (Stage) btnCadastrar.getScene().getWindow();
        stage.close();
    }
}
