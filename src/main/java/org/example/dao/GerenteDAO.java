package org.example.dao;

import org.example.model.FuncionarioModel;
import org.example.model.GerenteModel;
import org.example.model.ValidacoesModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GerenteDAO {
    Connection connection;
    FuncionarioDAO funcionarioDAO;

    public GerenteDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(GerenteModel gerente) {
        int funcionarioId = funcionarioDAO.inserir(gerente);

        if(funcionarioId == -1){
            System.out.println("Erro ao inserir funcionario base para gerente");
            return;
        }

        String sql = "INSERT INTO gerentes (id, equipe) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, funcionarioId);
            stmt.setInt(2, gerente.getEquipe());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                gerente.setId(funcionarioId);
                System.out.println("Gerente inserido com sucesso");
            } else {
                System.out.println("Falha ao inserir gerente");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao inserir o gerente: " + e.getMessage());
        }
    }

    public void atualizar(GerenteModel gerente) {
        if (!ValidacoesModel.validaPessoa(gerente) || !ValidacoesModel.validaID(gerente.getId())) {
            System.out.println("Erro, gerente inválido");
            return;
        }

        funcionarioDAO.atualizar(gerente);

        String sql = "UPDATE gerentes SET equipe = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, gerente.getEquipe());
            stmt.setInt(2, gerente.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new SQLException("Nenhuma atualização realizada");
            } else {
                System.out.println("Gerente atualizado com sucesso");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao atualizar o gerente: " + e.getMessage());
        }
    }

    public void remover(int id) {
        String sql = "DELETE FROM gerentes WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            if (!ValidacoesModel.validaID(id)) {
                throw new SQLException("ID Inválido");
            }

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Gerente removido com sucesso");
                funcionarioDAO.remover(id);
            } else {
                System.out.println("Nenhum gerente encontrado com o ID informado");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao remover o gerente: " + e.getMessage());
        }

    }

    public GerenteModel buscaPorId(int id) {
        String sql = "SELECT * FROM gerentes WHERE id = ?";
        GerenteModel gerente = null;


        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            if (!ValidacoesModel.validaID(id)) {
                System.out.println("ID inválido");
                throw new SQLException("ID Inválido");
            }
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    FuncionarioModel funcionario = funcionarioDAO.buscaPorId(id);
                    int equipe = rs.getInt("equipe");

                    gerente = new GerenteModel(funcionario.getNome(),
                                               funcionario.getCpf(),
                                               funcionario.getSetor(),
                                               funcionario.getSalario(),
                                               equipe);
                    gerente.setId(rs.getInt("id"));
                }

            }


        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao realizar busca por ID: " + e.getMessage());
        }
        return gerente;
    }

    public List<GerenteModel> listarTodos() {
        List<GerenteModel> gerentes = new ArrayList<>();
        String sql = "SELECT * FROM gerentes";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int equipe = rs.getInt("equipe");
                int id = rs.getInt("id");
                FuncionarioModel funcionario = funcionarioDAO.buscaPorId(id);

                if(funcionario != null){
                    GerenteModel gerente = new GerenteModel(funcionario.getNome(),
                                                            funcionario.getCpf(),
                                                            funcionario.getSetor(),
                                                            funcionario.getSalario(),
                                                            equipe);
                    gerente.setId(rs.getInt("id"));
                    gerentes.add(gerente);

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao listar dados: " + e.getMessage());
        }
        return gerentes;
    }
}
