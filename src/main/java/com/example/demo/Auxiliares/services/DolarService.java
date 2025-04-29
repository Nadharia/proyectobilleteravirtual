package com.example.demo.Auxiliares.services;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



import com.example.demo.dtos.DolarResponse;

@Service
public class DolarService {

	
	private final RestTemplate restTemplate;

	public DolarService(RestTemplate restTemplate) {
		
		this.restTemplate = restTemplate;
	}
	
	public DolarResponse obtenerValorDolar() {
		String url="https://dolarapi.com/v1/dolares/oficial";
		return restTemplate.getForObject(url, DolarResponse.class);
		
	}
	
	
	
}
