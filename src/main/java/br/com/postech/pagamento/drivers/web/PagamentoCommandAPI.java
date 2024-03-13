package br.com.postech.pagamento.drivers.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pagamentos - Command (Kafka)", description = "Todas as operações referentes a comandos de pagamento")
public interface PagamentoCommandAPI {

    @Operation(
            summary = "Realizar pagamento",
            description = "Ao realizar as informacoes do pagamento, realiza-o."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pagamento realizado." )
    })
    void pagar(String jsonPagamento);

}
