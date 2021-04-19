package nl.hari;

import com.google.common.base.Predicates;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.function.Predicate;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;
@EnableSwagger2
@SpringBootApplication

@EnableJpaRepositories("nl.hari.jpa")
public class SwaggerGenerateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwaggerGenerateApplication.class, args);
	}

	@Bean
	public Docket swagger() {
		return new Docket(SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.paths(Predicates.not(PathSelectors.regex("/error")))
				.build();
	}

}
