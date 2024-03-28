package org.example.service;

import org.example.connection.ConnectionDb;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TransfrenciaGerador {

    public static void gerarTransferecia(int quantidade) {

        String querry = "SELECT C.PK_ID_CONTA,C.NR_NUMERO_CONTA,C.NR_AGENCIA,U.NM_CPF FROM TB_CONTA C " +
                "JOIN TB_USUARIO U ON C.FK_ID_USUARIO = U.PK_ID_USUARIO";

        try(Connection connection = ConnectionDb.abrirConexao();
            Statement statement = connection.createStatement();
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\-Git-Project\\GeradorTransacoes\\Transacoes.txt",false))) {

            ResultSet rsConta = statement.executeQuery(querry);

            List<Long> idContas = new ArrayList<>();
            List<Integer> agencias = new ArrayList<>();
            List<Integer> numerosContas = new ArrayList<>();
            List<String> cpfs = new ArrayList<>();

            while (rsConta.next()) {
                idContas.add(rsConta.getLong("PK_ID_CONTA"));
                agencias.add(rsConta.getInt("NR_AGENCIA"));
                numerosContas.add(rsConta.getInt("NR_NUMERO_CONTA"));
                cpfs.add(rsConta.getString("NM_CPF"));
            }

            writer.write("");
            Random random = new Random();
            for (int i = 0; i < quantidade; i++) {

                int vetorOrigem = random.nextInt(idContas.size());
                int vetorDestino = random.nextInt(idContas.size());

                long idContaOrigem = idContas.get(vetorOrigem);
                long idContaDestino = idContas.get(vetorDestino);

                while (idContaOrigem == idContaDestino) {

                    vetorDestino = random.nextInt(idContas.size());
                    idContaDestino = idContas.get(vetorDestino);
                    System.out.println("precisou mudar");
                }

                int agenciaOrigem = agencias.get(vetorOrigem);
                int agenciaDestino = agencias.get(vetorDestino);

                int numeroContaOrigem = numerosContas.get(vetorOrigem);
                int numeroContaDestino = numerosContas.get(vetorDestino);

                String cpfOrigem = cpfs.get(vetorOrigem);

                BigDecimal valor = BigDecimal.valueOf(random.nextDouble() * 490 + 10).setScale(2, BigDecimal.ROUND_HALF_UP);

                LocalDateTime date = LocalDateTime.now().minusDays(random.nextInt(30));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                String dataFormatada = date.format(formatter);

               // String tipo = "TED";

                char entradaSaida = random.nextBoolean() ? 'D' : 'C';

                while(!verificarSaldo(idContaOrigem, valor) && entradaSaida == 'C') {
                    valor = BigDecimal.valueOf(random.nextDouble() * 490 + 10).setScale(2, BigDecimal.ROUND_HALF_UP);
                    System.out.println("precisou mudar saldo");
                }


                String valorFormatado = String.format("%09.2f", valor);
                valorFormatado = valorFormatado.replace(",", "");

                writer.write(cpfOrigem +""+ agenciaOrigem +""+ numeroContaOrigem +""+ agenciaDestino +""+ numeroContaDestino +""+ valorFormatado +""+ dataFormatada +""+ entradaSaida);
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

    private static boolean verificarSaldo(long idContaOrigem, BigDecimal valor) {

        String querry = "SELECT NR_SALDO FROM TB_CONTA WHERE PK_ID_CONTA = " + idContaOrigem;

        try(Connection connection = ConnectionDb.abrirConexao();
            Statement statement = connection.createStatement()) {

            ResultSet rsSaldo = statement.executeQuery(querry);

            if(rsSaldo.next()) {
                BigDecimal saldo = rsSaldo.getBigDecimal("NR_SALDO");

                if (saldo.compareTo(valor) >= 0 && valor.compareTo(BigDecimal.ZERO) > 00) {
                    return true;
                }

            }else{
                throw new RuntimeException("Conta não encontrada");
            }


        } catch (SQLException e) {
            throw new RuntimeException("Erro JDBC: "+e.getMessage());
        }
        return false;
    }
}
