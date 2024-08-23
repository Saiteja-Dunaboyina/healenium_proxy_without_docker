package com.healeniumproxy.base;

import com.aventstack.extentreports.App;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.gson.JsonObject;
import com.healeniumproxy.pages.AddToCartPage;
import com.healeniumproxy.pages.HomePage;
import com.healeniumproxy.pages.LoginPage;
import com.healeniumproxy.pages.SideBarValidation;
import com.healeniumproxy.replacethelocators.ReadTheDataBaseResult;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;


public class Base {

    public static ExtentReports extent;
    public static ExtentTest test;
    public WebDriver driver;
    public JsonObject jsonObject;
    public Properties properties;
    private final JsonDataConverter converter = new JsonDataConverter();
    public LoginPage loginPage;
    public AddToCartPage addToCartPage;
    public SideBarValidation sideBarValidation;
    public HomePage homePage;
    //mobile
    public AppiumDriver appiumDriver;
    public Properties appiumProperties;
    public JsonObject jsonData;
    public DesiredCapabilities capabilities;
    String nodeURL = "http://localhost:8085";


    @BeforeSuite(alwaysRun = true)
    public void getWebDriver() throws MalformedURLException {
//        ChromeOptions options = new ChromeOptions();
//        driver = new RemoteWebDriver(new URL(nodeURL), options);
        properties = new Properties();
        loginPage = new LoginPage(driver);
        addToCartPage = new AddToCartPage(driver);
        sideBarValidation = new SideBarValidation(driver);
        homePage = new HomePage(driver);
        FileReader fileReader;
        try {
            fileReader = new FileReader(
                    System.getProperty("user.dir") + "/src/test/resources/saucedemo.properties");
            properties.load(fileReader);
            jsonObject = converter.getJsonObject(properties.getProperty("saucedemoDataPath"));
            fileReader.close();

            //mobile
//            AppiumServer.getServer().start();
            appiumProperties = new Properties();
            appiumProperties.load(App.class.getClassLoader().getResourceAsStream("./config/bigoven.properties"));
            jsonData = converter.getJsonObject(appiumProperties.getProperty("bigovenDataPath"));
            capabilities = new DesiredCapabilities();
            capabilities.setCapability("newCommandTimeout", 30000);
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, appiumProperties.getProperty("platformName"));
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, appiumProperties.getProperty("deviceName"));
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, appiumProperties.getProperty("automationName"));
            capabilities.setCapability(MobileCapabilityType.APP,
                    System.getProperty("user.dir") + appiumProperties.getProperty("appPath"));
            capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, appiumProperties.getProperty("timeout"));
            capabilities.setCapability("appium:autoAcceptAlerts", appiumProperties.getProperty("autoAcceptAlerts"));
            capabilities.setCapability("appium:noReset", appiumProperties.getProperty("noReset"));
            capabilities.setCapability("appium:appPackage", appiumProperties.getProperty("appPackage"));
            capabilities.setCapability("appium:appActivity", appiumProperties.getProperty("appActivity"));
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
                    properties.getProperty("platformVersion"));
//            appiumDriver = new AndroidDriver(new URL(appiumProperties.getProperty("appiumServerUrl")), capabilities);
//            appiumDriver = new AndroidDriver(new URL(nodeURL), capabilities);
//            appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @BeforeTest
    public void startReport() {
        ExtentSparkReporter spark = new ExtentSparkReporter("./reports/Extentreport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("MyReport");
        spark.config().setReportName("Test Report");
        spark.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
    }

    private String captureScreenshot(String testName, String directoryPath) {
        String screenshotPath = null;
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedTime = currentTime.format(formatter);
        try {
            TakesScreenshot ts =  (TakesScreenshot) driver;
            File screenshotFile = ts.getScreenshotAs(OutputType.FILE);
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            System.out.println(testName + "    Test Name");
            screenshotPath = directoryPath + "/" + testName + formattedTime + ".png";
            FileUtils.copyFile(screenshotFile, new File(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(screenshotPath);
        return screenshotPath;
    }


    @AfterMethod
    public void getResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, result.getThrowable());
            String path = captureScreenshot(result.getName(), System.getProperty("user.dir") + properties.getProperty("imagesPath"));
            test.log(Status.FAIL, "Test Case Failed");
            System.out.println(System.getProperty("user.dir") + properties.getProperty("imagesPath"));
            System.out.println(path);
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            test.fail("Screenshot below: " + test.addScreenCaptureFromPath(path));
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test Case Passed Sucessfully");
        } else {
            test.log(Status.SKIP, result.getTestName());
        }
    }

    @AfterTest
    public void endReport() {
        extent.flush();
    }

    @AfterSuite
    public void tearDown() throws Exception {
        driver.quit();
//        appiumDriver.quit();
        ReadTheDataBaseResult dataBaseResult = new ReadTheDataBaseResult();
        dataBaseResult.readTheDataFromDB();
    }

}
