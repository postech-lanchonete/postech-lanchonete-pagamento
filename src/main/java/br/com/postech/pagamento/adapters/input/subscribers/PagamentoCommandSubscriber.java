package br.com.postech.pagamento.adapters.input.subscribers;

import br.com.postech.pagamento.adapters.adapter.PagamentoAdapter;
import br.com.postech.pagamento.adapters.dto.PagamentoRequestDTO;
import br.com.postech.pagamento.adapters.gateways.PedidoGateway;
import br.com.postech.pagamento.business.usecases.UseCase;
import br.com.postech.pagamento.core.entities.Pagamento;
import br.com.postech.pagamento.core.enums.StatusDoPedido;
import br.com.postech.pagamento.drivers.web.PagamentoCommandAPI;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PagamentoCommandSubscriber implements PagamentoCommandAPI {

    private final UseCase<Pagamento, Pagamento> realizarPagamentoUseCase;
    private final UseCase<Pagamento, Pagamento> desfazerPagamentoUseCase;
    private final PedidoGateway pedidoGateway;
    private final ObjectMapper objectMapper;
    private final PagamentoAdapter pagamentoAdapter;


    public PagamentoCommandSubscriber(@Qualifier("realizarPagamentoUseCase") UseCase<Pagamento, Pagamento> realizarPagamentoUseCase,
                                      @Qualifier("desfazerPagamentoUseCase") UseCase<Pagamento, Pagamento> desfazerPagamentoUseCase,
                                      PedidoGateway pedidoGateway, ObjectMapper objectMapper, PagamentoAdapter pagamentoAdapter) {
        this.realizarPagamentoUseCase = realizarPagamentoUseCase;
        this.desfazerPagamentoUseCase = desfazerPagamentoUseCase;
        this.pedidoGateway = pedidoGateway;
        this.objectMapper = objectMapper;
        this.pagamentoAdapter = pagamentoAdapter;
    }

    @Override
    @KafkaListener(topics = "postech-pagamento-input", groupId = "postech-group-pagamento")
    public void pagar(String pagamentoJson) {
         try {
             log.info("Received Message: " + pagamentoJson);
             PagamentoRequestDTO pagamentoRequest = objectMapper.readValue(pagamentoJson, PagamentoRequestDTO.class);
             if (pagamentoRequest.getPedido().getIdCliente() == 2L) {
                log.error("erro simulado");
                throw new RuntimeException("Erro ao realizar pagamento");
             }
             Pagamento pagamento = pagamentoAdapter.toEntity(pagamentoRequest);
             Pagamento pagamentoRealizado = realizarPagamentoUseCase.realizar(pagamento);
             pedidoGateway.enviarConfirmacaoPagamento(pagamentoRealizado);
        } catch (Exception e) {
            log.error("Erro ao processar a mensagem JSON: " + e.getMessage());
            pedidoGateway.enviarErroPagamento(pagamentoJson);
        }
    }
}
