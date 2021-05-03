package br.com.alura.microservico.loja.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.alura.microservico.loja.controller.dto.InfoFornecedorDTO;
import br.com.alura.microservico.loja.controller.dto.InfoPedidoDTO;
import br.com.alura.microservico.loja.controller.dto.ItemDaCompraDTO;

// Vamos informar o ID do serviço que vamos acessar
@FeignClient("fornecedor")
public interface FornecedorClient {

	@RequestMapping("/info/{estado}")
	InfoFornecedorDTO getInfoPorEstado(@PathVariable String estado);

	@PostMapping("/pedido")
	InfoPedidoDTO realizaPedido(List<ItemDaCompraDTO> itens);
	
	
	
}
