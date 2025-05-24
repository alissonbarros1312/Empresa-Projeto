package org.example.dao;

import org.example.model.FuncionarioModel;
import org.example.model.ValidacoesModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {
    private Connection connection;

    public FuncionarioDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(FuncionarioModel funcionario) {
        String sql = "INSERT INTO funcionarios (nome, cpf, setor, salario) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setString(3, funcionario.getSetor());
            stmt.setDouble(4, funcionario.getSalario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(FuncionarioModel funcionario) {
        if (!ValidacoesModel.validaPessoa(funcionario) || !ValidacoesModel.validaID(funcionario.getId())) {
            System.out.println("Erro...");
            return;
        }

        String sql = "UPDATE funcionarios SET nome = ?, cpf = ?, setor = ?, salario = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setString(3, funcionario.getSetor());
            stmt.setDouble(4, funcionario.getSalario());
            stmt.setInt(5, funcionario.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new SQLException("Nenhuma linha atualizada, ID pode não existir");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void remover(int id) {
        String sql = "DELETE FROM funcionarios WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Funcionario removido com sucesso");
            } else {
                System.out.println("Nenhum funcionario encontrado com o ID informado");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public FuncionarioModel buscaPorId(int id) {
        String sql = "SELECT * FROM funcionarios WHERE id = ?";

        FuncionarioModel funcionario = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String setor = rs.getString("setor");
                double salario = rs.getDouble("salario");

                funcionario = new FuncionarioModel(nome, cpf, setor, salario);
                funcionario.setId(rs.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return funcionario;

    }

    public List<FuncionarioModel> listarTodos(){
        List<FuncionarioModel> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM funcionarios";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()){
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String setor = rs.getString("setor");
                double salario = rs.getDouble("salario");

                FuncionarioModel funcionario = new FuncionarioModel(nome, cpf, setor, salario);
                funcionario.setId(rs.getInt("id"));

                funcionarios.add(funcionario);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return funcionarios;
    }
}
