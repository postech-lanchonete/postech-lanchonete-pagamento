package br.com.postech.pagamento.drivers.web;

import br.com.postech.pagamento.adapters.dto.PagamentoRequestDTO;
import br.com.postech.pagamento.adapters.dto.PagamentoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Pagamentos", description = "Todas as operações referentes a pagamento")
public interface PagamentoAPI {

    @Operation(
            summary = "Busca pagamentos",
            description = "Busca todos os pagamento, podendo filtrar por status."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PagamentoResponseDTO.class)))
            }),
    })
    List<PagamentoResponseDTO> buscarPorStatus(@Parameter(description = "Status do pagamento, podendo ser 'PENDENTE', 'APROVADO', ou 'REPROVADO'", example = "APROVADO", schema = @Schema(type = "string", allowableValues = {"PENDENTE", "APROVADO", "REPROVADO"}))
                                                @PathVariable(name = "status") String status);

    @Operation(
            summary = "Realizar pagamento",
            description = "Ao realizar as informacoes do pagamento, realiza-o."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pagamento realizado." )
    })
    PagamentoResponseDTO pagar(@Valid @RequestBody PagamentoRequestDTO pagamento);


    @Operation(
            summary = "Busca pagamento por id",
            description = "Busca pagamento pagamento, filtrando por id."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PagamentoResponseDTO.class)))
            }),
    })
    PagamentoResponseDTO buscarPorId(@PathVariable(name = "id") String id);

}
