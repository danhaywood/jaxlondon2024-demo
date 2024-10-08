package petclinicapp.webapp.application.services.homepage;

import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.HomePage;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.TableDecorator;
import org.apache.causeway.applib.services.clock.ClockService;

import petclinicapp.modules.petowner.dom.petowner.PetOwner;
import petclinicapp.modules.petowner.dom.petowner.PetOwners;
import petclinicapp.modules.visit.dom.visit.Visit;
import petclinicapp.modules.visit.dom.visit.VisitRepository;
import petclinicapp.webapp.application.ApplicationModule;

@Named(ApplicationModule.NAMESPACE + ".HomePageViewModel")
@DomainObject(nature = Nature.VIEW_MODEL)
@HomePage
@DomainObjectLayout()
public class HomePageViewModel {

    @ObjectSupport public String title() {
        return getPetOwners().size() + " pet owners, " + getFutureVisits().size() + " future visits";
    }

    @Collection
    @CollectionLayout(tableDecorator = TableDecorator.DatatablesNet.class)
    public List<PetOwner> getPetOwners() {
        return petOwners.listAll();
    }

    @Collection
    @CollectionLayout(tableDecorator = TableDecorator.DatatablesNet.class)
    public List<Visit> getFutureVisits() {                                  // <.>
        LocalDateTime now = clockService.getClock().nowAsLocalDateTime();
        return visitRepository.findByVisitAtAfter(now);
    }

    @Inject ClockService clockService;
    @Inject VisitRepository visitRepository;
    @Inject PetOwners petOwners;
}
