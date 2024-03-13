package br.com.postech.pagamento.core.entities;

import br.com.postech.pagamento.business.exceptions.NegocioException;
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
    private Long id;
    private List<Produto> produtos;
    private Long idCliente;
    private LocalDateTime dataCriacao;

    public Pedido() {
    }

    public Pedido(Long cliente, List<Produto> produtos) {
        this.idCliente = cliente;
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