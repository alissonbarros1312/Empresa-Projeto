package org.example.service;

import org.example.dao.GerenteDAO;
import org.example.model.GerenteModel;
import org.example.util.LoggerUtil;
import org.example.util.ValidacoesUtil;

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
            LoggerUtil.logWarning("DADOS INVÁLIDOS PARA GERENTE");
            return -1;
        }
        int funcionarioId = funcionarioService.inserir(gerente);

        if (funcionarioId == -1) {
            LoggerUtil.logWarning("ID INVALIDO. ID: " + funcionarioId);
            return -1;
        }
        gerente.setId(funcionarioId);

        int gerenteId = gerenteDAO.inserir(gerente);
        if(gerenteId == -1){
            LoggerUtil.logWarning("ID INVALIDO. ID: " + gerenteId);
            return -1;
        }
        return gerenteId;
    }

    public boolean atualizar(GerenteModel gerente){
        if (!ValidacoesUtil.validaPessoa(gerente) || !ValidacoesUtil.validaID(gerente.getId())) {
            LoggerUtil.logWarning("Erro, gerente inválido");
            return false;
        }

        boolean sucessoFuncionario = funcionarioService.atualizar(gerente);
        if(!sucessoFuncionario){
            LoggerUtil.logWarning("FALHA AO ATUALIZAR DADOS BASE DE GERENTE");
            return false;
        }

        boolean sucessoGerente = gerenteDAO.atualizar(gerente);
        if (!sucessoGerente){
            LoggerUtil.logWarning("FALHA AO ATUALIZAR DADOS ESPECIFICOS DE GERENTE");
            return false;
        }
        return sucessoGerente;
    }

    public boolean remover(int id){
        if(!ValidacoesUtil.validaID(id)){
            LoggerUtil.logWarning("ID NÃO VALIDO. ID: " + id);
            return false;
        }

        boolean sucessoGerente = gerenteDAO.remover(id);
        if(!sucessoGerente){
            LoggerUtil.logWarning("FALHA AO REMOVER DADOS ESPECIFICOS. ID: " + id);
        }

        boolean sucesso = funcionarioService.remover(id);
        if(!sucesso){
            LoggerUtil.logWarning("FALHA AO REMOVER DADOS BASE. ID: " + id);
        }

        return sucesso;
    }

    public GerenteModel buscarPorId(int id){
        if(!ValidacoesUtil.validaID(id)){
            LoggerUtil.logWarning("ID NÃO VALIDO");
            return null;
        }

        GerenteModel gerente = gerenteDAO.buscaPorId(id);

        if(gerente != null){
            LoggerUtil.logInfo("GERENTE ENCONTRADO. ID: " + id);
        } else {
            LoggerUtil.logWarning("GERENTE NÃO ENCONTRADO. ID: " + id);
        }
        return gerente;
    }

    public List<GerenteModel> listarTodos(){
        List<GerenteModel> gerentes = gerenteDAO.listarTodos();

        LoggerUtil.logInfo("LISTAGEM FINALIZADA. TOTAL DE GERENTES ENCONTRADOS: " + gerentes.size());
        return gerentes;
    }
}
