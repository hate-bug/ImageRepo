package com.shopify.imagerepo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan (basePackages = "com.shopify.imagerepo.Repository")
@ComponentScan (basePackages = "com.shopify.imagerepo.Model")
@ComponentScan (basePackages = "com.shopify.imagerepo.Security")
@ComponentScan (basePackages = "com.shopify.imagerepo.Service")
@SpringBootApplication
public class ImagerepoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImagerepoApplication.class, args);
	}

}
