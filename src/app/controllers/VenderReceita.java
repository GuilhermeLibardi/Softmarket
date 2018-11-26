package app.controllers;

import app.Main;
import app.classes.*;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.jfree.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
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

    private int qnt;

    private Estoque estoque = Estoque.getInstance();

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
    void pressQnt (KeyEvent event) throws IOException {
        if (!txtQnt.getText().isEmpty()) {
            if (event.getCode().equals(KeyCode.ENTER)) {
                qnt = Integer.parseInt(txtQnt.getText());
                for (Receitas receitas : estoque.getEstoqueR()) {
                    if (receitas.getNome().equals(lblReceita.getText())) {
                        for (Ingredientes ingR : receitas.getIngredientes()) {
                            for (Ingredientes ingE : estoque.getEstoqueI()) {
                                if (ingR.getCodigo().equals(ingE.getCodigo())) {
                                    System.out.println(ingR.getPeso()*qnt + " - " + ingE.getPeso());
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
                        Receitas receita = new Receitas(receitas.getNome(),receitas.getValorCusto()*qnt,receitas.getValorVenda()*qnt,receitas.getCodigo(),qnt);

                        Ingredientes ingredientes1;

                        for(Ingredientes ing : receitas.getIngredientes()){
                            for (int i=0; i<estoque.getEstoqueI().size();i++){
                                if(estoque.getEstoqueI().get(i).getCodigo().equals(ing.getCodigo())){
                                    ingredientes1 = estoque.getEstoqueI().remove(i);
                                    ingredientes1.setPeso(estoque.getEstoqueI().get(i).getPeso()-ing.getPeso()*qnt);
                                    estoque.getEstoqueI().add(new Ingredientes(ingredientes1.getNome(),ingredientes1.getPeso(),ingredientes1.getCodigo()));
                                    break;
                                }
                            }
                        }
                        Estoque.getInstance1().getEstoqueI().setAll(estoque.getEstoqueI());

                        LoginController.vc.getListaProdutos().add(receita);
                        Stage stage = (Stage) txtQnt.getScene().getWindow();
                        stage.close();
                        return;
                    }
                }
            }
        }
    }

    public void descontarEstoque1 (ArrayList<Ingredientes> ingredientes, int qnt){

    }

    public void descontarEstoque (ArrayList<Ingredientes> ingredientes, int qnt){

        for(int i=0;i<ingredientes.size();i++){
            Estoque.getInstance().editarIngrediente(new Ingredientes(ingredientes.get(i).getNome(),ingredientes.get(i).getPeso()-ingredientes.get(i).getPeso()*qnt, ingredientes.get(i).getCodigo()));
        }
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
