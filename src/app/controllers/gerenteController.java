package app.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class gerenteController {

    @FXML
    private Button btnGerenciarEstoque;

    @FXML
    private Button btnGerarRelatorios;

    @FXML
    private Pane painelEstoque;

    @FXML
    private TextField txtDataInicial;

    @FXML
    private TextField txtDataFinal;

    @FXML
    private SplitMenuButton dropTipoRelatorio;

    @FXML
    private Button btnRelatorio;

    @FXML
    private Pane painelRelatórios;

    @FXML
    private TableView<?> tabelaProdutos;

    @FXML
    private TextField txtPesquisa;

    @FXML
    private Pane painelBemvindo;

    @FXML
    void gerarRelatorio(ActionEvent event) {

    }

    @FXML
    void updateTable(KeyEvent event) {

    }

}
