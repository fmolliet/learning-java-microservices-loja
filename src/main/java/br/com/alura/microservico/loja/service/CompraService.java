package br.com.alura.microservico.loja.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.alura.microservico.loja.controller.dto.CompraDTO;
import br.com.alura.microservico.loja.controller.dto.InfoFornecedorDTO;

public class CompraService {

    public void realizaCompra(CompraDTO compra) {

        // É um cliente de request HTTP como axios
        RestTemplate client = new RestTemplate();
        // o Client.exchange é onde passamos os parametros do que será executado, metodo
        // e como ele vai retornar
        ResponseEntity<InfoFornecedorDTO> exchange = client.exchange(
                "http://localhost:8081/info/" + compra.getEndereco().getEstado(), HttpMethod.GET, null,
                InfoFornecedorDTO.class);
        System.out.println(exchange.getBody().getEndereco());
    }

}
