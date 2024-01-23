package br.com.postech_lanchonete_pagamento.core.entities;

import br.com.postech_lanchonete_pagamento.core.enums.StatusPagamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@SuppressWarnings("unused")
@Document(collection = "pagamentos")
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {
    @Id
    private UUID id;

    private BigDecimal valor;

    private StatusPagamento status;

    private Pedido pedido;

}
