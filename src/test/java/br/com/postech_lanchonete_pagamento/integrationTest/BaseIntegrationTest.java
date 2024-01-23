package br.com.postech_lanchonete_pagamento.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;


public class BaseIntegrationTest {

    public String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
