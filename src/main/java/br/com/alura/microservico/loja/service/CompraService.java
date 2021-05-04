package br.com.alura.microservico.loja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.alura.microservico.loja.client.FornecedorClient;
import br.com.alura.microservico.loja.controller.dto.CompraDTO;
import br.com.alura.microservico.loja.controller.dto.InfoFornecedorDTO;
import br.com.alura.microservico.loja.controller.dto.InfoPedidoDTO;
import br.com.alura.microservico.loja.model.Compra;
import br.com.alura.microservico.loja.repository.CompraRepository;

@Service
public class CompraService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CompraService.class);
	
	//@Autowired
	//private RestTemplate client;
	@Autowired
	private FornecedorClient fornecedorClient;
	
	@Autowired
	private CompraRepository compraRepository;

	// Para Separar as threads para não travar toda aplicação, usamos um parametro no @HystrixCommand chamado threadPoolKey
	// No qual informamos qual é o nome da Thread daquele processo é chamado de Bulkhead Pattern
	@HystrixCommand( fallbackMethod = "getCompraByIdFallback",
			threadPoolKey = "getCompraByIdThreadPool")
	public Compra getCompraById(Long id) {
		return compraRepository.findById(id).orElse(new Compra());
	}
	
	/*
	 * A anotação @HystrixCommand habilita o circuitbreaker
	 * O Circuit Breaker tem como funcionalidade principal a análise das requisições anteriores,
	 *  para decidir se deve parar de repassar as requisições vindas do cliente para um microsserviço
	 *   com problemas de performance. Enquanto o circuito está fechado, o Hystrix continua tentando a cada 5 segundos,
	 *    com o objetivo de verificar se o servidor voltou a funcionar normalmente.
	 *    
	 *    A diferença entre timeout e o circuit breaker Possui a vantagem principal de fechar o circuito,
	 *     evitando que uma requisição com alto índice de falhas seja executada,
	 *     até que o microsserviço volte a operar dentro dos parâmetros aceitáveis
	 *     
	 *     O Circuit Breaker implementado pelo Hystrix executa o processamento em uma thread separada.
	 *      Quando o tempo limite é excedido, o Hystrix mata a execução da thread e, caso configurado,
	 *       repassa a execução para o método de Fallback, de forma que este possa implementar livremente um tratamento de erro.
	 */
	@HystrixCommand( fallbackMethod = "realizaCompraFallback",
			threadPoolKey = "realizaCompraThreadPool")
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
    	
    	compraRepository.save(compraSalva);
    	
    	return compraSalva;
        // É um cliente de request HTTP como axios 
    	// Passamos a logica de realizar o template para o Bean na classe main
        //RestTemplate client = new RestTemplate();
        // o Client.exchange é onde passamos os parametros do que será executado, metodo
        // e como ele vai retornar
        //ResponseEntity<InfoFornecedorDTO> exchange = client.exchange("http://fornecedor/info/" + compra.getEndereco().getEstado(), HttpMethod.GET, null,InfoFornecedorDTO.class);
        //System.out.println(exchange.getBody().getEndereco());
    }
	
	public Compra realizaCompraFallback(CompraDTO compra ) {
		Compra compraFallback = new Compra();
		compraFallback.setEnderecoDestino(compra.getEndereco().toString());
		return compraFallback;
	}



}
