package org.example.abstractclass;

public abstract class Pessoa {
    private String nome;
    private String cpf;

    public Pessoa(String nome, String cpf) {
        setNome(nome);
        setCpf(cpf);
    }

    public void exibirInfo() {
        System.out.println("Nome: " + getNome());
        System.out.println("CPF: " + getCpf());
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (Validacoes.validaString(nome)) {
            this.nome = nome;
        } else {
            System.out.println("Nome inválido");
        }
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (Validacoes.validaCPF(cpf)) {
            this.cpf = cpf;
        }
    }
}
