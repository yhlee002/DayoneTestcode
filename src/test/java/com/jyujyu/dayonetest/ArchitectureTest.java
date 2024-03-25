package com.jyujyu.dayonetest;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ArchitectureTest {

    JavaClasses javaClasses;

    @BeforeEach
    public void beforeEach() {
        javaClasses = new ClassFileImporter()
            .withImportOption(new ImportOption.DoNotIncludeTests())
            .importPackages("com.jyujyu.dayonetest");
    }

    @Test
    @DisplayName("controller 패키지 안에 있는 클래스들은 Controller로 끝나고 " +
        "RestController/Controller 어노테이션이 붙어야 한다.")
    public void controllerTest() {
        ArchRule rule = classes()
            .that().resideInAnyPackage("..controller")
            .should().haveSimpleNameEndingWith("Api");
        // resideInAnyPackage() : 인자로 받은 형태에 매칭되는 패키지안에 속한 클래스들 모두
        // haveSimpleNameEndingWith() : 인자로 받은 값으로 끝나는 이름을 가져야 한다.
        // Cf. resideInAnyPackage 인자의 `..controller` : `controller`로 끝나는 형태를 의미

        ArchRule annotationRule = classes()
            .that().resideInAnyPackage("..controller")
            .should().beAnnotatedWith(Controller.class)
                .orShould().beAnnotatedWith(RestController.class);

        rule.check(javaClasses);
        annotationRule.check(javaClasses);
    }

    @Test
    @DisplayName("request 패키지 안에 있는 클래스는 Request로 끝나야 한다.")
    public void requestTest() {
        ArchRule rule = classes()
            .that().resideInAnyPackage("..request..")
            .should().haveSimpleNameEndingWith("Request");

        rule.check(javaClasses);
    }

    @Test
    @DisplayName("response 패키지 안에 있는 클래스는 Response로 끝나냐 한다.")
    public void responseTest() {
        ArchRule rule = classes()
            .that().resideInAnyPackage("..response..")
            .should().haveSimpleNameEndingWith("Response");
    }

    @Test
    @DisplayName("repository 패키지 안에 있는 클래스는 Repository로 끝나야 하고, 인터페이스여야 한다.")
    public void repositoryTest() {
        ArchRule rule = classes()
            .that().resideInAnyPackage("..repository")
            .should().haveSimpleNameEndingWith("Repository")
            .andShould().beInterfaces();
        // beInterfaces() : 클래스를 생성해 @Repository 어노테이션을 붙어주더라도 테스트 실패

        rule.check(javaClasses);
    }

    @Test
    @DisplayName("service 패키지 안에 있는 클래스는 Service로 끝나야 하고, @Service 어노테이션이 붙어야 한다.")
    public void serviceTest() {
        ArchRule rule = classes()
            .that().resideInAnyPackage("..service..")
            .should().haveSimpleNameEndingWith("Service")
            .andShould().beAnnotatedWith(Service.class);

        rule.check(javaClasses);
    }

    @Test
    @DisplayName("config 패키지 안에 있는 클래스는 Config로 끝나야 하고, @Configuration 어노테이션이 붙어야 한다.")
    public void configTest() {
        ArchRule rule = classes()
            .that().resideInAnyPackage("..config")
            .should().haveSimpleNameEndingWith("Config")
            .andShould().beAnnotatedWith(Configuration.class);
    }
}
