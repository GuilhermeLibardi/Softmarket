package app.controllers;

import app.classes.Estoque;
import app.classes.Produto;
import app.classes.exceptions.ProdutoNaoEncontradoException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarProdutoController {

    @FXML
    private TextField txtCodBarras, txtNome, txtPrecoCompra, txtPrecoVenda, txtQuantidade, txtPeso, txtPesavel;

    @FXML
    private Label lblNome, lblCompra, lblVenda, lblQuantidade, lblPesavel, lblPeso;

    @FXML
    private Button btnCadastrar, btnSearch;

    @FXML
    void searchButton() {
        try {
            Produto p = Estoque.getInstance().pesquisarProduto(txtCodBarras.getText());
            txtNome.setVisible(true);
            txtPrecoCompra.setVisible(true);
            txtPrecoVenda.setVisible(true);
            txtQuantidade.setVisible(true);
            txtPeso.setVisible(true);
            txtPesavel.setVisible(true);
            btnSearch.setVisible(false);
            btnCadastrar.setVisible(true);
            lblNome.setVisible(true);
            lblCompra.setVisible(true);
            lblVenda.setVisible(true);
            lblQuantidade.setVisible(true);
            lblPesavel.setVisible(true);
            lblPeso.setVisible(true);

            txtNome.setText(p.getNome());
            txtPrecoCompra.setText(String.valueOf(p.getValorCusto()).replace(".", ","));
            txtQuantidade.setText(String.valueOf(p.getQuantidade()));
            txtPrecoVenda.setText(String.valueOf(p.getValorVenda()).replace(".", ","));
            txtPesavel.setText(p.getPesavel());
            txtPeso.setText(String.valueOf(p.getPeso()));
            return;

        } catch (ProdutoNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }

        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Produto não encontrado");
        alerta.setHeaderText("Produto não encontrado");
        alerta.setContentText("Não podemos encontrar este produto no estoque, verifique se digitou o código de barras corretamente");
        alerta.showAndWait();
    }

    @FXML
    void handleSearch() {
        searchButton();
    }

    @FXML
    void cancel() {
        Stage window = (Stage) btnCadastrar.getScene().getWindow();
        window.close();
    }

    @FXML
    void submit() {
        if (this.txtNome.isVisible()) {
            try {
                Estoque.getInstance().editarProduto(new Produto(this.txtNome.getText(), Integer.parseInt(this.txtQuantidade.getText()), Double.parseDouble(this.txtPrecoCompra.getText().replace(",", ".")), Double.parseDouble(this.txtPrecoVenda.getText().replace(",", ".")), txtCodBarras.getText(), Double.parseDouble(this.txtPeso.getText().replace(",", ".")), this.txtPesavel.getText(), null));
            } catch (ProdutoNaoEncontradoException e) {
                System.out.println(e.getMessage());
            }

            Stage window = (Stage) btnCadastrar.getScene().getWindow();
            window.close();
        }
    }

}
