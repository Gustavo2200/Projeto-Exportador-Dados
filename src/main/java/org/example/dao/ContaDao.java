package org.example.dao;

import org.example.connection.ConnectionDb;
import org.example.model.Conta;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContaDao {
    private BufferedWriter writer;

    public static void exportarDados() {

        try (Connection connection = ConnectionDb.abrirConexao();
             Statement statement = connection.createStatement();
             BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\-Git-Project\\GeradorTransacoes\\Contas.txt",false))) {

            writer.write("");

            ResultSet rs = statement.executeQuery("SELECT * FROM TB_CONTA");

            while (rs.next()) {

                Conta conta = new Conta();
                conta.setIdConta(rs.getLong("PK_ID_CONTA"));
                conta.setIdUsuario(rs.getLong("FK_ID_USUARIO"));
                conta.setAgencia(rs.getInt("NR_AGENCIA"));
                conta.setNumeroConta(rs.getInt("NR_NUMERO_CONTA"));
                conta.setSaldo(rs.getBigDecimal("NR_SALDO"));
                conta.setLimiteCredito(rs.getBigDecimal("NR_LIMITE_CREDITO"));

                writer.write(conta.getIdConta()+","+conta.getIdUsuario()+","+
                        conta.getAgencia()+","+conta.getNumeroConta()+","+conta.getSaldo()+
                        ","+conta.getLimiteCredito());
                writer.newLine();
            }
            writer.flush();

        }catch (SQLException e) {
            throw new RuntimeException("Erro JDBC: "+e.getMessage());
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("Erro Arquivo: "+ex.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Erro IO: "+e.getMessage());
        }
    }
}
