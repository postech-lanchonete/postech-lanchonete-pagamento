package br.com.postech.pagamento.adapters.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    @Size(max = 255, message = "Nome do produto deve ter no máximo 255 caracteres")
    private String nome;

    @Positive(message = "O preço do produto deve ser maior que zero")
    @NotNull(message = "Preço do produtos é obrigatório")
    @DecimalMax(value = "200.00", message = "O preço do produto não pode ser maior que 200.00")
    private BigDecimal preco;

}