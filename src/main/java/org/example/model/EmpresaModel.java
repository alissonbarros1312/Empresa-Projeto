package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class EmpresaModel {
    private List<FuncionarioModel> funcionarios = new ArrayList<>();
    private List<GerenteModel> gerentes = new ArrayList<>();

    public void adicionaFuncionario(FuncionarioModel pessoa){
        if(ValidacoesModel.validaPessoa(pessoa)){
            funcionarios.add(pessoa);
        }
    }
    public void adicionaGerente(GerenteModel pessoa){
        if(ValidacoesModel.validaPessoa(pessoa)){
            gerentes.add(pessoa);
        }
    }
    public void exibirFuncionarios(){
        for(FuncionarioModel pessoa : funcionarios){
            pessoa.exibirInfo();
        }
    }public void exibirGerentes(){
        for(GerenteModel pessoa : gerentes){
            pessoa.exibirInfo();
        }
    }
}
