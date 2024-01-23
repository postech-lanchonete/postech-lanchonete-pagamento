package br.com.postech_lanchonete_pagamento.business.usecases;

public interface UseCase<E, S> {
    S realizar(E entrada);
}
