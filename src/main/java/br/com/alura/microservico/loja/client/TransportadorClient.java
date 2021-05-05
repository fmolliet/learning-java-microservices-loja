package br.com.alura.microservico.loja.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.alura.microservico.loja.controller.dto.InfoEntregaDTO;
import br.com.alura.microservico.loja.controller.dto.VoucherDTO;



@FeignClient("transportador")
public interface TransportadorClient {

		@PostMapping("/entrega")
		VoucherDTO reservaEntrega( InfoEntregaDTO pedidoDTO);
		
}
