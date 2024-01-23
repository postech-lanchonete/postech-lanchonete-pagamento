package br.com.postech_lanchonete_pagamento.core.entities;

import br.com.postech_lanchonete_pagamento.business.exceptions.NegocioException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@SuppressWarnings("unused")
public class Pedido {
    private List<Produto> produtos;
    private Cliente cliente;
    private LocalDateTime dataCriacao;

    public Pedido() {
    }

    public Pedido(Cliente cliente, List<Produto> produtos) {
        this.dataCriacao = LocalDateTime.now();
        this.produtos = produtos;
        validar();
    }

    private void validar() {
        if (CollectionUtils.isEmpty(produtos)) {
            throw new NegocioException("Deve ser informado ao menos um produto válido para criar um pedido");
        }
    }

    public BigDecimal calculaValorTotal() {
        return produtos.stream()
                .map(Produto::getPreco)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}