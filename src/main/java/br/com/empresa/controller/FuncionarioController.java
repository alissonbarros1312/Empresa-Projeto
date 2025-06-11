package br.com.empresa.controller;

import br.com.empresa.model.FuncionarioModel;
import br.com.empresa.service.FuncionarioService;
import br.com.empresa.util.LoggerUtil;
import br.com.empresa.util.ValidacoesUtil;

import java.util.List;

public class FuncionarioController {
    FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    public int criarFuncionario(FuncionarioModel funcionario){
        if(funcionario == null || !ValidacoesUtil.validaFuncionarioSemId(funcionario)){
            LoggerUtil.logInfo("[FUNCIONARIO CONTROLLER] FUNCIONARIO INVALIDO");
            return -1;
        }
        return funcionarioService.inserir(funcionario);
    }

    public boolean atualizarFuncionario(FuncionarioModel funcionario){
        if(funcionario == null || !ValidacoesUtil.validaFuncionarioComId(funcionario)){
            LoggerUtil.logInfo("[FUNCIONARIO CONTROLLER] FUNCIONARIO INVALIDO");
            return false;
        }
        return funcionarioService.atualizar(funcionario);
    }

    public boolean removerFuncionario(int id){
        if (!ValidacoesUtil.validaID(id)) {
            LoggerUtil.logWarning("ID NÃO VALIDO. ID: " + id);
            return false;
        }
        return funcionarioService.remover(id);
    }

    public FuncionarioModel buscarFuncionarioPorId(int id){
        if (!ValidacoesUtil.validaID(id)) {
            LoggerUtil.logWarning("ID NÃO VALIDO. ID: " + id);
            return null;
        }
        return funcionarioService.buscaPorId(id);
    }

    public List<FuncionarioModel> listarTodosFuncionarios(){
        return funcionarioService.listarTodos();
    }
}
