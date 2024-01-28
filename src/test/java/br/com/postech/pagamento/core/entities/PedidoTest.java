package br.com.postech.pagamento.core.entities;

import br.com.postech.pagamento.business.exceptions.NegocioException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PedidoTest {
    @Test
    void criarPedidoComClienteEProdutosDeveAtribuirDataCriacaoEValidar() {
        Cliente cliente = mock(Cliente.class);
        Produto produto1 = mock(Produto.class);
        Produto produto2 = mock(Produto.class);

        Pedido pedido = new Pedido(cliente, Arrays.asList(produto1, produto2));

        assertNotNull(pedido.getDataCriacao());
        assertEquals(cliente, pedido.getCliente());
        assertEquals(Arrays.asList(produto1, produto2), pedido.getProdutos());
    }

    @Test
    void criarPedidoSemProdutosDeveLancarExcecao() {
        Cliente cliente = mock(Cliente.class);

        assertThrows(NegocioException.class, () -> {
            List<Produto> produtosVazia = Collections.emptyList();
            new Pedido(cliente, produtosVazia);
        });
    }

    @Test
    void calcularValorTotalDeveRetornarSomaDosPrecosDosProdutos() {
        Cliente cliente = mock(Cliente.class);
        Produto produto1 = new Produto("Produto1", BigDecimal.TEN);
        Produto produto2 = new Produto("Produto2", new BigDecimal("15.50"));

        Pedido pedido = new Pedido(cliente, Arrays.asList(produto1, produto2));

        assertEquals(new BigDecimal("25.50"), pedido.calculaValorTotal());
    }
}