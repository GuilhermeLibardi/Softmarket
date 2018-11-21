package app.controllers;

import app.Main;
import app.classes.Estoque;
import app.classes.Ingredientes;
import app.classes.Produto;
import app.classes.Receitas;
import app.classes.relatorios.RelatorioAnualVendas;
import app.classes.relatorios.RelatorioDiarioVendas;
import app.classes.relatorios.RelatorioItensMaisVendidos;
import app.classes.relatorios.RelatorioMensalVendas;
import app.classes.usuarios.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GerenteController implements Initializable {

    @FXML
    private Button btnGerenciarEstoque, btnGerarRelatorios, btnRelatorio,
            btnAddProd, btnEditProd, btnRemoveProd, btnAddProd1, btnEditProd1,
            btnRemoveProd1, btnAddProduto11, btnEditProd11, btnRemoveProd11;

    @FXML
    private Label lblGerente, periodoR, deL, aL;

    @FXML
    private TextField txtDataInicial, txtDataFinal, txtPesquisa, txtPesquisa1, txtPesquisa11;

    @FXML
    private ComboBox comboTipo;

    @FXML
    private TableView<Produto> tabelaProdutos;

    @FXML
    private TableView<Receitas> tabelaReceitas;

    @FXML
    private TableView<Ingredientes> tabelaIngrediente;

    @FXML
    private Pane painelEstoque, painelRelatorios, painelReceitas, painelIngredientes;

    @FXML
    private TableColumn<Produto, String> colCodBarras, colNome, colPesavel;

    @FXML
    private TableColumn<Ingredientes, String> colCodI, colNomeI;

    @FXML
    private TableColumn<Ingredientes, Double> colPesoI;

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
                "Relatório diário de vendas", "Relatório mensal de vendas", "Relatório de itens mais vendidos"
        );
        this.comboTipo.setItems(options);

        colCodBarras1.setCellValueFactory(new PropertyValueFactory<Receitas, String>("codigo"));
        colNome1.setCellValueFactory(new PropertyValueFactory<Receitas, String>("nome"));
        colCusto1.setCellValueFactory(new PropertyValueFactory<Receitas, Double>("valorCusto"));
        colVenda1.setCellValueFactory(new PropertyValueFactory<Receitas, Double>("valorVenda"));

        colCodI.setCellValueFactory(new PropertyValueFactory<Ingredientes, String>("codigo"));
        colNomeI.setCellValueFactory(new PropertyValueFactory<Ingredientes, String>("nome"));
        colPesoI.setCellValueFactory(new PropertyValueFactory<Ingredientes, Double>("peso"));

        colCodBarras.setCellValueFactory(new PropertyValueFactory<Produto, String>("codigo"));
        colNome.setCellValueFactory(new PropertyValueFactory<Produto, String>("nome"));
        colEstoque.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("quantidade"));
        colVenda.setCellValueFactory(new PropertyValueFactory<Produto, Double>("valorVenda"));
        colCompra.setCellValueFactory(new PropertyValueFactory<Produto, Double>("valorCusto"));
        colPeso.setCellValueFactory(new PropertyValueFactory<Produto, Double>("peso"));
        colPesavel.setCellValueFactory(new PropertyValueFactory<Produto, String>("pesavel"));

        txtPesquisa.setPromptText("Procure por produtos");

        FilteredList<Receitas> filteredData2 = new FilteredList<>(Estoque.getInstance().getEstoqueR(), r -> true);
        FilteredList<Ingredientes> filteredData3 = new FilteredList<>(Estoque.getInstance().getEstoqueI(), i -> true);
        FilteredList<Produto> filteredData = new FilteredList<>(Estoque.getInstance().getEstoque(), p -> true);

        txtPesquisa11.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData3.setPredicate(ingredientes -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                return ingredientes.getNome().toLowerCase().contains(lowerCaseFilter);
            });
        });

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
        SortedList<Ingredientes> sortedData3 = new SortedList<>(filteredData3);

        sortedData2.comparatorProperty().bind(tabelaReceitas.comparatorProperty());
        sortedData.comparatorProperty().bind(tabelaProdutos.comparatorProperty());
        sortedData3.comparatorProperty().bind(tabelaIngrediente.comparatorProperty());


        tabelaReceitas.setItems(sortedData2);
        tabelaProdutos.setItems(sortedData);
        tabelaIngrediente.setItems(sortedData3);


        comboTipo.setOnAction(event -> {
            if (comboTipo.getValue().toString().equals("Relatório periódico de vendas")) {
                periodoR.setVisible(true);
                deL.setVisible(true);
                aL.setVisible(true);
                dataFinal.setVisible(true);
                dataInicial.setVisible(true);
            } else {
                periodoR.setVisible(false);
                deL.setVisible(false);
                aL.setVisible(false);
                dataFinal.setVisible(false);
                dataInicial.setVisible(false);
            }
        });
    }


    @FXML
    void gerarRelatorio() {
        Scene cena = lblGerente.getScene();
        cena.setCursor(Cursor.WAIT);
        switch (comboTipo.getValue().toString()) {
            case "Relatório diário de vendas":
                RelatorioDiarioVendas rdv = new RelatorioDiarioVendas();
                try {
                    rdv.gerarRelatorio();
                } catch (JRException e) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Erro ao gerar relatório diário de vendas");
                    alerta.setHeaderText("Erro ao gerar relatório diário de vendas");
                    alerta.setContentText(e.getMessage());
                    alerta.showAndWait();
                }
                break;
            case "Relatório mensal de vendas":
                RelatorioMensalVendas rmv = new RelatorioMensalVendas();
                try {
                    rmv.gerarRelatorio();
                } catch (JRException e) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Erro ao gerar relatório mensal de vendas");
                    alerta.setHeaderText("Erro ao gerar relatório mensal de vendas");
                    alerta.setContentText(e.getMessage());
                    alerta.showAndWait();
                }
                break;
            case "Relatório anual de vendas":
                RelatorioAnualVendas rav = new RelatorioAnualVendas();
                try {
                    rav.gerarRelatorio();
                } catch (JRException e) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Erro ao gerar relatório anual de vendas");
                    alerta.setHeaderText("Erro ao gerar relatório anual de vendas");
                    alerta.setContentText(e.getMessage());
                    alerta.showAndWait();
                }
                break;
            case "Relatório de itens mais vendidos":
                RelatorioItensMaisVendidos riv = new RelatorioItensMaisVendidos();
                try {
                    riv.gerarRelatorio();
                } catch (JRException e) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Erro ao gerar relatório anual de vendas");
                    alerta.setHeaderText("Erro ao gerar relatório anual de vendas");
                    alerta.setContentText(e.getMessage());
                    alerta.showAndWait();
                }
                break;

        }
        cena.setCursor(Cursor.DEFAULT);
    }


    @FXML
    void handleGerenciarEstoque() {
        painelRelatorios.setVisible(false);
        painelEstoque.setVisible(true);
        painelReceitas.setVisible(false);
        painelIngredientes.setVisible(false);
    }

    @FXML
    void handleGerarIngredientes() {
        painelRelatorios.setVisible(false);
        painelEstoque.setVisible(false);
        painelReceitas.setVisible(false);
        painelIngredientes.setVisible(true);
    }

    @FXML
    void handleGerarRelatorios() {
        painelRelatorios.setVisible(true);
        painelEstoque.setVisible(false);
        painelReceitas.setVisible(false);
        painelIngredientes.setVisible(false);
    }

    @FXML
    void handleGerarReceitas() {
        painelRelatorios.setVisible(false);
        painelEstoque.setVisible(false);
        painelReceitas.setVisible(true);
        painelIngredientes.setVisible(false);
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

    @FXML
    void addIngrediente() {
        try {
            changeScreen("../resources/fxml/telaAdicionarIngrediente.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void editIngrediente() {
        try {
            changeScreen("../resources/fxml/telaEditarIngrediente.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void removeIngrediente() {
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
