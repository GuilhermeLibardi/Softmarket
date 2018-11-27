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
import app.dao.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private TableColumn<Produto, String> colCodBarras, colNome;

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

    @FXML
    private ListView<Ingredientes> listaIngredientes;

    @FXML
    private DatePicker dataInicial, dataFinal;

    @FXML
    private ContextMenu contextMenu;

    @FXML
    private MenuItem menuItemRemover, menuItemEditar;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private LineChart<?, ?> graficoLinha;

    private Usuario usuario;

    private ObservableList<Ingredientes> ingredientesReceita = FXCollections.observableArrayList();


    public void changeUser(Usuario user) {
        this.usuario = user;
        this.lblGerente.setText("Gerente " + user.getNome());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try (Connection con = new ConnectionFactory().getConnection()) {
            XYChart.Series series = new XYChart.Series();
            series.setName("Lucro mensal");
            String sql = "select DAY(data) as dia, sum(lucro_real) as lucro from relatorio_vendas where MONTH(data) = MONTH(curdate())\n" +
                    "  AND YEAR(data) = YEAR(curdate()) group by DAY(data);";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet resultados = stmt.executeQuery();
            while (resultados.next()) {
                int dia = resultados.getInt("dia");
                double lucro = resultados.getDouble("lucro");
                series.getData().add(new XYChart.Data(String.valueOf(dia), lucro));
            }
            graficoLinha.getData().addAll(series);
        } catch (SQLException e) {
            System.out.print("Erro ao pegar dados do gráfico");
            System.out.println(e.getMessage());
        }


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

        listaIngredientes.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Ingredientes item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getNome() == null) {
                    setText(null);
                } else {
                    setText(item.getNome());
                }
            }
        });

        tabelaReceitas.setRowFactory(tv -> {
            TableRow<Receitas> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    Receitas r = row.getItem();
                    ingredientesReceita.clear();
                    ingredientesReceita.addAll(r.getIngredientes());
                }
            });
            return row;
        });

        tabelaProdutos.setRowFactory(tv -> {
            TableRow<Produto> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    Produto r = row.getItem();
                    editProduto(r.getCodigo());
                }
            });
            return row;
        });

        listaIngredientes.setItems(ingredientesReceita);

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
            changeScreen("/app/resources/fxml/telaAdicionarProduto.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void editProduto() {
        try {
            changeScreen("/app/resources/fxml/telaEditarProduto.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void editProduto(String cod) {
        try {
            changeScreen("/app/resources/fxml/telaEditarProduto.fxml", cod);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void removeProduto() {
        try {
            changeScreen("/app/resources/fxml/telaRemoverProduto.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void removeProduto(String cod) {
        try {
            changeScreen("/app/resources/fxml/telaRemoverProduto.fxml", cod);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addReceita() {
        try {
            changeScreen("/app/resources/fxml/telaAdicionarReceita.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void editReceita() {
        try {
            changeScreen("/app/resources/fxml/telaEditarReceita.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void removeReceita() {
        try {
            changeScreen("/app/resources/fxml/telaRemoverReceita.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addIngrediente() {
        try {
            changeScreen("/app/resources/fxml/telaAdicionarIngrediente.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void editIngrediente() {
        try {
            changeScreen("/app/resources/fxml/telaEditarIngrediente.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void removeIngrediente() {
        try {
            changeScreen("/app/resources//fxml/telaRemoverIngrediente.fxml");
        } catch (IOException e) {
            e.printStackTrace();
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

    private void changeScreen(String fxml, String cod) throws IOException {
        Stage stage = new Stage();

        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/app/resources/images/ICONE.png")));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent root = loader.load();

        if (fxml.contains("EditarProduto")) {
            EditarProdutoController produtoController = (EditarProdutoController) loader.getController();
            produtoController.setCod(cod);
        } else if (fxml.contains("RemoverProduto")) {
            RemoverProdutoController rmvProdutoController = (RemoverProdutoController) loader.getController();
            rmvProdutoController.setCod(cod);
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public void handleEditarMI(ActionEvent actionEvent) {
        Produto r = tabelaProdutos.getSelectionModel().getSelectedItem();
        editProduto(r.getCodigo());
    }

    public void handleRemoverMI(ActionEvent actionEvent) {
        Produto r = tabelaProdutos.getSelectionModel().getSelectedItem();
        removeProduto(r.getCodigo());
    }

}
