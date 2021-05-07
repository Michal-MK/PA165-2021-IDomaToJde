package cz.idomatojde.rest;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Swagger2 configuration class
 *
 * @author Michal Hazdra
 */
@Configuration
public class RestConfiguration implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/swagger", "/swagger-ui.html");
    }
}
