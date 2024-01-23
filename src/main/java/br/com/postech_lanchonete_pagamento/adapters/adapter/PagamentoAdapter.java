package br.com.postech_lanchonete_pagamento.adapters.adapter;

import br.com.postech_lanchonete_pagamento.adapters.dto.PagamentoRequestDTO;
import br.com.postech_lanchonete_pagamento.adapters.dto.PagamentoResponseDTO;
import br.com.postech_lanchonete_pagamento.core.entities.Pagamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = UUID.class)
public interface PagamentoAdapter {
    PagamentoResponseDTO toDto(Pagamento pagamento);

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    Pagamento toEntity(PagamentoRequestDTO pagamentoRequestDTO);
}
