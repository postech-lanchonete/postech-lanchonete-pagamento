package br.com.postech.pagamento.core.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("unused")
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    private String nome;

    private String sobrenome;

    private String cpf;

    private String email;

}