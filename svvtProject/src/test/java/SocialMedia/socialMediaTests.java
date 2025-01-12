package SocialMedia;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class socialMediaTests {
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


    // TEST IF THE LINK OPENS INSTAGRAM PAGE

    @Test
    public void socialInstagram() throws InterruptedException {
        webDriver.get("https://www.zara.com/ba/");
        Thread.sleep(1000);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        WebElement instagram = webDriver.findElement(By.xpath(
                "//*[@id=\"homeSocialFooter\"]/li[2]/a"));
        ((JavascriptExecutor) webDriver).executeScript
                ("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", instagram);

        Thread.sleep(500);
        instagram.click();
        Thread.sleep(1000);
        String mainWindow = webDriver.getWindowHandle();

        Set<String> windowHandles = webDriver.getWindowHandles();
        assertTrue(windowHandles.size() > 1, "New tab did not open!");

        for (String handle : windowHandles) {
            if (!handle.equals(mainWindow)) {
                webDriver.switchTo().window(handle);
                break;
            }
        }
        String currentUrl = webDriver.getCurrentUrl();
        assertTrue(currentUrl.contains("instagram.com"), "instagram page did not open");

    }


    // TEST IF THE LINK OPENS FACEBOOK PAGE

    @Test
    public void socialFacebook() throws InterruptedException {
        webDriver.get("https://www.zara.com/ba/");
        Thread.sleep(1000);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        WebElement facebook = webDriver.findElement(By.xpath(
                "//*[@id=\"homeSocialFooter\"]/li[3]/a"));
        ((JavascriptExecutor) webDriver).executeScript
                ("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", facebook);
        Thread.sleep(500);
        facebook.click();
        Thread.sleep(1000);

        String mainWindow = webDriver.getWindowHandle();
        Set<String> windowHandles = webDriver.getWindowHandles();
        assertTrue(windowHandles.size() > 1, "New tab did not open!");

        for (String handle : windowHandles) {
            if (!handle.equals(mainWindow)) {
                webDriver.switchTo().window(handle);
                break;
            }
        }
        String currentUrl = webDriver.getCurrentUrl();
        assertTrue(currentUrl.contains("facebook.com"), "facebook page didn't open");

    }


}