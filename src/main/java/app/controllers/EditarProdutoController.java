package app.controllers;

import app.classes.Estoque;
import app.classes.Ingredientes;
import app.classes.Produto;
import app.classes.exceptions.ProdutoNaoEncontradoException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditarProdutoController implements Initializable {

    @FXML
    private TextField txtCodBarras, txtNome, txtPrecoCompra, txtPrecoVenda, txtQuantidade, txtPeso, txtPesavel;

    @FXML
    private Label lblNome, lblCompra, lblVenda, lblQuantidade, lblPesavel, lblPeso, lblIng;

    @FXML
    private Button btnCadastrar, btnSearch;

    @FXML
    private ComboBox comboIng;

    private String cod;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> ingredientes = FXCollections.observableArrayList();

        ingredientes.add("Nenhum");

        for(Ingredientes ingrediente: Estoque.getInstance1().getEstoqueI()){
            ingredientes.add(ingrediente.getNome());
        }
        txtCodBarras.setText(cod);

        this.comboIng.setItems(ingredientes);
    }

    @FXML
    void searchButton() {
        try {
            Produto p = Estoque.getInstance1().pesquisarProduto(txtCodBarras.getText());
            txtNome.setVisible(true);
            txtPrecoCompra.setVisible(true);
            txtPrecoVenda.setVisible(true);
            txtQuantidade.setVisible(true);
            txtPeso.setVisible(true);
            btnSearch.setVisible(false);
            btnCadastrar.setVisible(true);
            lblNome.setVisible(true);
            lblCompra.setVisible(true);
            lblVenda.setVisible(true);
            lblQuantidade.setVisible(true);
            lblPeso.setVisible(true);
            lblIng.setVisible(true);
            comboIng.setVisible(true);

            for(Ingredientes ing: Estoque.getInstance1().getEstoqueI()){
                if(ing.getCodigo().equals(p.getIngredienteId())){
                    comboIng.setValue(ing.getNome());
                }
            }

            txtNome.setText(p.getNome());
            txtPrecoCompra.setText(String.valueOf(p.getValorCusto()).replace(".", ","));
            txtQuantidade.setText(String.valueOf(p.getQuantidade()));
            txtPrecoVenda.setText(String.valueOf(p.getValorVenda()).replace(".", ","));
            txtPeso.setText(String.valueOf(p.getPeso()));
            return;

        } catch (ProdutoNaoEncontradoException e) {
            System.out.println(e.getMessage());
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
            try {

                String codIng="";

                for(Ingredientes ing : Estoque.getInstance1().getEstoqueI()){
                    if(ing.getNome().equals(comboIng.getValue().toString())){
                        codIng = ing.getCodigo();
                        break;
                    }else if(ing.getNome().equals("Nenhum")){
                        codIng = null;
                        break;
                    }
                }

                Estoque.getInstance1().editarProduto(new Produto(this.txtNome.getText(), Integer.parseInt(this.txtQuantidade.getText()), Double.parseDouble(this.txtPrecoCompra.getText().replace(",", ".")), Double.parseDouble(this.txtPrecoVenda.getText().replace(",", ".")), txtCodBarras.getText(), Double.parseDouble(this.txtPeso.getText().replace(",", ".")), codIng));
            } catch (ProdutoNaoEncontradoException e) {
                System.out.println(e.getMessage());
            }

            Stage window = (Stage) btnCadastrar.getScene().getWindow();
            window.close();
        }
    }

    public void setCod(String cod){
        this.cod = cod;
        txtCodBarras.setText(cod);
        btnSearch.fire();
    }

}
