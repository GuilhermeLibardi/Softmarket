package app.controllers;

import app.Main;
import app.classes.usuarios.Gerente;
import app.classes.usuarios.Usuario;
import app.classes.usuarios.Vendedor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    Button btnLogin;
    @FXML
    TextField txtUsuario;
    @FXML
    TextField txtSenha;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleLogin() throws java.io.IOException {
        Usuario usuario = new Usuario(txtUsuario.getText(), txtSenha.getText());
        switch(usuario.verificaLogin()){
            case "g":
                Gerente g = new Gerente(usuario.getNome(), txtUsuario.getText(), txtSenha.getText());
                changeScreen("../resources/fxml/telaGerente.fxml", g);
                break;
            case "v":
                Vendedor v = new Vendedor(usuario.getNome(), txtUsuario.getText(), txtSenha.getText());
                changeScreen("../resources/fxml/telaVendas.fxml", v);
                break;
            default:
                Alert erroEst = new Alert(Alert.AlertType.ERROR);
                erroEst.setTitle("Erro de login");
                erroEst.setHeaderText("Erro ao tentar logar");
                erroEst.setContentText("Usuário e/ou senha incorretos");
                erroEst.showAndWait();
        }
    }

    @FXML
    public void handleSubmitSenha() throws Exception {
        handleLogin();
    }

    @FXML
    public void handleEnterUsuario() {
        txtSenha.requestFocus();
    }

    private void changeScreen(String fxml, Usuario usuario) throws IOException {
        btnLogin.getScene().getWindow().hide();
        File f = new File("out/production/Softmarket/app/resources/styles/style.css");
        Stage stage = new Stage();
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("resources/images/ICONE.png")));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent root = loader.load();

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().clear();
        scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        stage.show();
        stage.setResizable(true);
        stage.setMaximized(true);
        if (fxml.contains("Gerente")) {
            stage.setTitle("Sistema de gerenciamento");
            GerenteController controladorGerente = (GerenteController) loader.getController();
            controladorGerente.changeUser((Gerente) usuario);
        } else if (fxml.contains("Vendas")) {
            stage.setTitle("Sistema de vendas");
            VendasController controladorVendas = (VendasController) loader.getController();
            controladorVendas.changeUser((Vendedor) usuario);
        }
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
    }
}
