package br.com.postech.pagamento.business.usecases;

import br.com.postech.pagamento.adapters.gateways.PagamentoGateway;
import br.com.postech.pagamento.core.enums.StatusPagamento;
import br.com.postech.pagamento.core.entities.Pagamento;
import br.com.postech.pagamento.core.entities.Produto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@AllArgsConstructor
@Component("realizarPagamentoUseCase")
public class RealizarPagamentoUseCase implements UseCase<Pagamento, Pagamento> {

    private final PagamentoGateway pagamentoGateway;

    @Override
    public Pagamento realizar(Pagamento pagamento) {
        var valorTotal = pagamento.getPedido().getProdutos().stream()
                .map(Produto::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        pagamento.setValor(valorTotal);
        pagamento.setStatus(StatusPagamento.PENDENTE);
        this.pagamentoGateway.salvar(pagamento);
        log.info("Realizando pagamento do cliente {}...", pagamento.getPedido().getCliente().getCpf());
        pagamento.setStatus(StatusPagamento.APROVADO);
        log.info("Pagamento realizado com sucesso");
        return this.pagamentoGateway.salvar(pagamento);
    }
}
