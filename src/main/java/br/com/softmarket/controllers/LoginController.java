package br.com.softmarket.controllers;

import br.com.softmarket.classes.Main.Main;
import br.com.softmarket.classes.RH.Funcionario;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    Button btnLogin, btnFechar;
    @FXML
    TextField txtUsuario;
    @FXML
    TextField txtSenha;
    @FXML
    Label lblData;

    public static VendasController vc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        java.util.Calendar c = Calendar.getInstance();
        lblData.setText(String.format("Data: %tc", new Date()));
    }

    @FXML
    public void handleLogin() throws java.io.IOException {
        Funcionario funcionario = new Funcionario(txtUsuario.getText(),txtSenha.getText());

        if(funcionario.verificaLogin()){
            changeScreen("/fxml/telaVendas.fxml", funcionario);
        }
    }

    @FXML
    public void handleSubmitSenha() throws Exception {
        handleLogin();
    }

    @FXML
    public void fecharPrograma(){
        Stage stage = (Stage) btnFechar.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    @FXML
    public void handleEnterUsuario() {
        txtSenha.requestFocus();
    }

    private void changeScreen(String fxml, Funcionario usuario) throws IOException {
        btnLogin.getScene().getWindow().hide();
        Stage stage = new Stage();
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/ICONE.png")));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent root = loader.load();

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.show();
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.setTitle("Softmarket | Módulo Vendedor");
        VendasController controladorVendas = (VendasController) loader.getController();
        vc = controladorVendas;
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
    }
}
