package br.com.empresa;

import br.com.empresa.test.TesteGerenteDao;
import br.com.empresa.view.EmpresaView;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        EmpresaView program = new EmpresaView();

//        TesteConexao teste = new TesteConexao();
//        teste.testeConexao();
//        TesteFuncionarioDao testeFuncionarioDao = new TesteFuncionarioDao();
//        testeFuncionarioDao.testeFuncionario();
        TesteGerenteDao testeGerenteDao = new TesteGerenteDao();
        testeGerenteDao.testeGerente();

//        program.iniciaPrograma();

    }
}