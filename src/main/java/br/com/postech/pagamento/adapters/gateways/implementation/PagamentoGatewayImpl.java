package br.com.postech.pagamento.adapters.gateways.implementation;

import br.com.postech.pagamento.adapters.gateways.PagamentoGateway;
import br.com.postech.pagamento.adapters.repositories.PagamentoRepository;
import br.com.postech.pagamento.core.entities.Pagamento;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PagamentoGatewayImpl implements PagamentoGateway {

    private final PagamentoRepository pagamentoRepository;

    public PagamentoGatewayImpl(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    @Override
    public Pagamento salvar(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    @Override
    public List<Pagamento> buscarPor(Example<Pagamento> pagamentoExample) {
        return pagamentoRepository.findAll(pagamentoExample);
    }
}
