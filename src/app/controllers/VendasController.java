package app.controllers;

import app.Main;
import app.classes.Estoque;
import app.classes.Itens;
import app.classes.Produto;
import app.classes.Venda;
import app.classes.usuarios.Vendedor;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

public class VendasController implements Initializable{

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
    private TableView<Itens> tableProdutos;

    @FXML
    private TextField txtCodBarras;

    @FXML
    private TextField txtQuantidade;

    private ObservableList<Itens> listaProdutos = FXCollections.observableArrayList();

    private ArrayList<Produto> estoqueProdutos = new ArrayList<>();

    @FXML
    private TableColumn<Itens, String> colCodBarras, colNome;

    @FXML
    private TableColumn<Itens, Double> colVenda;

    @FXML
    private TableColumn<Itens, Integer> colEstoque;

    @FXML
    private Button inserir;

    private String statusVenda=("Ocioso");

    private Vendedor vendedor;

    private Venda venda;


    public ObservableList<Itens> getListaProdutos() {
        return listaProdutos;
    }

    public void setListaProdutos(ObservableList<Itens> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public TableView<Itens> getTableProdutos() {
        return tableProdutos;
    }

    public void setTableProdutos(TableView<Itens> tableProdutos) {
        this.tableProdutos = tableProdutos;
    }



    @FXML
    void digitarCdb(KeyEvent event){

        txtCodBarras.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCodBarras.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        if(event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
            for(Produto procurar : Estoque.getInstance().getEstoque()) {
                if (procurar.getCodigo().equals(txtCodBarras.getText())) {
                    txtQuantidade.requestFocus();
                    return;
                }
            }
            Alert erroCdB = new Alert(Alert.AlertType.ERROR);
            erroCdB.setTitle("Produto não encontrado!");
            erroCdB.setHeaderText("Código de barras não encontrado");
            erroCdB.setContentText("Digite um código de barras de um produto cadastrado no sistema.");
            erroCdB.showAndWait();
            txtCodBarras.requestFocus();
        } else if(event.getCode().equals(KeyCode.F6)){
            try {
                changeScreen("/app/resources/fxml/telaVenderReceita.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void qntVenda(KeyEvent event) {

        txtQuantidade.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtQuantidade.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        if(event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
            if(!txtQuantidade.getText().equals("")) {
                for (Produto procurar1 : Estoque.getInstance().getEstoque()) {
                    if (procurar1.getCodigo().equals(txtCodBarras.getText())) {
                        if (procurar1.getQuantidade() >= (Integer.parseInt(txtQuantidade.getText()))) {
                            if (statusVenda.equals("Ativa")) {
                                if (verificaLista(venda.getProdutos())) {
                                    for (Produto vendasP : estoqueProdutos) {
                                        if (vendasP.getCodigo().equals(txtCodBarras.getText())) {
                                            if (vendasP.getQuantidade() < Integer.parseInt(txtQuantidade.getText())) {
                                                txtQuantidade.clear();
                                                alertaEstoque(vendasP);
                                                txtQuantidade.requestFocus();
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                            inserir.requestFocus();
                            return;
                        } else {
                            if (statusVenda.equals("Ativa")) {
                                for (Produto produtoEst : estoqueProdutos) {
                                    if (produtoEst.getCodigo().equals(txtCodBarras.getText())) {
                                        txtQuantidade.clear();
                                        alertaEstoque(produtoEst);
                                        txtQuantidade.requestFocus();
                                        return;
                                    }
                                }
                            }
                            txtQuantidade.clear();
                            alertaEstoque(procurar1);
                            txtQuantidade.requestFocus();
                            return;
                        }
                    }
                }
                txtCodBarras.requestFocus();
            }else if(!txtCodBarras.getText().equals("")){
                Alert erroEst = new Alert(Alert.AlertType.ERROR);
                erroEst.setTitle("Digite um valor");
                erroEst.setHeaderText("Nenhum valor foi inserido");
                erroEst.setContentText("Informe a Quantidade desejada");
                erroEst.showAndWait();
                txtQuantidade.requestFocus();
            }else{
                Alert erroEst = new Alert(Alert.AlertType.ERROR);
                erroEst.setTitle("Código Barras Não Digitado");
                erroEst.setHeaderText("Você não informou o produto desejado");
                erroEst.setContentText("Digite o código de barras.");
                erroEst.showAndWait();
                txtCodBarras.requestFocus();
            }
        }
    }

    @FXML
    public void inserirProduto(){
        Produto produto1, produtoE;

        if(!txtCodBarras.getText().isEmpty() && !txtQuantidade.getText().isEmpty()) {
            lblNomeProduto.setVisible(true);
            lblQuantidade.setVisible(true);
            lblSubtotal.setVisible(true);
            lblTotal.setVisible(true);
            xVisible.setVisible(true);
            realVisible1.setVisible(true);
            realVisible2.setVisible(true);
            if (statusVenda.equals("Ocioso")) {
                statusVenda = ("Ativa");
                venda = vendedor.iniciarVenda();
                for (Produto mainP : Estoque.getInstance().getEstoque()) {
                    if (mainP.getCodigo().equals(txtCodBarras.getText())) {
                        produto1 = new Produto(mainP.getNome(), Integer.parseInt(txtQuantidade.getText()), mainP.getValorCusto(), mainP.getValorVenda(), mainP.getCodigo(), mainP.getIngredienteId());
                        produtoE = new Produto(mainP.getNome(), mainP.getQuantidade() - Integer.parseInt(txtQuantidade.getText()), mainP.getValorCusto(), mainP.getValorVenda(), mainP.getCodigo(), mainP.getIngredienteId());
                        estoqueProdutos.add(produtoE);
                        venda.setValor(venda.getValor() + mainP.getValorVenda() * Double.parseDouble(txtQuantidade.getText()));
                        listaProdutos.add(produto1);
                        venda.inserirProduto(produto1);
                        txtQuantidade.clear();
                        txtCodBarras.clear();
                        lblQuantidade.setText(Integer.toString(produto1.getQuantidade()));
                        lblNomeProduto.setText(produto1.getNome());
                        lblSubtotal.setText(String.format("%.2f", produto1.getValorVenda() * produto1.getQuantidade()));
                        lblTotal.setText(String.format("%.2f", venda.getValor()));
                        txtCodBarras.clear();
                        txtQuantidade.clear();
                        txtCodBarras.requestFocus();
                    }
                }
            }else if(statusVenda.equals("Ativa")){
                for (Produto procurar : estoqueProdutos) {
                    if (procurar.getCodigo().equals(txtCodBarras.getText())) {
                        for (int i = 0; i<listaProdutos.size(); i++) {
                            if(listaProdutos.get(i) instanceof Produto) {
                                Produto produtop = (Produto) listaProdutos.get(i);
                                if (produtop.getCodigo().equals(procurar.getCodigo())) {
                                    produto1 = new Produto(produtop.getNome(), produtop.getQuantidade(), produtop.getValorCusto(), produtop.getValorVenda(), produtop.getCodigo(), produtop.getIngredienteId());
                                    produto1.setQuantidade(produto1.getQuantidade() + Integer.parseInt(txtQuantidade.getText()));
                                    listaProdutos.remove(produtop);
                                    listaProdutos.add(produto1);
                                    procurar.setQuantidade(procurar.getQuantidade() - Integer.parseInt(txtQuantidade.getText()));
                                    venda.setValor(venda.getValor() + produto1.getValorVenda() * Double.parseDouble(txtQuantidade.getText()));
                                    txtQuantidade.clear();
                                    txtCodBarras.clear();
                                    txtCodBarras.requestFocus();
                                    lblQuantidade.setText(Integer.toString(produto1.getQuantidade()));
                                    lblNomeProduto.setText(produto1.getNome());
                                    lblSubtotal.setText(String.format("%.2f", produto1.getValorVenda() * produto1.getQuantidade()));
                                    lblTotal.setText(String.format("%.2f", venda.getValor()));

                                    return;
                                }
                            }
                        }
                    }
                }
                for (Produto mainP : Estoque.getInstance().getEstoque()) {
                    if (mainP.getCodigo().equals(txtCodBarras.getText())) {
                        produto1 = new Produto(mainP.getNome(), Integer.parseInt(txtQuantidade.getText()), mainP.getValorCusto(), mainP.getValorVenda(), mainP.getCodigo(), mainP.getIngredienteId());
                        produtoE = new Produto(mainP.getNome(), mainP.getQuantidade() - Integer.parseInt(txtQuantidade.getText()), mainP.getValorCusto(), mainP.getValorVenda(), mainP.getCodigo(), mainP.getIngredienteId());
                        estoqueProdutos.add(produtoE);
                        venda.setValor(venda.getValor() + mainP.getValorVenda() * Double.parseDouble(txtQuantidade.getText()));
                        listaProdutos.add(produto1);
                        venda.inserirProduto(produto1);
                        txtQuantidade.clear();
                        txtCodBarras.clear();
                        lblQuantidade.setText(Integer.toString(produto1.getQuantidade()));
                        lblNomeProduto.setText(produto1.getNome());
                        lblSubtotal.setText(String.format("%.2f", produto1.getValorVenda() * produto1.getQuantidade()));
                        lblTotal.setText(String.format("%.2f", venda.getValor()));
                        txtCodBarras.clear();
                        txtQuantidade.clear();
                        txtCodBarras.requestFocus();
                    }
                }
            }
        }
    }


    public void creatTable() {
        colCodBarras.setCellValueFactory(new PropertyValueFactory<Itens, String>("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory<Itens, String>("nome"));
        colEstoque.setCellValueFactory(new PropertyValueFactory<Itens, Integer>("quantidade"));
        colVenda.setCellValueFactory(new PropertyValueFactory<Itens, Double>("valorVenda"));

        Callback<TableColumn<Itens, Double>, TableCell<Itens, Double>> cellFactory = new Callback<TableColumn<Itens, Double>, TableCell<Itens, Double>>() {
            @Override
            public TableCell<Itens, Double> call(TableColumn<Itens, Double> col) {
                return new TableCell<Itens, Double>() {
                    @Override
                    public void updateItem(Double value, boolean empty) {
                        super.updateItem(value, empty) ;
                        if (value==null) {
                            setText(null);
                        } else {
                            setText(String.format("R$ %.2f", value.doubleValue()));
                        }
                    }
                };
            }
        };
        colVenda.setCellFactory(cellFactory);
        tableProdutos.setItems(listaProdutos);
    }

    public boolean verificaLista(ObservableList<Produto> listaP){
        for (Produto produtos : listaP){
            if(produtos.getCodigo().equals(txtCodBarras.getText())){
                return true;
            }
        }
        return false;
    }

    public boolean verificaLista(ArrayList<Produto> listaP){
        for (Produto produtos : listaP){
            if(produtos.getCodigo().equals(txtCodBarras.getText())){
                return true;
            }
        }
        return false;
    }

    public void fPress (KeyEvent event){
        if(!listaProdutos.isEmpty()) {
            switch (event.getCode()) {
                case F3:
                    pressF3();
                    break;
                case F4:
                    pressF4();
                    break;
                case F5:
                    pressF5();
                    break;
            }
        }else if(event.getCode() == KeyCode.F6){
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

    public void alertaEstoque(Produto produto) {
        Alert erroEst = new Alert(Alert.AlertType.ERROR);
        erroEst.setTitle("Estoque Indisponível!");
        erroEst.setHeaderText("Quantidade desejada indisponível no estoque.");
        erroEst.setContentText("Você pode inserir até: " + produto.getQuantidade() + " unidades.");
        erroEst.showAndWait();
    }

    public void pressF3(){
        TextInputDialog dialogoRemocao = new TextInputDialog();

        txtCodBarras.requestFocus();

        dialogoRemocao.setTitle("Cancelamento de venda de produto");
        dialogoRemocao.setHeaderText("Informe o código de barras do produto a ser removido.");
        dialogoRemocao.setContentText("Código de Barras:");
        Optional<String> result = dialogoRemocao.showAndWait();
        String cDb = "none";

        if(result.isPresent()) {
            cDb = result.get();
            for (Iterator<Itens> it = listaProdutos.iterator(); it.hasNext();) {
                Produto produto1 = (Produto) it.next();
                if (produto1.getCodigo().equals(cDb)) {
                    it.remove();
                    for (Iterator<Produto> i = estoqueProdutos.iterator(); i.hasNext();) {
                        Produto produto = i.next();
                        if (produto.getCodigo().equals(produto1.getCodigo())) {
                            i.remove();
                            break;
                        }
                    }
                    venda.cancelarProduto(produto1);
                    venda.setValor(venda.getValor()-(produto1.getValorVenda()*produto1.getQuantidade()));
                    if(!listaProdutos.isEmpty()){
                        Produto produto = (Produto) listaProdutos.get(listaProdutos.size()-1);
                        lblNomeProduto.setText(listaProdutos.get(listaProdutos.size()-1).getNome());
                        lblQuantidade.setText(Integer.toString(produto.getQuantidade()));
                        lblSubtotal.setText(String.format("%.2f", listaProdutos.get(listaProdutos.size()-1).getValorVenda()*produto.getQuantidade()));
                        lblTotal.setText(String.format("%.2f", venda.getValor()));
                    }else{
                        lblNomeProduto.setVisible(false);
                        lblQuantidade.setVisible(false);
                        lblSubtotal.setVisible(false);
                        lblTotal.setVisible(false);
                        xVisible.setVisible(false);
                        realVisible1.setVisible(false);
                        realVisible2.setVisible(false);
                    }
                    return;
                }
            }
            Alert erroEst = new Alert(Alert.AlertType.ERROR);
            erroEst.setTitle("Código de barras inválido!");
            erroEst.setHeaderText("O código de barras informado não foi inserido nessa venda.");
            erroEst.setContentText("Insira o código de barras novamente.");
            erroEst.showAndWait();
            pressF3();
        }
    }

    public void pressF4(){
        Alert cancelarVenda = new Alert(Alert.AlertType.CONFIRMATION);
        cancelarVenda.setTitle("Cancelamento de Venda");
        cancelarVenda.setHeaderText("Você está prestes a cancelar uma venda.");
        cancelarVenda.setContentText("Você tem certeza disso?");

        Optional<ButtonType> result = cancelarVenda.showAndWait();
        if(result.get() ==  ButtonType.OK){
            listaProdutos.clear();
            estoqueProdutos.clear();
            venda.cancelarVenda();
            venda.setValor(0.0);
            lblNomeProduto.setVisible(false);
            lblQuantidade.setVisible(false);
            lblSubtotal.setVisible(false);
            lblTotal.setVisible(false);
            xVisible.setVisible(false);
            realVisible1.setVisible(false);
            realVisible2.setVisible(false);
        }
        txtCodBarras.requestFocus();
    }

    public void pressF5(){
        Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnCartao = new ButtonType("Cartão");
        ButtonType btnDinheiro = new ButtonType("Dinheiro");
        dialogoExe.setTitle("Forma de Pagamento");
        dialogoExe.setHeaderText("Informe a Forma de Pagamento");
        dialogoExe.setContentText("Você irá pagar em dinheiro ou cartão?");

        dialogoExe.getButtonTypes().setAll(btnCartao, btnDinheiro);

        Optional<ButtonType> resultado = dialogoExe.showAndWait();

        if(resultado.get() == btnCartao){
            venda.setTipoPag('c');
            venda.setPagamento(venda.getValor());
        }else if(resultado.get() == btnDinheiro) {
            venda.setTipoPag('d');
            TextInputDialog dialogoPagamento = new TextInputDialog();

            dialogoPagamento.setTitle("Valor Pago");
            dialogoPagamento.setHeaderText("Insira o valor pago");
            dialogoPagamento.setContentText("R$");
            Optional<String> pagamento = dialogoPagamento.showAndWait();

            System.out.println(Double.parseDouble(pagamento.get()));
            while (!isDoubleString(pagamento.get()) || Double.parseDouble(pagamento.get()) < venda.getValor()) {
                Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
                dialogoInfo.setTitle("Valor Incorreto");
                dialogoInfo.setHeaderText("Você digitou um valor incorreto.");
                dialogoInfo.setContentText("Digite o valor pago pelo cliente novamente.");
                dialogoInfo.showAndWait();
                pagamento = dialogoPagamento.showAndWait();
            }

            venda.setPagamento(Double.parseDouble(pagamento.get()));
            venda.setTroco(venda.calculaTroco());

            Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
            dialogoInfo.setTitle("Valor do Troco");
            dialogoInfo.setHeaderText("O valor do troco é:");
            dialogoInfo.setContentText(String.format("R$ %.2f", venda.getTroco()));
            dialogoInfo.showAndWait();

        }

        Alert dialogoNota = new Alert(Alert.AlertType.INFORMATION);
        dialogoNota.setTitle("Nota Fiscal");
        dialogoNota.setHeaderText("Nota Fiscal");
        dialogoNota.setContentText(venda.notaFiscal());
        dialogoNota.showAndWait();

        listaProdutos.clear();
        estoqueProdutos.clear();
        vendedor.fecharVenda(venda);
        txtCodBarras.requestFocus();
        statusVenda = ("Ocioso");
        lblNomeProduto.setVisible(false);
        lblQuantidade.setVisible(false);
        lblSubtotal.setVisible(false);
        lblTotal.setVisible(false);
        xVisible.setVisible(false);
        realVisible1.setVisible(false);
        realVisible2.setVisible(false);
    }

    public void changeUser(Vendedor user) {
        this.vendedor = user;
    }

    public static boolean isDoubleString (String s){
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        creatTable();
        Estoque.getInstance();
    }

    public Label getLblQuantidade() {
        return lblQuantidade;
    }

    public void setLblQuantidade(Label lblQuantidade) {
        this.lblQuantidade = lblQuantidade;
    }
}