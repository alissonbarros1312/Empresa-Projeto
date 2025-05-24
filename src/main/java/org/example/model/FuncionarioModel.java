package org.example.model;

public class FuncionarioModel extends PessoaModel{
    private String setor;
    private double salario;

    public FuncionarioModel() {
    }

    public FuncionarioModel(String nome, String cpf, String setor, double salario) {
        super(nome, cpf);
        setSetor(setor);
        setSalario(salario);
    }

    public FuncionarioModel(String nome, String cpf, int id, String setor, double salario) {
        super(nome, cpf, id);
        setSetor(setor);
        setSalario(salario);
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        if(ValidacoesModel.validaString(setor)){
           this.setor = setor;
        }
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        if(ValidacoesModel.validaSalario(salario)){
            this.salario = salario;
        }
    }

    @Override
    public void exibirInfo() {
        System.out.println("----- FUNCIONARIO ------");
        super.exibirInfo();
        System.out.println("Setor: " + getSetor());
        System.out.printf("Salario: %.2f\n", getSalario());
        System.out.println();
    }
}
