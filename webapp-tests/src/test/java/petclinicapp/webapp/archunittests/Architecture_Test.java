package petclinicapp.webapp.archunittests;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import org.apache.causeway.testing.archtestsupport.applib.classrules.ArchitectureDomainRules;

import petclinicapp.modules.petowner.PetOwnerModule;
import petclinicapp.modules.visit.VisitModule;
import petclinicapp.webapp.application.ApplicationModule;

@AnalyzeClasses(
        packagesOf = {
                PetOwnerModule.class
                , VisitModule.class
                , ApplicationModule.class
        },
        importOptions = { ImportOption.DoNotIncludeTests.class }
)
public class Architecture_Test {

    @ArchTest
    public static ArchRule every_DomainObject_must_also_be_annotated_with_DomainObjectLayout =
            ArchitectureDomainRules.every_DomainObject_must_also_be_annotated_with_DomainObjectLayout();

}