package petclinicapp.webapp.application.fixture.scenarios;

import javax.inject.Inject;

import org.apache.causeway.testing.fixtures.applib.fixturescripts.FixtureScript;
import org.apache.causeway.testing.fixtures.applib.modules.ModuleWithFixturesService;

import petclinicapp.modules.visit.fixture.Visit_persona;

public class PetclinicAppDemo extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext ec) {
        ec.executeChildren(this, moduleWithFixturesService.getTeardownFixture());
        ec.executeChild(this, new Visit_persona.PersistAll());
    }

    @Inject ModuleWithFixturesService moduleWithFixturesService;

}
