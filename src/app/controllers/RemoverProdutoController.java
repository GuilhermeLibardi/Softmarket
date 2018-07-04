package app.controllers;

import app.Main;
import app.classes.Produto;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class RemoverProdutoController {

    @FXML
    private Button btnRemover;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtCodBarras;

    @FXML
    void handleCancelar() {
        Stage window = (Stage) btnCancelar.getScene().getWindow();
        window.close();
    }

    @FXML
    void handleRemover() {
        for (int i = 0; i < Main.estoqueProdutos.size(); i++) {
            if (Main.estoqueProdutos.get(i).getCodigo().equals(this.txtCodBarras.getText())) {
                Produto produto = Main.estoqueProdutos.get(i);
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Confirme sua ação");
                alerta.setHeaderText("Remover " + produto.toString() + " do seu estoque?");
                alerta.setContentText("Confirmar a remoção?");

                Optional<ButtonType> result = alerta.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Main.estoqueProdutos.remove(produto);
                    Stage window = (Stage) btnCancelar.getScene().getWindow();
                    window.close();
                }
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
    void handleEnter() {
        handleRemover();
    }
}
