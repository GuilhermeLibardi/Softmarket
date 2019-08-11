package br.com.softmarket.controllers;

import br.com.softmarket.classes.Main.Main;
import br.com.softmarket.classes.PDV.Caixa;
import br.com.softmarket.classes.RH.Funcionario;
import br.com.softmarket.dao.ApiController;
import com.google.gson.Gson;
import io.sentry.Sentry;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
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
       try{
           Caixa caixa = ApiController.verificaLogin(funcionario);
           System.out.println(caixa.toString());
           if(new Gson().toJson(caixa).equals("{}")){
               Optional<String> result = criarDialog("Abrir Caixa", "Adicionar valor inicial", "Valor inicial:").showAndWait();
               if(result.isPresent()){
                   if(result.get().matches("([0-9])*(.([0-9]){2})*")){
                       Main.caixa = caixa.AbrirCaixa(Double.parseDouble(result.get()));
                       changeScreen("/fxml/telaVendas.fxml", funcionario);
                   }
               }
           }else{
               Main.caixa = caixa;
               changeScreen("/fxml/telaVendas.fxml", funcionario);
           }
       }catch (Exception e){
           Sentry.capture(e);
       }
    }

    private TextInputDialog criarDialog(String title, String header, String content){
        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle(title);
        dialogo.setHeaderText(header);
        dialogo.setContentText(content);

        return dialogo;
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
