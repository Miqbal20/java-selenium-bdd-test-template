<table>
<tr>
<td>Framework</td>
<td>Maven</td>
</tr>
<tr>
<td>Code by</td>
<td><a href="https://github.com/Miqbal20">Muhammad Iqbal</a> </td>
</tr>
</table>

## Step by Step

### Add Dependecies
```
<dependencies>
        <!-- https://mvnrepository.com/artifact/org.junit.vintage/junit-vintage-engine -->
        <dependency>
            <groupId>org.junit.vintage</groupId>
            <artifactId>junit-vintage-engine</artifactId>
            <version>5.10.0</version>
            <scope>test</scope>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java -->
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>4.10.0</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/io.github.bonigarcia/webdrivermanager -->
        <dependency>
            <groupId>io.github.bonigarcia</groupId>
            <artifactId>webdrivermanager</artifactId>
            <version>5.5.3</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/com.opencsv/opencsv -->
        <dependency>
            <groupId>com.opencsv</groupId>
            <artifactId>opencsv</artifactId>
            <version>5.7.1</version>
        </dependency>
        <dependency>
            <groupId>com.lmax</groupId>
            <artifactId>disruptor</artifactId>
            <version>3.4.4</version>
            <scope>test</scope>
        </dependency>

        <!-- https://mvnrepository.com/artifact/io.cucumber/cucumber-java -->
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-java</artifactId>
            <version>7.14.0</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/io.cucumber/cucumber-junit -->
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-junit</artifactId>
            <version>7.14.0</version>
            <scope>test</scope>
        </dependency>

    </dependencies>
```

### Create Folder inside folder java 
<table>
    <tr>
        <td colspan="2">Project</td>
    </tr>
    <tr>
        <td></td>
        <td>
            features <br>
            helper <br>
            report <br>
            runner <br>
            steps <br>
        </td>
    </tr>
</table>

### Format Feature
```
Feature: Authentication
  @Positive @Auth
  Scenario Outline: Verify Success User Login
    Given User on Login Pages Saucedemo
    When User fills valid <username> as username and <password> as password
    And User click on Login button
    Then User redirect to Dashboard Page

    Examples:
      | username      | password     |
      | standard_user | secret_sauce |
      | problem_user  | secret_sauce |
      | error_user    | secret_sauce |
      | visual_user   | secret_sauce |
```
### Format Step
```
package saucedemo.steps;

import io.cucumber.java.en.*;
import org.junit.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import java.time.Duration;

import saucedemo.helper.*;

public class Auth {
    WebDriver driver;
    String baseUrl = "https://saucedemo.com";
    Integer timeout = 500;

    public Auth(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
    }

    @Given("User on Login Pages Saucedemo")
    public void userOnLoginPagesSaucedemo() {
        this.driver.get(baseUrl);
    }

    @When("User fills valid (.*) as username and (.*) as password$")
    public void userFillsValidUsernameAndPassword(String email, String password) {
        this.driver.findElement(By.id("user-name")).sendKeys(email);
        this.driver.findElement(By.id("password")).sendKeys(password);
    }

    @And("User click on Login button")
    public void userClickOnLoginButton() {
        this.driver.findElement(By.id("login-button")).click();

    }

    @Then("User redirect to Dashboard Page")
    public void userRedirectToDashboardPage() throws InterruptedException {
        // Wait
        WaitingFunction delay = new WaitingFunction();
        delay.waiting_by_cssSelector(this.driver, 10, ".app_logo");

        // Variable Assertion
        String PageUrl = this.driver.getCurrentUrl();
        String PageTitle = this.driver.findElement(By.className("app_logo")).getText();

        // Assert
        Assert.assertEquals(PageUrl, "https://www.saucedemo.com/inventory.html");
        Assert.assertEquals(PageTitle, "Swag Labs");
        Thread.sleep(timeout);

        // Done
        this.driver.quit();

    }
}

```
### Format Helper
#### Waiting
```
package saucedemo.helper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class WaitingFunction {
    public void waiting_by_id(WebDriver driver, int seconds, String elements){
        WebElement element = driver.findElement(By.id(elements));
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(d -> element.isDisplayed());
    }

    public void waiting_by_cssSelector(WebDriver driver, int seconds, String elements){
        WebElement element = driver.findElement(By.cssSelector(elements));
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(d -> element.isDisplayed());
    }

    public void waiting_by_xpath(WebDriver driver, int seconds, String elements){
        WebElement element = driver.findElement(By.id(elements));
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(d -> element.isDisplayed());
    }
}

```

### Format Runner
```
package saucedemo.runner;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

// Code by Miqbal20
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/saucedemo/features",
        glue = "saucedemo.steps",
        plugin = {"html:src/test/java/saucedemo/report/HTML_report.html"}
)

public class Run {
}
```