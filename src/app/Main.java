package app;

import app.classes.Produto;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static ObservableList<Produto> estoqueProdutos = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("resources/fxml/telaLogin.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setTitle("Login marketsoft");
            primaryStage.setResizable(false);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args)
    {
        Main.estoqueProdutos.add(new Produto("Chapolim", 1, 1, 1.2, "24"));
        Main.estoqueProdutos.add(new Produto("Coca-cola", 3, 5.5, 6.5, "7891236313182"));
        launch(args);

    }

}
