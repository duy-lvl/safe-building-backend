package com.safepass.safebuilding;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@OpenAPIDefinition(info=@Info(title= "Safe Building API", description= "This page will view all api that safe building has."))
@EnableCaching
public class SafeBuildingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafeBuildingApplication.class, args);
	}
}
