package org.example.service;

import org.example.dao.ConnectionFactory;
import org.example.dao.FuncionarioDAO;
import org.example.model.FuncionarioModel;
import org.example.util.LoggerUtil;
import org.example.util.ValidacoesUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FuncionarioService {
    private FuncionarioDAO funcionarioDAO;

    public FuncionarioService(FuncionarioDAO funcionarioDAO) {
        this.funcionarioDAO = funcionarioDAO;
    }

    protected int inserir(FuncionarioModel funcionario, Connection conn) {
        if (!ValidacoesUtil.validaPessoa(funcionario) || !ValidacoesUtil.validaID(funcionario.getId())) {
            LoggerUtil.logWarning("FUNCIONARIO NÃO VALIDO");
            return -1;
        }

        int idGerado = funcionarioDAO.inserir(funcionario, conn);

        if (idGerado != -1) {
            LoggerUtil.logInfo("[FUNCIONARIO SERVICE] FUNCIONARIO INSERIDO COM SUCESSO. ID: " + idGerado);
        } else {
            LoggerUtil.logWarning("[FUNCIONARIO SERVICE] FALHA AO INSERIR FUNCIONARIO");
        }
        return idGerado;
    }

    public int inserir(FuncionarioModel funcionarioModel){
        Connection conn = null;
        int funcionarioId = -1;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            funcionarioId = inserir(funcionarioModel, conn);

            conn.commit();
            return funcionarioId;
        } catch (SQLException e) {
            if(conn != null){
                try {
                    conn.rollback();
                } catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
            LoggerUtil.logInfo("[FUNCIONARIO SERVICE] ERRO INESPERADO AO INSERIR FUNCIONARIO");
        } finally {
            if (conn != null){
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return funcionarioId;
    }

    public boolean atualizar(FuncionarioModel funcionario) {
        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);
            boolean sucesso = atualizar(funcionario, conn);

            conn.commit();
            return sucesso;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    e.printStackTrace();
                }
            }
            LoggerUtil.logErro("[FUNCIONARIO SERVICE] ERRO AO ATUALIZAR", e);
            return false;
        } finally {
            if (conn != null){
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected boolean atualizar(FuncionarioModel funcionario, Connection conn){
        if (!ValidacoesUtil.validaPessoa(funcionario) || !ValidacoesUtil.validaID(funcionario.getId())) {
            LoggerUtil.logWarning("FUNCIONARIO NÃO VALIDO. ID: " + funcionario.getId());
            return false;
        }
        boolean sucesso = funcionarioDAO.atualizar(funcionario, conn);

        if (sucesso) {
            LoggerUtil.logInfo("FUNCIONARIO ATUALIZADO COM SUCESSO. ID: " + funcionario.getId());
        } else {
            LoggerUtil.logWarning("FALHA AO ATUALIZAR FUNCIONARIO. ID: " + funcionario.getId());
        }

        return sucesso;
    }

    protected boolean remover(int id, Connection conn) {
        if (!ValidacoesUtil.validaID(id)) {
            LoggerUtil.logWarning("ID NÃO VALIDO. ID: " + id);
            return false;
        }

        boolean sucesso = funcionarioDAO.remover(id, conn);
        if (sucesso) {
            LoggerUtil.logInfo("FUNCIONARIO REMOVIDO COM SUCESSO. ID: " + id);
        } else {
            LoggerUtil.logWarning("FALHA AO REMOVER FUNCIONARIO. ID: " + id);
        }
        return sucesso;
    }

    public boolean remover(int id){
        Connection conn = null;
        boolean sucesso = false;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            sucesso = funcionarioDAO.remover(id, conn);
            conn.commit();
        } catch (SQLException e) {
            if (conn != null){
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            LoggerUtil.logErro("[FUNCIONARIO SERVICE] ERRO AO REMOVER", e);
            return false;
        } finally {
            if(conn != null){
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return sucesso;
    }

    public FuncionarioModel buscaPorId(int id) {
        if (!ValidacoesUtil.validaID(id)) {
            LoggerUtil.logWarning("ID NÃO VALIDO");
            return null;
        }

        FuncionarioModel funcionario = funcionarioDAO.buscaPorId(id);

        if (funcionario != null) {
            LoggerUtil.logInfo("FUNCIONARIO ENCONTRADO. ID: " + id);
        } else {
            LoggerUtil.logWarning("FUNCIONARIO NAO ENCONTRADO. ID: " + id);
        }
        return funcionario;
    }

    public List<FuncionarioModel> listarTodos() {
        List<FuncionarioModel> funcionarios = funcionarioDAO.listarTodos();

        LoggerUtil.logInfo("LISTAGEM FINALIZADA. TOTAL DE FUNCIONARIOS ENCONTRADOS: " + funcionarios.size());

        return funcionarios;
    }
}
