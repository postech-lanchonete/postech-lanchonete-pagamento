package br.com.postech.pagamento.business.usecases;

public interface UseCase<E, S> {
    S realizar(E entrada);

}
