package controller;

import br.com.empresa.controller.GerenteController;
import br.com.empresa.model.FuncionarioModel;
import br.com.empresa.model.GerenteModel;
import br.com.empresa.service.GerenteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GerenteControllerTest {
    private GerenteController controller;
    private GerenteService serviceMock;
    private GerenteModel gerente = new GerenteModel("Teste", "12345678910", "Tecnologia", 2500, 2);
    private GerenteModel gerenteCopia = new GerenteModel(gerente.getNome(), gerente.getCpf(), gerente.getSetor(), gerente.getSalario(), gerente.getEquipe());

    @BeforeEach
    void setUp(){
        serviceMock = Mockito.mock(GerenteService.class);
        controller = new GerenteController(serviceMock);
    }

    @Test
    void deveInserirGerenteComSucesso(){
        when(serviceMock.inserir(gerente)).thenReturn(1);

        int id = controller.inserirGerente(gerente);
        assertEquals(1, id);
        verify(serviceMock).inserir(gerente);
    }

    @Test
    void naoDeveInserirGerenteNull(){
        GerenteModel gerenteNull = null;
        when(serviceMock.inserir(gerenteNull)).thenReturn(-1);

        int resultado = controller.inserirGerente(gerenteNull);
        assertEquals(-1, resultado);
        verify(serviceMock, never()).inserir(any());
    }

    @Test
    void naoDeveInserirComDadosInvalidos(){
        GerenteModel gerenteTeste = new GerenteModel();
        gerenteTeste.setNome(" ");
        when(serviceMock.inserir(gerenteTeste)).thenReturn(-1);

        int resultado = controller.inserirGerente(gerenteTeste);
        assertEquals(-1, resultado);
        verify(serviceMock, never()).inserir(any());
    }

    @Test
    void deveAtualizarGerenteComSucesso(){
        gerente.setId(1);
        when(serviceMock.atualizar(gerente)).thenReturn(true);

        boolean sucesso = controller.atualizarGerente(gerente);
        assertTrue(sucesso);
        verify(serviceMock).atualizar(gerente);
    }

    @Test
    void naoDeveAtualizarGerenteComIdInvalido(){
        gerente.setId(-1);
        when(serviceMock.atualizar(gerente)).thenReturn(false);

        boolean resultado = controller.atualizarGerente(gerente);

        assertFalse(resultado);
        verify(serviceMock, never()).atualizar(any());
    }

    @Test
    void naoDeveRemoverGerenteComIdInexistente(){
        int idInvalido = 999;
        when(serviceMock.remover(idInvalido)).thenReturn(false);

        boolean resultado = controller.removerGerente(idInvalido);
        assertFalse(resultado);
        verify(serviceMock, never()).atualizar(any());
    }

    @Test
    void deveRemoverGerenteComSucesso(){
        int id = 1;

        when(serviceMock.remover(id)).thenReturn(true);

        boolean sucesso = controller.removerGerente(id);
        assertTrue(sucesso);
        verify(serviceMock).remover(id);
    }

    @Test
    void deveBuscarGerentePorIdComSucesso(){
        GerenteModel gerente = new GerenteModel();
        int id = 23;

        when(serviceMock.buscarPorId(id)).thenReturn(gerente);

        GerenteModel resultado = controller.buscarGerentePorId(id);

        assertNotNull(resultado);
        verify(serviceMock).buscarPorId(id);
    }

    @Test
    void deveListarTodosGerentes(){
        when(serviceMock.listarTodos()).thenReturn(Arrays.asList(new GerenteModel(), new GerenteModel()));

        List<GerenteModel> resultado = controller.listarTodos();

        assertEquals(2, resultado.size());
        verify(serviceMock).listarTodos();
    }
}
