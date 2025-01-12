package SessionPersistence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SessionPersistenceTests {
    private static WebDriver webDriver;
    private static final String baseUrl = "https://www.zara.com/ba/";

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/Korisnik/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
    }

  /* @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }*/




    // SESSION SHOULD EXPIRE AFTER CERTAIN PERIOD OF INACTIVITY,
    // PAGE MUST REMAIN INACTIVE FOR CERTAIN PERIOD OF TIME FOR THIS TEST TO PASS

    @Test
    public void sessionExpired() throws InterruptedException {
        webDriver.get("https://www.zara.com/ba/");
        webDriver.get("https://www.zara.com/ba/hr/logon");
        webDriver.findElement(By.name("logonId")).sendKeys("nora.zehak@gmail.com");
        webDriver.findElement(By.name("password")).sendKeys("Svvtprojekat1");
        webDriver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/div/div/" +
                "div[3]/main/article/div/div/div[1]/div[1]/section/form/div[2]/button")).click();


        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe("https://www.zara.com/ba/hr/error/invalid-session"));

        String expectedUrl = "https://www.zara.com/ba/hr/error/invalid-session";
        String actualUrl = webDriver.getCurrentUrl();
        System.out.println("Actual URL: " + actualUrl); // Debugging line
        assertEquals(expectedUrl, actualUrl, "The user should be redirected to expired session page");
        Thread.sleep(1000);

    }

    // LOGIN AFTER EXPIRED SESSION

    @Test
    public void sessionExpiredLogin() throws InterruptedException {
        webDriver.get("https://www.zara.com/ba/hr/error/invalid-session");
        WebElement prijava = webDriver.findElement(By.xpath("//*[@id=\"main\"]/article/div[2]/a[1]"));
        webDriver.get("https://www.zara.com/ba/hr/logon");


        webDriver.findElement(By.name("logonId")).sendKeys("nora.zehak@gmail.com");
        webDriver.findElement(By.name("password")).sendKeys("Svvtprojekat1");
        webDriver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/div/" +
                "div/div[3]/main/article/div/div/div[1]/div[1]/section/form/div[2]/button")).click();


       WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe("https://www.zara.com/ba/hr/user/account?authorized=true"));

        String expectedUrl = "https://www.zara.com/ba/hr/user/account?authorized=true";
        String actualUrl = webDriver.getCurrentUrl();
        System.out.println("Actual URL: " + actualUrl); // Debugging line
        assertEquals(expectedUrl, actualUrl, "The user should be redirected account page");
        Thread.sleep(1000);

    }






}