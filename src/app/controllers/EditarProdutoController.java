package app.controllers;

import app.Main;
import app.classes.Produto;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarProdutoController {

    @FXML
    private TextField txtCodBarras, txtNome, txtPrecoCompra, txtPrecoVenda, txtQuantidade;

    @FXML
    private Label lblNome, lblCompra, lblVenda, lblQuantidade;

    @FXML
    private Button btnCadastrar;

    private int productIndex;

    @FXML
    void handleSearch() {
        for(int i = 0; i < Main.estoqueProdutos.size(); i++){
            if (Main.estoqueProdutos.get(i).getCodigo().equals(txtCodBarras.getText())) {
                txtNome.setVisible(true);
                txtPrecoCompra.setVisible(true);
                txtPrecoVenda.setVisible(true);
                txtQuantidade.setVisible(true);
                lblNome.setVisible(true);
                lblCompra.setVisible(true);
                lblVenda.setVisible(true);
                lblQuantidade.setVisible(true);

                txtNome.setText(Main.estoqueProdutos.get(i).getNome());
                txtPrecoCompra.setText(String.valueOf(Main.estoqueProdutos.get(i).getValorCusto()));
                txtQuantidade.setText(String.valueOf(Main.estoqueProdutos.get(i).getQuantidade()));
                txtPrecoVenda.setText(String.valueOf(Main.estoqueProdutos.get(i).getValorVenda()));
                this.productIndex = i;
                return;
            }
        }
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Produto não encontrado");
        alerta.setHeaderText("Produto não encontrado");
        alerta.setContentText("Não podemos encontrar este produto no estoque, verifique se digitou o código de barras corretamente");
        alerta.showAndWait();
    }

    @FXML
    void cancel() {
        Stage window = (Stage) btnCadastrar.getScene().getWindow();
        window.close();
    }

    @FXML
    void submit() {
        Produto old = Main.estoqueProdutos.remove(this.productIndex);
        Main.estoqueProdutos.add(new Produto(this.txtNome.getText(), Integer.parseInt(this.txtQuantidade.getText()), Double.parseDouble(this.txtPrecoCompra.getText()), Double.parseDouble(this.txtPrecoVenda.getText()), old.getCodigo()));

        Stage window = (Stage) btnCadastrar.getScene().getWindow();
        window.close();
    }

}
