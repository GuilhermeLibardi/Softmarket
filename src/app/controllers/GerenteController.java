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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
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
import javafx.util.Duration;
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
    private MenuItem menuItemRemover, menuItemEditar, menuRemover, menuEditar;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private LineChart<?, ?> graficoLinha;

    private Usuario usuario;

    private ObservableList<Ingredientes> ingredientesReceita = FXCollections.observableArrayList();

    private Estoque e;


    public void changeUser(Usuario user) {
        this.usuario = user;
        this.lblGerente.setText("Gerente " + user.getNome());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        e = Estoque.getInstance();

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
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    Produto r = row.getItem();
                    editProduto(r.getCodigo());
                }
            });
            return row;
        });

        tabelaIngrediente.setRowFactory(tv -> {
            TableRow<Ingredientes> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    Ingredientes i = row.getItem();
                    editIngrediente(i.getCodigo());
                }
            });
            return row;
        });

        listaIngredientes.setItems(ingredientesReceita);

        txtPesquisa.setPromptText("Procure por produtos");

        FilteredList<Receitas> filteredData2 = new FilteredList<>(Estoque.getInstance1().getEstoqueR(), r -> true);
        FilteredList<Ingredientes> filteredData3 = new FilteredList<>(Estoque.getInstance1().getEstoqueI(), i -> true);
        FilteredList<Produto> filteredData = new FilteredList<>(Estoque.getInstance1().getEstoque(), p -> true);

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

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                atualizarGrafico();
                return null;
            }
        };

        graficoLinha.setAnimated(false);
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), event -> task.run()));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
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

    void editIngrediente(String cod) {
        try {
            changeScreen("/app/resources/fxml/telaEditarIngrediente.fxml", cod);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void removeIngrediente(String cod) {
        try {
            changeScreen("/app/resources//fxml/telaRemoverIngrediente.fxml", cod);
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
        } else if (fxml.contains("EditarIngrediente")) {
            EditarIngredienteController ingredienteController = (EditarIngredienteController) loader.getController();
            ingredienteController.setCod(cod);
        } else if (fxml.contains("RemoverIngrediente")) {
            RemoverIngredienteController rmvIngredienteController = (RemoverIngredienteController) loader.getController();
            rmvIngredienteController.setCod(cod);
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

    public void menuEditar(ActionEvent actionEvent) {
        Ingredientes i = tabelaIngrediente.getSelectionModel().getSelectedItem();
        editIngrediente(i.getCodigo());
    }

    public void menuRemover(ActionEvent actionEvent) {
        Ingredientes i = tabelaIngrediente.getSelectionModel().getSelectedItem();
        removeIngrediente(i.getCodigo());
    }
    private void atualizarGrafico() {
        graficoLinha.getData().clear();
        try (Connection con = new ConnectionFactory().getConnection()) {
            XYChart.Series series1 = new XYChart.Series();
            XYChart.Series series2 = new XYChart.Series();
            series1.setName("Lucro mensal");
            series2.setName("Volume de vendas");
            String sql = "select sum(lucro_real * quantidade) as lucro, concat(day(data), '/', month(data)) as datas\n" +
                    "from RELATORIO_VENDAS\n" +
                    "where MONTH(data) = MONTH(now())\n" +
                    "  AND YEAR(data) = YEAR(now())\n" +
                    "group by datas\n" +
                    "order by datas;";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet resultados = stmt.executeQuery();
            while (resultados.next()) {
                String data = resultados.getString("datas");
                double lucro = resultados.getDouble("lucro");
                series1.getData().add(new XYChart.Data(String.valueOf(data), lucro));

            }
            sql = "select concat(day(data), '/', month(data)) as data, sum(quantidade) as volume from RELATORIO_VENDAS where month(data) = '11'\n" +
                    "group by day(data);";
            stmt = con.prepareStatement(sql);
            resultados = stmt.executeQuery();
            while (resultados.next()) {
                String data = resultados.getString("data");
                double volume = resultados.getDouble("volume");
                series2.getData().add(new XYChart.Data(String.valueOf(data), volume));
            }

            graficoLinha.getData().addAll(series1);
            graficoLinha.getData().addAll(series2);
        } catch (SQLException e) {
            System.out.print("Erro ao pegar dados do gráfico");
            System.out.println(e.getMessage());
        }
    }
}
