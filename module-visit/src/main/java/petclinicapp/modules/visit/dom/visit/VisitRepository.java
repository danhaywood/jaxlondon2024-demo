package petclinicapp.modules.visit.dom.visit;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import petclinicapp.modules.petowner.dom.pet.Pet;
import petclinicapp.modules.petowner.dom.petowner.PetOwner;

public interface VisitRepository extends Repository<Visit, Integer> {

    List<Visit> findByVisitAtAfter(LocalDateTime visitAt);

    Visit findByPetAndVisitAt(Pet pet, LocalDateTime visitAt);

    @Query("select v from Visit v where v.pet.petOwner = :petOwner")
    List<Visit> findByPetOwner(PetOwner petOwner);
}
