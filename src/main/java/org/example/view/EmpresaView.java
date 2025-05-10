package org.example.view;

import org.example.controller.EmpresaController;
import org.example.model.EmpresaModel;
import org.example.model.GerenteModel;

import java.util.Scanner;

public class EmpresaView {
    Scanner sc = new Scanner(System.in);
    EmpresaController empresaController = new EmpresaController();

    public void iniciaPrograma(){
        int option = 1;
        do {
            System.out.println("--------- SISTEMA DA EMPRESA ---------");
            System.out.println("1 - Cadastrar funcionário" +
                             "\n2 - Cadastrar Gerente" +
                             "\n3 - Exibir funcionarios cadastrados" +
                             "\n4 - Exibir Gerentes cadastrados" +
                             "\n0 - Sair");
            System.out.print("Digite a opção desejada: ");
            option = sc.nextInt();
            System.out.println("--------------------------------------");
            System.out.println();
            sc.nextLine();
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
            case 0:
                System.out.println("Saindo....");
                break;
            default:
                System.out.println("Opção inválida");
        }
    }
}
