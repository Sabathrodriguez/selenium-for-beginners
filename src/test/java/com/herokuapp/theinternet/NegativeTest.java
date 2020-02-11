package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NegativeTest {

    @Test(priority = 1, groups = { "negativeTests", "smokeTests" })
    public void invalidUsernameTest() {
        System.out.println("starting invalid username test");

        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
        WebDriver webDriver = new FirefoxDriver();

        String url = "http://the-internet.herokuapp.com/login";
        webDriver.get(url);
        System.out.println("page is opened");

        webDriver.manage().window().maximize();

        WebElement username = webDriver.findElement(By.xpath("/html//input[@id='username']"));
        username.sendKeys("incorrect");

        WebElement password = webDriver.findElement(By.xpath("/html//input[@id='password']"));
        password.sendKeys("SuperSecretPassword!");

        WebElement loginButton = webDriver.findElement(By.xpath("//form[@id='login']//i[@class='fa fa-2x fa-sign-in']"));
        loginButton.click();

        //unsuccessful login message
        WebElement invalidUsernameMessage = webDriver.findElement(By.xpath("/html//div[@id='flash']"));
        String expectedMessage = "Your username is invalid!";
        String actualMessage = invalidUsernameMessage.getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage), "Actual message is not expected message");

        webDriver.quit();
    }

    @Test(priority = 2, groups = { "negativeTests" })
    public void invalidPasswordTest() {
        System.out.println("starting invalid password test");

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        WebDriver webDriver = new ChromeDriver();

        String url = "http://the-internet.herokuapp.com/login";
        webDriver.get(url);
        System.out.println("Page is opened");

        webDriver.manage().window().maximize();

        WebElement username = webDriver.findElement(By.xpath("/html//input[@id='username']"));
        username.sendKeys("tomsmith");

        WebElement password = webDriver.findElement(By.xpath("/html//input[@id='password']"));
        password.sendKeys("invalidPassword");


        WebElement loginButton = webDriver.findElement(By.xpath("//form[@id='login']//i[@class='fa fa-2x fa-sign-in']"));
        loginButton.click();

        WebElement invalidPasswordMessage = webDriver.findElement(By.xpath("/html//div[@id='flash']"));
        String expectedMessage = "Your password is invalid!";
        String actualMessage = invalidPasswordMessage.getText();

        Assert.assertTrue(actualMessage.contains(expectedMessage), "password is not the same as expected");

        webDriver.quit();
    }
}
