package cz.idomatojde.rest;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.regex.Matcher;

/**
 * Bean configuration class for the facade layer
 *
 * @author Michal Hazdra
 */
@Configuration
@ComponentScan("cz.idomatojde.*")
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RestLayerApplication.class);
    }

    @Bean
    public Docket swaggerDoc() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths((s) -> !s.contains("error"))
                .build();
    }
}
