package org.example.controller;

import org.example.model.*;

import java.util.Scanner;

public class EmpresaController {
    EmpresaModel empresa;

    public EmpresaController() {
        empresa = new EmpresaModel();
        carregarDados();
    }

    public void cadastrarFuncionario(Scanner sc) {
        FuncionarioModel funcionario = capturaFuncionario(sc);
        empresa.adicionaFuncionario(funcionario);
    }

    public void cadastrarGerente(Scanner sc) {
        GerenteModel gerente = capturaGerente(sc);
        empresa.adicionaGerente(gerente);
    }

    public void exibirFuncionarios() {
        empresa.exibirFuncionarios();
    }

    public void exibirGerentes() {
        empresa.exibirGerentes();
    }

    public FuncionarioModel capturaFuncionario(Scanner sc) {
        String msgNome = "Digite o nome: ";
        String nome = lerString(sc, msgNome);

        String msgCpf = "Digite o cpf: ";
        String cpf = lerString(sc, msgCpf);

        String msgSetor = "Digite o setor: ";
        String setor = lerString(sc, msgSetor);

        String msgSalario = "Digite o salario: ";
        double salario = lerDouble(sc, msgSalario);

        System.out.println("Cadastrado com sucesso!\n");

        return new FuncionarioModel(nome, cpf, setor, salario);
    }

    public GerenteModel capturaGerente(Scanner sc) {
        String msgNome = "Digite o nome: ";
        String nome = lerString(sc, msgNome);

        String msgCpf = "Digite o cpf: ";
        String cpf = lerString(sc, msgCpf);

        String msgSetor = "Digite o setor: ";
        String setor = lerString(sc, msgSetor);

        String msgSalario = "Digite o salario: ";
        double salario = lerDouble(sc, msgSalario);

        String msgEquipe = "Digite o numero de funcionarios na equipe: ";
        int equipe = lerInt(sc, msgEquipe);

        System.out.println("Cadastrado com sucesso!\n");
        return new GerenteModel(nome, cpf, setor, salario, equipe);
    }

    public String lerString(Scanner sc, String mensagem) {
        String entrada;
        while (true) {
            System.out.print(mensagem);
            entrada = sc.nextLine();

            if (ValidacoesModel.validaString(entrada)) {
                return entrada;
            } else {
                System.out.println("Entrada invalida. Tente novamente");
            }
        }

    }

    public Double lerDouble(Scanner sc, String mensagem) {
        double salario;
        while (true) {
            System.out.print(mensagem);

            if (sc.hasNextDouble()) {
                salario = sc.nextDouble();
                sc.nextLine();

                if (ValidacoesModel.validaSalario(salario)) {
                    return salario;
                } else {
                    System.out.println("Salario precisa ser positivo.");
                }

            } else {
                System.out.println("Entrada invalida");
                sc.nextLine();
            }

        }
    }

    public int lerInt(Scanner sc, String mensagem) {
        int equipe;

        while (true) {
            System.out.print(mensagem);
            if (sc.hasNextInt()) {
                equipe = sc.nextInt();
                sc.nextLine();
                if (ValidacoesModel.validaEquipe(equipe)) {
                    return equipe;
                } else {
                    System.out.println("Equipe precisa ser maior que 0");
                }
            } else {
                System.out.println("Numero invalido");
            }
        }
    }

    public void salvarDados (){
        empresa.salvarFuncionariosEmArquivo("src/dados/funcionarios.txt");
        empresa.salvarGerentesEmArquivo("src/dados/gerentes.txt");
        System.out.println("Salvo com sucesso!");
    }

    public void carregarDados(){
        empresa.carregarFuncionariosDeArquivo("src/dados/funcionarios.txt");
        empresa.carregarGerentesDeArquivos("src/dados/gerentes.txt");
        System.out.println("Dados carregados!");
    }

}
