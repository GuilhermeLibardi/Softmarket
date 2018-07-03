package app.controllers;

import app.Main;
import app.classes.Produto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdicionarProdutoController {

    @FXML
    private TextField txtCodBarras, txtNome, txtPreco, txtLucro, txtQuantidade;

    @FXML
    private Button btnCadastrar;

    @FXML
    void submit() {
        Double valorCompra = Double.parseDouble(txtPreco.getText());
        Double valorVenda = ((valorCompra * Double.parseDouble(txtLucro.getText())) / 100) + valorCompra;
        Main.estoqueProdutos.add(new Produto(txtNome.getText(), Integer.parseInt(txtQuantidade.getText()), valorCompra, valorVenda, txtCodBarras.getText()));
        Stage stage = (Stage) btnCadastrar.getScene().getWindow();
        stage.close();
    }
}
