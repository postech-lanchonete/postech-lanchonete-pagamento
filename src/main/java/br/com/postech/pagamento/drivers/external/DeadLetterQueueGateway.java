package br.com.postech.pagamento.drivers.external;

public interface DeadLetterQueueGateway {
    void enviar(String pagamentoJson, String topic);
}
