package br.com.postech.pagamento.adapters.gateways.implementation;

import br.com.postech.pagamento.adapters.gateways.PagamentoGateway;
import br.com.postech.pagamento.adapters.repositories.PagamentoRepository;
import br.com.postech.pagamento.core.entities.Pagamento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagamentoGatewayImplTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @InjectMocks
    private PagamentoGatewayImpl pagamentoGateway;

    @Test
    void salvar_deveSalvar_quandoChamado() {
        when(pagamentoRepository.save(any())).thenReturn(new Pagamento());
        pagamentoGateway.salvar(new Pagamento());
        verify(pagamentoRepository, times(1)).save(any());
    }

    @Test
    void buscarPor_deveBuscarUtilizandoExample_quandoChamado() {
        when(pagamentoRepository.findAll(any(Example.class))).thenReturn(List.of(new Pagamento()));
        pagamentoGateway.buscarPor(Example.of(new Pagamento()));
        verify(pagamentoRepository, times(1)).findAll(any(Example.class));
    }

}