package org.example;

import org.example.abstractclass.Funcionario;
import org.example.abstractclass.Gerente;
import org.example.controller.EmpresaController;
import org.example.view.EmpresaView;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//        Funcionario f1 = new Funcionario("Alisson", "123.456.789-00", "TI", 2500.0);
//        Gerente g1 = new Gerente("Sara", "987.654.321-00", "RH", 3500.0, 5);
//
//        f1.exibirInfo();
//        System.out.println();
//        g1.exibirInfo();

        EmpresaView program = new EmpresaView();
        program.iniciaPrograma();


    }
}