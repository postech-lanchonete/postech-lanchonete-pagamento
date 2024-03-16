package br.com.postech.pagamento.adapters.input.subscribers;

import br.com.postech.pagamento.adapters.adapter.PagamentoAdapter;
import br.com.postech.pagamento.adapters.dto.PagamentoRequestDTO;
import br.com.postech.pagamento.adapters.dto.PedidoDTO;
import br.com.postech.pagamento.adapters.gateways.DeadLetterQueueGateway;
import br.com.postech.pagamento.adapters.gateways.PagamentoGateway;
import br.com.postech.pagamento.adapters.gateways.PedidoGateway;
import br.com.postech.pagamento.business.usecases.RealizarPagamentoUseCase;
import br.com.postech.pagamento.core.entities.Pagamento;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagamentoCommandSubscriberTest {
    @Spy
    private ObjectMapper objectMapper;

    @Spy
    private PagamentoAdapter pagamentoAdapter;

    @Mock
    private PagamentoGateway pagamentoGateway;
    @Mock
    private DeadLetterQueueGateway deadLetterQueueGateway;

    @InjectMocks
    private PagamentoCommandSubscriber producaoSubscriber;

    @Test
    void pagar_Success() {
        String pagamentoJson = "{\"pedido\": {\"id\": 1, \"idCliente\": 100}}";
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