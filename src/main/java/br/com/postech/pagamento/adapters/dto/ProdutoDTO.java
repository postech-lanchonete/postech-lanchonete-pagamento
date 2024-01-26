package br.com.postech.pagamento.adapters.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class ProdutoDTO {
    @NotBlank(message = "Nome do produtos é obrigatório")
    private String nome;
    @Positive(message = "O preço do produto deve ser maior que zero")
    @NotNull(message = "Preço do produtos é obrigatório")
    private BigDecimal preco;

}