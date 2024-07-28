package practica.spring.ejemplo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class EjemploApplication {

	public static void main(String[] args) {
		SpringApplication.run(EjemploApplication.class, args);
	}

}
