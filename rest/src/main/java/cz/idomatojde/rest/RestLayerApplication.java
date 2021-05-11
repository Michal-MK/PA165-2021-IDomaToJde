package cz.idomatojde.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Spring Rest application startup class
 *
 * @author Michal Hazdra
 */
@SpringBootApplication
@EnableSwagger2
public class RestLayerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestLayerApplication.class, args);
    }
}
