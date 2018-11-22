package app;

import app.classes.Venda;
import app.classes.util.CSVParser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;


public class Main extends Application {
    public static ArrayList<Venda> vendasFechadas = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        try {
            Platform.setImplicitExit(false);
            Parent root = FXMLLoader.load(getClass().getResource("/app/resources/fxml/telaLogin.fxml"));
            primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/app/resources/images/ICONE.png")));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();
            primaryStage.setTitle("Softmarket |  Login");
            primaryStage.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CSVParser parser = new CSVParser();
        try {
            vendasFechadas = parser.readVendas();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        launch(args);
    }

}
