package br.com.postech.pagamento.business.usecases;

import br.com.postech.pagamento.drivers.external.PagamentoGateway;
import br.com.postech.pagamento.business.usecases.implementation.BuscarPagamentosByStatusUseCase;
import br.com.postech.pagamento.core.entities.Pagamento;
import br.com.postech.pagamento.core.enums.StatusPagamento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarPagamentosByStatusUseCaseTest {

    @Mock
    private PagamentoGateway pagamentoGateway;

    @InjectMocks
    private BuscarPagamentosByStatusUseCase buscarPagamentosByStatusUseCase;

    @Test
    void buscarPagamentosPorStatus_DeveRetornarListaQuandoEncontrado() {
        StatusPagamento status = StatusPagamento.APROVADO;
        Pagamento pagamento1 = criarPagamento(status);
        Pagamento pagamento2 = criarPagamento(status);
        when(pagamentoGateway.buscarPor(any(Example.class))).thenReturn(Arrays.asList(pagamento1, pagamento2));

        List<Pagamento> resultados = buscarPagamentosByStatusUseCase.realizar(status);

        assertEquals(2, resultados.size());
        assertEquals(status, resultados.get(0).getStatus());
        assertEquals(status, resultados.get(1).getStatus());
        verify(pagamentoGateway, times(1)).buscarPor(any(Example.class));
    }

    @Test
    void buscarPagamentosPorStatus_DeveRetornarListaVaziaQuandoNaoEncontrado() {
        StatusPagamento status = StatusPagamento.PENDENTE;
        when(pagamentoGateway.buscarPor(any(Example.class))).thenReturn(Collections.emptyList());

        List<Pagamento> resultados = buscarPagamentosByStatusUseCase.realizar(status);

        assertEquals(0, resultados.size());
        verify(pagamentoGateway, times(1)).buscarPor(any(Example.class));
    }

    private Pagamento criarPagamento(StatusPagamento status) {
        return Pagamento.builder().status(status).build();
    }
}
