package domainapp.modules.visit.contributions;

import java.util.List;

import javax.inject.Inject;

import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.MemberSupport;

import lombok.RequiredArgsConstructor;

import domainapp.modules.petowner.dom.petowner.PetOwner;
import domainapp.modules.visit.dom.visit.Visit;
import domainapp.modules.visit.dom.visit.VisitRepository;

@Collection
@RequiredArgsConstructor
public class PetOwner_visits {

    private final PetOwner petOwner;

    @MemberSupport
    public List<Visit> coll() {
        return visitRepository.findByPetOwner(petOwner);
    }

    @Inject VisitRepository visitRepository;

}
