package app.controllers;

import app.classes.Estoque;
import app.classes.Ingredientes;
import app.classes.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class AdicionarProdutoController implements Initializable {

    @FXML
    private TextField txtCodBarras, txtNome, txtPreco, txtPrecoVenda, txtQuantidade, txtPeso, txtPesavel;

    @FXML
    private Button btnCadastrar;

    @FXML
    private ComboBox comboIng;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> ingredientes = FXCollections.observableArrayList();

        ingredientes.add("Nenhum");

        for(Ingredientes ingrediente: Estoque.getInstance().getEstoqueI()){
            ingredientes.add(ingrediente.getNome());
        }

        this.comboIng.setItems(ingredientes);

        comboIng.setValue("Nenhum");
    }

    @FXML
    void submit() {
        for(Produto p : Estoque.getInstance().getEstoque()) {
            if (p.getCodigo().equals(txtCodBarras.getText())) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Erro de inserção");
                alerta.setHeaderText("Erro ao inserir novo produto");
                alerta.setContentText("Este código de barras já está cadastrado no banco de dados!");
                alerta.showAndWait();
                return;
            }
            if(p.getNome().equals(txtNome.getText())) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Alerta ao inserir");
                alerta.setHeaderText("Produto com nome repetido");
                alerta.setContentText("Produto com este nome já existe, considere alterar o nome");
                alerta.showAndWait();
                return;
            }
        }
        double valorCompra = Double.parseDouble(txtPreco.getText().replace(",","."));
        double valorVenda = Double.parseDouble(txtPrecoVenda.getText().replace(",","."));
        double peso = Double.parseDouble(txtPeso.getText().replace(",","."));
        String codIng = null;

        for(Ingredientes ing : Estoque.getInstance().getEstoqueI()){
            if(ing.getNome().equals(comboIng.getValue().toString())){
                System.out.println("Entrou!");
                codIng = ing.getCodigo();
                break;
            }
        }

        Estoque.getInstance().adicionarProduto(new Produto(txtNome.getText(), Integer.parseInt(txtQuantidade.getText()), valorCompra, valorVenda, txtCodBarras.getText(), peso, codIng));
        Stage stage = (Stage) btnCadastrar.getScene().getWindow();
        stage.close();
    }
}
