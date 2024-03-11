package br.com.postech.pagamento.adapters.gateways.implementation;

import br.com.postech.pagamento.adapters.gateways.PedidoGateway;
import br.com.postech.pagamento.business.exceptions.BadRequestException;
import br.com.postech.pagamento.core.entities.Pagamento;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PedidoGatewayImpl implements PedidoGateway {

    private static final String TOPIC_PAGAMENTO = "postech-pagamento-output";
    private static final String TOPIC_PAGAMENTO_ERRO = "postech-pagamento-output-error";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public PedidoGatewayImpl(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void enviarConfirmacaoPagamento(Pagamento pagamentoRealizado) {
        try {
            String jsonPagamento = objectMapper.writeValueAsString(pagamentoRealizado);
            kafkaTemplate.send(TOPIC_PAGAMENTO, jsonPagamento);
        } catch (JsonProcessingException e) {
            log.error("Erro ao serializar o objeto PagamentoRequestDTO para JSON", e);
            throw new BadRequestException("Erro ao serializar o objeto PagamentoRequestDTO para JSON");
        } catch (Exception e) {
            log.error("Erro ao enviar o pagamento para o Kafka", e);
            throw new BadRequestException("Erro ao enviar o pagamento ");
        }
    }

    @Override
    public void enviarErroPagamento(String pagamentoJson) {
        kafkaTemplate.send(TOPIC_PAGAMENTO_ERRO, pagamentoJson);
    }
}
