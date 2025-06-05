package br.com.empresa.controller;

import br.com.empresa.model.GerenteModel;
import br.com.empresa.service.GerenteService;

import java.util.List;

public class GerenteController {
    GerenteService gerenteService;

    public GerenteController(GerenteService gerenteService) {
        this.gerenteService = gerenteService;
    }

    public int inserirGerente(GerenteModel gerente){
        return gerenteService.inserir(gerente);
    }

    public boolean atualizarGerente(GerenteModel gerente){
        return gerenteService.atualizar(gerente);
    }

    public boolean removerGerente(int id){
        return gerenteService.remover(id);
    }

    public GerenteModel buscarGerentePorId(int id){
        return gerenteService.buscarPorId(id);
    }

    public List<GerenteModel> listarTodos(){
        return gerenteService.listarTodos();
    }
}
