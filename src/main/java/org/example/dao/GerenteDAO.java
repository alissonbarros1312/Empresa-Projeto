package org.example.dao;

import org.example.model.GerenteModel;
import org.example.util.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GerenteDAO {
    Connection connection;
    FuncionarioDAO funcionarioDAO;

    public GerenteDAO(Connection connection) {
        this.connection = connection;
        this.funcionarioDAO = new FuncionarioDAO(connection);
    }

    public int inserir(GerenteModel gerente, Connection conn) {
        String sql = "INSERT INTO gerentes (id, equipe) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, gerente.getId());
            stmt.setInt(2, gerente.getEquipe());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                LoggerUtil.logInfo("Gerente inserido com sucesso. ID: " + gerente.getId());
                return gerente.getId();
            } else {
                LoggerUtil.logWarning("Falha ao inserir gerente com id: " + gerente.getId());
                return -1;
            }

        } catch (SQLException e) {
            LoggerUtil.logErro("INSERIR GERENTE", e);
            return -1;
        }
    }

    public boolean atualizar(GerenteModel gerente, Connection conn) {
        String sql = "UPDATE gerentes SET equipe = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, gerente.getEquipe());
            stmt.setInt(2, gerente.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                LoggerUtil.logWarning("NENHUMA ATUALIZAÇÃO REALIZADA PARA O GERENTE COM ID: " + gerente.getId());
                return false;
            } else {
                LoggerUtil.logInfo("Gerente atualizado com sucesso");
                return true;
            }
        } catch (SQLException e) {
            LoggerUtil.logErro("[GERENTE DAO] FALHA AO ATUALIZAR GERENTE", e);
            return false;
        }
    }

    public boolean remover(int id, Connection conn) {
        String sql = "DELETE FROM gerentes WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                LoggerUtil.logInfo("Gerente removido com sucesso");
                return true;
            } else {
                LoggerUtil.logWarning("Nenhum gerente encontrado com o ID informado. ID: " + id);
                return false;
            }

        } catch (SQLException e) {
            LoggerUtil.logErro("REMOVER GERENTE", e);
            return false;
        }
    }

    public GerenteModel buscarPorId(int id, Connection conn) {
        String sql = "SELECT g.id, g.equipe, f.nome, f.cpf, f.setor, f.salario " +
                     "FROM gerentes g " +
                     "JOIN funcionarios f ON g.id = f.id " +
                     "WHERE g.id = ?";

        GerenteModel gerente = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    gerente = new GerenteModel(rs.getString("nome"),
                                               rs.getString("cpf"),
                                               rs.getString("setor"),
                                               rs.getDouble("salario"),
                                               rs.getInt("equipe"));
                    gerente.setId(rs.getInt("id"));
                    LoggerUtil.logInfo("GERENTE ENCONTRADO. ID: " + gerente.getId());
                }

            }


        } catch (SQLException e) {
            LoggerUtil.logErro("BUSCAR POR ID", e);
        }
        return gerente;
    }

    public List<GerenteModel> listarTodos(Connection conn) {
        List<GerenteModel> gerentes = new ArrayList<>();
        String sql = "SELECT g.id, g.equipe, f.nome, f.cpf, f.setor, f.salario " +
                     "FROM gerentes g " +
                     "JOIN funcionarios f ON g.id = f.id";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                    GerenteModel gerente = new GerenteModel(rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("setor"),
                            rs.getDouble("salario"),
                            rs.getInt("equipe"));
                    gerente.setId(rs.getInt("id"));
                    gerentes.add(gerente);
            }

        } catch (SQLException e) {
            LoggerUtil.logErro("LISTAR TODOS", e);
        }
        return gerentes;
    }
}
