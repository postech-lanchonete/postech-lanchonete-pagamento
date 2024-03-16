package br.com.postech.pagamento.adapters.gateways.implementation;

import br.com.postech.pagamento.business.exceptions.BadRequestException;
import br.com.postech.pagamento.core.entities.Pagamento;
import br.com.postech.pagamento.core.entities.Pedido;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.aggregator.ArgumentAccessException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoGatewayImplTest {
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private PedidoGatewayImpl producaoGateway;

    @Test
    void enviarConfirmacaoPagamento_DeveRetornarRespostaCorreta() throws JsonProcessingException {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(UUID.randomUUID());
        String jsonPagamento = "{\"id\":\"" + pagamento.getId() + "\"}";
        when(objectMapper.writeValueAsString(pagamento)).thenReturn(jsonPagamento);

        producaoGateway.enviarConfirmacaoPagamento(pagamento);

        verify(objectMapper, times(1)).writeValueAsString(pagamento);
        verify(kafkaTemplate, times(1)).send(anyString(), eq(jsonPagamento));
    }

    @Test
    void enviarConfirmacaoPagamento_DeveRetornarErroGeral() throws JsonProcessingException {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(UUID.randomUUID());
        when(objectMapper.writeValueAsString(pagamento)).thenThrow(ArgumentAccessException.class);

        assertThrows(BadRequestException.class, () -> producaoGateway.enviarConfirmacaoPagamento(pagamento));

        verify(objectMapper, times(1)).writeValueAsString(pagamento);
        verify(kafkaTemplate, times(0)).send(anyString(), anyString());
    }

    @Test
    void enviarConfirmacaoPagamento_DeveRetornarErroAoSerializar() throws JsonProcessingException {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(UUID.randomUUID());
        when(objectMapper.writeValueAsString(pagamento)).thenThrow(JsonProcessingException.class);

        assertThrows(BadRequestException.class, () -> producaoGateway.enviarConfirmacaoPagamento(pagamento));

        verify(objectMapper, times(1)).writeValueAsString(pagamento);
        verify(kafkaTemplate, times(0)).send(anyString(), anyString());
    }

}