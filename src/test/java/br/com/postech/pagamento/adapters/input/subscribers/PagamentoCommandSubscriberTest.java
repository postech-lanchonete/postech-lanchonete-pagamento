package br.com.postech.pagamento.adapters.input.subscribers;

import br.com.postech.pagamento.adapters.adapter.PagamentoAdapter;
import br.com.postech.pagamento.adapters.dto.PagamentoRequestDTO;
import br.com.postech.pagamento.adapters.dto.PedidoDTO;
import br.com.postech.pagamento.drivers.external.DeadLetterQueueGateway;
import br.com.postech.pagamento.drivers.external.PagamentoGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PagamentoCommandSubscriberTest {
    @Spy
    private ObjectMapper objectMapper;

    @Spy
    private PagamentoAdapter pagamentoAdapter = Mappers.getMapper(PagamentoAdapter.class);;

    @Mock
    private PagamentoGateway pagamentoGateway;
    @Mock
    private DeadLetterQueueGateway deadLetterQueueGateway;

    @InjectMocks
    private PagamentoCommandSubscriber producaoSubscriber;

    @Test
    void pagar_Success() {
        String pagamentoJson = "{\"pedido\": {\"id\": 1, \"idCliente\": 100, \"produtos\":[{\"nome\":\"Batata frita\",\"preco\":\"10\"}]}}";
        PagamentoRequestDTO pagamentoRequestDTO = new PagamentoRequestDTO();
        pagamentoRequestDTO.setPedido(new PedidoDTO());
        pagamentoRequestDTO.getPedido().setIdCliente(1L);

        producaoSubscriber.pagar(pagamentoJson);

        verify(pagamentoGateway, times(1)).salvar(any());
    }

    @Test
    void pagar_Exception() {
        String pagamentoJson = "{\"pedido\": {\"idCliente\": 2}, \"valor\": 100.0}";

        producaoSubscriber.pagar(pagamentoJson);

        verify(deadLetterQueueGateway, times(1)).enviar(anyString(), anyString());
    }
}