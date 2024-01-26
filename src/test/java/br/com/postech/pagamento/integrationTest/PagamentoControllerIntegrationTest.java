package br.com.postech.pagamento.integrationTest;

import br.com.postech.pagamento.adapters.repositories.PagamentoRepository;
import br.com.postech.pagamento.stub.PagamentoStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PagamentoControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @BeforeEach
    void setUp() {
        // Limpar dados de teste antes de cada execução de teste
        pagamentoRepository.deleteAll();
    }

    @Test
    void pagar_deveRealizarPagamentoNoBanco_QuandoReceberDadosCorretos() throws Exception {
        mockMvc.perform(post("/v1/pagamentos/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(PagamentoStub.createPagamentoRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.valor", is(25)))
                .andExpect(jsonPath("$.status", is("APROVADO")))
                .andExpect(jsonPath("$.pedido.produtos", hasSize(2)))
                .andExpect(jsonPath("$.pedido.cliente.nome", is("Antonio")))
                .andExpect(jsonPath("$.pedido.cliente.sobrenome", is("Machado")))
                .andExpect(jsonPath("$.pedido.cliente.cpf", is("11111111111")))
                .andExpect(jsonPath("$.pedido.cliente.email", is("antonio.machado@gmail.com")));
    }

//    @Test
//    void pagar_deveRetornar400_QuandoValorIgualAZero() throws Exception {
//        var request = PagamentoStub.createPagamentoRequest();
//        request.getPedido().getProdutos().forEach(produtoDTO -> produtoDTO.setPreco(BigDecimal.ZERO));
//        request.getPedido().setCliente(null);
//        mockMvc.perform(post("/v1/pagamentos/")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(request)))
//                .andExpect(status().isBadRequest());
//    }

//    @Test
//    void pagar_deveRetornar400_QuandoValorInferiorAZero() throws Exception {
//        var request = PagamentoStub.createPagamentoRequest();
//        request.getPedido().getProdutos().forEach(produtoDTO -> produtoDTO.setPreco(BigDecimal.valueOf(-1)));
//        mockMvc.perform(post("/v1/pagamentos/")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(request)))
//                .andExpect(status().isBadRequest());
//    }

    @Test
    void buscarPorStatus_deveRetornarUmPagamento_quandoExistirUmPagamentoAprovado() throws Exception {
        var document = PagamentoStub.createPagamentoDocument();
        pagamentoRepository.save(document);

        mockMvc.perform(get("/v1/pagamentos/status/APROVADO")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].valor").value(document.getValor()))
                .andExpect(jsonPath("$[0].status").value(document.getStatus().name()))
                .andExpect(jsonPath("$[0].pedido.produtos").isArray())
                .andExpect(jsonPath("$[0].pedido.produtos[0].nome").value(document.getPedido().getProdutos().get(0).getNome()))
                .andExpect(jsonPath("$[0].pedido.produtos[0].preco").value(document.getPedido().getProdutos().get(0).getPreco()))
                .andExpect(jsonPath("$[0].pedido.cliente.nome").value(document.getPedido().getCliente().getNome()))
                .andExpect(jsonPath("$[0].pedido.cliente.sobrenome").value(document.getPedido().getCliente().getSobrenome()))
                .andExpect(jsonPath("$[0].pedido.cliente.cpf").value(document.getPedido().getCliente().getCpf()))
                .andExpect(jsonPath("$[0].pedido.cliente.email").value(document.getPedido().getCliente().getEmail()));
    }

    @Test
    void buscarPorStatus_deveRetornarListaVazia_quandoNaoExistirPagamentosNoStatusSelecionado() throws Exception {
        mockMvc.perform(get("/v1/pagamentos/status/APROVADO")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void buscarPorStatus_deveRetornar400_quandoStatusInformadoNaoExistir() throws Exception {
        mockMvc.perform(get("/v1/pagamentos/status/XXXX")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void buscarPorId_deveRetornar404_quandoIdInformadoNaoRetornarNenhumProduto() throws Exception {
        mockMvc.perform(get("/v1/pagamentos/3fa85f64-5717-4562-b3fc-2c963f66afa6")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void buscarPorId_deveRetornar400_quandoIdInformadoNaoForUmUUID() throws Exception {
        mockMvc.perform(get("/v1/pagamentos/3fa85f64")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void buscarPorId_deveRetornarPagamento_quandoIdInformadoExistente() throws Exception {
        var document = PagamentoStub.createPagamentoDocument();
        pagamentoRepository.save(document);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/pagamentos/" + document.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(document.getId().toString()))
                .andExpect(jsonPath("$.valor").value(document.getValor()))
                .andExpect(jsonPath("$.status").value(document.getStatus().name()))
                .andExpect(jsonPath("$.pedido.produtos").isArray())
                .andExpect(jsonPath("$.pedido.produtos[0].nome").value(document.getPedido().getProdutos().get(0).getNome()))
                .andExpect(jsonPath("$.pedido.produtos[0].preco").value(document.getPedido().getProdutos().get(0).getPreco()))
                .andExpect(jsonPath("$.pedido.cliente.nome").value(document.getPedido().getCliente().getNome()))
                .andExpect(jsonPath("$.pedido.cliente.sobrenome").value(document.getPedido().getCliente().getSobrenome()))
                .andExpect(jsonPath("$.pedido.cliente.cpf").value(document.getPedido().getCliente().getCpf()))
                .andExpect(jsonPath("$.pedido.cliente.email").value(document.getPedido().getCliente().getEmail()));
    }
}
