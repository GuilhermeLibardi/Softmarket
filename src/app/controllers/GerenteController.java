package app.controllers;

import app.Main;
import app.classes.Estoque;
import app.classes.Produto;
import app.classes.Receitas;
import app.classes.relatorios.RelatorioDeEstoque;
import app.classes.relatorios.RelatorioDeVendas;
import app.classes.usuarios.Usuario;
import app.classes.util.Periodo;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GerenteController implements Initializable {

    @FXML
    private Button btnGerenciarEstoque, btnGerarRelatorios, btnRelatorio,
            btnAddProd, btnEditProd, btnRemoveProd, btnAddProd1, btnEditProd1, btnRemoveProd1;

    @FXML
    private Label lblGerente, periodoR, deL, aL;

    @FXML
    private TextField txtDataInicial, txtDataFinal, txtPesquisa, txtPesquisa1;

    @FXML
    private ComboBox comboTipo;

    @FXML
    private TableView<Produto> tabelaProdutos;

    @FXML
    private TableView<Receitas> tabelaReceitas;

    @FXML
    private Pane painelEstoque, painelRelatorios, painelReceitas;

    @FXML
    private TableColumn<Produto, String> colCodBarras, colNome, colPesavel ;

    @FXML
    private TableColumn<Receitas, String> colCodBarras1, colNome1;

    @FXML
    private TableColumn<Produto, Double> colCompra, colVenda, colPeso;

    @FXML
    private TableColumn<Receitas, Double> colCusto1, colVenda1;

    @FXML
    private TableColumn<Produto, Integer> colEstoque;

    private Usuario usuario;

    @FXML
    private DatePicker dataInicial, dataFinal;


    public void changeUser(Usuario user) {
        this.usuario = user;
        this.lblGerente.setText("Gerente " + user.getNome());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Relatório de Vendas", "Relatório de Estoque"
        );
        this.comboTipo.setItems(options);

        colCodBarras1.setCellValueFactory(new PropertyValueFactory<Receitas, String>("codigo"));
        colNome1.setCellValueFactory(new PropertyValueFactory<Receitas, String>("nome"));
        colCusto1.setCellValueFactory(new PropertyValueFactory<Receitas, Double>("valorCusto"));
        colVenda1.setCellValueFactory(new PropertyValueFactory<Receitas, Double>("valorVenda"));

        colCodBarras.setCellValueFactory(new PropertyValueFactory<Produto, String>("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory<Produto, String>("nome"));
        colEstoque.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("quantidade"));
        colVenda.setCellValueFactory(new PropertyValueFactory<Produto, Double>("valorVenda"));
        colCompra.setCellValueFactory(new PropertyValueFactory<Produto, Double>("valorCusto"));
        colPeso.setCellValueFactory(new PropertyValueFactory<Produto, Double>("peso"));
        colPesavel.setCellValueFactory(new PropertyValueFactory<Produto, String>("pesavel"));

        txtPesquisa.setPromptText("Procure por produtos");

        FilteredList<Receitas> filteredData2 = new FilteredList<>(Estoque.getInstance().getEstoqueR(),r -> true);
        FilteredList<Produto> filteredData = new FilteredList<>(Estoque.getInstance().getEstoque(),p -> true);

        txtPesquisa1.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData2.setPredicate(receitas -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                return receitas.getNome().toLowerCase().contains(lowerCaseFilter);
            });
        });

        txtPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(produto -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                return produto.getNome().toLowerCase().contains(lowerCaseFilter);
            });
        });


        SortedList<Receitas> sortedData2 = new SortedList<>(filteredData2);
        SortedList<Produto> sortedData = new SortedList<>(filteredData);

        sortedData2.comparatorProperty().bind(tabelaReceitas.comparatorProperty());
        sortedData.comparatorProperty().bind(tabelaProdutos.comparatorProperty());


        tabelaReceitas.setItems(sortedData2);
        tabelaProdutos.setItems(sortedData);


        comboTipo.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                if (comboTipo.getValue().toString().equals("Relatório de Vendas")) {
                    periodoR.setVisible(true);
                    deL.setVisible(true);
                    aL.setVisible(true);
                    dataFinal.setVisible(true);
                    dataInicial.setVisible(true);
                } else if (comboTipo.getValue().toString().equals("Relatório de Estoque")) {
                    periodoR.setVisible(false);
                    deL.setVisible(false);
                    aL.setVisible(false);
                    dataFinal.setVisible(false);
                    dataInicial.setVisible(false);
                }
            }
        });
    }


    @FXML
    void gerarRelatorio() throws IOException {
        if (comboTipo.getValue().toString().equals("Relatório de Vendas")) {
            Periodo p = new Periodo(dataInicial.getValue(), dataFinal.getValue());
            RelatorioDeVendas relVendas = new RelatorioDeVendas(p, Main.vendasFechadas);
            relVendas.gerarRelatorio();
        } else if (comboTipo.getValue().toString().equals("Relatório de Estoque")) {
            RelatorioDeEstoque relatorio = new RelatorioDeEstoque(Estoque.getInstance().getEstoque());
            relatorio.gerarRelatorio();
        }
    }


    @FXML
    void handleGerenciarEstoque() {
        painelRelatorios.setVisible(false);
        painelEstoque.setVisible(true);
        painelReceitas.setVisible(false);
    }

    @FXML
    void handleGerarRelatorios() {
        painelRelatorios.setVisible(true);
        painelEstoque.setVisible(false);
        painelReceitas.setVisible(false);
    }

    @FXML
    void handleGerarReceitas() {
        painelRelatorios.setVisible(false);
        painelEstoque.setVisible(false);
        painelReceitas.setVisible(true);
    }

    @FXML
    void addProduto() {
        try {
            changeScreen("../resources/fxml/telaAdicionarProduto.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void editProduto() {
        try {
            changeScreen("../resources/fxml/telaEditarProduto.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void removeProduto() {
        try {
            changeScreen("../resources/fxml/telaRemoverProduto.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addReceita() {
        try {
            changeScreen("../resources/fxml/telaAdicionarProduto.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void editReceita() {
        try {
            changeScreen("../resources/fxml/telaEditarProduto.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void removeReceita() {
        try {
            changeScreen("../resources/fxml/telaRemoverProduto.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void changeScreen(String fxml) throws IOException {
        Stage stage = new Stage();

        stage.getIcons().add(new Image(Main.class.getResourceAsStream("resources/images/ICONE.png")));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

}
