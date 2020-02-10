package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
//        enter password
        WebElement password = driver.findElement(By.xpath("/html//input[@id='password']"));
//        click login button
        WebElement loginButton = driver.findElement(By.xpath("//form[@id='login']/button[@class='radius']"));

//        verification:

//        new url:

//        logout button is visible
        WebElement logoutButton = driver.findElement(By.xpath("div[@id='content']//a[@href='/logout']"));

//        successful login message
        WebElement successMessage = driver.findElement(By.cssSelector("#flash"));
        //close brower
        driver.quit();

    }
}
