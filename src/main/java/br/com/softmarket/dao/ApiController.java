package br.com.softmarket.dao;

import br.com.softmarket.classes.PDV.Caixa;
import br.com.softmarket.classes.PDV.Venda;
import br.com.softmarket.classes.Producao.Produto;
import br.com.softmarket.classes.RH.Funcionario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.net.UnknownHostException;

public class ApiController {
    private static TokenApi token;

    public static ObservableList<Produto> index() throws Exception {
        Client client = new Client();
        WebResource webResource = client.resource("http://35.199.115.8/api/produtos");
        ClientResponse response = webResource.accept("application/json")
                .header("Authorization", "Bearer " + token.getToken())
                .get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new Exception("Não foi possivel receber os produtos.");
        }
        String output = response.getEntity(String.class);
        return FXCollections.observableArrayList(new Gson().fromJson(output, Produto[].class));
    }

    public static void fecharVenda(Venda venda) throws Exception {
        Client client = new Client();
        WebResource webResource = client.resource("http://35.199.115.8/api/venda");
        MultivaluedMap formData = new MultivaluedMapImpl();
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
                .setPrettyPrinting().create();
        formData.add("json", gson.toJson(venda));
        System.out.println(gson.toJsonTree(venda));
        ClientResponse response = webResource
                .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .header("Authorization", "Bearer " + token.getToken())
                .post(ClientResponse.class, formData);
        if(response.getStatus() != 200) throw new Exception("Requisição rejeitada ao fechar venda");

        String output = response.getEntity(String.class);
        System.out.println(output);
    }

    public static Caixa verificaLogin(Funcionario funcionario) throws Exception{
        Client client = new Client();
        WebResource webResource = client.resource("http://35.199.115.8/api/login");
        ClientResponse response = webResource.accept("application/json").type("application/json").post(ClientResponse.class, new Gson().toJson(funcionario));

        if(response.getStatus() != 200) throw new Exception("Requisição rejeitada efetuar login");

        String output = response.getEntity(String.class);
        Gson gson = new Gson();
        token = gson.fromJson(output, TokenApi.class);
        return token.getCaixa();
    }

    public static Caixa abrirCaixa(Double montanteInicial) throws Exception {
        Client client = new Client();
        WebResource webResource = client.resource("http://35.199.115.8/api/caixa/novo");
        ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .header("Authorization", "Bearer " + token.getToken())
                .post(ClientResponse.class,"montanteInicial="+ new Gson().toJson(montanteInicial));
        System.out.println(new Gson().toJson(montanteInicial));

        if(response.getStatus() != 200) throw new Exception("Requisição de caixa falhou.");

        String output = response.getEntity(String.class);
        System.out.println(output);
        return new Gson().fromJson(output,Caixa.class);
    }

    public static void fecharCaixa (Double montanteAtual) throws Exception {
        Client client = new Client();
        WebResource webResource = client.resource("http://35.199.115.8/api/caixa/fechar");
        ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .header("Authorization", "Bearer " + token.getToken())
                .post(ClientResponse.class,"montanteAtual="+ new Gson().toJson(montanteAtual));

        if(response.getStatus() != 200) throw new Exception("Requisição de caixa falhou.");

        String output = response.getEntity(String.class);
        System.out.println(output);
    }

    public static boolean pingServidor() throws UnknownHostException {
        Client client = new Client();
        WebResource webResource = client.resource("http://35.199.115.8/api/produtos");
        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);
        return response.getStatus() == 200;
    }


}
