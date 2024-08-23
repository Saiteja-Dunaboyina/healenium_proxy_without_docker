package com.healeniumproxy.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SideBarValidation {
    public WebDriver driver;
    @FindBy(id = "react-burger-menu-btn")
    WebElement sideBarBtn;
    @FindBy(id = "react-burger-cross-btn")
    WebElement closeBtn;

    public SideBarValidation(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickOnSideBarBtn() {
        sideBarBtn.click();
    }

    public boolean sideBarValidation(String element) {
        return driver.findElement(By.xpath("//a[text()='"+ element +"']")).isDisplayed();
    }

    public void clickOnCloseBtn() {
        closeBtn.click();
    }
}
