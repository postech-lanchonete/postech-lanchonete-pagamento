package br.com.postech.pagamento.drivers.external;

import br.com.postech.pagamento.core.entities.Pagamento;
import br.com.postech.pagamento.drivers.external.Gateway;

public interface PagamentoGateway extends Gateway<Pagamento> {
}
