package br.com.postech.pagamento.stub;

import br.com.postech.pagamento.adapters.dto.ClienteDTO;
import br.com.postech.pagamento.adapters.dto.PagamentoRequestDTO;
import br.com.postech.pagamento.adapters.dto.PedidoDTO;
import br.com.postech.pagamento.adapters.dto.ProdutoDTO;
import br.com.postech.pagamento.core.entities.Pedido;
import br.com.postech.pagamento.core.enums.StatusPagamento;
import br.com.postech.pagamento.core.entities.Cliente;
import br.com.postech.pagamento.core.entities.Pagamento;
import br.com.postech.pagamento.core.entities.Produto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

public class PagamentoStub {
    public static PagamentoRequestDTO createPagamentoRequest() {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setProdutos(Arrays.asList(
                new ProdutoDTO("Hamburguer", BigDecimal.valueOf(20)),
                new ProdutoDTO("Suco Laranja", BigDecimal.valueOf(5))
        ));

        ClienteDTO clienteDTO = new ClienteDTO("Antonio", "Machado", "11111111111", "antonio.machado@gmail.com");
        var response = new PagamentoRequestDTO();
        pedidoDTO.setCliente(clienteDTO);
        response.setPedido(pedidoDTO);
        return response;
    }

    public static Pagamento createPagamentoDocument() {
        Pedido pedido = new Pedido();
        pedido.setProdutos(Arrays.asList(
                new Produto("Hamburguer", BigDecimal.valueOf(20)),
                new Produto("Suco Laranja", BigDecimal.valueOf(5))
        ));

        Cliente cliente = new Cliente("Antonio", "Machado", "11111111111", "antonio.machado@gmail.com");
        pedido.setCliente(cliente);
        return Pagamento.builder()
                .id(UUID.randomUUID())
                .status(StatusPagamento.APROVADO)
                .valor(BigDecimal.valueOf(25))
                .pedido(pedido)
                .build();
    }
}
