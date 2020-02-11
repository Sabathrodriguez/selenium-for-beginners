package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PositiveTest {

    @Test
    public void loginTest() {
//        create driver
        System.out.println("Starting loginTest");

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        WebDriver driver = new ChromeDriver();

//        open test page
        String url = "http://the-internet.herokuapp.com/login";
        driver.get(url);
        System.out.println("Page is opened");

        //maximize driver window
        driver.manage().window().maximize();

//        enter username
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("tomsmith");

//        enter password
        WebElement password = driver.findElement(By.xpath("/html//input[@id='password']"));
        password.sendKeys("SuperSecretPassword!");

//        click login button
        WebElement loginButton = driver.findElement(By.xpath("//form[@id='login']/button[@class='radius']"));
        loginButton.click();

//        verification:

//        new url:
        String expectedUrl = "http://the-internet.herokuapp.com/secure";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "actual page url is not the same as expected");

//        logout button is visible
        WebElement logoutButton = driver.findElement(By.xpath("//a[@href='/logout']"));
        Assert.assertTrue(logoutButton.isDisplayed(), "Logout button is not visible");

//        successful login message
        WebElement successMessage = driver.findElement(By.xpath("/html//div[@id='flash']"));
        String expectedMessage = "You logged into a secure area!";
        String actualMessage = successMessage.getText();
//        Assert.assertEquals(actualMessage, expectedMessage, "actual message is not the same as expected");
        Assert.assertTrue(actualMessage.contains(expectedMessage), "actual message is not the same as expected." +
                "\nActual message: "+actualMessage+", \nExpected message: "+expectedMessage+"\n");

        //close brower
        driver.quit();

    }
}
