package br.com.softmarket.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost/softmarketdb", "root", "");
                    //"jdbc:mysql://jpaconsultoria.com.br:3306/jpacon92_softmarketdb?useSSL=false", "jpacon92_admin", "softmarket2018");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}