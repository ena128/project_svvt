package NavigationLinks;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NavigationLinksTests {
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







    // VERIFY THE MAIN NAVIGATION TAKES USER TO CORRECT SECTION OF THE STORE

    @Test
    public void zaraWomenJackets() throws InterruptedException {

        webDriver.get("https://www.zara.com/ba/");
        webDriver.manage().window().maximize();
        Thread.sleep(1500);

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        WebElement woman= wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"main\"]/article/div[2]/ul[2]/li[1]/button")));
        woman.click();
        Thread.sleep(1500);

        WebElement jackets = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"theme-app\"]/div/div/div[1]/div/div/div[2]/" +
                        "nav/div[2]/div/div/div/div[1]/ul/li[3]/div/ul/li[3]/a")));


        ((JavascriptExecutor) webDriver).executeScript
                ("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' });", jackets);
        Thread.sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(jackets)).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.urlToBe("https://www.zara.com/ba/hr/zene-jakne-l1114.html?v1=2418596"));
        Thread.sleep(1500);

        String currentUrl = webDriver.getCurrentUrl();
        assertEquals("https://www.zara.com/ba/hr/zene-jakne-l1114.html?v1=2418596",currentUrl);
        Thread.sleep(1000);
    }


    // PICK TYPE OF PRODUCT FROM SUBCATEGORY LINKS (after navigating to dresses, choose mini dress)

    @Test
    public void zaraWomenDressType() throws InterruptedException {
        webDriver.get("https://www.zara.com/ba/");
        webDriver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        WebElement woman= wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"main\"]/article/div[2]/ul[2]/li[1]/button")));
        woman.click();
        Thread.sleep(500);

        WebElement dresses = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"theme-app\"]/div/div/div[1]/div/div/div[2]/" +
                        "nav/div[2]/div/div/div/div[1]/ul/li[3]/div/ul/li[5]/a")));

        ((JavascriptExecutor) webDriver).executeScript(
                "arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' });", dresses);
        Thread.sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(dresses)).click();
        Thread.sleep(1000);

        wait.until(ExpectedConditions.urlToBe("https://www.zara.com/ba/hr/zene-haljine-l1066.html?v1=2417457"));
        Thread.sleep(1000);

        WebElement dressType= wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"theme-app\"]/div/div/header/div[4]/nav/ul/li[3]/div/a")));
        dressType.click();
        Thread.sleep(1500);
        String currentUrl = webDriver.getCurrentUrl();
        assertEquals("https://www.zara.com/ba/hr/zene-haljine-mini-l1083.html?v1=2417387",
                webDriver.getCurrentUrl());
        Thread.sleep(1000);
    }








    // TEST ACCESSIBILITY BUTTON IN THE FOOTER OF THE PAGE

    @Test
    public void accessibility() throws InterruptedException {

        webDriver.get("https://www.zara.com/ba/");
        webDriver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        WebElement accessibilityButton= wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"INDmenu-btn\"]")));
        accessibilityButton.click();
        Thread.sleep(500);

        WebElement accessibilityDiv = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"I2024-HOME\"]/body")));
        Thread.sleep(1500);



        // assert the accessibility div is visible
        assertTrue(accessibilityDiv.isDisplayed(),
                "The accessibility div should be displayed after clicking the the button in bottom right corner.");


    }




    // TEST HELP BUTTON IN THE HEADER OF THE PAGE

    @Test
    public void helpCenter() throws InterruptedException {
        webDriver.get("https://www.zara.com/ba/");
        webDriver.manage().window().maximize();
        Thread.sleep(500);

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        WebElement help= wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"theme-app\"]/div/div/header/div[3]/a[2]")));
        Thread.sleep(1000);
        help.click();
        Thread.sleep(1000);

        wait.until(ExpectedConditions.urlToBe("https://www.zara.com/ba/hr/help-center"));
        Thread.sleep(500);

        String actualUrl = webDriver.getCurrentUrl();
        String currentUrl = webDriver.getCurrentUrl();
        assertTrue(currentUrl.contains("https://www.zara.com/ba/hr/help-center"),
                "The URL should take you to page for customer support");
    }



}