package org.example.view;

import org.example.controller.EmpresaController;

import java.util.Scanner;

public class EmpresaView {
    Scanner sc = new Scanner(System.in);
    EmpresaController empresaController = new EmpresaController();

    public void iniciaPrograma(){
        int option = -1;

        do {
            System.out.println("--------- SISTEMA DA EMPRESA ---------");
            System.out.println("1 - Cadastrar funcionário" +
                             "\n2 - Cadastrar Gerente" +
                             "\n3 - Exibir funcionarios cadastrados" +
                             "\n4 - Exibir Gerentes cadastrados" +
                             "\n5 - Salvar Dados" +
                             "\n0 - Sair");
            System.out.print("Digite a opção desejada: ");

            try {
                option = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e){
                System.out.println("Entrada inválida. Digite um número");
                option = -1;
                continue;
            }

            System.out.println("--------------------------------------");
            System.out.println();

            optionResponse(option);
        } while (option != 0);
    }

    private void optionResponse(int option){
        switch (option){
            case 1:
                empresaController.cadastrarFuncionario(sc);
                break;
            case 2:
                empresaController.cadastrarGerente(sc);
                break;
            case 3:
                empresaController.exibirFuncionarios();
                break;
            case 4:
                empresaController.exibirGerentes();
                break;
            case 5:
                empresaController.salvarDados();
                break;
            case 0:
                confirmaSaida(sc);
                break;
            default:
                System.out.println("Opção inválida");
        }
    }

    private void confirmaSaida(Scanner sc){
        System.out.print("Deseja sair sem salvar? (S/N) : ");
        String resp = sc.nextLine();

        if(resp.equalsIgnoreCase("n")) {
            empresaController.salvarDados();
            System.out.println("Saindo....");
            System.exit(0);
        } else if (resp.equalsIgnoreCase("s")){
            System.out.println("Saindo sem salvar ...");
            System.exit(0);
        } else {
            System.out.println("Opção inválida, voltando ao menu ...");
        }
    }
}
