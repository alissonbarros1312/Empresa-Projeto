package controller;

import br.com.empresa.controller.FuncionarioController;
import br.com.empresa.model.FuncionarioModel;
import br.com.empresa.service.FuncionarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FuncionarioControllerTest {
    private FuncionarioController controller;
    private FuncionarioService serviceMock;

    @BeforeEach
    void setUp() {
        serviceMock = Mockito.mock(FuncionarioService.class);
        controller = new FuncionarioController(serviceMock);
    }

    @Test
    void deveCriarFuncionarioComSucesso() {
        FuncionarioModel funcionario = new FuncionarioModel();
        when(serviceMock.inserir(funcionario)).thenReturn(42);

        int id = controller.criarFuncionario(funcionario);

        assertEquals(42, id);
        verify(serviceMock).inserir(funcionario);
    }

    @Test
    void deveRetornarFuncionarioPorId() {
        FuncionarioModel funcionario = new FuncionarioModel();
        funcionario.setId(1);

        when(serviceMock.buscaPorId(1)).thenReturn(funcionario);

        FuncionarioModel resultado = controller.buscarFuncionarioPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    void deveListarTodosFuncionarios() {
        when(serviceMock.listarTodos()).thenReturn(Arrays.asList(new FuncionarioModel(), new FuncionarioModel()));

        List<FuncionarioModel> lista = controller.listarTodosFuncionarios();

        assertEquals(2, lista.size());
        verify(serviceMock).listarTodos();
    }

}
