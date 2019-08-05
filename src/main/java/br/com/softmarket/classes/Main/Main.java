package br.com.softmarket.classes.Main;

import io.sentry.Sentry;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Platform.setImplicitExit(false);
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/telaVendas.fxml"));
            primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/ICONE.png")));
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
}
