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

public class EditarIngredienteController{

    @FXML
    private TextField txtCodBarras, txtNome, txtPeso;

    @FXML
    private Label lblCodigo, lblNome, lblPeso;

    @FXML
    private Button btnEditar, btnSearch, btnCancelar;

    @FXML
    void searchButton() {
        try {
            Ingredientes i = Estoque.getInstance1().pesquisarIngrediente(txtCodBarras.getText());
            btnSearch.setVisible(false);
            txtNome.setVisible(true);
            txtPeso.setVisible(true);
            txtCodBarras.setVisible(true);
            btnEditar.setVisible(true);
            btnCancelar.setVisible(true);
            lblNome.setVisible(true);
            lblPeso.setVisible(true);

            txtNome.setText(i.getNome());
            txtPeso.setText(String.valueOf(i.getPeso()));

            return;

        } catch (ProdutoNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }

        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Ingrediente não encontrado");
        alerta.setHeaderText("Ingrediente não encontrado");
        alerta.setContentText("Não podemos encontrar este ingrediente no estoque, verifique se digitou o código de barras corretamente");
        alerta.showAndWait();
    }

    @FXML
    void handleSearch() {
        searchButton();
    }

    @FXML
    void cancel() {
        Stage window = (Stage) btnCancelar.getScene().getWindow();
        window.close();
    }

    @FXML
    void edit() {
        if (this.txtNome.isVisible()) {

            Estoque.getInstance1().editarIngrediente(new Ingredientes(this.txtNome.getText(), Double.parseDouble(this.txtPeso.getText().replace(",", ".")), this.txtCodBarras.getText()));

            Stage window = (Stage) btnCancelar.getScene().getWindow();
            window.close();
        }
    }
    public void setCod(String cod){

        txtCodBarras.setText(cod);
        btnSearch.fire();
    }

}
