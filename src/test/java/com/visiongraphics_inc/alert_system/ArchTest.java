package com.visiongraphics_inc.alert_system;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.visiongraphics_inc.alert_system");

        noClasses()
            .that()
                .resideInAnyPackage("com.visiongraphics_inc.alert_system.service..")
            .or()
                .resideInAnyPackage("com.visiongraphics_inc.alert_system.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.visiongraphics_inc.alert_system.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
