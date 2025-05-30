package org.example.controller;

import org.example.model.GerenteModel;
import org.example.service.GerenteService;

import java.util.List;

public class GerenteController {
    GerenteService gerenteService;

    public GerenteController(GerenteService gerenteService) {
        this.gerenteService = gerenteService;
    }

    public int criarGerente(GerenteModel gerente){
        return gerenteService.inserir(gerente);
    }

    public boolean atualizarGerente(GerenteModel gerente){
        return gerenteService.atualizar(gerente);
    }

    public boolean removerGerente(int id){
        return gerenteService.remover(id);
    }

    public GerenteModel buscaGerentePorId(int id){
        return gerenteService.buscarPorId(id);
    }

    public List<GerenteModel> listarTodos(){
        return gerenteService.listarTodos();
    }
}
