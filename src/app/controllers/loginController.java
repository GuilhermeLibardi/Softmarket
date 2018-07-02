package app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class loginController {
    @FXML
    Button btnLogin;
    @FXML
    TextField txtUsuario;
    @FXML
    TextField txtSenha;

    public void handleLogin() throws Exception {
        if (txtUsuario.getText().equals("admin") && txtSenha.getText().equals("admin")) {
            changeScreen("../resources/fxml/telaGerente.fxml");
        } else if (txtUsuario.getText().equals("vendas") && txtSenha.getText().equals("vendas2018")) {
            changeScreen("../resources/fxml/telaVendas.fxml");
        } else {
            System.out.println("Login inválido");
        }
    }

    public void handleSubmitSenha() throws Exception{
        handleLogin();
    }

    private void changeScreen(String fxml) throws IOException {
        btnLogin.getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent root = loader.load();

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
    }
}
