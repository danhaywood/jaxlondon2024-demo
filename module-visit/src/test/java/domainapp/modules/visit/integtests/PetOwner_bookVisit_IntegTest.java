package domainapp.modules.visit.integtests;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.apache.causeway.applib.services.clock.ClockService;
import org.apache.causeway.applib.services.wrapper.InvalidException;
import org.apache.causeway.testing.fakedata.applib.services.FakeDataService;

import domainapp.modules.petowner.fixture.PetOwner_persona;
import domainapp.modules.visit.contributions.PetOwner_bookVisit;
import domainapp.modules.visit.dom.visit.VisitRepository;

public class PetOwner_bookVisit_IntegTest extends VisitModuleIntegTestAbstract {

    @BeforeEach
    void setup() {
        fixtureScripts.run(new PetOwner_persona.PersistAll());
    }

    @Test
    public void happy_case() {

        // given
        final var somePetOwner = fakeDataService.enums()
                .anyOf(PetOwner_persona.class)
                .findUsing(serviceRegistry);
        final var somePet = fakeDataService.collections()
                .anyOf(somePetOwner.getPets());

        final var before = visitRepository.findByPetOwner(somePetOwner);
        assertThat(before).isEmpty();

        // when
        var visitAt = clockService.getClock().nowAsLocalDateTime()
                .plusDays(fakeDataService.ints().between(1, 3));
        if(visitAt.getHour() < 9) {
            visitAt = visitAt.plusHours(9 - visitAt.getHour());
        }

        wrapMixin(PetOwner_bookVisit.class, somePetOwner).act(somePet, visitAt);

        // then
        final var after = visitRepository.findByPetOwner(somePetOwner);
        assertThat(after).hasSize(1);

        final var visit = after.get(0);

        assertThat(visit.getPet()).isSameAs(somePet);
        assertThat(visit.getPet().getPetOwner()).isSameAs(somePetOwner);
        assertThat(visit.getVisitAt()).isEqualTo(visitAt);
    }

    @Test
    public void cannot_book_in_the_past() {

        // given
        final var somePetOwner = fakeDataService.enums()
                .anyOf(PetOwner_persona.class)
                .findUsing(serviceRegistry);
        final var somePet = fakeDataService.collections()
                .anyOf(somePetOwner.getPets());

        // when, then
        final var visitAt = clockService.getClock().nowAsLocalDateTime();

        assertThatThrownBy(() ->
                wrapMixin(PetOwner_bookVisit.class, somePetOwner).act(somePet, visitAt)
        )
                .isInstanceOf(InvalidException.class)
                .hasMessage("Must book in the future");
    }


    @Inject FakeDataService fakeDataService;
    @Inject VisitRepository visitRepository;
    @Inject ClockService clockService;

}
