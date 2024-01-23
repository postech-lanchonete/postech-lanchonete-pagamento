package br.com.postech_lanchonete_pagamento.business.exceptions;

public class NegocioException extends RuntimeException {
    public NegocioException(String message) {
        super(message);
    }
}
