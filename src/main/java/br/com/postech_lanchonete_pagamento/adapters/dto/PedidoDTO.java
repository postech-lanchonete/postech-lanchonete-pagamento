package br.com.postech_lanchonete_pagamento.adapters.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@SuppressWarnings("unused")
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    @NotEmpty(message = "Lista de produtos é obrigatória")
    private List<ProdutoDTO> produtos;
    @NotNull(message = "Cliente do produto é obrigatório")
    private ClienteDTO cliente;
}