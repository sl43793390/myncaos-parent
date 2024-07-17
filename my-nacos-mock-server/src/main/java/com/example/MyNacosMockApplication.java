package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication(exclude = { DataSourceTransactionManagerAutoConfiguration.class,
        DataSourceAutoConfiguration.class})
@EnableCaching
@EnableScheduling
@EnableVaadin
@Theme(value = "mytodo" ,variant = Lumo.LIGHT)
//@JsModule("@vaadin/vaadin-lumo-styles/presets/compact.js")//压缩模式
@NpmPackage(value = "line-awesome", version = "1.3.0")
@Push
public class MyNacosMockApplication implements AppShellConfigurator {

    private static final long serialVersionUID = 8498827362438741254L;

	public static void main(String[] args) {
        SpringApplication.run(MyNacosMockApplication.class, args);
    }

}