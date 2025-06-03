package org.example.controller;

import org.example.model.FuncionarioModel;
import org.example.service.FuncionarioService;

import java.sql.Connection;
import java.util.List;

public class FuncionarioController {
    FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    public int criarFuncionario(FuncionarioModel funcionario){
        return funcionarioService.inserir(funcionario);
    }

    public boolean atualizarFuncionario(FuncionarioModel funcionario){
        return funcionarioService.atualizar(funcionario);
    }

    public boolean removerFuncionario(int id){
        return funcionarioService.remover(id);
    }

    public FuncionarioModel buscarFuncionarioPorId(int id){
        return funcionarioService.buscaPorId(id);
    }

    public List<FuncionarioModel> listarTodosFuncionarios(){
        return funcionarioService.listarTodos();
    }
}
