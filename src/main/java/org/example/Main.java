package org.example;

import org.example.test.TesteConexao;
import org.example.test.TesteFuncionarioDao;
import org.example.view.EmpresaView;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        EmpresaView program = new EmpresaView();

//        TesteConexao teste = new TesteConexao();
//        teste.testeConexao();
//        TesteFuncionarioDao testeFuncionarioDao = new TesteFuncionarioDao();
//        testeFuncionarioDao.testeFuncionario();

        program.iniciaPrograma();

    }
}