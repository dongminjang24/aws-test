package jpaBook.jpaShop2;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
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
	Hibernate5JakartaModule hibernate5Module() {
		Hibernate5JakartaModule hibernate5JakartaModule = new Hibernate5JakartaModule();
//		hibernate5JakartaModule.configure(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING, true);
//		return hibernate5JakartaModule;
		return new Hibernate5JakartaModule();
	}
}
