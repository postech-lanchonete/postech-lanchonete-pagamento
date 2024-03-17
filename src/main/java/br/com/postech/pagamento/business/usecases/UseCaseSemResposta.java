package br.com.postech.pagamento.business.usecases;

public interface UseCaseSemResposta<E> {
    void realizar(E entrada);
}