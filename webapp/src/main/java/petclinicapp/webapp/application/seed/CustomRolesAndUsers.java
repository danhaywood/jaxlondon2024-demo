package petclinicapp.webapp.application.seed;

import petclinicapp.modules.petowner.PetOwnerModule;
import petclinicapp.modules.visit.VisitModule;
import petclinicapp.webapp.application.ApplicationModule;

import java.util.function.Supplier;

import jakarta.inject.Inject;

import org.apache.causeway.applib.services.appfeat.ApplicationFeatureId;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.core.config.CausewayConfiguration;
import org.apache.causeway.extensions.secman.applib.permission.dom.ApplicationPermissionMode;
import org.apache.causeway.extensions.secman.applib.permission.dom.ApplicationPermissionRule;
import org.apache.causeway.extensions.secman.applib.role.fixtures.AbstractRoleAndPermissionsFixtureScript;
import org.apache.causeway.extensions.secman.applib.user.dom.AccountType;
import org.apache.causeway.extensions.secman.applib.user.fixtures.AbstractUserAndRolesFixtureScript;
import org.apache.causeway.testing.fixtures.applib.fixturescripts.FixtureScript;

public class CustomRolesAndUsers extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {
        executionContext.executeChildren(this,
                new ApplicationModuleSuperuserRole(),
                new PetClinicSuperuserRole(),
                new SvenUser());
    }

    private static class PetClinicSuperuserRole extends AbstractRoleAndPermissionsFixtureScript {

        public static final String ROLE_NAME = "petclinic-superuser";

        public PetClinicSuperuserRole() {
            super(ROLE_NAME, "Permission to use everything in the 'petowner' and 'visit' modules");
        }

        @Override
        protected void execute(ExecutionContext executionContext) {
            newPermissions(
                    ApplicationPermissionRule.ALLOW,
                    ApplicationPermissionMode.CHANGING,
                    Can.of(ApplicationFeatureId.newNamespace(PetOwnerModule.NAMESPACE), ApplicationFeatureId.newNamespace(VisitModule.NAMESPACE), ApplicationFeatureId.newNamespace(ApplicationModule.NAMESPACE))
            );
        }
    }

    private static class ApplicationModuleSuperuserRole extends AbstractRoleAndPermissionsFixtureScript {

        public static final String ROLE_NAME = "application-superuser";

        public ApplicationModuleSuperuserRole() {
            super(ROLE_NAME, "Permission to use everything in the 'application' module");
        }

        @Override
        protected void execute(ExecutionContext executionContext) {
            newPermissions(
                    ApplicationPermissionRule.ALLOW,
                    ApplicationPermissionMode.CHANGING,
                    Can.of(ApplicationFeatureId.newNamespace(ApplicationModule.NAMESPACE))
            );
        }
    }

    private static class SvenUser extends AbstractUserAndRolesFixtureScript {
        public SvenUser() {
            super(() -> "sven", () -> "pass", () -> AccountType.LOCAL, new RoleSupplier());
        }

        private static class RoleSupplier implements Supplier<Can<String>> {
            @Override
            public Can<String> get() {
                return Can.of(
                        causewayConfiguration.getExtensions().getSecman().getSeed().getRegularUser().getRoleName(), // built-in stuff
                        PetClinicSuperuserRole.ROLE_NAME,
                        ApplicationModuleSuperuserRole.ROLE_NAME
                        );
            }
            @Inject CausewayConfiguration causewayConfiguration;
        }
    }

}
