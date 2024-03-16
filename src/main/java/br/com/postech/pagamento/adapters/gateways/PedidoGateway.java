package br.com.postech.pagamento.adapters.gateways;

import br.com.postech.pagamento.core.entities.Pagamento;

public interface PedidoGateway  {
    void enviarConfirmacaoPagamento(Pagamento pagamentoRealizado);

}
