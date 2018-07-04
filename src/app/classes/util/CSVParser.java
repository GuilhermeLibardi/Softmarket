package app.classes.util;

import app.classes.Produto;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.ObservableList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVParser {

    public ObservableList<Produto> readEstoque() throws java.io.FileNotFoundException {
        ArrayList<Produto> estoque = new ArrayList<>();
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
        PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (new File(url.getPath()), false)));
        StringBuilder sb = new StringBuilder();
        for (Produto p : estoque) sb.append(p.toCSV());
        pw.print(sb.toString());
        pw.close();
    }
}
