package br.com.empresa.dao;

import br.com.empresa.model.FuncionarioModel;
import br.com.empresa.util.LoggerUtil;
import br.com.empresa.util.ValidacoesUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {
    private Connection connection;

    public FuncionarioDAO(Connection connection) {
        this.connection = connection;
    }

    public int inserir(FuncionarioModel funcionario, Connection conn) {
        String sql = "INSERT INTO funcionarios (nome, cpf, setor, salario) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setString(3, funcionario.getSetor());
            stmt.setDouble(4, funcionario.getSalario());
            int linhasAfetadas = stmt.executeUpdate();

            if(linhasAfetadas == 0){
                LoggerUtil.logWarning("FALHA AO INSERIR FUNCIONARIO");
                throw new SQLException("Falha ao inserir funcionario");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()){
                if(generatedKeys.next()){
                    return generatedKeys.getInt(1);
                } else {
                    LoggerUtil.logWarning("FALHA AO OBTER ID DO FUNCIONARIO INSERIDO");
                    throw new SQLException("Falha ao obter ID do funcionario inserido");
                }
            }

        } catch (SQLException e) {
            LoggerUtil.logErro("INSERIR FUNCIONARIO", e);
            return -1;
        }
    }

    public boolean atualizar(FuncionarioModel funcionario, Connection conn) {
        String sql = "UPDATE funcionarios SET nome = ?, cpf = ?, setor = ?, salario = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setString(3, funcionario.getSetor());
            stmt.setDouble(4, funcionario.getSalario());
            stmt.setInt(5, funcionario.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                LoggerUtil.logInfo("ATUALIZAÇÃO REALIZADA COM SUCESSO. ID: " + funcionario.getId());
            } else {
                LoggerUtil.logWarning("NENHUMA LINHA ATUALIZADA, ID PODE NÃO EXISTIR");
                throw new SQLException("Nenhuma linha atualizada, ID pode não existir");
            }

        } catch (SQLException e) {
            LoggerUtil.logErro("ATUALIZAR FUNCIONARIO", e);
            return false;
        }
        return true;

    }

    public boolean remover(int id, Connection conn) {
        String sql = "DELETE FROM funcionarios WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                LoggerUtil.logInfo("Funcionario removido com sucesso. ID: " + id);
                return true;
            } else {
                LoggerUtil.logWarning("Nenhum funcionario encontrado com o ID informado. ID: " + id);
                return false;
            }

        } catch (SQLException e) {
            LoggerUtil.logErro("REMOVER FUNCIONARIO", e);
            return false;
        }
    }

    public FuncionarioModel buscaPorId(int id) {
        if(!ValidacoesUtil.validaID(id)){
            LoggerUtil.logWarning("ID NÃO VALIDO");
            return null;
        }
        String sql = "SELECT * FROM funcionarios WHERE id = ?";

        FuncionarioModel funcionario = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()){
                if (rs.next()) {
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    String setor = rs.getString("setor");
                    double salario = rs.getDouble("salario");

                    funcionario = new FuncionarioModel(nome, cpf, setor, salario);
                    funcionario.setId(rs.getInt("id"));
                }
            }


        } catch (SQLException e) {
            LoggerUtil.logErro("BUSCAR POR ID", e);
        }

        return funcionario;

    }

    public List<FuncionarioModel> listarTodos(){
        List<FuncionarioModel> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM funcionarios";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    String setor = rs.getString("setor");
                    double salario = rs.getDouble("salario");

                    FuncionarioModel funcionario = new FuncionarioModel(nome, cpf, setor, salario);
                    funcionario.setId(rs.getInt("id"));

                    funcionarios.add(funcionario);
                }
            }

        } catch (SQLException e){
            LoggerUtil.logErro("LISTAR TODOS", e);
        }
        return funcionarios;
    }
}
