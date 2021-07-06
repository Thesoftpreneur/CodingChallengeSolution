package com.softprenuer.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/reports/cucumber-html-report.html",
        "json:target/reports/cucumber.json"},
        features = {"src/test/java/com/softprenuer/features"},
        glue = {"com.softprenuer.stepdefinitions"})
public class RunCucumberTest {

}
