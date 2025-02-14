package de.thi.inf.cnd.supermarket_scout.userprofile_auth_app;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "de.thi.inf.cnd.supermarket_scout.userprofile_auth_app")
public class DependencyTest {
    /**
     * Check dependencies between adapters
     */
    @ArchTest
    public static final ArchRule REST_SHOULD_NOT_ACCESS_JPA = noClasses().that().resideInAPackage("..adapter.rest..")
            .should().accessClassesThat().resideInAPackage("..adapter.jpa..");

    @ArchTest
    public static final ArchRule JPA_SHOULD_NOT_ACCESS_REST = noClasses().that().resideInAPackage("..adapter.jpa..")
            .should().accessClassesThat().resideInAPackage("..adapter.rest..");

    /**
     * Check dependencies between domain/application and adapters
     */
    @ArchTest
    public static final ArchRule DOMAIN_SHOULD_NOT_DEPEND_ON_ANY_OTHER_LAYER = noClasses().that()
            .resideInAnyPackage("..domain..").should().dependOnClassesThat().resideInAnyPackage(
                    "..adapter..",
                    "..application..",
                    "javax..",
                    "org.springframework"
            );

    @ArchTest
    public static final ArchRule APPLICATION_SHOULD_ONLY_DEPEND_ON_DOMAIN_AND_PORTS = classes().that()
            .resideInAnyPackage("..application..").should().onlyDependOnClassesThat()
            .resideInAnyPackage(
                    "java..",
                    "javax..",
                    "..domain..",
                    "..ports..",
                    "org.springframework.."
            );
}
