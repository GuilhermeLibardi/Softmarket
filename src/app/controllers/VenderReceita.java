package app.controllers;

import app.Main;
import app.classes.Estoque;
import app.classes.Ingredientes;
import app.classes.Produto;
import app.classes.Receitas;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class VenderReceita implements Initializable {

    @FXML
    private ListView<String> listReceitas, listIngredientes;

    @FXML
    private  Label lblVdP, lblReceita, lblQnt;

    @FXML
    private Button bntReceita;

    @FXML
    private TextField txtReceita, txtQnt;

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
        if(event.getCode().equals(KeyCode.ENTER)) {
            if(!(listReceitas.getItems().isEmpty())){
                String receitaatual = listReceitas.getSelectionModel().getSelectedItem();
                lblVdP.setVisible(false);
                txtReceita.setVisible(false);
                listReceitas.setVisible(false);
                lblReceita.setText(receitaatual);
                lblReceita.setVisible(true);
                listIngredientes.setVisible(true);
                txtQnt.setVisible(true);
                lblQnt.setVisible(true);

                Estoque.atualizarEstoqueR();
                ObservableList<String> ingredientes = FXCollections.observableArrayList();

                for(Receitas receitas : Estoque.getInstance().getEstoqueR()){
                    if(receitas.getNome().equals(receitaatual)){
                        for(Ingredientes ing: receitas.getIngredientes()){
                            ingredientes.add(ing.getNome());
                        }
                        break;
                    }
                }

                listIngredientes.setItems(ingredientes);
                txtQnt.requestFocus();
            }
        }else if(event.getCode().isLetterKey()) {
            txtReceita.requestFocus();
        }

    }

    @FXML
    void pressQnt (KeyEvent event){
        if (!txtQnt.getText().isEmpty()) {
            if (event.getCode().equals(KeyCode.ENTER)) {
                int qnt = Integer.parseInt(txtQnt.getText());
                for (Receitas receitas : Estoque.getInstance().getEstoqueR()) {
                    if (receitas.getNome().equals(lblReceita.getText())) {
                        for (Ingredientes ingR : receitas.getIngredientes()) {
                            for (Ingredientes ingE : Estoque.getInstance().getEstoqueI()) {
                                if (ingR.getCodigo().equals(ingE.getCodigo())) {
                                    if (ingR.getPeso() * qnt > ingE.getPeso()) {
                                        Alert alerta = new Alert(Alert.AlertType.ERROR);
                                        alerta.setTitle("Quantidade Indisponível");
                                        alerta.setHeaderText("Quantidade Excedeu o Limite");
                                        alerta.setContentText("Diminua a quantidade de receitas");
                                        alerta.showAndWait();
                                        return;
                                    }
                                }
                            }
                        }
                        Produto receitaP = new Produto(receitas.getNome(), qnt,receitas.getValorCusto(), receitas.getValorVenda(), receitas.getCodigo(), "Nenhum");
                        //Continuar
                        break;
                    }
                }
            }
        }
    }

    @FXML
    public void fecharPrograma(){
        Stage stage = (Stage) bntReceita.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    void pesquisarReceita (KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER)) {
            if(!(listReceitas.getItems().isEmpty())){
                listReceitas.requestFocus();
            }
        }
    }

    private void changeScreen(String fxml) throws IOException {
        Stage stage = new Stage();

        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/app/resources/images/ICONE.png")));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    @FXML
    void submit() {

    }

}
