package org.example.model;

import org.example.util.ValidacoesUtil;

public class GerenteModel extends FuncionarioModel{
    private int equipe;

    public GerenteModel(String nome, String cpf, String setor, double salario, int equipe) {
        super(nome, cpf, setor, salario);
        setEquipe(equipe);
    }

    public int getEquipe() {
        return equipe;
    }

    public void setEquipe(int equipe) {
        if(ValidacoesUtil.validaEquipe(equipe)){
            this.equipe = equipe;
        }
    }

    @Override
    public void exibirInfo() {
        System.out.println("----- GERENTE -----");
        super.exibirInfo();
        System.out.println("Equipe: " + getEquipe());
        System.out.println();
    }
}
