package app.controllers;

import app.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class loginController {
    @FXML
    Button btnLogin;
    @FXML
    TextField txtUsuario;
    @FXML
    TextField txtSenha;

    private Main application;

    public void handleLogin() throws Exception{
        btnLogin.getScene().getWindow().hide();
        Stage telaGerente = new Stage();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../resources/fxml/telaGerente.fxml"));
        Parent root = loader.load();

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        Scene scene = new Scene(root);
        telaGerente.setScene(scene);
        telaGerente.show();
        telaGerente.setResizable(false);

        telaGerente.setX(bounds.getMinX());
        telaGerente.setY(bounds.getMinY());
        telaGerente.setWidth(bounds.getWidth());
        telaGerente.setHeight(bounds.getHeight());
    }
}
