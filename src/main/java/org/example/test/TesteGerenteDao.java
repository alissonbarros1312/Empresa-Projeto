package org.example.test;

import org.example.dao.ConnectionFactory;
import org.example.dao.GerenteDAO;
import org.example.model.GerenteModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class TesteGerenteDao {
    public void testeGerente() {
        Connection conn = ConnectionFactory.getConnection();
        GerenteDAO gerenteDAO = new GerenteDAO(conn);

        // teste inserir
        GerenteModel gerente = new GerenteModel("TesteGerente", "09876543211", "TI", 3000, 4);
        //gerenteDAO.inserir(gerente);
        System.out.println("Inserido com sucesso");
        System.out.println();


        // teste listarTodos
        List<GerenteModel> gerentesLista = gerenteDAO.listarTodos(conn);
        System.out.println("---- Listar Todos ----");
        for (GerenteModel g : gerenteDAO.listarTodos(conn)) {
            System.out.println("Nome: " + g.getNome() + " ID: " + g.getId());
            System.out.println();
        }

        // teste busca por ID
        int id = 1;
        GerenteModel gerenteID = gerenteDAO.buscarPorId(id, conn);
        if (gerenteID != null) {
            System.out.println("Gerente encontrado");
            System.out.println("Nome: " + gerenteID.getNome());
            System.out.println("ID: " + gerenteID.getId());
            System.out.println("-----------------");
        } else {
            System.out.println("ID não encontrado");
        }

        System.out.println();

        // teste atualizar
        if (gerenteID != null) {
            gerente.setEquipe(8);
            //gerenteDAO.atualizar(gerente);
            System.out.println("Atualizado com sucesso");
        } else {
            System.out.println("Não foi possivel atualizar");
        }

        System.out.println();

        // teste remover
        if (gerenteID != null) {
            int idRemove = gerenteID.getId();
            //gerenteDAO.remover(idRemove);
            System.out.println("ID: " + idRemove + " removido com sucesso");
        } else {
            System.out.println("Gerente inválido");
        }

        System.out.println();
    }
    public void limpaTela(){
        String sql = "DELETE * FROM gerentes";
        String sqlReset = "ALTER TABLE gerentes AUTO_INCREMENT = 1";

        try (Statement stmt = ConnectionFactory.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Todos os itens foram apagados com sucesso");

            stmt.executeUpdate(sqlReset);
            System.out.println("AUTO_INCREMENT resetado a 1");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
