package org.example.service;

import org.example.dao.ConnectionFactory;
import org.example.dao.GerenteDAO;
import org.example.model.GerenteModel;
import org.example.util.LoggerUtil;
import org.example.util.ValidacoesUtil;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class GerenteService {
    GerenteDAO gerenteDAO;
    FuncionarioService funcionarioService;

    public GerenteService(GerenteDAO gerenteDAO, FuncionarioService funcionarioService) {
        this.gerenteDAO = gerenteDAO;
        this.funcionarioService = funcionarioService;
    }

    public int inserir(GerenteModel gerente) {
        if (!ValidacoesUtil.validaPessoa(gerente)) {
            LoggerUtil.logWarning("[GERENTE SERVICE] DADOS INVÁLIDOS PARA GERENTE");
            return -1;
        }
        Connection conn = null;
        int gerenteId = -1;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);


            int funcionarioId = funcionarioService.inserir(gerente, conn);
            if (funcionarioId == -1) {
                conn.rollback();
                LoggerUtil.logWarning("[GERENTE SERVICE] FALHA AO INSERIR FUNCIONARIO BASE. ID: " + funcionarioId);
                return -1;
            }

            gerente.setId(funcionarioId);

            gerenteId = gerenteDAO.inserir(gerente, conn);
            if (gerenteId == -1) {
                conn.rollback();
                LoggerUtil.logWarning("[GERENTE SERVICE] FALHA AO INSERIR DADOS ESPECIFICOS. ID: " + gerenteId);
                return -1;
            }

            conn.commit();
            return gerenteId;
        } catch (Exception e) {
            rollbackSilencioso(conn);
            LoggerUtil.logErro("[GERENTE SERVICE] ERRO INESPERADO AO INSERIR GERENTE", e);
            gerenteId = -1;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return gerenteId;
    }

    public boolean atualizar(GerenteModel gerente) {
        if (!ValidacoesUtil.validaPessoa(gerente) || !ValidacoesUtil.validaID(gerente.getId())) {
            LoggerUtil.logWarning("[GERENTE SERVICE] Erro, gerente inválido");
            return false;
        }

        Connection conn = null;
        boolean sucesso = false;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            boolean sucessoFuncionario = funcionarioService.atualizar(gerente, conn);
            if (!sucessoFuncionario) {
                conn.rollback();
                LoggerUtil.logWarning("[GERENTE SERVICE] FALHA AO ATUALIZAR DADOS BASE DE GERENTE");
                return false;
            }

            boolean sucessoGerente = gerenteDAO.atualizar(gerente, conn);
            if (!sucessoGerente) {
                conn.rollback();
                LoggerUtil.logWarning("[GERENTE SERVICE] FALHA AO ATUALIZAR DADOS ESPECIFICOS DE GERENTE");
                return false;
            }
            conn.commit();
            sucesso = true;
        } catch (Exception e) {
            rollbackSilencioso(conn);
            LoggerUtil.logErro("[GERENTE SERVICE] ERRO INESPERADO AO ATUALIZAR GERENTE", e);
            return false;
        } finally {
            if (conn != null) {
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

    public boolean remover(int id) {
        if (!ValidacoesUtil.validaID(id)) {
            LoggerUtil.logWarning("[GERENTE SERVICE] ID NÃO VALIDO. ID: " + id);
            return false;
        }

        Connection conn = null;
        boolean sucesso = false;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            boolean sucessoGerente = gerenteDAO.remover(id, conn);
            if (!sucessoGerente) {
                conn.rollback();
                LoggerUtil.logWarning("[GERENTE SERVICE] FALHA AO REMOVER DADOS ESPECIFICOS. ID: " + id);
                return false;
            }

            boolean sucessoFuncionario = funcionarioService.remover(id, conn);
            if (!sucessoFuncionario) {
                conn.rollback();
                LoggerUtil.logWarning("[GERENTE SERVICE] FALHA AO REMOVER DADOS BASE. ID: " + id);
                return false;
            }

            conn.commit();
            sucesso = true;
        } catch (Exception e) {
            rollbackSilencioso(conn);
            LoggerUtil.logErro("[GERENTE SERVICE] ERRO INESPERADO AO REMOVER GERENTE", e);
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return sucesso;
    }

    public GerenteModel buscarPorId(int id) {
        if (!ValidacoesUtil.validaID(id)) {
            LoggerUtil.logWarning("[GERENTE SERVICE] ID NÃO VALIDO");
            return null;
        }
        GerenteModel gerente = null;

        try (Connection conn = ConnectionFactory.getConnection()){
            gerente = gerenteDAO.buscarPorId(id, conn);

            if (gerente != null) {
                LoggerUtil.logInfo("GERENTE ENCONTRADO. ID: " + id);
            } else {
                LoggerUtil.logWarning("[GERENTE SERVICE] GERENTE NÃO ENCONTRADO. ID: " + id);
            }

        } catch (Exception e) {
            LoggerUtil.logErro("[GERENTE SERVICE] ERRO INESPERADO AO BUSCAR POR ID - GERENTE", e);
        }
        return gerente;
    }

    public List<GerenteModel> listarTodos() {
        try (Connection conn = ConnectionFactory.getConnection()){
            List<GerenteModel> gerentes = gerenteDAO.listarTodos(conn);

            LoggerUtil.logInfo("LISTAGEM FINALIZADA. TOTAL DE GERENTES ENCONTRADOS: " + gerentes.size());
            return gerentes;
        } catch (Exception e) {
            LoggerUtil.logErro("[GERENTE SERVICE] ERRO INESPERADO AO LISTAR TODOS - GERENTE", e);
            return Collections.emptyList();
        }
    }

    private void rollbackSilencioso(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
