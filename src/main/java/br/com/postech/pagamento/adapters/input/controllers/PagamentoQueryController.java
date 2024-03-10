package br.com.postech.pagamento.adapters.input.controllers;

import br.com.postech.pagamento.adapters.dto.PagamentoResponseDTO;
import br.com.postech.pagamento.adapters.adapter.PagamentoAdapter;
import br.com.postech.pagamento.adapters.dto.PagamentoRequestDTO;
import br.com.postech.pagamento.business.exceptions.BadRequestException;
import br.com.postech.pagamento.business.usecases.UseCase;
import br.com.postech.pagamento.core.entities.Pagamento;
import br.com.postech.pagamento.core.enums.StatusPagamento;
import br.com.postech.pagamento.drivers.web.PagamentoQueryAPI;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/pagamentos")
public class PagamentoQueryController implements PagamentoQueryAPI {
    private final UseCase<UUID, Pagamento> buscarPagamentoByIdUseCase;
    private final UseCase<StatusPagamento, List<Pagamento>> buscarPagamentosByStatusUseCase;

    private final PagamentoAdapter pagamentoAdapter;

    public PagamentoQueryController(@Qualifier("buscarPagamentoByIdUseCase") UseCase<UUID, Pagamento> buscarPagamentoByIdUseCase,
                                    @Qualifier("buscarPagamentoByStatusUseCase") UseCase<StatusPagamento, List<Pagamento>> buscarPagamentosByStatusUseCase,
                                    PagamentoAdapter pagamentoAdapter) {
        this.buscarPagamentoByIdUseCase = buscarPagamentoByIdUseCase;
        this.buscarPagamentosByStatusUseCase = buscarPagamentosByStatusUseCase;
        this.pagamentoAdapter = pagamentoAdapter;
    }


    @Override
    @GetMapping("/status/{status}")
    public List<PagamentoResponseDTO> buscarPorStatus(String status) {
        try {
            var statusPagamento = StatusPagamento.valueOf(status);
            return this.buscarPagamentosByStatusUseCase
                    .realizar(statusPagamento)
                    .stream()
                    .map(pagamentoAdapter::toDto)
                    .toList();
        } catch (IllegalArgumentException exception) {
            throw new BadRequestException("O status informado não corresponde a um status válido.");
        }
    }

    @Override
    @GetMapping("/{id}")
    public PagamentoResponseDTO buscarPorId(String id) {
        try {
            var uuid = UUID.fromString(id);
            var pagamento = this.buscarPagamentoByIdUseCase.realizar(uuid);
            return this.pagamentoAdapter.toDto(pagamento);
        } catch (IllegalArgumentException exception) {
            throw new BadRequestException("O id informado não corresponde a um id válido.");
        }
    }
}
