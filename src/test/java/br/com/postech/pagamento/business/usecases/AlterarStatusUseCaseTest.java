package br.com.postech.pagamento.business.usecases;

import br.com.postech.pagamento.adapters.dto.AlteraStatusDTO;
import br.com.postech.pagamento.adapters.gateways.PedidoGateway;
import br.com.postech.pagamento.core.entities.Pagamento;
import br.com.postech.pagamento.core.enums.StatusPagamento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;


class AlterarStatusUseCaseTest {

    @Test
    void realizar_deveDarErroDeAssert_quandoStatusNaoSejaAprovadoOuReprovado() {
        UseCase<Pagamento, Pagamento> realizarPagamentoUseCaseMock = mock(UseCase.class);
        UseCase<Pagamento, Pagamento> desfazerPagamentoUseCaseMock = mock(UseCase.class);
        UseCase<UUID, Pagamento> buscarPagamentoByIdUseCaseMock = mock(UseCase.class);
        PedidoGateway pedidoGatewayMock = mock(PedidoGateway.class);

        AlterarStatusUseCase alterarStatusUseCase = new AlterarStatusUseCase(
                realizarPagamentoUseCaseMock,
                desfazerPagamentoUseCaseMock,
                buscarPagamentoByIdUseCaseMock,
                pedidoGatewayMock
        );
        var pagamento = new AlteraStatusDTO("", StatusPagamento.PENDENTE);
        assertThrows(AssertionError.class, () -> alterarStatusUseCase.realizar(pagamento));
    }

    @Test
    void realizar_StatusAprovado_DeveChamarRealizarPagamentoUseCase() {
        UseCase<Pagamento, Pagamento> realizarPagamentoUseCaseMock = mock(UseCase.class);
        UseCase<Pagamento, Pagamento> desfazerPagamentoUseCaseMock = mock(UseCase.class);
        UseCase<UUID, Pagamento> buscarPagamentoByIdUseCaseMock = mock(UseCase.class);
        PedidoGateway pedidoGatewayMock = mock(PedidoGateway.class);

        AlterarStatusUseCase alterarStatusUseCase = new AlterarStatusUseCase(
                realizarPagamentoUseCaseMock,
                desfazerPagamentoUseCaseMock,
                buscarPagamentoByIdUseCaseMock,
                pedidoGatewayMock
        );

        AlteraStatusDTO entrada = new AlteraStatusDTO(UUID.randomUUID().toString(), StatusPagamento.APROVADO);
        Pagamento pagamento = new Pagamento();

        when(buscarPagamentoByIdUseCaseMock.realizar(any(UUID.class))).thenReturn(pagamento);

        alterarStatusUseCase.realizar(entrada);

        verify(realizarPagamentoUseCaseMock, times(1)).realizar(eq(pagamento));

        verifyNoInteractions(desfazerPagamentoUseCaseMock);

        verify(pedidoGatewayMock, times(1)).enviarConfirmacaoPagamento(eq(pagamento));
    }

    @Test
    void realizar_StatusReprovado_DeveChamarDesfazerPagamentoUseCase() {
        UseCase<Pagamento, Pagamento> realizarPagamentoUseCaseMock = mock(UseCase.class);
        UseCase<Pagamento, Pagamento> desfazerPagamentoUseCaseMock = mock(UseCase.class);
        UseCase<UUID, Pagamento> buscarPagamentoByIdUseCaseMock = mock(UseCase.class);
        PedidoGateway pedidoGatewayMock = mock(PedidoGateway.class);

        AlterarStatusUseCase alterarStatusUseCase = new AlterarStatusUseCase(
                realizarPagamentoUseCaseMock,
                desfazerPagamentoUseCaseMock,
                buscarPagamentoByIdUseCaseMock,
                pedidoGatewayMock
        );

        AlteraStatusDTO entrada = new AlteraStatusDTO(UUID.randomUUID().toString(), StatusPagamento.REPROVADO);
        Pagamento pagamento = new Pagamento();

        when(buscarPagamentoByIdUseCaseMock.realizar(any(UUID.class))).thenReturn(pagamento);

        alterarStatusUseCase.realizar(entrada);

        verify(desfazerPagamentoUseCaseMock, times(1)).realizar(eq(pagamento));

        verifyNoInteractions(realizarPagamentoUseCaseMock);

        verify(pedidoGatewayMock, times(1)).enviarConfirmacaoPagamento(eq(pagamento));
    }
}