package br.com.alura.microservico.loja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.alura.microservico.loja.controller.dto.CompraDTO;
import br.com.alura.microservico.loja.controller.dto.InfoFornecedorDTO;

@Service
public class CompraService {
	
	@Autowired
	private RestTemplate client;

    public void realizaCompra(CompraDTO compra) {

        // É um cliente de request HTTP como axios 
    	// Passamos a logica de realizar o template para o Bean na classe main
        //RestTemplate client = new RestTemplate();
        // o Client.exchange é onde passamos os parametros do que será executado, metodo
        // e como ele vai retornar
        ResponseEntity<InfoFornecedorDTO> exchange = client.exchange(
                "http://fornecedor/info/" + compra.getEndereco().getEstado(), HttpMethod.GET, null,
                InfoFornecedorDTO.class);
        System.out.println(exchange.getBody().getEndereco());
    }

}
