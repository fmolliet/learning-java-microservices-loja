package br.com.alura.microservico.loja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
//Para falar que vamos usar o Feign, usamos a anotação
@EnableFeignClients
@EnableHystrix
public class LojaApplication {
	
	// Deixamos o spring liberar a injeção de dependencia + o
	// @LoadBalance que faz o spring buscar ip e porta do serviço do eureka
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(LojaApplication.class, args);
	}

}
