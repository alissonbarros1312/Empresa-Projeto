package org.example.abstractclass;

public class Gerente extends Funcionario {
    private int equipe;

    public Gerente(String nome, String cpf, String setor, double salario, int equipe) {
        super(nome, cpf, setor, salario);
        setEquipe(equipe);
    }

    @Override
    public void exibirInfo() {
        System.out.println("---------- GERENTE ----------");
        super.exibirInfo();
        System.out.println("Numero de pessoas na equipe: " + getEquipe());
    }

    public int getEquipe() {
        return equipe;
    }

    public void setEquipe(int equipe) {
        if(Validacoes.validaEquipePositiva(equipe)){
            this.equipe = equipe;
        } else {
            System.out.println("Numero de equipe inválido");
        }
    }
}
