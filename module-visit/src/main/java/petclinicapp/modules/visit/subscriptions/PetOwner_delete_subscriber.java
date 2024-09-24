package petclinicapp.modules.visit.subscriptions;

import java.util.List;

import javax.inject.Inject;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import petclinicapp.modules.petowner.dom.petowner.PetOwner;
import petclinicapp.modules.visit.dom.visit.Visit;
import petclinicapp.modules.visit.dom.visit.VisitRepository;

@Component
public class PetOwner_delete_subscriber {

    @EventListener(PetOwner.DeleteActionDomainEvent.class)
    void on(PetOwner.DeleteActionDomainEvent event) {
        PetOwner subject = event.getSubject();
        switch (event.getEventPhase()) {
            case HIDE:
                break;
            case DISABLE:
                List<Visit> visits = visitRepository.findByPetOwner(subject);
                if (!visits.isEmpty()) {
                    event.veto("This owner has %d visit%s", visits.size(), (visits.size() == 1 ? "" : "s"));
                }
                break;
            case VALIDATE:
                break;
            case EXECUTING:
                break;
            case EXECUTED:
                break;
        }
    }

    @Inject VisitRepository visitRepository;
}
