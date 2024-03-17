package br.com.postech.pagamento.adapters.dto;

import br.com.postech.pagamento.core.enums.StatusPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlteraStatusDTO {
    private final String id;
    private final StatusPagamento statusPagamento;
}
