# API Testing Project - RESTful API Automation

This project demonstrates automated testing of RESTful APIs using Java, RestAssured, and Cucumber BDD framework. The tests are designed to validate CRUD operations on the [ReqRes.in](https://reqres.in/) public API.

## 🚀 Quick Start

### Prerequisites

Before running the tests, ensure you have the following installed:

- **Java JDK 11 or higher**
  ```bash
  java -version
  ```

- **Apache Maven 3.6 or higher**
  ```bash
  mvn -version
  ```

- **IDE** (IntelliJ IDEA, Eclipse, or VS Code with Java extensions)

### Project Setup

1. **Clone or download the project**
   ```bash
   git clone <repository-url>
   cd api-testing-project
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Verify setup**
   ```bash
   mvn compile
   ```

## 🏗️ Project Structure

```
api-testing-project/
├── src/
│   ├── main/java/                 # Deleted
│   └── test/
│       ├── java/com/apitest/
│       │   ├── runners/
│       │   │   └── TestRunner.java     # Cucumber test runner
│       │   ├── stepdefinitions/
│       │       └── APIStepDefinitions.java  # Step implementations
│       │   
│       │             
│       └── resources/features/
│           ├── users.feature           # User API test scenarios
│           └── resources.feature       # Resource API test scenarios
├── target/
│   ├── cucumber-reports/           # Generated test reports
│   └── test-classes/              # Compiled test classes
├── pom.xml                        # Maven configuration
└── README.md                      # This file
```

## 🧪 Test Execution

### Running All Tests
```bash
mvn test
```

### Running Tests by Tags

**Smoke Tests (Core functionality)**
```bash
mvn test -Dcucumber.filter.tags="@smoke"
```

**GET Request Tests**
```bash
mvn test -Dcucumber.filter.tags="@get"
```

**POST Request Tests**
```bash
mvn test -Dcucumber.filter.tags="@post"
```

**Negative Test Cases**
```bash
mvn test -Dcucumber.filter.tags="@negative"
```

**Combined Tags**
```bash
mvn test -Dcucumber.filter.tags="@smoke and @get"
mvn test -Dcucumber.filter.tags="@smoke or @negative"
```

### Running from IDE

1. **Run all tests**: Right-click on `TestRunner.java` → Run
2. **Run specific feature**: Right-click on any `.feature` file → Run
3. **Run specific scenario**: Click the play button next to any scenario

## 📊 Test Reports

After test execution, reports are generated in multiple formats:

### HTML Report
- **Location**: `target/cucumber-reports/index.html`
- **Features**: Interactive report with screenshots, step details, and execution statistics
- **Access**: Open in any web browser

### JSON Report
- **Location**: `target/cucumber-reports/Cucumber.json`
- **Usage**: Machine-readable format for CI/CD integration

### JUnit XML Report
- **Location**: `target/cucumber-reports/Cucumber.xml`
- **Usage**: Integration with build tools and CI systems

## 🎯 Test Scenarios Covered

### User Management (users.feature)

| Scenario | HTTP Method | Endpoint | Description |
|----------|-------------|----------|-------------|
| Get all users | GET | `/users` | Retrieves paginated user list |
| Get user by ID | GET | `/users/{id}` | Retrieves specific user details |
| Get non-existent user | GET | `/users/999` | Validates 404 error handling |
| Create new user | POST | `/users` | Creates user with valid data |
| Create user (invalid data) | POST | `/users` | Tests with empty/invalid data |
| Update user | PUT | `/users/{id}` | Full user update |
| Partial update user | PATCH | `/users/{id}` | Partial user update |
| Delete user | DELETE | `/users/{id}` | User deletion |

### Resource Management (resources.feature)

| Scenario | HTTP Method | Endpoint | Description |
|----------|-------------|----------|-------------|
| Get all resources | GET | `/unknown` | Retrieves resource list |
| Get resource by ID | GET | `/unknown/{id}` | Retrieves specific resource |
| Get non-existent resource | GET | `/unknown/999` | Validates 404 error handling |

## 🔧 Configuration Options

### Modifying Test Tags in TestRunner.java
```java
@CucumberOptions{
    tags = '@smoke';
    tags = '@get or @post';
    tags = 'not @negative';
}
```

### Environment Configuration
To test against different environments, modify the base URL in feature files:
```gherkin
Given the API base URL is "https://your-api-endpoint.com/api"
```

### Timeout Settings
Modify response time validations in step definitions:
```java
@Then("the response time should be less than {int}ms")
public void validateResponseTime(long maxTime) {
    // Adjust maxTime as needed for your environment
}
```

## 🐛 Troubleshooting

### Common Issues and Solutions

1. **Tests fail with connection timeout**
    - Check internet connectivity
    - Verify reqres.in is accessible
    - Increase timeout in RestAssured configuration

2. **Maven dependency issues**
   ```bash
   mvn clean install -U  # Force update dependencies
   mvn dependency:tree   # Check dependency conflicts
   ```

3. **Java version conflicts**
   ```bash
   java -version        # Check Java version
   mvn -version         # Check Maven's Java version
   export JAVA_HOME=/path/to/java11  # Set correct Java home
   ```

4. **IDE not recognizing tests**
    - Refresh/reimport Maven project
    - Verify Cucumber plugin is installed
    - Check test source folders are marked correctly

5. **Reports not generating**
    - Ensure target directory has write permissions
    - Check for special characters in project path
    - Run with `-X` flag for detailed logging: `mvn test -X`

### Debug Mode
Run tests with verbose logging:
```bash
mvn test -Dcucumber.options="--plugin pretty --plugin html:target/cucumber-reports" -X
```

## 📈 Extending the Framework

### Adding New Test Scenarios

1. **Add to feature files**:
   ```gherkin
   Scenario: New test case
     Given some precondition
     When I perform an action
     Then I expect some result
   ```

2. **Implement step definitions**:
   ```java
   @Given("some precondition")
   public void precondition() {
       // Implementation
   }
   ```

### Adding New Endpoints

1. Create new feature file in `src/test/resources/features/`
2. Add corresponding step definitions
3. Update TestRunner tags if needed

### Custom Validations

Add custom assertion methods in `APIStepDefinitions.java`:
```java
@Then("the response should have valid schema")
public void validateSchema() {
    response.then().assertThat()
           .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));
}
```

## 🔗 API Documentation

The tests use the **ReqRes.in** API which provides:
- **Base URL**: https://reqres.in/api
- **Users endpoint**: `/users` (supports GET, POST, PUT, PATCH, DELETE)
- **Resources endpoint**: `/unknown` (supports GET)
- **Authentication**: Not required
- **Rate limiting**: None for testing purposes

For detailed API documentation, visit: [https://reqres.in/](https://reqres.in/)

## 📞 Support

For issues or questions:
1. Check the troubles