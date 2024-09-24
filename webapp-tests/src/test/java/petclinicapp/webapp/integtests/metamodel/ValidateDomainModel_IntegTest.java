package petclinicapp.webapp.integtests.metamodel;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import org.springframework.test.annotation.DirtiesContext;

import org.apache.causeway.applib.services.registry.ServiceRegistry;
import org.apache.causeway.testing.integtestsupport.applib.validate.DomainModelValidator;

import petclinicapp.webapp.integtests.WebAppIntegTestAbstract;

@DirtiesContext
class ValidateDomainModel_IntegTest extends WebAppIntegTestAbstract {

    @Inject ServiceRegistry serviceRegistry;

    @Test
    void validate() {
        new DomainModelValidator(serviceRegistry).assertValid();
    }


}
