package br.com.postech_lanchonete_pagamento.bdd.steps;

import br.com.postech_lanchonete_pagamento.adapters.dto.ClienteDTO;
import br.com.postech_lanchonete_pagamento.adapters.dto.PagamentoRequestDTO;
import br.com.postech_lanchonete_pagamento.adapters.dto.PagamentoResponseDTO;
import br.com.postech_lanchonete_pagamento.adapters.dto.PedidoDTO;
import br.com.postech_lanchonete_pagamento.adapters.dto.ProdutoDTO;
import br.com.postech_lanchonete_pagamento.bdd.helper.RequestHelper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class PagamentoStepDefinition {

    PagamentoRequestDTO pagamentoRequestDTO = new PagamentoRequestDTO();

    private RequestHelper<PagamentoResponseDTO> requestHelper;

    @Dado("que um novo pagamento foi criado")
    public void queUmNovoPagamentoFoiCriado() {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setProdutos(new ArrayList<>());
        ClienteDTO clienteDTO = new ClienteDTO("Antonio", "Machado", "11111111111", "antonio.machado@gmail.com");
        var requestDTO = new PagamentoRequestDTO();
        pedidoDTO.setCliente(clienteDTO);
        requestDTO.setPedido(pedidoDTO);
        this.pagamentoRequestDTO = requestDTO;
    }

    @E("tenha um produto com valor {int}")
    public void tenhaUmProdutoComValor(int valor) {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setPreco(BigDecimal.valueOf(valor));
        this.pagamentoRequestDTO.getPedido().getProdutos().add(produtoDTO);
    }

    @Quando("for requisitado a criacao pagamento via api")
    public void forRequisitadoACriacaoPagamentoViaApi() {
        this.requestHelper = RequestHelper
                .realizar("/v1/pagamentos", HttpMethod.POST, this.pagamentoRequestDTO, PagamentoResponseDTO.class);
    }

    @Entao("deve retornar o pagamento criado")
    public void deveRetornarOPagamentoCriado() {
        Assert.assertNotNull("Resposta é nula", this.requestHelper.getSuccessResponse().getBody());
    }

    @E("o status deve ser igual a {int}")
    public void oStatusDeveSerIgualA(int status) {
        if(this.requestHelper.getSuccessResponse() != null) {
            Assert.assertEquals("Status should match", HttpStatusCode.valueOf(status), this.requestHelper.getSuccessResponse().getStatusCode());
        } else {
            Assert.assertEquals("Status should match", HttpStatusCode.valueOf(status), this.requestHelper.getErrorResponse().getStatusCode());
        }
    }

    @E("o total deve ser de {int}")
    public void oTotalDeveSerDe(int valor) {
        Assert.assertEquals(BigDecimal.valueOf(valor).floatValue(), Objects.requireNonNull(this.requestHelper.getSuccessResponse().getBody()).getValor().floatValue(), 0.001);
    }

    @Dado("que exista um pagamento com um id")
    public void queExistaUmPagamentoComOId() {
        queUmNovoPagamentoFoiCriado();
        forRequisitadoACriacaoPagamentoViaApi();
    }

    @Quando("for requisitado a busca com um id existente")
    public void forRequisitadoABuscaComUmIdExistente() {
        this.requestHelper = RequestHelper
                .realizar("/v1/pagamentos/" + Objects.requireNonNull(this.requestHelper.getSuccessResponse().getBody()).getId(),
                        HttpMethod.GET, null, PagamentoResponseDTO.class);

    }

    @Entao("deve retornar o pagamento")
    public void deveRetornarOPagamento() {
        deveRetornarOPagamentoCriado();
    }

    @Dado("que nao exista um pagamento com o id {string}")
    public void queNaoExistaUmPagamentoComOId(String id) {
    }

    @Quando("for requisitado a busca via id {string}")
    public void forRequisitadoABuscaViaId(String id) {
        UUID uuid = UUID.fromString(id);

        this.requestHelper = RequestHelper
                .realizar("/v1/pagamentos/" + uuid,
                        HttpMethod.GET, null, PagamentoResponseDTO.class);
    }

    @Dado("que exista um pagamento com o status {string}")
    public void queExistaUmPagamentoComOStatus(String arg0) {

    }

    @Quando("for requisitado a busca via status {string}")
    public void forRequisitadoABuscaViaStatus(String status) {
        this.requestHelper = RequestHelper
                .realizar("/v1/pagamentos/status/" + status,
                        HttpMethod.GET, null, PagamentoResponseDTO.class);
    }

}
