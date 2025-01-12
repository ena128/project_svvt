package PasswordRecovery;

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

import static org.junit.jupiter.api.Assertions.*;

public class PasswordRecoveryTests {

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




    // INITIATE PASSWORD RECOVERY WITH VALID EMAIL

    @Test
    public void successfulRecoveryEmailSent() throws InterruptedException {

        // THIS CODE IS JUST A PREVIOUS STEP THAT LEADS US TO TESTING RECOVERY
        webDriver.get("https://www.zara.com/ba/hr/logon");
        webDriver.manage().window().maximize();

        webDriver.findElement(By.name("logonId")).sendKeys("nora.zehak@gmail.com");
        Thread.sleep(500);

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("onetrust-policy-text")));

        WebElement closeCookie = webDriver.findElement(By.cssSelector("#onetrust-close-btn-container button"));
        wait.until(ExpectedConditions.elementToBeClickable(closeCookie)).click();
        Thread.sleep(500);

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", closeCookie);
        wait.until(ExpectedConditions.elementToBeClickable(closeCookie)).click();
        Thread.sleep(500);


        // wait for the 'Forgot Password' link to be visible and ensurAe no overlay is blocking it
        WebElement forgotPasswordLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("action-link")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ot-sdk-row")));
        Thread.sleep(500);

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", forgotPasswordLink);

        wait.until(ExpectedConditions.urlToBe("https://www.zara.com/ba/hr/user/account/password/recover"));

        String actualUrl = webDriver.getCurrentUrl();
        assertEquals("https://www.zara.com/ba/hr/user/account/password/recover", actualUrl,
                "The user should be redirected to the password recovery page.");

        Thread.sleep(1000);
        //AFTER WE SUCCESSFULLY REDIRECTED TO PASSWORD RECOVERY PAGE,
        // WE WANT TO VERIFY THAT THE PASSWORD RECOVERY LINK WILL BE SENT TO OUR EMAIL ADDRESS

        webDriver.get("https://www.zara.com/ba/hr/user/account/password/recover");
        webDriver.manage().window().maximize();

        webDriver.findElement(By.name("logonId")).sendKeys("nora.zehak@gmail.com");
        Thread.sleep(500);

        WebElement recoveryButton= webDriver.findElement(By.xpath("//*[@id=\"main\"]/article/div/div/" +
                "form/div[2]/button"));
        recoveryButton.click();
        Thread.sleep(500);

        WebElement confirmationDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[4]/div[2]/div")));

        Thread.sleep(1000);

        assertTrue(confirmationDiv.isDisplayed(),
                "The password correction request should be successfully received");

    }


    // INITIATE PASSWORD RECOVERY WITH INVALID EMAIL

    @Test
    public void invalidRecoveryEmail() throws InterruptedException {


        webDriver.get("https://www.zara.com/ba/hr/user/account/password/recover");
        webDriver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        webDriver.findElement(By.name("logonId")).sendKeys("invalid-email");
        Thread.sleep(500);

        WebElement recoveryButton= webDriver.findElement(By.xpath("//*[@id=\"main\"]/article/" +
                "div/div/form/div[2]/button"));
        recoveryButton.click();
        Thread.sleep(500);

        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"zds-1\"]/div/span[2]")));
        Thread.sleep(1000);


        assertTrue(errorMessage.isDisplayed(),
                "An error message should be displayed, " +
                        "indicating that the email address is not valid or not registered with the system.");
    }




}











