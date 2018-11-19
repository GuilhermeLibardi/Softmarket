package app.controllers;

import app.classes.Estoque;
import app.classes.Produto;
import app.classes.util.CSVParser;
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
    private Button btnCadastrar, btnSearch;

    private int productIndex;

    @FXML
    void searchButton() {
        for (int i = 0; i < Estoque.getInstance().getEstoque().size(); i++) {
            if (Estoque.getInstance().getEstoque().get(i).getCodigo().equals(txtCodBarras.getText())) {
                txtNome.setVisible(true);
                txtPrecoCompra.setVisible(true);
                txtPrecoVenda.setVisible(true);
                txtQuantidade.setVisible(true);
                btnSearch.setVisible(false);
                btnCadastrar.setVisible(true);
                lblNome.setVisible(true);
                lblCompra.setVisible(true);
                lblVenda.setVisible(true);
                lblQuantidade.setVisible(true);

                txtNome.setText(Estoque.getInstance().getEstoque().get(i).getNome());
                txtPrecoCompra.setText(String.valueOf(Estoque.getInstance().getEstoque().get(i).getValorCusto()).replace(".",","));
                txtQuantidade.setText(String.valueOf(Estoque.getInstance().getEstoque().get(i).getQuantidade()));
                txtPrecoVenda.setText(String.valueOf(Estoque.getInstance().getEstoque().get(i).getValorVenda()).replace(".",","));
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
            Produto old = Estoque.getInstance().getEstoque().remove(this.productIndex);
            Estoque.getInstance().getEstoque().add(new Produto(this.txtNome.getText(), Integer.parseInt(this.txtQuantidade.getText()), Double.parseDouble(this.txtPrecoCompra.getText().replace(",",".")), Double.parseDouble(this.txtPrecoVenda.getText().replace(",",".")), old.getCodigo()));
            CSVParser parser = new CSVParser();
            try {
                parser.writeEstoque(Estoque.getInstance().getEstoque());
            } catch (Exception e) {
                e.printStackTrace();
            }

            Stage window = (Stage) btnCadastrar.getScene().getWindow();
            window.close();
        }
    }

}
