package br.com.alura.microservico.loja.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Compra {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long pedidoId;
	
	private Integer tempoDePreparo;
	
	private String enderecoDestino;
	
	private LocalDate previsaoParaEntrega;
	
	private Long voucher;
	@Enumerated(EnumType.STRING)
	private CompraStatus status;
	

	public CompraStatus getStatus() {
		return status;
	}

	public void setStatus(CompraStatus status) {
		this.status = status;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public Integer getTempoDePreparo() {
		return tempoDePreparo;
	}

	public void setTempoDePreparo(Integer tempoDePreparo) {
		this.tempoDePreparo = tempoDePreparo;
	}

	public String getEnderecoDestino() {
		return enderecoDestino;
	}

	public void setEnderecoDestino(String enderecoDestino) {
		this.enderecoDestino = enderecoDestino;
	}

	public void setDataParaEntrega(LocalDate previsaoParaEntrega) {
		this.previsaoParaEntrega = previsaoParaEntrega; 
	}

	public void setVoucher(Long numero) {
		this.voucher = numero;
	}
	
	public LocalDate getPrevisaoParaEntrega() {
		return previsaoParaEntrega;
	}

	public Long getVoucher() {
		return voucher;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
