package org.example.test;

import org.example.dao.ConnectionFactory;

import java.sql.Connection;

public class TesteConexao {

    public void testeConexao() {
        try (Connection conexao = ConnectionFactory.getConnection()) {
            if(conexao != null){
                System.out.println("Conexao bem-sucedida!");
            } else {
                System.out.println("Conexao retornou nula");
            }
        } catch (Exception e) {
            System.out.println("Erro ao conectar! " + e.getMessage());;
        }
    }
}
