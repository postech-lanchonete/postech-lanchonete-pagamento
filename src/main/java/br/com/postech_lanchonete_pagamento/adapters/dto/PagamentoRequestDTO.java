package br.com.postech_lanchonete_pagamento.adapters.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("unused")
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoRequestDTO {
    @NotNull(message = "Pedido do produto é obrigatório")
    private PedidoDTO pedido;
}
