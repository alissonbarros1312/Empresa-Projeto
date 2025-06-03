package org.example.dao;

import org.example.model.FuncionarioModel;
import org.example.model.GerenteModel;
import org.example.util.LoggerUtil;
import org.example.util.ValidacoesUtil;

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
        int funcionarioId = funcionarioDAO.inserir(gerente, conn);

        String sql = "INSERT INTO gerentes (id, equipe) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, funcionarioId);
            stmt.setInt(2, gerente.getEquipe());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                gerente.setId(funcionarioId);
                LoggerUtil.logInfo("Gerente inserido com sucesso");
            } else {
                LoggerUtil.logWarning("Falha ao inserir gerente com id: " + funcionarioId);
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    LoggerUtil.logWarning("FALHA AO OBTER ID DO GERENTE INSERIDO");
                    throw new SQLException("Falha ao obter ID do gerente inserido");
                }
            }

        } catch (SQLException e) {
            LoggerUtil.logErro("INSERIR GERENTE", e);
            return -1;
        }
    }

    public boolean atualizar(GerenteModel gerente, Connection conn) {
        if (!funcionarioDAO.atualizar(gerente, conn)) {
            LoggerUtil.logWarning("ATUALIZAÇÃO DE DADOS BASE NÃO REALIZADA");
            return false;
        }

        String sql = "UPDATE gerentes SET equipe = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, gerente.getEquipe());
            stmt.setInt(2, gerente.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                LoggerUtil.logWarning("NENHUMA ATUALIZAÇÃO REALIZADA PARA O GERENTE COM ID: " + gerente.getId());
                throw new SQLException("Nenhuma atualização realizada");
            } else {
                LoggerUtil.logInfo("Gerente atualizado com sucesso");
                return true;
            }
        } catch (SQLException e) {
            LoggerUtil.logErro("ATUALIZAR GERENTE", e);
            return false;
        }
    }

    public boolean remover(int id, Connection conn) {
        String sql = "DELETE FROM gerentes WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0 && funcionarioDAO.remover(id, conn)) {
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

    public GerenteModel buscaPorId(int id) {
        String sql = "SELECT * FROM gerentes WHERE id = ?";
        GerenteModel gerente = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    FuncionarioModel funcionario = funcionarioDAO.buscaPorId(id);

                    if (funcionario == null) {
                        LoggerUtil.logWarning("FUNCIONARIO BASE NÃO ENCONTRADO PARA GERENTE COM ID: " + id);
                        return null;
                    }

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
            LoggerUtil.logErro("BUSCAR POR ID", e);
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

                if (funcionario == null) {
                    LoggerUtil.logWarning("FUNCIONARIO BASE NÃO ENCONTRADO PARA GERENTE COM ID: " + id);
                }

                if (funcionario != null) {
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
            LoggerUtil.logErro("LISTAR TODOS", e);
        }
        return gerentes;
    }
}
