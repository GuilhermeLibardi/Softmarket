package br.com.softmarket.classes.Main;

import io.sentry.Sentry;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Sentry.init("https://a82469e23ec1407a943055b6d07cbcb7@sentry.io/1504629");

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/telaVendas2.fxml"));
            primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/ICONE.png")));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();
            primaryStage.setFullScreen(true);
            primaryStage.setTitle("Softmarket |  Login");
            primaryStage.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
