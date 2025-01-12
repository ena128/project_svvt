package Search;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class SearchProductTests {
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



    // SEARCH FOR PRODUCTS THAT EXIST (in general not specific one)

    @Test
    public void testSearchValidProduct() throws InterruptedException {
        webDriver.get("https://www.zara.com/ba/");
        webDriver.manage().window().maximize();

        WebElement searchIcon = webDriver.findElement(By.cssSelector("[aria-label='Tražilica proizvoda']"));
        searchIcon.click();
        Thread.sleep(1000);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[2]/div[1]/div[1]/div/div/div[2]/main/article/input")));

        searchBar.sendKeys("flare");
        Thread.sleep(500);
        searchBar.sendKeys(Keys.RETURN);
        Thread.sleep(500);

        wait.until(ExpectedConditions.urlContains("search?searchTerm=flare"));
        Thread.sleep(500);

        String currentUrl = webDriver.getCurrentUrl();
        assertTrue(currentUrl.contains("search?searchTerm=flare"),
                "The URL should contain the search term 'search?searchTerm=flare'.");
    }


    // SEARCH FOR A SPECIFIC PRODUCT BY THE CODE (specific product)
    @Test
    public void testSearchSpecificProduct() throws InterruptedException {
        webDriver.get("https://www.zara.com/ba/");
        webDriver.manage().window().maximize();

        WebElement searchIcon = webDriver.findElement(By.cssSelector("[aria-label='Tražilica proizvoda']"));
        searchIcon.click();
        Thread.sleep(500);


        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div[1]/div[1]/div/div/div[2]/main/article/input")));

        searchBar.sendKeys("5862/171/038");
        Thread.sleep(500);
        searchBar.sendKeys(Keys.RETURN);
        Thread.sleep(1000);

        wait.until(ExpectedConditions.urlContains("search?searchTerm=5862%2F171%2F038"));


        String currentUrl = webDriver.getCurrentUrl();
        Thread.sleep(1000);

        assertTrue(currentUrl.contains("search?searchTerm=5862%2F171%2F038"),
                "The URL should contain the search term 'search?searchTerm=5862%2F171%2F038'.");
    }





    // SEARCH FOR A PRODUCT THAT DOES NOT EXIST

    @Test
    public void testSearchInvalidProduct() throws InterruptedException {
        webDriver.get("https://www.zara.com/ba/");
        webDriver.manage().window().maximize();


        WebElement searchIcon = webDriver.findElement(By.cssSelector("[aria-label='Tražilica proizvoda']"));
        searchIcon.click();
        Thread.sleep(500);

        // Wait for the search input field to become visible
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("/html/body/div[2]/div[1]/div[1]/div/div/div[2]/main/article/input")));

        searchBar.sendKeys("3910/388/800");
        Thread.sleep(500);
        searchBar.sendKeys(Keys.RETURN);
        Thread.sleep(1000);

        wait.until(ExpectedConditions.urlContains("search?searchTerm=3910%2F388%2F800"));
        String currentUrl = webDriver.getCurrentUrl();

        WebElement pageText = webDriver.findElement(
                By.xpath("//*[@id=\"main\"]/article/div/div/section/div[2]/div/div[2]/span"));
        String text = pageText.getText();
        Thread.sleep(1000);

        assertEquals("NEMA NIJEDNOG REZULTATA PRETRAŽIVANJA", text);
    }





    // EMPTY INPUT (the page should remain unchanged)

    @Test
    public void testSearchWithEmptyInput() throws InterruptedException {
        webDriver.get("https://www.zara.com/ba/");
        webDriver.manage().window().maximize();

        WebElement searchIcon = webDriver.findElement(By.cssSelector("[aria-label='Tražilica proizvoda']"));
        searchIcon.click();
        Thread.sleep(500);

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[2]/div[1]/div[1]/div/div/div[2]/main/article/input")));

        // leave the search bar empty and press ENTER
        searchBar.sendKeys(Keys.RETURN);
        Thread.sleep(500);

        String initialUrl = webDriver.getCurrentUrl();
        Thread.sleep(1000);


        String currentUrl = webDriver.getCurrentUrl();
        System.out.println("Initial URL: " + initialUrl); //for debug
        System.out.println("Current URL: " + currentUrl);
        assertEquals(initialUrl,currentUrl);
    }




}
