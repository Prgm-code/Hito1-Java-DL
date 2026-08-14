package com.chronus;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.chronus", importOptions = ImportOption.DoNotIncludeTests.class)
class ArchitectureTest {

    @ArchTest
    static final ArchRule domainMustNotDependOnApplication = noClasses()
            .that().resideInAPackage("com.chronus.domain..")
            .should().dependOnClassesThat().resideInAPackage("com.chronus.application..");

    @ArchTest
    static final ArchRule domainMustNotDependOnInfrastructure = noClasses()
            .that().resideInAPackage("com.chronus.domain..")
            .should().dependOnClassesThat().resideInAPackage("com.chronus.infrastructure..");

    @ArchTest
    static final ArchRule applicationMustNotDependOnInfrastructure = noClasses()
            .that().resideInAPackage("com.chronus.application..")
            .should().dependOnClassesThat().resideInAPackage("com.chronus.infrastructure..");

    @ArchTest
    static final ArchRule domainMustNotDependOnFrameworks = noClasses()
            .that().resideInAPackage("com.chronus.domain..")
            .should().dependOnClassesThat().resideInAnyPackage(
                    "org.springframework..",
                    "jakarta.persistence..",
                    "javax.persistence..",
                    "com.fasterxml..");

    @ArchTest
    static final ArchRule applicationMustNotDependOnFrameworks = noClasses()
            .that().resideInAPackage("com.chronus.application..")
            .should().dependOnClassesThat().resideInAnyPackage(
                    "org.springframework..",
                    "jakarta.persistence..",
                    "javax.persistence..",
                    "com.fasterxml..");
}
