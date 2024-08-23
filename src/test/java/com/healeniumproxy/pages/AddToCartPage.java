package com.healeniumproxy.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddToCartPage {

    public WebDriver driver;
    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    WebElement addToCartBtn;
    @FindBy(xpath = "//a[@class='shopping_cart_link']")
    WebElement addToCartLogo;
    @FindBy(id = "checkout")
    WebElement checkOutBtn;
    @FindBy(id = "first-name")
    WebElement firstNameInput;
    @FindBy(id = "last-name")
    WebElement lastNameInput;
    @FindBy(id = "postal-code")
    WebElement pincodeInput;
    @FindBy(id = "continue")
    WebElement continueBtn;
    @FindBy(id = "finish")
    WebElement finishBtn;
    @FindBy(id = "back-to-products")
    WebElement backHomeBtn;

    public AddToCartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickOnAddToCart() {
        addToCartBtn.click();
        addToCartLogo.click();
    }

    public void clickOnCheckout() {
        checkOutBtn.click();
    }

    public void enterUserDetails(String firstname,String lastname,String pincode) {
        firstNameInput.sendKeys(firstname);
        lastNameInput.sendKeys(lastname);
        pincodeInput.sendKeys(pincode);
        continueBtn.click();
    }

    public void clickOnFinish() {
        finishBtn.click();
    }

    public boolean isBackHomeBtnVisible() {
        return backHomeBtn.isDisplayed();
    }

    public void clickOnBackHome() {
        backHomeBtn.click();
    }
}
