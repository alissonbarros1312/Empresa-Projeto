package org.example.service;

import org.example.dao.FuncionarioDAO;
import org.example.model.FuncionarioModel;
import org.example.util.LoggerUtil;
import org.example.util.ValidacoesUtil;

import java.util.List;

public class FuncionarioService {
    private FuncionarioDAO funcionarioDAO;

    public FuncionarioService(FuncionarioDAO funcionarioDAO) {
        this.funcionarioDAO = funcionarioDAO;
    }

    public int inserir(FuncionarioModel funcionario){
        if (!ValidacoesUtil.validaPessoa(funcionario) || !ValidacoesUtil.validaID(funcionario.getId())) {
            LoggerUtil.logWarning("FUNCIONARIO NÃO VALIDO");
            return -1;
        }

        int idGerado = funcionarioDAO.inserir(funcionario);

        if(idGerado != -1){
            LoggerUtil.logInfo("FUNCIONARIO INSERIDO COM SUCESSO. ID: "+ idGerado);
        } else {
            LoggerUtil.logWarning("FALHA AO INSERIR FUNCIONARIO");
        }
        return idGerado;
    }

    public boolean atualizar(FuncionarioModel funcionario){
        if (!ValidacoesUtil.validaPessoa(funcionario) || !ValidacoesUtil.validaID(funcionario.getId())) {
            LoggerUtil.logWarning("FUNCIONARIO NÃO VALIDO. ID: " + funcionario.getId());
            return false;
        }

        boolean sucesso = funcionarioDAO.atualizar(funcionario);

        if(sucesso){
            LoggerUtil.logInfo("FUNCIONARIO ATUALIZADO COM SUCESSO. ID: " + funcionario.getId());
        } else {
            LoggerUtil.logWarning("FALHA AO ATUALIZAR FUNCIONARIO. ID: " + funcionario.getId());
        }
        return sucesso;
    }

    public boolean remover(int id){
        if(!ValidacoesUtil.validaID(id)){
            LoggerUtil.logWarning("ID NÃO VALIDO. ID: " + id);
            return false;
        }

        boolean sucesso = funcionarioDAO.remover(id);
        if(sucesso){
            LoggerUtil.logInfo("FUNCIONARIO REMOVIDO COM SUCESSO. ID: " + id);
        } else {
            LoggerUtil.logWarning("FALHA AO REMOVER FUNCIONARIO. ID: " + id);
        }
        return sucesso;
    }

    public FuncionarioModel buscaPorId(int id){
        if(!ValidacoesUtil.validaID(id)){
            LoggerUtil.logWarning("ID NÃO VALIDO");
            return null;
        }

        FuncionarioModel funcionario = funcionarioDAO.buscaPorId(id);

        if(funcionario != null){
            LoggerUtil.logInfo("FUNCIONARIO ENCONTRADO. ID: " + id);
        } else {
            LoggerUtil.logWarning("FUNCIONARIO NAO ENCONTRADO. ID: " + id);
        }
        return funcionario;
    }

    public List<FuncionarioModel> listarTodos(){
        List<FuncionarioModel> funcionarios = funcionarioDAO.listarTodos();

        LoggerUtil.logInfo("LISTAGEM FINALIZADA. TOTAL DE FUNCIONARIOS ENCONTRADOS: " + funcionarios.size());

        return funcionarios;
    }
}
