package br.com.softmarket.controllers;

import br.com.softmarket.classes.Estoque.Estoque;
import br.com.softmarket.classes.Main.Main;
import br.com.softmarket.classes.PDV.Venda;
import br.com.softmarket.classes.Producao.Produto;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class VendasController implements Initializable {

    @FXML
    private Label lblQuantidade;

    @FXML
    private Label lblNomeProduto;

    @FXML
    private Label lblSubtotal;

    @FXML
    private Label lblTotal;

    @FXML
    private Label xVisible, realVisible1, realVisible2;

    @FXML
    private TableView<Produto> tableProdutos;

    @FXML
    private TextField txtCodBarras;

    @FXML
    private TextField txtQuantidade;

    private ObservableList<Produto> listaProdutos = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Produto, String> colCodBarras, colNome;

    @FXML
    private TableColumn<Produto, Double> colVenda;

    @FXML
    private TableColumn<Produto, Integer> colEstoque;

    @FXML
    private Button inserir;

    private String statusVenda = ("Ocioso");

    private Venda vendaAtual;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        creatTable();
        Estoque.getInstance();
    }

    private void creatTable() {
        colCodBarras.setCellValueFactory(new PropertyValueFactory<Produto, String>("codigoBarras"));
        colNome.setCellValueFactory(new PropertyValueFactory<Produto, String>("nome"));
        colEstoque.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("quantidade"));
        colVenda.setCellValueFactory(new PropertyValueFactory<Produto, Double>("precoVenda"));

        Callback<TableColumn<Produto, Double>, TableCell<Produto, Double>> cellFactory = new Callback<TableColumn<Produto, Double>, TableCell<Produto, Double>>() {
            @Override
            public TableCell<Produto, Double> call(TableColumn<Produto, Double> col) {
                return new TableCell<Produto, Double>() {
                    @Override
                    public void updateItem(Double value, boolean empty) {
                        super.updateItem(value, empty);
                        if (value == null) {
                            setText(null);
                        } else {
                            setText(String.format("R$ %.2f", value));
                        }
                    }
                };
            }
        };
        colVenda.setCellFactory(cellFactory);
        tableProdutos.setItems(listaProdutos);
    }

    @FXML
    void digitarCdb(KeyEvent event) {
        //Impedir a inserção de letras no código de barras
        txtCodBarras.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCodBarras.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        //Verfica se o codigo de barras inserido existe
        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
            if(Estoque.getInstance().existeCodBarras(txtCodBarras.getText())) {
                txtQuantidade.requestFocus();
                return;
            }
            //Alerta de Produto não encontrado
            criarAlertaErro("Produto não encontrado!", "Código de barras não encontrado", "Digite um código de barras de um produto cadastrado no sistema.");
            txtCodBarras.requestFocus();
        }
    }

    @FXML
    void qntVenda(KeyEvent event) {
        //Verifica se foi inserido um número em quantidade
        txtQuantidade.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtQuantidade.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
            if (!txtQuantidade.getText().equals("")) {
                int tempQnt = Estoque.getInstance().qntProduto(txtCodBarras.getText());
                if (statusVenda.equals("Ativa")) {
                    if(vendaAtual.foiVendido(txtCodBarras.getText())) {
                        if(tempQnt >= vendaAtual.qntVendida(txtCodBarras.getText())+Integer.parseInt(txtQuantidade.getText())) {
                            inserir.requestFocus();
                            return;
                        }else{
                            criarAlertaErro("Estoque Indisponível", "Quantidade excedida","A quantidade disponível é " + (tempQnt -vendaAtual.qntVendida(txtCodBarras.getText())));
                            txtQuantidade.clear();
                            txtQuantidade.requestFocus();
                            return;
                        }
                    }
                }
                if(tempQnt >= Integer.parseInt(txtQuantidade.getText())){
                    inserir.requestFocus();
                    return;
                }
                criarAlertaErro("Estoque Indisponível", "Quantidade excedida","A quantidade disponível é " + tempQnt);
                txtQuantidade.clear();
                txtQuantidade.requestFocus();
            }
            txtQuantidade.requestFocus();
        }
    }

    @FXML
    public void inserirProduto() {
        Produto produtoAtual = Estoque.getInstance().retornarProduto(txtCodBarras.getText(), txtQuantidade.getText());

        if (!txtCodBarras.getText().isEmpty() && !txtQuantidade.getText().isEmpty()) {
            setarVisibilidadeTrue();
            if (statusVenda.equals("Ocioso")) {
                statusVenda = ("Ativa");
                vendaAtual = Venda.iniciarVenda();
                vendaAtual.inserirProdutoVenda(txtCodBarras.getText(), Integer.parseInt(txtQuantidade.getText()));
            }else if (statusVenda.equals("Ativa")) {
                if(vendaAtual.foiVendido(txtCodBarras.getText())){
                    vendaAtual.inserirProdutoVenda(txtCodBarras.getText(), Integer.parseInt(txtQuantidade.getText())+vendaAtual.qntVendida(txtCodBarras.getText()));
                }else{
                    vendaAtual.inserirProdutoVenda(txtCodBarras.getText(), Integer.parseInt(txtQuantidade.getText()));
                }
            }
            vendaAtual.setValorVenda(produtoAtual.getPrecoVenda()*Double.parseDouble(txtQuantidade.getText()) + vendaAtual.getValorVenda());
            listaProdutos.add(produtoAtual);
            lblQuantidade.setText(txtQuantidade.getText());
            lblNomeProduto.setText(produtoAtual.getNome());
            lblSubtotal.setText(String.format("%.2f", produtoAtual.getPrecoVenda() * Double.parseDouble(txtQuantidade.getText())));
            lblTotal.setText(String.format("%.2f", vendaAtual.getValorVenda()));
            txtCodBarras.clear();
            txtQuantidade.clear();
            txtCodBarras.requestFocus();
        }
    }

    @FXML
    private void setarVisibilidadeTrue(){
        lblNomeProduto.setVisible(true);
        lblQuantidade.setVisible(true);
        lblSubtotal.setVisible(true);
        lblTotal.setVisible(true);
        xVisible.setVisible(true);
        realVisible1.setVisible(true);
        realVisible2.setVisible(true);
    }

    @FXML
    private void setarVisibilidadeFalse(){
        lblNomeProduto.setVisible(false);
        lblQuantidade.setVisible(false);
        lblSubtotal.setVisible(false);
        lblTotal.setVisible(false);
        xVisible.setVisible(false);
        realVisible1.setVisible(false);
        realVisible2.setVisible(false);
    }

    public void fPress(KeyEvent event) {
        if (!listaProdutos.isEmpty()) {
            switch (event.getCode()) {
                case F3:
                    removerProduto();
                    break;
                case F4:
                    cancelarVenda();
                    break;
                case F5:
                    confirmarVenda();
                    Estoque.getInstance();
                    break;
            }
        } else if (event.getCode() == KeyCode.F6) {
            try {
                changeScreen("/app/resources/fxml/telaVenderReceita.fxml");
            } catch (IOException e) {
                e.printStackTrace();
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

    private TextInputDialog criarDialog(String title, String header, String content){
        TextInputDialog dialogoCodBarras = new TextInputDialog();
        dialogoCodBarras.setTitle(title);
        dialogoCodBarras.setHeaderText(header);
        dialogoCodBarras.setContentText(content);

        return dialogoCodBarras;
    }

    private void removerProduto() {
        Produto produto;

        Optional<String> resultCodBarras = criarDialog("Cancelamento de venda de item", "Informe o código de barras do item a ser removido.", "Código de Barras:").showAndWait();
        double valorProduto=0.0;
        txtCodBarras.requestFocus();

        if(resultCodBarras.isPresent()){
            if(vendaAtual.foiVendido(resultCodBarras.get())) {
                for (Iterator<Produto> it = listaProdutos.iterator(); it.hasNext(); ) {
                    produto = it.next();
                    if (produto.getCodigoBarras().equals(resultCodBarras.get())) {
                        valorProduto = produto.getPrecoVenda();
                        it.remove();
                    }
                }
                vendaAtual.setValorVenda(vendaAtual.getValorVenda() - valorProduto * vendaAtual.qntVendida(resultCodBarras.get()));
                vendaAtual.removerProdutoVenda(resultCodBarras.get());
                if (!listaProdutos.isEmpty()) {
                    produto = listaProdutos.get(listaProdutos.size() - 1);
                    lblNomeProduto.setText(listaProdutos.get(listaProdutos.size() - 1).getNome());
                    lblQuantidade.setText(Integer.toString(produto.getQuantidade()));
                    lblSubtotal.setText(String.format("%.2f", listaProdutos.get(listaProdutos.size() - 1).getPrecoVenda() * produto.getQuantidade()));
                    lblTotal.setText(String.format("%.2f", vendaAtual.getValorVenda()));
                } else {
                    statusVenda = "Ocioso";
                    setarVisibilidadeFalse();
                }
            }else{
                criarAlertaErro("Código de barras inválido!","O código de barras informado não foi inserido nessa venda.","Insira o código de barras novamente.");
                removerProduto();
            }
        }
    }

    private void criarAlertaErro(String Title, String Header, String Content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(Title);
        alert.setHeaderText(Header);
        alert.setContentText(Content);
        alert.showAndWait();
    }

    private Alert criarAlertaConfirmacao(String Title, String Header, String Content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(Title);
        alert.setHeaderText(Header);
        alert.setContentText(Content);
        return alert;
    }

    private void cancelarVenda() {
        Optional<ButtonType> result = criarAlertaConfirmacao("Cancelamento de Venda","Você está prestes a cancelar uma venda.","Você tem certeza disso?").showAndWait();

        if (result.get() == ButtonType.OK) {
            listaProdutos.clear();
            vendaAtual.cancelarVenda();
            setarVisibilidadeFalse();
            statusVenda = "Ocioso";
        }
        txtCodBarras.requestFocus();
    }

    private void confirmarVenda() {

        Alert dialogoExe = criarAlertaConfirmacao("Forma de Pagamento","Informe a Forma de Pagamento","Você irá pagar em dinheiro ou cartão?");
        ButtonType btnCartao = new ButtonType("Cartão");
        ButtonType btnDinheiro = new ButtonType("Dinheiro");
        dialogoExe.getButtonTypes().setAll(btnCartao, btnDinheiro);
        Optional<ButtonType> resultado = dialogoExe.showAndWait();

        if (resultado.get() == btnCartao) {
            vendaAtual.setOrigemVenda("C");
            vendaAtual.setValorPago(vendaAtual.getValorVenda());
        } else if (resultado.get() == btnDinheiro) {
            TextInputDialog dialogoPagamento = criarDialog("Valor Pago","Insira o valor pago", "R$");
            vendaAtual.setOrigemVenda("D");
            Optional<String> pagamento = dialogoPagamento.showAndWait();
            while (!isDoubleString(pagamento.get()) || Double.parseDouble(pagamento.get()) < vendaAtual.getValorVenda()) {
                criarAlertaErro("Valor Incorreto", "Você digitou um valor incorreto.", "Digite o valor pago pelo cliente novamente.");
                pagamento = dialogoPagamento.showAndWait();
            }
            vendaAtual.setValorPago(Double.parseDouble(pagamento.get()));
            vendaAtual.setValorTroco(vendaAtual.calcularTroco());

            Alert dialogoInfo = criarAlertaInformativo("Valor do Troco","O valor do troco é:", String.format("R$ %.2f", vendaAtual.getValorTroco()));
            dialogoInfo.showAndWait();
        }
        vendaAtual.fecharVenda();
        listaProdutos.clear();
        txtCodBarras.requestFocus();
        statusVenda = ("Ocioso");
        setarVisibilidadeFalse();
    }

    private static boolean isDoubleString(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private Alert criarAlertaInformativo(String title, String header, String content){
        Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
        dialogoInfo.setTitle(title);
        dialogoInfo.setHeaderText(header);
        dialogoInfo.setContentText(content);
        return dialogoInfo;
    }
}