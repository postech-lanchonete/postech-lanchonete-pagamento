package br.com.postech.pagamento.adapters.input.subscribers;

import br.com.postech.pagamento.adapters.adapter.PagamentoAdapter;
import br.com.postech.pagamento.adapters.dto.PagamentoRequestDTO;
import br.com.postech.pagamento.core.entities.Produto;
import br.com.postech.pagamento.core.enums.StatusPagamento;
import br.com.postech.pagamento.drivers.external.DeadLetterQueueGateway;
import br.com.postech.pagamento.drivers.external.PagamentoGateway;
import br.com.postech.pagamento.core.entities.Pagamento;
import br.com.postech.pagamento.drivers.web.PagamentoCommandAPI;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Component
public class PagamentoCommandSubscriber implements PagamentoCommandAPI {
    public static final String TOPIC_PAGAMENTO_INPUT = "postech-pagamento-input";
    public static final String TOPIC_PAGAMENTO_INPUT_DLQ = "postech-pagamento-input-dlq";
    private final ObjectMapper objectMapper;
    private final PagamentoAdapter pagamentoAdapter;
    private final PagamentoGateway pagamentoGateway;
    private final DeadLetterQueueGateway deadLetterQueueGateway;


    public PagamentoCommandSubscriber(ObjectMapper objectMapper, PagamentoAdapter pagamentoAdapter, PagamentoGateway pagamentoGateway, DeadLetterQueueGateway deadLetterQueueGateway) {
        this.objectMapper = objectMapper;
        this.pagamentoAdapter = pagamentoAdapter;
        this.pagamentoGateway = pagamentoGateway;
        this.deadLetterQueueGateway = deadLetterQueueGateway;
    }

    @Override
    @Transactional
    @KafkaListener(topics = TOPIC_PAGAMENTO_INPUT, groupId = "postech-group-pagamento")
    public void pagar(String pagamentoJson) {
         try {
             log.info("Received Message: " + pagamentoJson);
             PagamentoRequestDTO pagamentoRequest = objectMapper.readValue(pagamentoJson, PagamentoRequestDTO.class);
             Pagamento pagamento = pagamentoAdapter.toEntity(pagamentoRequest);
             var valorTotal = pagamento.getPedido().getProdutos().stream()
                     .map(Produto::getPreco)
                     .reduce(BigDecimal.ZERO, BigDecimal::add);
             pagamento.setValor(valorTotal);
             pagamento.setStatus(StatusPagamento.PENDENTE);
             pagamentoGateway.salvar(pagamento);
        } catch (Exception e) {
            log.error("Erro ao processar a mensagem JSON: " + e.getMessage());
             deadLetterQueueGateway.enviar(TOPIC_PAGAMENTO_INPUT_DLQ, pagamentoJson);
        }
    }
}
