package com.apitest.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.junit.BeforeClass;
import java.io.File;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // Path to feature files
        glue = "com.apitest.stepdefinitions",     // Package containing step definitions
        plugin = {
                "pretty",                          // Console output formatting
                "html:target/cucumber-reports/html-report", // HTML report with subdirectory
                "json:target/cucumber-reports/cucumber.json", // JSON report (lowercase filename)
                "junit:target/cucumber-reports/cucumber.xml"  // JUnit XML report (lowercase filename)
        },
        tags = "@smoke", // Run only smoke tests by default
        monochrome = true, // Clean console output
        publish = false    // Don't publish to Cucumber Reports service
)
public class TestRunner {

    /**
     * Creates necessary directories before test execution
     * This ensures the report directories exist before Cucumber tries to write to them
     */
    @BeforeClass
    public static void setUp() {
        // Create the cucumber-reports directory if it doesn't exist
        File reportsDir = new File("target/cucumber-reports");
        if (!reportsDir.exists()) {
            boolean created = reportsDir.mkdirs();
            if (created) {
                System.out.println("Created cucumber-reports directory: " + reportsDir.getAbsolutePath());
            } else {
                System.err.println("Failed to create cucumber-reports directory: " + reportsDir.getAbsolutePath());
            }
        }

        // Create subdirectories for different report types
        File htmlReportDir = new File("target/cucumber-reports/html-report");
        if (!htmlReportDir.exists()) {
            htmlReportDir.mkdirs();
        }
    }
}