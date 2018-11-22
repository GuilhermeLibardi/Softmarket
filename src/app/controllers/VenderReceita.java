package app.controllers;

import app.classes.Estoque;
import app.classes.Ingredientes;
import app.classes.Produto;
import app.classes.Receitas;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class VenderReceita implements Initializable {

    @FXML
    private ListView<String> listReceitas;

    @FXML
    private Button bntReceita;

    @FXML
    private TextField txtReceita;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> receitas = FXCollections.observableArrayList();

        for(Receitas r : Estoque.getInstance().getEstoqueR()){
            receitas.add(r.getNome());
        }

        FilteredList<String> filteredList= new FilteredList<>(receitas, data -> true);
        listReceitas.setItems(filteredList);

        txtReceita.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredList.setPredicate(data -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseSearch=newValue.toLowerCase();
                return data.toLowerCase().contains(lowerCaseSearch);
            });
        }));
    }

    @FXML
    void digitarReceita(KeyEvent event) {

        txtReceita.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtReceita.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        if(event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {

            Alert erroCdB = new Alert(Alert.AlertType.ERROR);
            erroCdB.setTitle("Produto não encontrado!");
            erroCdB.setHeaderText("Código de barras não encontrado");
            erroCdB.setContentText("Digite um código de barras de um produto cadastrado no sistema.");
            erroCdB.showAndWait();
            txtReceita.requestFocus();
        }

    }

    @FXML
    void submit() {

    }

}
