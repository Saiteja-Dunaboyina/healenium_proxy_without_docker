package com.healeniumproxy.test;

import com.epam.healenium.SelfHealingDriver;
import com.healeniumproxy.replacethelocators.ReadTheDataBaseResult;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class FormTest {
    public WebDriver driver;
    String nodeURL = "http://localhost:8085";

    @BeforeSuite(alwaysRun = true)
    public void getWebDriver() throws MalformedURLException {
//        driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL(nodeURL), options);
    }

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver.manage().window().maximize();
        driver.get("C:\\Users\\jyothi.dunaboyina\\IdeaProjects\\healeniumproxywithoutdocker\\src\\test\\resources\\Html\\form.html");
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
    }

    @Test
    public void testFormSubmission() {
        // Locate form elements
        WebElement nameInput = driver.findElement(By.id("inputName"));
        WebElement emailInput = driver.findElement(By.id("inputEmail"));
        WebElement passwordInput = driver.findElement(By.cssSelector("input#pwd1"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        // Fill in the form
        nameInput.sendKeys("John Doe");
        emailInput.sendKeys("john.doe@example.com");
        passwordInput.sendKeys("input#inputPassword123");

        // Submit the form
        submitButton.click();
    }

    @AfterSuite
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
            ReadTheDataBaseResult dataBaseResult = new ReadTheDataBaseResult();
            dataBaseResult.readTheDataFromDB();
        }
    }

}
