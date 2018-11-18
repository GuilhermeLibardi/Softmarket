package app.classes.usuarios;

import app.dao.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
    private String nome;
    private String login;
    private String senha;

    Usuario(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String verificaLogin(){
        try (Connection con = new ConnectionFactory().getConnection()){
            String sql = "SELECT * FROM softmarketdb.usuarios WHERE login = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, this.login);
            ResultSet resultados = stmt.executeQuery();

            while(resultados.next()){
                String senha = resultados.getString("senha");
                String tipo = resultados.getString("tipo");
                this.nome = resultados.getString("nome");

                if(senha.equals(this.senha)) return tipo;
            }
            return "";


        } catch (SQLException e) {
            System.out.print("Erro ao preparar STMT: ");
            System.out.println(e.getMessage());
        }
        return "";
    }
}
