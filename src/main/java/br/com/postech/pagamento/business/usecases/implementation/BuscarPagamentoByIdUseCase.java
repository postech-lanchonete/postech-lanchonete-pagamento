package br.com.postech.pagamento.business.usecases.implementation;

import br.com.postech.pagamento.drivers.external.PagamentoGateway;
import br.com.postech.pagamento.business.exceptions.NotFoundException;
import br.com.postech.pagamento.business.usecases.UseCase;
import br.com.postech.pagamento.core.entities.Pagamento;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Component("buscarPagamentoByIdUseCase")
public class BuscarPagamentoByIdUseCase implements UseCase<UUID, Pagamento> {

    private final PagamentoGateway pagamentoGateway;

    @Override
    public Pagamento realizar(UUID id) {
        log.debug("Iniciando busca por pagamento com id {}", id);
        Example<Pagamento> pagamentoExample = Example.of(Pagamento.builder().id(id).build());
        List<Pagamento> pagamentos = pagamentoGateway.buscarPor(pagamentoExample);
        if (CollectionUtils.isEmpty(pagamentos)) {
            log.debug("Nenhum pagamento encontrado com id {}", id);
            throw new NotFoundException("Pagamento não encontrado com o id: " + id);
        }
        return pagamentos.get(0);
    }
}
