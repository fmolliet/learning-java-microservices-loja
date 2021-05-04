package br.com.alura.microservico.loja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.alura.microservico.loja.controller.dto.CompraDTO;
import br.com.alura.microservico.loja.model.Compra;
import br.com.alura.microservico.loja.service.CompraService;

@RestController
@RequestMapping("/compra")
public class CompraController {

	@Autowired
	private CompraService compraService;
	
	@GetMapping("/{id}")
	public Compra getCompraById( @PathVariable("id") Long id ) {
		return compraService.getCompraById( id );
	}

	@PostMapping
	public Compra realizaCompra(@RequestBody CompraDTO compra) {
		return compraService.realizaCompra(compra);
	}

}
