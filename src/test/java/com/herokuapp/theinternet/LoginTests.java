package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class LoginTests {
    private WebDriver webDriver;

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    private void setUp(@Optional("webdriver.chrome.driver") String browser) {
        System.setProperty(browser, browser.equals("webdriver.chrome.driver") ? "src/main/resources/chromedriver" : "src/main/resources/geckodriver" );
        webDriver = browser.equals("webdriver.chrome.driver") ? new ChromeDriver() : new FirefoxDriver();

        webDriver.manage().window().maximize();

//        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


    }

    @AfterMethod(alwaysRun = true)
    private void tearDown() {
        webDriver.quit();
    }

    @Test(priority = 1, groups = { "positiveTests", "smokeTests" })
    public void positiveLoginTest() {
        System.out.println("starting negative login test");

        String url = "http://the-internet.herokuapp.com/login";
        webDriver.get(url);
        System.out.println("page is opened");

        webDriver.manage().window().maximize();

        WebElement usernameElement = webDriver.findElement(By.xpath("/html//input[@id='username']"));
        usernameElement.sendKeys("tomsmith");

        WebElement passwordElement = webDriver.findElement(By.xpath("/html//input[@id='password']"));
        passwordElement.sendKeys("SuperSecretPassword!");

        WebDriverWait wait = new WebDriverWait(webDriver, 10);

        WebElement loginButton = webDriver.findElement(By.xpath("//form[@id='login']//i[@class='fa fa-2x fa-sign-in']"));
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();

        //unsuccessful login message
        WebElement invalidUsernameMessage = webDriver.findElement(By.xpath("/html//div[@id='flash']"));
        String expectedMessage = "You logged into a secure area!";
        String actualMessage = invalidUsernameMessage.getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage), "Actual message is not expected message");


        WebElement logoutButtonElement = webDriver.findElement(By.xpath("//div[@id='content']//a[@href='/logout']/i[@class='icon-2x icon-signout']"));
        logoutButtonElement.click();


    }

    @Parameters({"username", "password", "expectedMessage"})
    @Test(priority = 2, groups = { "negativeTests", "smokeTests" })
    public void negativeLoginTest(String username, String password, String expectedMessage) {
        System.out.println("starting negative login test");


        String url = "http://the-internet.herokuapp.com/login";
        webDriver.get(url);
        System.out.println("page is opened");

        webDriver.manage().window().maximize();

        WebElement usernameElement = webDriver.findElement(By.xpath("/html//input[@id='username']"));
        usernameElement.sendKeys(username);

        WebElement passwordElement = webDriver.findElement(By.xpath("/html//input[@id='password']"));
        passwordElement.sendKeys(password);

        WebElement loginButton = webDriver.findElement(By.xpath("//form[@id='login']//i[@class='fa fa-2x fa-sign-in']"));
        loginButton.click();

        //unsuccessful login message
        WebElement invalidUsernameMessage = webDriver.findElement(By.xpath("/html//div[@id='flash']"));
        String actualMessage = invalidUsernameMessage.getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage), "Actual message is not expected message");
    }
}
