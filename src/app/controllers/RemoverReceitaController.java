package app.controllers;

import app.classes.Estoque;
import app.classes.Receitas;
import app.classes.exceptions.ProdutoNaoEncontradoException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class RemoverReceitaController {

    @FXML
    private Button btnRemover;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtCodigo;

    @FXML
    void handleCancelar() {
        Stage window = (Stage) btnCancelar.getScene().getWindow();
        window.close();
    }

    @FXML
    void handleRemover() {
        try{
            Receitas receita = Estoque.getInstance().pesquisarReceita(txtCodigo.getText());
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirme sua ação");
            alerta.setHeaderText("Remover " + receita.toString() + " do seu estoque?");
            alerta.setContentText("Remover esta receita?");

            Optional<ButtonType> result = alerta.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Estoque.getInstance1().removerReceitas(receita.getCodigo());
                Stage window = (Stage) btnCancelar.getScene().getWindow();
                window.close();
            }
        } catch (ProdutoNaoEncontradoException e){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Receita não encontrada");
            alerta.setHeaderText("Receita não encontrada");
            alerta.setContentText("Não podemos encontrar esta receita no estoque, verifique se digitou o código de barras corretamente");
            alerta.showAndWait();
        }
    }


    @FXML
    void handleEnter() {
        handleRemover();
    }
}
