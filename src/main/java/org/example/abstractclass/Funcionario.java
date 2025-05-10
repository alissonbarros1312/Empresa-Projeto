package org.example.abstractclass;

public class Funcionario extends Pessoa {
    private String setor;
    private double salario;

    public Funcionario(String nome, String cpf, String setor, double salario) {
        super(nome, cpf);
        setSetor(setor);
        setSalario(salario);
    }

    @Override
    public void exibirInfo() {
        super.exibirInfo();
        System.out.println("Setor: " + getSetor());
        System.out.printf("Salario: R$ %.2f\n", getSalario());
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        if (Validacoes.validaString(setor)) {
            this.setor = setor;
        } else {
            System.out.println("Setor inválido");
        }
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        if (Validacoes.validaSalarioPositivo(salario)) {
            this.salario = salario;
        } else {
            System.out.println("Salario inválido");
        }
    }
}
