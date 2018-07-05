package app.controllers;

import app.Main;
import app.classes.usuarios.Gerente;
import app.classes.usuarios.Usuario;
import app.classes.usuarios.Vendedor;
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

public class LoginController {
    @FXML
    Button btnLogin;
    @FXML
    TextField txtUsuario;
    @FXML
    TextField txtSenha;

    public void handleLogin() throws Exception {
        for (Usuario user : Main.usuariosCadastrados) {
            if (txtUsuario.getText().equals(user.getLogin()) && txtSenha.getText().equals(user.getSenha())) {
                if (user instanceof Gerente) changeScreen("../resources/fxml/telaGerente.fxml");
                else if (user instanceof Vendedor) changeScreen("../resources/fxml/telaVendas.fxml");
            }
        }
    }

    public void handleSubmitSenha() throws Exception {
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
