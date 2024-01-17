package com.andreypaavlov.cardregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CardregistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardregistryApplication.class, args);
	}

}


//login to db   card_service
// pwd  kC4Tc_8bWXj9gxRY