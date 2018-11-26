package app.controllers;

import app.classes.Estoque;
import app.classes.Ingredientes;
import app.classes.Receitas;
import app.dao.ReceitaContemIngredientes;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.util.ResourceBundle;


public class AdicionarReceitaController implements Initializable {

    @FXML
    private TextField txtCodBarras, txtNome, txtPdc, txtPdv;

    @FXML
    private Label lblTitulo, lblPdv, lblPdc, lblNome, lblCod;

    @FXML
    private ListView<Ingredientes> listaIngredientes;

    @FXML
    private TableView<Ingredientes> tableIngredientes;

    @FXML
    private TableColumn<Ingredientes, String> columnNome;

    @FXML
    private TableColumn<Ingredientes, Double> columnPeso;

    @FXML
    private Button btnCadastrar;

    private int estado;

    private ObservableList<Ingredientes> ingredientesEstoque, ingredientesEscolhidos;

    private String codBarras, nome;

    private double pCusto, pVenda;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ingredientesEstoque = Estoque.getInstance().getEstoqueI();
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
        listaIngredientes.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                ingredientesEscolhidos = listaIngredientes.getSelectionModel().getSelectedItems();
            }

        });
        listaIngredientes.setItems(ingredientesEstoque);
        listaIngredientes.setEditable(false);
        listaIngredientes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        columnNome.setCellValueFactory(new PropertyValueFactory<Ingredientes, String>("nome"));
        columnPeso.setCellValueFactory(new PropertyValueFactory<Ingredientes, Double>("peso"));

        tableIngredientes.setEditable(true);
        columnPeso.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        estado = 0;
    }

    @FXML
    void submit() {
        switch (estado) {
            case 0:
                //Pega os valores
                codBarras = txtCodBarras.getText();
                nome = txtNome.getText();
                pCusto = Double.parseDouble(txtPdc.getText());
                pVenda = Double.parseDouble(txtPdv.getText());

                //Deixa os itens atuais invisiveis
                txtCodBarras.setVisible(false);
                txtNome.setVisible(false);
                txtPdc.setVisible(false);
                txtPdv.setVisible(false);

                lblPdv.setVisible(false);
                lblPdc.setVisible(false);
                lblNome.setVisible(false);
                lblCod.setVisible(false);
                //Deixa os itens da proxima tela visiveis
                listaIngredientes.setVisible(true);

                //Altera o nome da tela
                lblTitulo.setText("Ingredientes");

                //Incrementa o estado
                estado++;
                break;
            case 1:
                //Pega os valores
                Ingredientes ing;
                for (Ingredientes ingredienteEstoque : ingredientesEstoque) {
                    for (Ingredientes ingredientesEscolhido : ingredientesEscolhidos) {
                        if (ingredientesEscolhido.getNome().equals(ingredienteEstoque.getNome())) {
                            ingredientesEscolhido.setPeso(0.0);
                            ingredientesEscolhido.setCodigo(ingredienteEstoque.getCodigo());
                        }
                    }
                }


                FilteredList<Ingredientes> filtered = new FilteredList<>(ingredientesEscolhidos, i -> true);
                SortedList<Ingredientes> sorted = new SortedList<>(filtered);
                tableIngredientes.setItems(sorted);
                //Deixa os itens atuais invisiveis
                this.listaIngredientes.setVisible(false);
                //Deixa os itens da proxima tela visiveis
                this.tableIngredientes.setVisible(true);
                //Altera o nome da tela
                lblTitulo.setText("Quantidades");
                btnCadastrar.setText("Concluir");
                //Incrementa o estado
                estado++;
                break;
            case 2:
                //Insere os valores no banco de dados
                Receitas r = new Receitas(this.nome, this.pCusto, this.pVenda, this.codBarras);
                Estoque.getInstance1().adicionarReceita(r);

                ReceitaContemIngredientes rci = new ReceitaContemIngredientes();
                rci.setCodReceita(this.codBarras);
                for(Ingredientes i : tableIngredientes.getItems()){
                    rci.setCodIngrediente(i.getCodigo());
                    rci.setPeso(i.getPeso());
                    rci.Inserir();
                }

                //Fecha janela
                Stage a = (Stage) btnCadastrar.getScene().getWindow();
                a.close();
                break;
            default:

        }
    }

    public void editPeso(TableColumn.CellEditEvent<Ingredientes, Double> ingredientesDoubleCellEditEvent) {
        Ingredientes ingredientes = tableIngredientes.getSelectionModel().getSelectedItem();
        ingredientes.setPeso(ingredientesDoubleCellEditEvent.getNewValue());
    }
}
