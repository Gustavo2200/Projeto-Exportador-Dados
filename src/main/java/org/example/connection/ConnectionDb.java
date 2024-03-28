package org.example.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDb {

    private static final String URL = "jdbc:h2:file:C:\\-Git-Project\\BancoEquipe1\\BancoDigitalDB";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection abrirConexao() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao abrir conexão", e);
        }
    }
}
