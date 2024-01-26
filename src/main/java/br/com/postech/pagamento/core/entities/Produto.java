package br.com.postech.pagamento.core.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@SuppressWarnings("unused")
@AllArgsConstructor
@NoArgsConstructor
public class Produto {
    private String nome;
    private BigDecimal preco;

}