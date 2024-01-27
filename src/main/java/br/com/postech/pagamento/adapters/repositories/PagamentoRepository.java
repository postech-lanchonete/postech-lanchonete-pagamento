package br.com.postech.pagamento.adapters.repositories;

import br.com.postech.pagamento.core.entities.Pagamento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PagamentoRepository extends MongoRepository<Pagamento, UUID> {
}