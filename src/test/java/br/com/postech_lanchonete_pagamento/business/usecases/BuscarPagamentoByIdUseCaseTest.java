package br.com.postech_lanchonete_pagamento.business.usecases;

import br.com.postech_lanchonete_pagamento.adapters.gateways.PagamentoGateway;
import br.com.postech_lanchonete_pagamento.business.exceptions.NotFoundException;
import br.com.postech_lanchonete_pagamento.core.entities.Pagamento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarPagamentoByIdUseCaseTest {

    @Mock
    private PagamentoGateway pagamentoGateway;

    @InjectMocks
    private BuscarPagamentoByIdUseCase buscarPagamentoByIdUseCase;

    @Test
    public void buscarPagamentoPorId_DeveRetornarPagamentoExistente_QuandoEncontrado() {
        UUID id = UUID.randomUUID();
        Pagamento pagamentoExistente = criarPagamento(id);
        when(pagamentoGateway.buscarPor(any(Example.class))).thenReturn(Collections.singletonList(pagamentoExistente));

        Pagamento resultado = buscarPagamentoByIdUseCase.realizar(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(pagamentoGateway, times(1)).buscarPor(any(Example.class));
    }

    @Test
    public void buscarPagamentoPorId_DeveLancarNotFoundException_QuandoNaoEncontrado() {
        UUID id = UUID.randomUUID();
        when(pagamentoGateway.buscarPor(any(Example.class))).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class, () -> buscarPagamentoByIdUseCase.realizar(id));
    }

    private Pagamento criarPagamento(UUID id) {
        return Pagamento.builder().id(id).build();
    }
}
