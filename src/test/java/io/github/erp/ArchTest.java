package io.github.erp;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("io.github.erp");

        noClasses()
            .that()
            .resideInAnyPackage("io.github.erp.service..")
            .or()
            .resideInAnyPackage("io.github.erp.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..io.github.erp.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
