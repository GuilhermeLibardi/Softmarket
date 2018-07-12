package app;

import app.classes.Produto;
import app.classes.Venda;
import app.classes.usuarios.Usuario;
import app.classes.util.CSVParser;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Main extends Application {

    public static ObservableList<Produto> estoqueProdutos;
    public static ArrayList<Usuario> usuariosCadastrados;
    public static ArrayList<Venda> vendasFechadas = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("resources/fxml/telaLogin.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setTitle("Softmarket Login");
            primaryStage.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CSVParser parser = new CSVParser();
        try {
            estoqueProdutos = parser.readEstoque();
            usuariosCadastrados = parser.readUsuarios();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        launch(args);
    }

}
