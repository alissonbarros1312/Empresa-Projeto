package org.example.dao;

import org.example.model.FuncionarioModel;
import org.example.model.GerenteModel;
import org.example.util.LoggerUtil;
import org.example.util.ValidacoesUtil;

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
        this.funcionarioDAO = new FuncionarioDAO(connection);
    }

    public void inserir(GerenteModel gerente) {
        if(!ValidacoesUtil.validaPessoa(gerente)){
            LoggerUtil.logWarning("DADOS INVÁLIDOS PARA GERENTE");
            return;
        }

        int funcionarioId = funcionarioDAO.inserir(gerente);

        if(funcionarioId == -1){
            LoggerUtil.logWarning("Erro ao inserir funcionario base para gerente");
            return;
        }

        String sql = "INSERT INTO gerentes (id, equipe) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, funcionarioId);
            stmt.setInt(2, gerente.getEquipe());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                gerente.setId(funcionarioId);
                LoggerUtil.logInfo("Gerente inserido com sucesso");
            } else {
                LoggerUtil.logWarning("Falha ao inserir gerente com id: " + funcionarioId);
            }

        } catch (SQLException e) {
            LoggerUtil.logErro("INSERIR GERENTE", e);
        }
    }

    public void atualizar(GerenteModel gerente) {
        if (!ValidacoesUtil.validaPessoa(gerente) || !ValidacoesUtil.validaID(gerente.getId())) {
            LoggerUtil.logWarning("Erro, gerente inválido");
            return;
        }

        if(!funcionarioDAO.atualizar(gerente)){
            LoggerUtil.logWarning("ATUALIZAÇÃO DE DADOS BASE NÃO REALIZADA");
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
            }
        } catch (SQLException e) {
            LoggerUtil.logErro("ATUALIZAR GERENTE", e);
        }
    }

    public void remover(int id) {
        String sql = "DELETE FROM gerentes WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            if (!ValidacoesUtil.validaID(id)) {
                LoggerUtil.logWarning("ID INVÁLIDO = " + id);
                throw new SQLException("ID Inválido");
            }

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                funcionarioDAO.remover(id);
                LoggerUtil.logInfo("Gerente removido com sucesso");
            } else {
                LoggerUtil.logWarning("Nenhum gerente encontrado com o ID informado");
            }

        } catch (SQLException e) {
            LoggerUtil.logErro("REMOVER GERENTE", e);
        }

    }

    public GerenteModel buscaPorId(int id) {
        String sql = "SELECT * FROM gerentes WHERE id = ?";
        GerenteModel gerente = null;


        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            if (!ValidacoesUtil.validaID(id)) {
                LoggerUtil.logWarning("ID inválido");
                throw new SQLException("ID Inválido");
            }
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    FuncionarioModel funcionario = funcionarioDAO.buscaPorId(id);

                    if(funcionario == null){
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

                if(funcionario == null){
                    LoggerUtil.logWarning("FUNCIONARIO BASE NÃO ENCONTRADO PARA GERENTE COM ID: " + id);
                }

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
            LoggerUtil.logErro("LISTAR TODOS", e);
        }
        return gerentes;
    }
}
