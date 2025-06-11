package controller;

import br.com.empresa.controller.FuncionarioController;
import br.com.empresa.model.FuncionarioModel;
import br.com.empresa.service.FuncionarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FuncionarioControllerTest {
    private FuncionarioController controller;
    private FuncionarioService serviceMock;
    private FuncionarioModel funcionarioTeste = new FuncionarioModel("Teste", "12345678910", "Tecnologia", 2500);
    private FuncionarioModel funcionarioCopia = new FuncionarioModel(funcionarioTeste.getNome(), funcionarioTeste.getCpf(), funcionarioTeste.getSetor(), funcionarioTeste.getSalario());

    @BeforeEach
    void setUp() {
        serviceMock = Mockito.mock(FuncionarioService.class);
        controller = new FuncionarioController(serviceMock);
    }

    @Test
    void deveCriarFuncionarioComSucesso() {
        when(serviceMock.inserir(funcionarioTeste)).thenReturn(42);

        int id = controller.criarFuncionario(funcionarioTeste);

        assertEquals(42, id);
        verify(serviceMock).inserir(funcionarioTeste);
    }

    @Test
    void naoDeveCriarFuncionarioNull(){
        FuncionarioModel funcionario = null;

        when(serviceMock.inserir(funcionario)).thenReturn(-1);

        int resultado = controller.criarFuncionario(funcionario);

        assertEquals(-1, resultado);
        verify(serviceMock, never()).inserir(any());
    }

    @Test
    void deveAtualizarFuncionario(){
        FuncionarioModel funcionario = new FuncionarioModel("Teste", "12345678910", "Tecnologia", 2500);
        funcionario.setId(1);
        when(serviceMock.atualizar(funcionario)).thenReturn(true);

        boolean sucesso = controller.atualizarFuncionario(funcionario);

        assertTrue(sucesso);
        verify(serviceMock).atualizar(funcionario);
    }

    @Test
    void naoDeveAtualizarFuncionarioComIdInvalido(){
        FuncionarioModel funcionario = new FuncionarioModel();
        funcionario.setId(-1);

        when(serviceMock.atualizar(funcionario)).thenReturn(false);

        boolean sucesso = controller.atualizarFuncionario(funcionario);

        assertFalse(sucesso);
        verify(serviceMock, never()).atualizar(any());
    }

    @Test
    void naoDeveAtualizarFuncionarioQuandoServiceFalhar(){
        funcionarioCopia.setId(123);

        when(serviceMock.atualizar(funcionarioCopia)).thenReturn(false);

        boolean sucesso = controller.atualizarFuncionario(funcionarioCopia);

        assertFalse(sucesso);
        verify(serviceMock).atualizar(funcionarioCopia);
    }

    @Test
    void naoDeveAtualizarFuncionarioNull(){
        FuncionarioModel funcionario = null;

        when(serviceMock.atualizar(funcionario)).thenReturn(false);

        boolean sucesso = controller.atualizarFuncionario(funcionario);

        assertFalse(sucesso);
        verify(serviceMock, never()).atualizar(any());
    }

    @Test
    void deveRemoverFuncionario(){
        FuncionarioModel funcionario = new FuncionarioModel();
        funcionario.setId(1);
        when(serviceMock.remover(1)).thenReturn(true);

        boolean sucesso = controller.removerFuncionario(funcionario.getId());
        assertTrue(sucesso);
        verify(serviceMock).remover(1);
    }

    @Test
    void naoDeveRemoverFuncionarioQuandoIdInexistente(){
        int id = 999;
        when(serviceMock.remover(id)).thenReturn(false);

        boolean sucesso = controller.removerFuncionario(id);

        assertFalse(sucesso);
        verify(serviceMock).remover(id);
    }

    @Test
    void naoDeveRemoverFuncionarioQuandoIdInvalido(){
        int idInvalido = -1;

        when(serviceMock.remover(idInvalido)).thenReturn(false);

        boolean falha = controller.removerFuncionario(idInvalido);
        assertFalse(falha);
        verify(serviceMock, never()).remover(anyInt());
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
    void naoDeveBuscarFuncionarioQuandoIdInvalido(){
        int idInvalido = -1;
        when(serviceMock.buscaPorId(idInvalido)).thenReturn(null);

        FuncionarioModel funcionario = controller.buscarFuncionarioPorId(idInvalido);

        assertNull(funcionario);
        verify(serviceMock, never()).buscaPorId(anyInt());
    }

    @Test
    void deveListarTodosFuncionarios() {
        when(serviceMock.listarTodos()).thenReturn(Arrays.asList(new FuncionarioModel(), new FuncionarioModel()));

        List<FuncionarioModel> lista = controller.listarTodosFuncionarios();

        assertEquals(2, lista.size());
        verify(serviceMock).listarTodos();
    }


}
