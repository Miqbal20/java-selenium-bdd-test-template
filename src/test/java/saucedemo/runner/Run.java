package saucedemo.runner;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

// Code by Miqbal20
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/saucedemo/features",
        glue = "saucedemo.steps",
        plugin = {"html:src/test/java/saucedemo/reports/HTML_report.html"}
)

public class Run {
}
