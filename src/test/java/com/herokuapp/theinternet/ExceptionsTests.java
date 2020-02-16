package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ExceptionsTests {
    private WebDriver webDriver;

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    private void setUp(@Optional("webdriver.chrome.driver") String browser) {
        System.setProperty(browser, browser.equals("webdriver.chrome.driver") ? "src/main/resources/chromedriver" : "src/main/resources/geckodriver" );
        webDriver = browser.equals("webdriver.chrome.driver") ? new ChromeDriver() : new FirefoxDriver();

        webDriver.manage().window().maximize();

//        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


    }

    @Test(priority = 1)
    private void notVisibleTest() {
        String url = "http://the-internet.herokuapp.com/dynamic_loading/1";
        webDriver.get(url);

        WebElement startButton = webDriver.findElement(By.xpath("//div[@id='start']/button[.='Start']"));
        startButton.click();

        WebDriverWait wait = new WebDriverWait(webDriver, 5L);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='finish']/h4[.='Hello World!']")));

        WebElement helloWorldText = webDriver.findElement(By.xpath("//div[@id='finish']/h4[.='Hello World!']"));
        String expectedText = "Hello World!";
        Assert.assertTrue(helloWorldText.getText().contains(expectedText), "Actual text is not expected test");
    }

    @Test(priority = 2)
    private void timeoutTest() {
        String url = "http://the-internet.herokuapp.com/dynamic_loading/1";
        webDriver.get(url);

        WebElement startButton = webDriver.findElement(By.xpath("//div[@id='start']/button[.='Start']"));
        startButton.click();

        WebDriverWait wait = new WebDriverWait(webDriver, 5L);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='finish']/h4[.='Hello World!']")));
        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
        }

        WebElement helloWorldText = webDriver.findElement(By.xpath("//div[@id='finish']/h4[.='Hello World!']"));
        String expectedText = "Hello World!";
        Assert.assertTrue(helloWorldText.getText().contains(expectedText), "Actual text is not expected test");

    }

    @Test(priority = 3)
    private void noSuchElementTest() {
        String url = "http://the-internet.herokuapp.com/dynamic_loading/2";
        webDriver.get(url);

        WebElement startButton = webDriver.findElement(By.xpath("//div[@id='start']/button[.='Start']"));
        startButton.click();

        WebDriverWait wait = new WebDriverWait(webDriver, 5L);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='finish']/h4[.='Hello World!']")));
        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
        }

        WebElement helloWorldText = webDriver.findElement(By.xpath("//div[@id='finish']/h4[.='Hello World!']"));
        String expectedText = "Hello World!";
        Assert.assertTrue(helloWorldText.getText().contains(expectedText), "Actual text is not expected test");

    }

    @Test
    public void staleElementTest() {
        String url = "http://the-internet.herokuapp.com/dynamic_controls";
        webDriver.get(url);

        WebElement checkBox = webDriver.findElement(By.xpath("/html//div[@id='checkbox']"));

        WebElement removeButton = webDriver.findElement(By.xpath("//form[@id='checkbox-example']/button[@type='button']"));

        WebDriverWait wait = new WebDriverWait(webDriver, 10);

        removeButton.click();

//        wait.until(ExpectedConditions.invisibilityOf(checkBox));
//        Assert.assertFalse(checkBox.isDisplayed());

//        Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOf(checkBox)));

        Assert.assertTrue(wait.until(ExpectedConditions.stalenessOf(checkBox)));

        WebElement addButton = webDriver.findElement(By.xpath("//form[@id='checkbox-example']/button[@type='button']"));
        addButton.click();

        checkBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkbox")));
        Assert.assertTrue(checkBox.isDisplayed());

    }

    @AfterMethod(alwaysRun = true)
    private void tearDown() {
        webDriver.quit();
    }
}
