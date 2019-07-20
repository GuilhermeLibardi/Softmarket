package br.com.softmarket.controllers;

import br.com.softmarket.classes.PDV.Venda;
import br.com.softmarket.classes.Producao.Produto;
import br.com.softmarket.classes.RH.Funcionario;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

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

    private ArrayList<Produto> estoqueProdutos = new ArrayList<>();

    @FXML
    private TableColumn<Produto, String> colCodBarras, colNome;

    @FXML
    private TableColumn<Produto, Double> colVenda;

    @FXML
    private TableColumn<Produto, Integer> colEstoque;

    @FXML
    private Button inserir;

    private String statusVenda = ("Ocioso");

    private Funcionario vendedor;

    private Venda venda;


    public ObservableList<Produto> getListaProdutos() {
        return listaProdutos;
    }

    public void setListaProdutos(ObservableList<Produto> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public TableView<Produto> getTableProdutos() {
        return tableProdutos;
    }

    public void setTableProdutos(TableView<Produto> tableProdutos) {
        this.tableProdutos = tableProdutos;
    }

    @FXML
    void digitarCdb(KeyEvent event) {

        txtCodBarras.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCodBarras.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
            for (Produto procurar : Estoque.getInstance1().getEstoque()) {
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
        } else if (event.getCode().equals(KeyCode.F6)) {
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
        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
            if (!txtQuantidade.getText().equals("")) {
                for (Produto procurar1 : Estoque.getInstance1().getEstoque()) {
                    if (procurar1.getCodigo().equals(txtCodBarras.getText())) {
                        if (procurar1.getQuantidade() >= (Integer.parseInt(txtQuantidade.getText()))) {
                            if (statusVenda.equals("Ativa")) {
                                if (verificaLista(venda.getProduto())) {
                                    for (Produto vendasP1 : estoqueProdutos) {
                                        if (vendasP1 instanceof Produto) {
                                            Produto vendasP = (Produto) vendasP1;
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
                            }
                            inserir.requestFocus();
                            return;
                        } else {
                            if (statusVenda.equals("Ativa")) {
                                for (Produto produtoEst : estoqueProdutos) {
                                    if (produtoEst instanceof Produto) {
                                        if (produtoEst.getCodigo().equals(txtCodBarras.getText())) {
                                            txtQuantidade.clear();
                                            alertaEstoque(produtoEst);
                                            txtQuantidade.requestFocus();
                                            return;
                                        }
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
            } else if (!txtCodBarras.getText().equals("")) {
                Alert erroEst = new Alert(Alert.AlertType.ERROR);
                erroEst.setTitle("Digite um valor");
                erroEst.setHeaderText("Nenhum valor foi inserido");
                erroEst.setContentText("Informe a Quantidade desejada");
                erroEst.showAndWait();
                txtQuantidade.requestFocus();
            } else {
                Alert erroEst = new Alert(Alert.AlertType.ERROR);
                erroEst.setTitle("Código Barras Não Digitado");
                erroEst.setHeaderText("Você não informou o produto desejado");
                erroEst.setContentText("Digite o código de barras.");
                erroEst.showAndWait();
                txtCodBarras.requestFocus();
            }
        }
    }

    public Label getLblNomeProduto() {
        return lblNomeProduto;
    }

    public void setLblNomeProduto(String string) {
        this.lblNomeProduto.setText(string);
    }

    public Label getLblSubtotal() {
        return lblSubtotal;
    }

    public void setLblSubtotal(String string) {
        this.lblSubtotal.setText(string);
    }

    public Label getLblTotal() {
        return lblTotal;
    }

    public void setLblTotal(String string) {
        this.lblTotal.setText(string);
    }

    public Label getxVisible() {
        return xVisible;
    }

    public void setxVisible(Label xVisible) {
        this.xVisible = xVisible;
    }

    public Label getRealVisible1() {
        return realVisible1;
    }

    public void setRealVisible1(Label realVisible1) {
        this.realVisible1 = realVisible1;
    }

    public Label getRealVisible2() {
        return realVisible2;
    }

    public void setRealVisible2(Label realVisible2) {
        this.realVisible2 = realVisible2;
    }

    @FXML
    public void inserirProduto() {
        Produto produto1, produtoE;

        if (!txtCodBarras.getText().isEmpty() && !txtQuantidade.getText().isEmpty()) {
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
                for (Produto mainP : Estoque.getInstance1().getEstoque()) {
                    if (mainP.getCodigo().equals(txtCodBarras.getText())) {
                        produto1 = new Produto(mainP.getNome(), Integer.parseInt(txtQuantidade.getText()), mainP.getValorCusto(), mainP.getValorVenda(), mainP.getCodigo(), mainP.getIngredienteId());
                        produtoE = new Produto(mainP.getNome(), mainP.getQuantidade() - Integer.parseInt(txtQuantidade.getText()), mainP.getValorCusto(), mainP.getValorVenda(), mainP.getCodigo(), mainP.getIngredienteId());
                        estoqueProdutos.add(produtoE);
                        venda.setValor(venda.getValor() + mainP.getValorVenda() * Double.parseDouble(txtQuantidade.getText()));
                        listaProdutos.add(produto1);
                        venda.inserirItem(produto1);
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
            } else if (statusVenda.equals("Ativa")) {
                for (Produto procurar : estoqueProdutos) {
                    if (procurar instanceof Produto) {
                        if (procurar.getCodigo().equals(txtCodBarras.getText())) {
                            for (int i = 0; i < listaProdutos.size(); i++) {
                                if (listaProdutos.get(i) instanceof Produto) {
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
                }
                for (Produto mainP : Estoque.getInstance1().getEstoque()) {
                    if (mainP.getCodigo().equals(txtCodBarras.getText())) {
                        produto1 = new Produto(mainP.getNome(), Integer.parseInt(txtQuantidade.getText()), mainP.getValorCusto(), mainP.getValorVenda(), mainP.getCodigo(), mainP.getIngredienteId());
                        produtoE = new Produto(mainP.getNome(), mainP.getQuantidade() - Integer.parseInt(txtQuantidade.getText()), mainP.getValorCusto(), mainP.getValorVenda(), mainP.getCodigo(), mainP.getIngredienteId());
                        estoqueProdutos.add(produtoE);
                        venda.setValor(venda.getValor() + mainP.getValorVenda() * Double.parseDouble(txtQuantidade.getText()));
                        listaProdutos.add(produto1);
                        venda.inserirItem(produto1);
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
        colCodBarras.setCellValueFactory(new PropertyValueFactory<Produto, String>("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory<Produto, String>("nome"));
        colEstoque.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("quantidade"));
        colVenda.setCellValueFactory(new PropertyValueFactory<Produto, Double>("valorVenda"));

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
                            setText(String.format("R$ %.2f", value.doubleValue()));
                        }
                    }
                };
            }
        };
        colVenda.setCellFactory(cellFactory);
        tableProdutos.setItems(listaProdutos);
    }

    public boolean verificaLista(ObservableList<Produto> listaP) {
        for (Produto item1 : listaP) {
            if (item1 instanceof Produto) {
                Produto item = (Produto) item1;
                if (item.getCodigo().equals(txtCodBarras.getText())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean verificaLista(ArrayList<Produto> listaP) {
        for (Produto produtos3 : listaP) {
            if (produtos3 instanceof Produto) {
                Produto produtos = (Produto) produtos3;
                if (produtos.getCodigo().equals(txtCodBarras.getText())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void fPress(KeyEvent event) {
        if (!listaProdutos.isEmpty()) {
            switch (event.getCode()) {
                case F3:
                    pressF3();
                    break;
                case F4:
                    pressF4();
                    break;
                case F5:
                    pressF5();
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

    public void alertaEstoque(Produto item) {
        Alert erroEst = new Alert(Alert.AlertType.ERROR);
        erroEst.setTitle("Estoque Indisponível!");
        erroEst.setHeaderText("Quantidade desejada indisponível no estoque.");
        erroEst.setContentText("Você pode inserir até: " + item.getQuantidade() + " unidades.");
        erroEst.showAndWait();
    }

    public void pressF3() {
        TextInputDialog dialogoRemocao = new TextInputDialog();

        txtCodBarras.requestFocus();

        dialogoRemocao.setTitle("Cancelamento de venda de item");
        dialogoRemocao.setHeaderText("Informe o código de barras do item a ser removido.");
        dialogoRemocao.setContentText("Código de Barras:");
        Optional<String> result = dialogoRemocao.showAndWait();
        String cDb = "none";
        Produto produto1;
        Produto produto;
        if (result.isPresent()) {
            cDb = result.get();
            for (Iterator<Produto> it = listaProdutos.iterator(); it.hasNext(); ) {
                produto1 = it.next();
                if (produto1.getCodigo().equals(cDb)) {
                    if (produto1 instanceof Receitas) {
                        atualizarEstoqueI((Receitas) produto1);
                    }
                    it.remove();
                    for (Iterator<Produto> i = estoqueProdutos.iterator(); i.hasNext(); ) {
                        produto = i.next();
                        if (produto.getCodigo().equals(produto1.getCodigo())) {
                            i.remove();
                            break;
                        }
                    }
                    venda.cancelarProduto(produto1);
                    venda.setValor(venda.getValor() - (produto1.getValorVenda() * produto1.getQuantidade()));
                    if (!listaProdutos.isEmpty()) {

                        Produto produto3 = listaProdutos.get(listaProdutos.size() - 1);
                        lblNomeProduto.setText(listaProdutos.get(listaProdutos.size() - 1).getNome());
                        lblQuantidade.setText(Integer.toString(produto3.getQuantidade()));
                        lblSubtotal.setText(String.format("%.2f", listaProdutos.get(listaProdutos.size() - 1).getValorVenda() * produto3.getQuantidade()));
                        lblTotal.setText(String.format("%.2f", venda.getValor()));
                    } else {
                        statusVenda = "Ocioso";
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

    public void pressF4() {
        Alert cancelarVenda = new Alert(Alert.AlertType.CONFIRMATION);
        cancelarVenda.setTitle("Cancelamento de Venda");
        cancelarVenda.setHeaderText("Você está prestes a cancelar uma venda.");
        cancelarVenda.setContentText("Você tem certeza disso?");

        Optional<ButtonType> result = cancelarVenda.showAndWait();
        if (result.get() == ButtonType.OK) {
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
            statusVenda = "Ocioso";
        }
        Estoque.getInstance();
        txtCodBarras.requestFocus();
    }

    public void pressF5() {
        Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnCartao = new ButtonType("Cartão");
        ButtonType btnDinheiro = new ButtonType("Dinheiro");
        dialogoExe.setTitle("Forma de Pagamento");
        dialogoExe.setHeaderText("Informe a Forma de Pagamento");
        dialogoExe.setContentText("Você irá pagar em dinheiro ou cartão?");

        dialogoExe.getButtonTypes().setAll(btnCartao, btnDinheiro);

        Optional<ButtonType> resultado = dialogoExe.showAndWait();

        if (resultado.get() == btnCartao) {
            venda.setTipoPag('c');
            venda.setPagamento(venda.getValor());
        } else if (resultado.get() == btnDinheiro) {
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

    public static boolean isDoubleString(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public void atualizarEstoqueI(Receitas receitas1) {
        Ingredientes ingredientes1;

        for (Receitas receitas : Estoque.getInstance1().getEstoqueR()) {
            if (receitas.getCodigo().equals(receitas1.getCodigo())) {
                for (Ingredientes ing : receitas.getIngredientes()) {
                    int size = Estoque.getInstance1().getEstoqueI().size();
                    for (int i = 0; i < size; i++) {
                        if (Estoque.getInstance1().getEstoqueI().get(i).getCodigo().equals(ing.getCodigo())) {
                            ingredientes1 = Estoque.getInstance1().getEstoqueI().remove(i);
                            ingredientes1.setPeso(ingredientes1.getPeso() + ing.getPeso() * receitas1.getQuantidade());
                            Estoque.getInstance1().getEstoqueI().add(new Ingredientes(ingredientes1.getNome(), ingredientes1.getPeso(), ingredientes1.getCodigo()));
                            break;
                        }
                    }
                }
                return;
            }
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

    public void setLblQuantidade(String string) {
        this.lblQuantidade.setText(string);
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public String getStatusVenda() {
        return statusVenda;
    }

    public void setStatusVenda(String statusVenda) {
        this.statusVenda = statusVenda;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public TextField getTxtCodBarras() {
        return txtCodBarras;
    }

    public void setTxtCodBarras(TextField txtCodBarras) {
        this.txtCodBarras = txtCodBarras;
    }

    public ArrayList<Produto> getEstoqueProdutos() {
        return estoqueProdutos;
    }

    public void setEstoqueProdutos(ArrayList<Produto> estoqueProdutos) {
        this.estoqueProdutos = estoqueProdutos;
    }
}