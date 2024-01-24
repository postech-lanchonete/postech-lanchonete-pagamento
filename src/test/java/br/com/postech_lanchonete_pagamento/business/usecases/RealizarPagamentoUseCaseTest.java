package br.com.postech_lanchonete_pagamento.business.usecases;

import br.com.postech_lanchonete_pagamento.adapters.gateways.PagamentoGateway;
import br.com.postech_lanchonete_pagamento.core.entities.Pagamento;
import br.com.postech_lanchonete_pagamento.core.enums.StatusPagamento;
import br.com.postech_lanchonete_pagamento.stub.PagamentoStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RealizarPagamentoUseCaseTest {

    @Mock
    private PagamentoGateway pagamentoGateway;

    @InjectMocks
    private RealizarPagamentoUseCase realizarPagamentoUseCase;

    @Test
    public void realizarPagamento_DeveRetornarPagamentoComStatusAprovado_QuandoRealizadoComSucesso() {
        Pagamento pagamento = PagamentoStub.createPagamentoDocument();
        BigDecimal valorTotal = BigDecimal.valueOf(25);
        when(pagamentoGateway.salvar(pagamento)).thenReturn(pagamento);

        Pagamento resultado = realizarPagamentoUseCase.realizar(pagamento);

        assertEquals(StatusPagamento.APROVADO, resultado.getStatus());
        assertEquals(valorTotal, resultado.getValor());
        verify(pagamentoGateway, times(2)).salvar(pagamento);
    }
}