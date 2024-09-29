package petclinicapp.webapp.integtests.smoke;

import java.util.List;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import org.apache.causeway.applib.services.wrapper.InvalidException;
import org.apache.causeway.applib.services.xactn.TransactionService;

import petclinicapp.modules.petowner.dom.petowner.PetOwner;
import petclinicapp.modules.petowner.dom.petowner.PetOwners;
import petclinicapp.webapp.integtests.WebAppIntegTestAbstract;

@DirtiesContext
@Transactional
class Smoke_IntegTest extends WebAppIntegTestAbstract {

    @Inject PetOwners menu;
    @Inject TransactionService transactionService;

    @Test
    void happy_case() {

        // when
        List<PetOwner> all = wrap(menu).listAll();

        // then
        assertThat(all).isEmpty();


        // when
        final PetOwner fred = wrap(menu).create("Frederick", "Fred", null, null);
        transactionService.flushTransaction();

        // then
        all = wrap(menu).listAll();
        assertThat(all).hasSize(1);
        assertThat(all).contains(fred);


        // when
        final PetOwner bill = wrap(menu).create("William", "Bill", null, null);
        transactionService.flushTransaction();

        // then
        all = wrap(menu).listAll();
        assertThat(all).hasSize(2);
        assertThat(all).contains(fred, bill);


        // when
        wrap(fred).updateName("Freddy");
        transactionService.flushTransaction();

        // then
        assertThat(wrap(fred).getName()).isEqualTo("Freddy");


        // when
        wrap(fred).setNotes("These are some notes");
        transactionService.flushTransaction();

        // then
        assertThat(wrap(fred).getNotes()).isEqualTo("These are some notes");


        // when
        Assertions.assertThrows(InvalidException.class, () -> {
            wrap(fred).updateName("New name !!!");
            transactionService.flushTransaction();
        }, "Exclamation mark is not allowed");

        // then
        assertThat(wrap(fred).getNotes()).isEqualTo("These are some notes");


        // when
        wrap(fred).delete();
        transactionService.flushTransaction();

        // then
        all = wrap(menu).listAll();
        assertThat(all).hasSize(1);
    }

}

