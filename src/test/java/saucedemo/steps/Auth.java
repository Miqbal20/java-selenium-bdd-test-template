package saucedemo.steps;

import io.cucumber.java.en.*;
import org.junit.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import java.time.Duration;
import org.openqa.selenium.support.ui.Wait;
import saucedemo.function.*;

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
