package org.example.test;

import org.example.dao.ConnectionFactory;
import org.example.dao.FuncionarioDAO;
import org.example.model.FuncionarioModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TesteFuncionarioDao {
    public void testeFuncionario(){
        FuncionarioDAO funcionarioDao = new FuncionarioDAO(ConnectionFactory.getConnection());

        // teste inserir
        FuncionarioModel novoFuncionario = new FuncionarioModel("TesteNome", "12345678910", "Secretaria", 2500 );
        funcionarioDao.inserir(novoFuncionario);
        System.out.println("Inserido com sucesso");
        System.out.println();

        // teste listar todos
        System.out.println("---- Listar Todos ----");
        for(FuncionarioModel f : funcionarioDao.listarTodos()){
            System.out.println("ID: " + f.getId() + ", Nome: " + f.getNome());
            System.out.println();
        }

        // teste buscar por id
        int id = 1;
        FuncionarioModel funcionarioId = funcionarioDao.buscaPorId(id);
        if(funcionarioId == null){
            System.out.println("Funcionario não encontrado");
        } else {
            System.out.println("--- Busca por ID ----");
            System.out.println("Nome: " + funcionarioId.getNome());
            System.out.println("ID: " + funcionarioId.getId());
            System.out.println();
        }

        // teste atualizar
        if(funcionarioId != null){
            funcionarioId.setSalario(5000);
            funcionarioDao.atualizar(funcionarioId);
            System.out.println("Salario atualizado");
            System.out.println();
        }

        // teste remover
        int idRemove = 1;
        funcionarioDao.remover(idRemove);
        System.out.println("ID: " + idRemove + " removido com sucesso");
        System.out.println();

        limparTabela();

    }
    public void limparTabela(){
        String sqlDelete = "DELETE FROM funcionarios";
        String sqlReset = "ALTER TABLE funcionarios AUTO_INCREMENT = 1";

        try (Statement stmt = ConnectionFactory.getConnection().createStatement()) {
            stmt.executeUpdate(sqlDelete);
            System.out.println("Todos os itens foram apagados com sucesso");

            stmt.executeUpdate(sqlReset);
            System.out.println("AUTO_INCREMENT resetado para 1");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
