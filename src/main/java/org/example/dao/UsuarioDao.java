package org.example.dao;

import org.example.connection.ConnectionDb;
import org.example.model.Usuario;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDao {

    public static void exportarDados() {

        try (Connection connection = ConnectionDb.abrirConexao();
             Statement statement = connection.createStatement();
             BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\-Git-Project\\GeradorTransacoes\\Usuarios.txt",false))) {

            writer.write("");

            ResultSet rs= statement.executeQuery("SELECT * FROM TB_USUARIO");

            while (rs.next()){

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getLong("PK_ID_USUARIO"));
                usuario.setNome(rs.getString("NM_NOME"));
                usuario.setSenha(rs.getString("NM_SENHA"));
                usuario.setCpf(rs.getString("NM_CPF"));
                usuario.setEmail(rs.getString("NM_EMAIL"));
                usuario.setDataNascimento(rs.getDate("DT_DATA_NASCIMENTO").toLocalDate());

                writer.write(usuario.getIdUsuario()+","+usuario.getNome()+","+
                        usuario.getSenha()+","+usuario.getCpf()+","+usuario.getEmail()+
                        ","+usuario.getDataNascimento());
                writer.newLine();
            }
            writer.flush();

        } catch (SQLException e) {
            throw new RuntimeException("Erro JDBC: "+e.getMessage());
        } catch (FileNotFoundException ex) {
            throw new RuntimeException( "Erro Arquivo: "+ex.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Erro IO: "+e.getMessage());
        }
    }
}
