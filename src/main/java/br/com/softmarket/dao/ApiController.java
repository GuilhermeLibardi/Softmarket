package br.com.softmarket.dao;

import br.com.softmarket.classes.PDV.Venda;
import br.com.softmarket.classes.Producao.Produto;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import io.sentry.Sentry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.net.UnknownHostException;

public class ApiController {
    public static ObservableList<Produto> index() throws Exception {
        Client client = new Client();
        WebResource webResource = client.resource("http://35.199.115.8/api/produtos");
        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new Exception("Não há conexão com a internet");
        }
        String output = response.getEntity(String.class);
        return FXCollections.observableArrayList(new Gson().fromJson(output, Produto[].class));
    }

    public static ObservableList<Produto> fecharVenda(Venda venda) throws Exception {
        Client client = new Client();
        WebResource webResource = client.resource("http://35.199.115.8/api/produtos");
        MultivaluedMap formData = new MultivaluedMapImpl();
        formData.add("json", new Gson().toJson(venda));
        ClientResponse response = webResource
                .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .post(ClientResponse.class, formData);
        if(response.getStatus() != 200) throw new Exception("Requisição rejeitada ao fechar venda");

        String output = response.getEntity(String.class);
        return FXCollections.observableArrayList(new Gson().fromJson(output, Produto[].class));
    }

    public static boolean pingServidor() throws UnknownHostException {
        Client client = new Client();
        WebResource webResource = client.resource("http://35.199.115.8/api/produtos");
        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);
        return response.getStatus() == 200;
    }


}
