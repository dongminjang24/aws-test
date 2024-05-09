package jpaBook.jpaShop2;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpaShop2Application {

	public static void main(String[] args) {
		SpringApplication.run(JpaShop2Application.class, args);
	}

	/*JpashopApplication.java*/
	@Bean
	Hibernate5Module hibernate5Module(){
		return new Hibernate5Module();
	}
}
