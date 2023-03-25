package com.safepass.safebuilding;

import com.safepass.safebuilding.common.service.MailSenderService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@OpenAPIDefinition(info=@Info(title= "Safe Building API", description= "This page will view all api that safe building has."))
@EnableCaching
public class SafeBuildingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafeBuildingApplication.class, args);
	}
}
