package com.fav.literatura.challenge_literalura_fav;

import com.fav.literatura.challenge_literalura_fav.principal.Principal;
import com.fav.literatura.challenge_literalura_fav.service.AutorService;
import com.fav.literatura.challenge_literalura_fav.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiteraluraFavApplication implements CommandLineRunner {

	@Autowired
	private LibroService libroService;

	@Autowired
	private AutorService autorService;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiteraluraFavApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroService, autorService);
		principal.muestraMenu();
	}
}
