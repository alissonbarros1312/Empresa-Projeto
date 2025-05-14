package org.example.model;

public abstract class PessoaModel {
    private String nome;
    private String cpf;

    public PessoaModel(String nome, String cpf) {
        setNome(nome);
        setCpf(cpf);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (ValidacoesModel.validaString(nome)) {
            this.nome = nome;
        }
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (ValidacoesModel.validaCpf(cpf)) {
            this.cpf = cpf;
        }
    }

    public void exibirInfo(){
        System.out.println("Nome: " + getNome());
        System.out.println("CPF: " + getCpf());
    }
}
