package org.example;

import org.example.dao.ContaDao;
import org.example.dao.UsuarioDao;
import org.example.service.TransfrenciaGerador;

public class Main {



    public static void main(String[] args) {

        ContaDao.exportarDados();
        UsuarioDao.exportarDados();
        TransfrenciaGerador.gerarTransferecia(20);

    }
}