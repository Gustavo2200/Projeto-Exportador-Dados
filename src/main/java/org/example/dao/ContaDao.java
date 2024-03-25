package org.example.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.example.model.Conta;

public class ContaDao{


	  try {
          FileWriter fileWriter = new FileWriter("C:\\Users\\ypiovarczik\\Downloads\\teste\\teste.txt", true);
          BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
          bufferedWriter.newLine();
          bufferedWriter.write(conta.getNome() + "," + conta.getIdade());
          bufferedWriter.flush();
          bufferedWriter.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
}
