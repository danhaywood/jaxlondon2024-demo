package petclinicapp.webapp.application;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import petclinicapp.modules.visit.VisitModule;

@Configuration
@Import({
        VisitModule.class
})
@ComponentScan
public class ApplicationModule {

    public static final String NAMESPACE = "application";

}
