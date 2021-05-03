package br.com.alura.microservico.loja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.alura.microservico.loja.client.FornecedorClient;
import br.com.alura.microservico.loja.controller.dto.CompraDTO;
import br.com.alura.microservico.loja.controller.dto.InfoFornecedorDTO;
import br.com.alura.microservico.loja.controller.dto.InfoPedidoDTO;
import br.com.alura.microservico.loja.model.Compra;

@Service
public class CompraService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CompraService.class);
	
	//@Autowired
	//private RestTemplate client;
	@Autowired
	private FornecedorClient fornecedorClient;

    public Compra realizaCompra(CompraDTO compra) {
    	
    	final String estado =  compra.getEndereco().getEstado();
    	
    	LOG.info("Buscando informação do fornecedor de {}", estado);
    	InfoFornecedorDTO info = fornecedorClient.getInfoPorEstado( estado );
    	
    	LOG.info("Realizando um pedido");
    	InfoPedidoDTO pedido = fornecedorClient.realizaPedido(compra.getItens());
    	
    	
    	Compra compraSalva = new Compra();
    	
    	compraSalva.setPedidoId(pedido.getId());
    	compraSalva.setTempoDePreparo(pedido.getTempoDePreparo());
    	compraSalva.setEnderecoDestino(info.getEndereco());
    	
    	return compraSalva;
        // É um cliente de request HTTP como axios 
    	// Passamos a logica de realizar o template para o Bean na classe main
        //RestTemplate client = new RestTemplate();
        // o Client.exchange é onde passamos os parametros do que será executado, metodo
        // e como ele vai retornar
        //ResponseEntity<InfoFornecedorDTO> exchange = client.exchange("http://fornecedor/info/" + compra.getEndereco().getEstado(), HttpMethod.GET, null,InfoFornecedorDTO.class);
        //System.out.println(exchange.getBody().getEndereco());
    }

}
