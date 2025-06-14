package br.com.empresa.util;

import br.com.empresa.model.FuncionarioModel;
import br.com.empresa.model.GerenteModel;
import br.com.empresa.model.PessoaModel;

public class ValidacoesUtil {
    public static boolean validaString(String text) {
        if (text == null || text.trim().isEmpty()) {
            System.out.println("Dado invalido");
            return false;
        }
        return true;
    }

    public static boolean validaCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}") || cpf.matches("(\\d)\\1{10}")) {
            System.out.println("CPF invalido");
            return false;
        }
        return true;
    }

    public static boolean validaSalario(double salario) {
        if (salario <= 0.0) {
            System.out.println("Salario invalido");
            return false;
        }
        return true;
    }

    public static boolean validaEquipe(int equipe) {
        if (equipe <= 0) {
            System.out.println("Equipe invalida");
            return false;
        }
        return true;
    }

    public static boolean validaPessoa(PessoaModel pessoa) {
        if (pessoa == null || pessoa.getNome() == null || pessoa.getNome().isBlank()) {
            System.out.println("Pessoa inválido");
            return false;
        }
        return true;
    }

    public static boolean validaID(int id) {
        if (id <= 0) {
            System.out.println("ID inválido");
            return false;
        }
        return true;
    }

    public static boolean validaFuncionarioComId(FuncionarioModel funcionario) {
        return validaPessoa(funcionario) && validaID(funcionario.getId()) && validaSalario(funcionario.getSalario()) && validaCpf(funcionario.getCpf());
    }

    public static boolean validaFuncionarioSemId(FuncionarioModel funcionario) {
        return validaPessoa(funcionario) && validaSalario(funcionario.getSalario()) && validaCpf(funcionario.getCpf());
    }

    public static boolean validaGerenteComId(GerenteModel gerente) {
        return validaPessoa(gerente) && validaID(gerente.getId()) && validaSalario(gerente.getSalario()) && validaCpf(gerente.getCpf()) && validaEquipe(gerente.getEquipe());
    }

    public static boolean validaGerenteSemId(GerenteModel gerente) {
        return validaPessoa(gerente) && validaSalario(gerente.getSalario()) && validaCpf(gerente.getCpf()) && validaEquipe(gerente.getEquipe());
    }

}
