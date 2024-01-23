package br.com.postech_lanchonete_pagamento.business.usecases.implementation.pagamento;

import br.com.postech_lanchonete_pagamento.adapters.gateways.PagamentoGateway;
import br.com.postech_lanchonete_pagamento.business.usecases.RealizarPagamentoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RealizarPagamentoUseCaseTest {

    @Mock
    private PagamentoGateway pagamentoGateway;

    @InjectMocks
    private RealizarPagamentoUseCase realizarPagamentoUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testPagamentoAprovado() {

    }

}
