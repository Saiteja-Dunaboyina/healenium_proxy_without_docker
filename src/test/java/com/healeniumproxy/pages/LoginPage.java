package com.healeniumproxy.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    public WebDriver driver;
    @FindBy(xpath = "//input[@id='user-name']")
    WebElement usernameInput;
    @FindBy(xpath = "//input[@id='password']")
    WebElement passwordInput;
    @FindBy(xpath = "//input[@id='login-button']")
    WebElement loginBtn;
    @FindBy(xpath = "//div[text()='Swag Labs']")
    WebElement headerText;
    @FindBy(xpath = "//button[@id='react-burger-menu-btn']")
    WebElement menuBtn;
    @FindBy(xpath = "//a[@id='logout_sidebar_link']")
    WebElement logoutBtn;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterUsernameAndPassword(String username,String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
    }

    public void clickOnLoginButton() {
        loginBtn.click();
    }

    public String getHeaderText() {
        return headerText.getText();
    }

    public void logout() {
        menuBtn.click();
        logoutBtn.click();
    }
}
