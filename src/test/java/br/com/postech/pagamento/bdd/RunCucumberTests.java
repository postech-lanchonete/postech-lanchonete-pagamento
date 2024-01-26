package br.com.postech.pagamento.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:build/reports/cucumber/cucumber-report.html"},
        features = "src/test/resources/features",
        glue = {"br.com.postech_lanchonete.pagamento.bdd"})
public class RunCucumberTests {
}
