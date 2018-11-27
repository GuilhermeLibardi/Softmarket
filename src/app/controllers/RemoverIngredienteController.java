package app.controllers;

import app.classes.Estoque;
import app.classes.Ingredientes;
import app.classes.Produto;
import app.classes.exceptions.ProdutoNaoEncontradoException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class RemoverIngredienteController {

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
        try{
            Ingredientes ingrediente = Estoque.getInstance1().pesquisarIngrediente(txtCodBarras.getText());
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirme sua ação");
            alerta.setHeaderText("Remover " + ingrediente.toString() + " do seu estoque?");
            alerta.setContentText("Confirmar a remoção?");

            Optional<ButtonType> result = alerta.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Estoque.getInstance1().removerIngredientes(ingrediente.getCodigo());
                Estoque.getInstance1().getEstoqueI().remove(ingrediente);
                Stage window = (Stage) btnCancelar.getScene().getWindow();
                window.close();
            }
        } catch (ProdutoNaoEncontradoException e){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Produto não encontrado");
            alerta.setHeaderText("Produto não encontrado");
            alerta.setContentText("Não podemos encontrar este ingrediente no estoque, verifique se digitou o código de barras corretamente");
            alerta.showAndWait();
        }
    }


    @FXML
    void handleEnter() {
        handleRemover();
    }
}
