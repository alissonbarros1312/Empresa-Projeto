package org.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = "jdbc:mysql://localhost:3306/empresaDB";
    private static final String USUARIO = "root";
    private static final String SENHA = "140420.Ba";

    public static Connection getConnection(){
        try{
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e){
            throw new RuntimeException("Erro ao getConnection ao banco de dados. " + e.getMessage());
        }
    }
}
