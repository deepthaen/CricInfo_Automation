import org.junit.After;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",   // Path to your feature files
        glue = "stepDefinitions",                   // Package name where step definitions are located
        plugin = { "pretty", "html:target/cucumber-reports.html", "json:target/cucumber-reports/Cucumber.json" },
        monochrome = true                          // Makes console output cleaner

)
public class TestRunner {

}
