package br.com.softmarket.classes.Main;

import br.com.softmarket.dao.ApiController;
import io.sentry.Sentry;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.UnknownHostException;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Platform.setImplicitExit(false);
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/telaLogin.fxml"));
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

    public static void main(String[] args) {
        try{
            if(ApiController.pingServidor()){
                Sentry.init("https://a82469e23ec1407a943055b6d07cbcb7@sentry.io/1504629");
            }
        }catch (Exception e){
        System.out.println("Não foi possível conectar ao Sentry");
        }
        launch();
    }
}
