package petclinicapp.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

import org.apache.causeway.core.config.presets.CausewayPresets;

@SpringBootApplication
@Import({
    AppManifest.class
//    , XrayEnable.class
})
public class PetClinicApp extends SpringBootServletInitializer {

    /**
     * @implNote this is to support the <em>Spring Boot Maven Plugin</em>, which auto-detects an
     * entry point by searching for classes having a {@code main(...)}
     */
    public static void main(String[] args) {
        CausewayPresets.prototyping();
        SpringApplication.run(new Class[] { PetClinicApp.class }, args);
    }

}
