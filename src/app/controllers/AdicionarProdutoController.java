package app.controllers;

import app.classes.Estoque;
import app.classes.Produto;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AdicionarProdutoController {

    @FXML
    private TextField txtCodBarras, txtNome, txtPreco, txtPrecoVenda, txtQuantidade, txtPeso, txtPesavel;

    @FXML
    private Button btnCadastrar;

    @FXML
    void submit() {
        for(Produto p : Estoque.getInstance().getEstoque()) {
            if (p.getCodigo().equals(txtCodBarras.getText())) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Erro de inserção");
                alerta.setHeaderText("Erro ao inserir novo produto");
                alerta.setContentText("Este código de barras já está cadastrado no banco de dados!");
                alerta.showAndWait();
                return;
            }
            if(p.getNome().equals(txtNome.getText())) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Alerta ao inserir");
                alerta.setHeaderText("Produto com nome repetido");
                alerta.setContentText("Produto com este nome já existe, considere alterar o nome");
                alerta.showAndWait();
                return;
            }
        }
        double valorCompra = Double.parseDouble(txtPreco.getText().replace(",","."));
        double valorVenda = Double.parseDouble(txtPrecoVenda.getText().replace(",","."));
        Estoque.getInstance().adicionarProduto(new Produto(txtNome.getText(), Integer.parseInt(txtQuantidade.getText()), valorCompra, valorVenda, txtCodBarras.getText(), Double.parseDouble(txtPeso.getText()), txtPesavel.getText()));
        Stage stage = (Stage) btnCadastrar.getScene().getWindow();
        stage.close();
    }
}
