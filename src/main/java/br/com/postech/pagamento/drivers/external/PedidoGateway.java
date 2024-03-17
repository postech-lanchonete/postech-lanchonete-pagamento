package br.com.postech.pagamento.drivers.external;

import br.com.postech.pagamento.core.entities.Pagamento;

public interface PedidoGateway  {
    void enviarConfirmacaoPagamento(Pagamento pagamentoRealizado);

}
