package FooterLinks;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FooterTests {
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



    @Test
    public void verifyFooterLinks() throws InterruptedException {
        webDriver.get("https://www.zara.com/ba/");
        Thread.sleep(1000);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        WebElement newsletter = webDriver.findElement(By.xpath(
                "//*[@id=\"slider-spot__slide_ss22-newsletter_subhome-xmedia-03\"]" +
                        "/div/div[2]/div[1]/div[1]/h2"));
        ((JavascriptExecutor) webDriver).executeScript
                ("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", newsletter);

        Thread.sleep(500);
        newsletter.click();
        Thread.sleep(1000);

        webDriver.getCurrentUrl();

        assertTrue(webDriver.getCurrentUrl().contains("newsletter"),
                "The URL should take you to newsletter page");

    }


    @Test
    public void cookieSettings() throws InterruptedException {
        webDriver.get("https://www.zara.com/ba/");
        Thread.sleep(1000);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement newsletter = webDriver.findElement(By.xpath(
                "//*[@id=\"slider-spot__slide_ss22-newsletter_subhome-xmedia-03\"]" +
                        "/div/div[2]/div[1]/div[1]/h2"));
        ((JavascriptExecutor) webDriver).executeScript
                ("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", newsletter);

        WebElement cookieSettings = webDriver.findElement(By.xpath(
                "//*[@id=\"slider-spot__slide_ss22-newsletter_subhome-xmedia-03\"]" +
                "/div/div[2]/div[1]/div[2]/nav/ul/li[1]/button"));

        WebElement closeCookie = webDriver.findElement(By.cssSelector("#onetrust-close-btn-container button"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", closeCookie);
        wait.until(ExpectedConditions.elementToBeClickable(closeCookie)).click();
        Thread.sleep(500);
        cookieSettings.click();
        Thread.sleep(500);
        WebElement cookieDiv = webDriver.findElement(By.xpath("//*[@id=\"onetrust-pc-sdk\"]/div"));
        Thread.sleep(1000);
        assertTrue(cookieDiv.isDisplayed(), "Cookie settings div should be displayed");

        WebElement refuseCookies=webDriver.findElement(By.xpath(
                "//*[@id=\"onetrust-pc-sdk\"]/div/div[3]/div[1]/div/button[1]"));
        refuseCookies.click();
        //rejecting all cookies
    }



}

