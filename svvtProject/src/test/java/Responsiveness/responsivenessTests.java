package Responsiveness;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;


public class responsivenessTests {
    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/Korisnik/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
    }

    @AfterEach
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }


    // VERIFY THAT THE WEBSITE IS DISPLAYED CORRECTLY ON A DESKTOP-SIZED WINDOW

    @Test
    public void verifyWebsiteResponsiveness() throws InterruptedException {

        webDriver.get("https://www.zara.com/ba/");
        //webDriver.manage().window().maximize();
        Thread.sleep(2000);

        Dimension initialSize = webDriver.manage().window().getSize();
        assertTrue(initialSize.width > 0 && initialSize.height > 0,
                "Website should display properly on desktop.");
        Thread.sleep(1000);

    }


    // TEST WEBSITE LOADING TIME

    @Test
    public void testWebsiteLoadingTime() {
        long startTime = System.currentTimeMillis();
        webDriver.get("https://www.zara.com/ba/");
        webDriver.manage().window().maximize();

        long endTime = System.currentTimeMillis();

        long loadTime = endTime - startTime;
        assertTrue(loadTime < 5000, "Website should load within 5 seconds.");
    }


    // VERIFY THAT KEY ELEMENT HEADER IS VISIBLE AFTER THE PAGE LOADS

    @Test
    public void verifyElementsLoadOnPage() throws InterruptedException {
        webDriver.get("https://www.zara.com/ba/");
        webDriver.manage().window().maximize();
        Thread.sleep(2000);

        WebElement header = webDriver.findElement(By.xpath("//*[@id=\"theme-app\"]/div/div/header"));
        assertTrue(header.isDisplayed(), "Header should be visible on the page.");
    }












}


