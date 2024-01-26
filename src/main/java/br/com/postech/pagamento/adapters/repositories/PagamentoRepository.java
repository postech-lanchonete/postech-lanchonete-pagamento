package br.com.postech.pagamento.adapters.repositories;

import br.com.postech.pagamento.core.entities.Pagamento;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface PagamentoRepository extends MongoRepository<Pagamento, UUID> {
}