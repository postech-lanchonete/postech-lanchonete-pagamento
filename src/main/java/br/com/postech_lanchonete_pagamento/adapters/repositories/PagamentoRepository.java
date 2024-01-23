package br.com.postech_lanchonete_pagamento.adapters.repositories;

import br.com.postech_lanchonete_pagamento.core.entities.Pagamento;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface PagamentoRepository extends MongoRepository<Pagamento, UUID> {
}