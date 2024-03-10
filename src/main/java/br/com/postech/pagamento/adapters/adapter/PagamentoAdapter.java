package br.com.postech.pagamento.adapters.adapter;

import br.com.postech.pagamento.adapters.dto.PagamentoRequestDTO;
import br.com.postech.pagamento.adapters.dto.PagamentoResponseDTO;
import br.com.postech.pagamento.core.entities.Pagamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = UUID.class)
public interface PagamentoAdapter {
    PagamentoResponseDTO toDto(Pagamento pagamento);

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "pedido", source = "pedido")
    Pagamento toEntity(PagamentoRequestDTO pagamentoRequestDTO);
}
