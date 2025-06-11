package br.com.empresa.controller;

import br.com.empresa.model.GerenteModel;
import br.com.empresa.service.GerenteService;
import br.com.empresa.util.LoggerUtil;
import br.com.empresa.util.ValidacoesUtil;

import java.util.List;

public class GerenteController {
    GerenteService gerenteService;

    public GerenteController(GerenteService gerenteService) {
        this.gerenteService = gerenteService;
    }

    public int inserirGerente(GerenteModel gerente){
        if(gerente == null || !ValidacoesUtil.validaGerenteSemId(gerente)){
            LoggerUtil.logInfo("[GERENTE CONTROLLER] GERENTE INVALIDO");
            return -1;
        }
        return gerenteService.inserir(gerente);
    }

    public boolean atualizarGerente(GerenteModel gerente){
        if(gerente == null || !ValidacoesUtil.validaGerenteComId(gerente)){
            LoggerUtil.logInfo("[GERENTE CONTROLLER] GERENTE INVALIDO");
            return false;
        }
        return gerenteService.atualizar(gerente);
    }

    public boolean removerGerente(int id){
        if (!ValidacoesUtil.validaID(id)) {
            LoggerUtil.logWarning("ID NÃO VALIDO. ID: " + id);
            return false;
        }
        return gerenteService.remover(id);
    }

    public GerenteModel buscarGerentePorId(int id){
        if (!ValidacoesUtil.validaID(id)) {
            LoggerUtil.logWarning("ID NÃO VALIDO. ID: " + id);
            return null;
        }
        return gerenteService.buscarPorId(id);
    }

    public List<GerenteModel> listarTodos(){
        return gerenteService.listarTodos();
    }
}
