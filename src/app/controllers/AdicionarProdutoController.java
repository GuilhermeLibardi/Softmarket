package app.controllers;

import app.Main;
import app.classes.Produto;
import app.classes.util.CSVParser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AdicionarProdutoController {

    @FXML
    private TextField txtCodBarras, txtNome, txtPreco, txtPrecoVenda, txtQuantidade;

    @FXML
    private Button btnCadastrar;

    @FXML
    void submit() {
        CSVParser parser = new CSVParser();
        Double valorCompra = Double.parseDouble(txtPreco.getText().replace(",","."));
        Double valorVenda = Double.parseDouble(txtPrecoVenda.getText().replace(",","."));
        Main.estoqueProdutos.add(new Produto(txtNome.getText(), Integer.parseInt(txtQuantidade.getText()), valorCompra, valorVenda, txtCodBarras.getText()));
        try {
            parser.writeEstoque(Main.estoqueProdutos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) btnCadastrar.getScene().getWindow();
        stage.close();
    }
}
