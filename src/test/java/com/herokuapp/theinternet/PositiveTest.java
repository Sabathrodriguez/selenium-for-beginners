package com.herokuapp.theinternet;

import org.openqa.selenium.WebDriver;
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

//        enter password

//        click login button

//        verification:

//        new url:

//        logout button is visible

//        successful login message

        //close brower
        driver.quit();

    }
}
