package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
 
@ComponentScan(basePackages = { "com.stock.Controllers","com.stock.restController"} )

public class StockManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockManagementSystemApplication.class, args);
	}
	 
	
}
