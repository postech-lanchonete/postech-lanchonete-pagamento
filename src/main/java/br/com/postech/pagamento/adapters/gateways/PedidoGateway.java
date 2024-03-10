package br.com.postech.pagamento.adapters.gateways;

import br.com.postech.pagamento.core.entities.Pagamento;
import br.com.postech.pagamento.core.enums.StatusDoPedido;

public interface PedidoGateway  {
    void enviarConfirmacaoPagamento(Pagamento pagamentoRealizado);

    void enviarErroPagamento(String pagamentoJson);
}
