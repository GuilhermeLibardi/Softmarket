package app.controllers;

import app.classes.Estoque;
import app.classes.Produto;
import app.classes.util.CSVParser;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
        CSVParser parser = new CSVParser();
        Double valorCompra = Double.parseDouble(txtPreco.getText().replace(",","."));
        Double valorVenda = Double.parseDouble(txtPrecoVenda.getText().replace(",","."));
        Estoque.getInstance().getEstoque().add(new Produto(txtNome.getText(), Integer.parseInt(txtQuantidade.getText()), valorCompra, valorVenda, txtCodBarras.getText()));
        try {
            parser.writeEstoque(Estoque.getInstance().getEstoque());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) btnCadastrar.getScene().getWindow();
        stage.close();
    }
}
