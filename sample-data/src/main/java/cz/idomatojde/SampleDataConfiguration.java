package cz.idomatojde;

import cz.idomatojde.configuration.ServiceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Configuration class for Sample data module
 *
 * @author Michal Hazdra
 */
@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = {SampleDataLoader.class})
public class SampleDataConfiguration {

    final SampleDataLoader sampleDataLoadingFacade;

    @Inject
    public SampleDataConfiguration(SampleDataLoader sampleDataLoadingFacade) {
        this.sampleDataLoadingFacade = sampleDataLoadingFacade;
    }

    @PostConstruct
    public void preload() {
        sampleDataLoadingFacade.loadData();
    }
}
