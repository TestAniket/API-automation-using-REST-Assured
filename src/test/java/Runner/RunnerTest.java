package Runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features= "classpath:FeatureFiles",
		glue="StepDefinition",
		tags="@GET", 
		plugin= {"pretty",
				"html:target/html/",
				"json:target/jsonfile.json"},
		stepNotifications = true,
		dryRun= false
		
		
		
		
		)
public class RunnerTest {

}
