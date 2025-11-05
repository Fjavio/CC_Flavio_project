package conservatory.bdd;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
	features = "src/test/resources/features", 
    glue = "bdd",            //Package step classes
    plugin = {"pretty", "html:target/cucumber-reports.html"} //Output report
)
public class TestRunner {
    
}