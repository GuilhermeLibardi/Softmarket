package app.controllers;

import app.classes.Estoque;
import app.classes.Produto;
import app.classes.util.CSVParser;
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
        for (int i = 0; i < Estoque.getInstance().getEstoque().size(); i++) {
            if (Estoque.getInstance().getEstoque().get(i).getCodigo().equals(this.txtCodBarras.getText())) {
                Produto produto = Estoque.getInstance().getEstoque().get(i);
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Confirme sua ação");
                alerta.setHeaderText("Remover " + produto.toString() + " do seu estoque?");
                alerta.setContentText("Confirmar a remoção?");

                Optional<ButtonType> result = alerta.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Estoque.getInstance().getEstoque().remove(produto);
                    CSVParser parser = new CSVParser();
                    try {
                        parser.writeEstoque(Estoque.getInstance().getEstoque());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
