package br.com.postech.pagamento.business.usecases;

import br.com.postech.pagamento.adapters.dto.AlteraStatusDTO;
import br.com.postech.pagamento.adapters.gateways.PedidoGateway;
import br.com.postech.pagamento.core.enums.StatusPagamento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AlterarStatusUseCaseTest {

    @Mock
    private RealizarPagamentoUseCase realizarPagamentoUseCase;
    @Mock
    private DesfazerPagamentoUseCase desfazerPagamentoUseCase;
    @Mock
    private BuscarPagamentoByIdUseCase buscarPagamentoByIdUseCase;
    @Mock
    private PedidoGateway pedidoGateway;
    @InjectMocks
    private AlterarStatusUseCase alterarStatusUseCase;

    @Test
    void realizar_deveDarErroDeAssert_quandoStatusNaoSejaAprovadoOuReprovado() {
        var pagamento = new AlteraStatusDTO("", StatusPagamento.PENDENTE);
        assertThrows(AssertionError.class, () -> alterarStatusUseCase.realizar(pagamento));
    }

//    @Test
//    void realizar_deveChamarRealizarPagamentoUseCase_quandoAlteracaoForAprovado() {
//        UUID uuid = UUID.randomUUID();
//        AlteraStatusDTO pagamentoDto = new AlteraStatusDTO(uuid.toString(), StatusPagamento.APROVADO);
//        Pagamento pagamento = new Pagamento();
//        when(buscarPagamentoByIdUseCase.realizar(any(UUID.class))).thenReturn(pagamento);
//        alterarStatusUseCase.realizar(pagamentoDto);
//        verify(realizarPagamentoUseCase, times(1)).realizar(any());
//        verify(pedidoGateway, times(1)).enviarConfirmacaoPagamento(any());
//        verifyNoInteractions(desfazerPagamentoUseCase);
//    }
//
//    @Test
//    void realizar_deveChamarDesfazerPagamentoUseCase_quandoAlteracaoForReprovado() {
//        var pagamentoDto = new AlteraStatusDTO(UUID.randomUUID().toString(), StatusPagamento.REPROVADO);
//        var pagamento = new Pagamento();
//        when(buscarPagamentoByIdUseCase.realizar(any())).thenReturn(pagamento);
//        alterarStatusUseCase.realizar(pagamentoDto);
//        verify(desfazerPagamentoUseCase, times(1)).realizar(any());
//        verify(pedidoGateway, times(1)).enviarConfirmacaoPagamento(any());
//        verifyNoInteractions(realizarPagamentoUseCase);
//    }

}