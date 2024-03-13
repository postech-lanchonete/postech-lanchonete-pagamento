package br.com.postech.pagamento.business.usecases;

import br.com.postech.pagamento.adapters.gateways.PagamentoGateway;
import br.com.postech.pagamento.core.entities.Pagamento;
import br.com.postech.pagamento.core.enums.StatusPagamento;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Component("buscarPagamentoByStatusUseCase")
public class BuscarPagamentosByStatusUseCase implements UseCase<StatusPagamento, List<Pagamento>> {

    private final PagamentoGateway pagamentoGateway;

    @Override
    public List<Pagamento> realizar(StatusPagamento status) {
        log.debug("Iniciando busca por pagamento com status {}", status);
        Example<Pagamento> pagamentoExample = Example.of(Pagamento.builder().status(status).build());
        return pagamentoGateway.buscarPor(pagamentoExample);
    }
}
