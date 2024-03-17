package br.com.postech.pagamento.business.usecases;

import br.com.postech.pagamento.drivers.external.PagamentoGateway;
import br.com.postech.pagamento.business.usecases.implementation.RealizarPagamentoUseCase;
import br.com.postech.pagamento.core.entities.Pagamento;
import br.com.postech.pagamento.core.enums.StatusPagamento;
import br.com.postech.pagamento.stub.PagamentoStub;
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
class RealizarPagamentoUseCaseTest {

    @Mock
    private PagamentoGateway pagamentoGateway;

    @InjectMocks
    private RealizarPagamentoUseCase realizarPagamentoUseCase;

    @Test
    void realizarPagamento_DeveRetornarPagamentoComStatusAprovado_QuandoRealizadoComSucesso() {
        Pagamento pagamento = PagamentoStub.createPagamentoDocument();
        BigDecimal valorTotal = BigDecimal.valueOf(25);
        when(pagamentoGateway.salvar(pagamento)).thenReturn(pagamento);

        Pagamento resultado = realizarPagamentoUseCase.realizar(pagamento);

        assertEquals(StatusPagamento.APROVADO, resultado.getStatus());
        assertEquals(valorTotal, resultado.getValor());
        verify(pagamentoGateway, times(2)).salvar(pagamento);
    }
}
