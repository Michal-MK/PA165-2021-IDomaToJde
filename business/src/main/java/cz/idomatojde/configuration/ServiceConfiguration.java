package cz.idomatojde.configuration;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
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

    @Bean
    public Mapper dozer() {
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.addMapping(new DozerCustomConfig());
        return dozer;
    }

    public static class DozerCustomConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
            /*TODO*/
        }
    }
}
