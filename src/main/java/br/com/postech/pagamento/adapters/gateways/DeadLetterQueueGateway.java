package br.com.postech.pagamento.adapters.gateways;

public interface DeadLetterQueueGateway {
    void enviar(String pagamentoJson, String topic);
}
