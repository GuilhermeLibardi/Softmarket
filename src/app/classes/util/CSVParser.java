package app.classes.util;

import app.classes.Produto;
import app.classes.Venda;
import app.classes.usuarios.Gerente;
import app.classes.usuarios.Usuario;
import app.classes.usuarios.Vendedor;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVParser {

    public ObservableList<Produto> readEstoque() throws java.io.FileNotFoundException {
        ArrayList<Produto> estoque = new ArrayList<>(50);
        URL url = getClass().getResource("../../resources/data/estoque.csv");
        Scanner scanner = new Scanner(new File(url.getPath()));
        while (scanner.hasNextLine()) {
            String[] info = scanner.nextLine().split(",");
            estoque.add(new Produto(info[1], Integer.parseInt(info[4]), Double.parseDouble(info[2]), Double.parseDouble(info[3]), info[0]));
        }
        scanner.close();
        ObservableList observableEstoque = new ObservableListWrapper(estoque);
        return observableEstoque;
    }

    public void writeEstoque(ObservableList<Produto> estoque) throws Exception {
        URL url = getClass().getResource("../../resources/data/estoque.csv");
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(url.getPath()), false)));
        StringBuilder sb = new StringBuilder();
        for (Produto p : estoque) sb.append(p.toCSV());
        pw.print(sb.toString());
        pw.close();
    }

    public ArrayList<Usuario> readUsuarios() throws FileNotFoundException {
        ArrayList<Usuario> usuarios = new ArrayList<>(5);
        URL url = getClass().getResource("../../resources/data/usuarios.csv");
        Scanner scanner = new Scanner(new File(url.getPath()));
        while (scanner.hasNextLine()) {
            String[] info = scanner.nextLine().split(",");
            switch(info[0]){
                case "g":
                    usuarios.add(new Gerente(info[1], info[2], info[3]));
                    break;
                case "v":
                    usuarios.add(new Vendedor(info[1], info[2], info[3]));
                    break;
            }
        }
        scanner.close();
        return usuarios;
    }

    public void writeVenda(ObservableList<Venda> vendas) throws Exception {
        URL url = getClass().getResource("../../resources/data/vendas.csv");
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(url.getPath()), false)));
        StringBuilder sb = new StringBuilder();
        for (Venda v : vendas) sb.append(v.toCSV());
        pw.print(sb.toString());
        pw.close();
    }

}
