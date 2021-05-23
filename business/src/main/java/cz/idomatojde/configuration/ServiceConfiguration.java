package cz.idomatojde.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author PA165 team, adapted to current project by Michal Hazdra
 */
@Configuration
@ImportResource("classpath:applicationConfig.xml")
@ComponentScan("cz.idomatojde.*")
public class ServiceConfiguration {

}
