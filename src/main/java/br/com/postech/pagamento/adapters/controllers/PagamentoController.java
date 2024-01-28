package br.com.postech.pagamento.adapters.controllers;

import br.com.postech.pagamento.adapters.dto.PagamentoResponseDTO;
import br.com.postech.pagamento.adapters.adapter.PagamentoAdapter;
import br.com.postech.pagamento.adapters.dto.PagamentoRequestDTO;
import br.com.postech.pagamento.business.exceptions.BadRequestException;
import br.com.postech.pagamento.business.usecases.UseCase;
import br.com.postech.pagamento.core.entities.Pagamento;
import br.com.postech.pagamento.core.enums.StatusPagamento;
import br.com.postech.pagamento.drivers.web.PagamentoAPI;
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
public class PagamentoController implements PagamentoAPI {
    private final UseCase<UUID, Pagamento> buscarPagamentoByIdUseCase;
    private final UseCase<StatusPagamento, List<Pagamento>> buscarPagamentosByStatusUseCase;
    private final UseCase<Pagamento, Pagamento> realizarPagamentoUseCase;
    private final UseCase<Pagamento, Pagamento> desfazerPagamentoUseCase;

    private final PagamentoAdapter pagamentoAdapter;

    public PagamentoController(@Qualifier("buscarPagamentoByIdUseCase") UseCase<UUID, Pagamento> buscarPagamentoByIdUseCase,
                               @Qualifier("buscarPagamentoByStatusUseCase") UseCase<StatusPagamento, List<Pagamento>> buscarPagamentosByStatusUseCase,
                               @Qualifier("realizarPagamentoUseCase") UseCase<Pagamento, Pagamento> realizarPagamentoUseCase,
                               @Qualifier("desfazerPagamentoUseCase") UseCase<Pagamento, Pagamento> desfazerPagamentoUseCase,
                               PagamentoAdapter pagamentoAdapter) {
        this.buscarPagamentoByIdUseCase = buscarPagamentoByIdUseCase;
        this.buscarPagamentosByStatusUseCase = buscarPagamentosByStatusUseCase;
        this.realizarPagamentoUseCase = realizarPagamentoUseCase;
        this.desfazerPagamentoUseCase = desfazerPagamentoUseCase;
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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PagamentoResponseDTO pagar(@RequestBody PagamentoRequestDTO pagamentoRequest) {
        var pagamento = pagamentoAdapter.toEntity(pagamentoRequest);
        var pagamentoResponse = realizarPagamentoUseCase.realizar(pagamento);
        return pagamentoAdapter.toDto(pagamentoResponse);
    }

    @Override
    @DeleteMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PagamentoResponseDTO defazerPagamento(@RequestBody PagamentoRequestDTO pagamentoRequest) {
        var pagamento = pagamentoAdapter.toEntity(pagamentoRequest);
        var pagamentoResponse = desfazerPagamentoUseCase.realizar(pagamento);
        return pagamentoAdapter.toDto(pagamentoResponse);
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
