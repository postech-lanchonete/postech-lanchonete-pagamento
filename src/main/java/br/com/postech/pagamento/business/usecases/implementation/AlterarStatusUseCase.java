package br.com.postech.pagamento.business.usecases.implementation;

import br.com.postech.pagamento.adapters.dto.AlteraStatusDTO;
import br.com.postech.pagamento.drivers.external.PedidoGateway;
import br.com.postech.pagamento.business.usecases.UseCase;
import br.com.postech.pagamento.business.usecases.UseCaseSemResposta;
import br.com.postech.pagamento.core.entities.Pagamento;
import br.com.postech.pagamento.core.enums.StatusPagamento;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component("alteraStatusUseCase")
public class AlterarStatusUseCase implements UseCaseSemResposta<AlteraStatusDTO> {

    private final UseCase<Pagamento, Pagamento> realizarPagamentoUseCase;
    private final UseCase<Pagamento, Pagamento> desfazerPagamentoUseCase;
    private final UseCase<UUID, Pagamento> buscarPagamentoByIdUseCase;
    private final PedidoGateway pedidoGateway;

    public AlterarStatusUseCase(@Qualifier("realizarPagamentoUseCase") UseCase<Pagamento, Pagamento> realizarPagamentoUseCase,
                                @Qualifier("desfazerPagamentoUseCase") UseCase<Pagamento, Pagamento> desfazerPagamentoUseCase,
                                @Qualifier("buscarPagamentoByIdUseCase") UseCase<UUID, Pagamento> buscarPagamentoByIdUseCase,
                                PedidoGateway pedidoGateway) {
        this.realizarPagamentoUseCase = realizarPagamentoUseCase;
        this.desfazerPagamentoUseCase = desfazerPagamentoUseCase;
        this.buscarPagamentoByIdUseCase = buscarPagamentoByIdUseCase;
        this.pedidoGateway = pedidoGateway;
    }

    @Override
    @Transactional
    public void realizar(AlteraStatusDTO entrada) {
        assert entrada.getStatusPagamento() == StatusPagamento.APROVADO || entrada.getStatusPagamento() == StatusPagamento.REPROVADO;
        Pagamento pagamento = buscarPagamentoByIdUseCase.realizar(UUID.fromString(entrada.getId()));
        if (entrada.getStatusPagamento() == StatusPagamento.APROVADO) {
            this.realizarPagamentoUseCase.realizar(pagamento);
        } else {
            this.desfazerPagamentoUseCase.realizar(pagamento);
        }
        pedidoGateway.enviarConfirmacaoPagamento(pagamento);
    }
}
