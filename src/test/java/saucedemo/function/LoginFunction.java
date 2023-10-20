package saucedemo.function;
import org.openqa.selenium.*;

public class LoginFunction {
    String baseUrl = "https://saucedemo.com";
    public void UserLogin(WebDriver driver, String username, String password) throws InterruptedException { // Method
        driver.get(baseUrl);
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
    }
}
