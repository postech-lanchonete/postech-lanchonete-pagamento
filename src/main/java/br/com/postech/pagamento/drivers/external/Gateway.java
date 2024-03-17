package br.com.postech.pagamento.drivers.external;

import org.springframework.data.domain.Example;

import java.util.List;

public interface Gateway<T> {
    List<T> buscarPor(Example<T> example);
    T salvar(T entity);
}
