package Checkout;

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


public class checkoutTests {
    private static WebDriver webDriver;
    private static String baseUrl="https://www.zara.com/ba/";

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/Korisnik/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
    }

    /*@AfterEach
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }*/


    // SUCCESSFUL CHECKOUT TEST

    @Test
    public void successfulCheckout() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(500);
        webDriver.get("https://www.zara.com/ba/hr/logon");
        webDriver.manage().window().maximize();

        webDriver.findElement(By.name("logonId")).sendKeys("nora.zehak@gmail.com");
        webDriver.findElement(By.name("password")).sendKeys("Svvtprojekat1");
        webDriver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/div/div/div[3]/" +
                "main/article/div/div/div[1]/div[1]/section/form/div[2]/button")).click();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe("https://www.zara.com/ba/hr/user/account?authorized=true"));


        String expectedUrl = "https://www.zara.com/ba/hr/user/account?authorized=true";
        String actualUrl = webDriver.getCurrentUrl();
        assertEquals(expectedUrl, actualUrl, "The user should be redirected to the account page after login.");
        Thread.sleep(1000);



        webDriver.get("https://www.zara.com/ba/");
        WebElement searchIcon = webDriver.findElement(By.cssSelector("[aria-label='Tražilica proizvoda']"));
        searchIcon.click();
        Thread.sleep(500);

        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[2]/div[1]/div[1]/div/div/div[2]/main/article/input")));

        searchBar.sendKeys("flare");
        Thread.sleep(500);
        searchBar.sendKeys(Keys.RETURN);
        Thread.sleep(1000);

        webDriver.getCurrentUrl();



        Thread.sleep(1000);
        WebElement product = webDriver.findElement(By.cssSelector
                ("img[alt='Zara HLAČE FLARE S DŽEPOVIMA ZW COLLECTION – Burgundac – Slika 0']"));
        product.click();

        wait.until(ExpectedConditions.urlToBe
                ("https://www.zara.com/ba/hr/hlace-flare-s-dzepovima-zw-collection-p08550187.html?v1=377075157"));

        String currentUrl = webDriver.getCurrentUrl();
        assertTrue(currentUrl.contains
                        ("https://www.zara.com/ba/hr/hlace-flare-s-dzepovima-zw-collection-p08550187.html?v1=377075157"),
                "The URL should take you to page of the product you searched for.");


        WebElement sizeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='size-selector-sizes-size__label']")
        ));

        if (sizeElement.getText().equals("XS")) {
            sizeElement.click();
        }
        Thread.sleep(500);


        WebElement addToCartButton = webDriver.findElement(By.xpath(
                "/html/body/div[2]/div[1]/div[1]/div/div/div[2]/" +
                        "main/article/div/div[1]/div[2]/div/div[3]/div/div/button"
        ));
        addToCartButton.click();
        Thread.sleep(500);

        // PROCEED TO CHECKOUT


        webDriver.get("https://www.zara.com/ba/hr/shop/cart");
        Thread.sleep(500);
        WebElement checkout= webDriver.findElement(By.xpath("//*[@id=\"main\"]/article/" +
                "div[2]/div/div/" +
                "div/div[2]/section/div[4]/button"));
        checkout.click();
        Thread.sleep(500);

        wait.until(ExpectedConditions.urlToBe("https://www.zara.com/ba/hr/shop/53598735449/user/billing-address"));
        Thread.sleep(500);

        WebElement address= webDriver.findElement(By.id("addresLines[0]137"));
        address.sendKeys("Generala Mehmeda Alagica 9");
        Thread.sleep(500);

        WebElement zip = webDriver.findElement(By.id("zipCode145"));
        zip.sendKeys("71000");
        Thread.sleep(500);

        WebElement city = webDriver.findElement(By.id("city149"));
        zip.sendKeys("Sarajevo");
        Thread.sleep(500);

        WebElement next= webDriver.findElement(By.xpath("//*[@id=\"address-form\"]/div[2]/div/" +
                "section/div[4]/button"));
        next.click();
        Thread.sleep(500);

        wait.until(ExpectedConditions.urlToBe
                ("https://www.zara.com/ba/hr/shop/53598735449/shipping/method-selection"));
        Thread.sleep(500);

        WebElement next2= webDriver.findElement(By.xpath("//*[@id=\"main\"]/article/div[2]/" +
                "div/div/form/div[2]/section/div[3]/button"));
        next2.click();
        Thread.sleep(500);

        assertTrue(currentUrl.contains("payment/selection"), "Should navigate to payment selection page");

    }
}
