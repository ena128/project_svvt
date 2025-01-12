package Login;

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

public class LoginTests {

    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/Korisnik/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
    }

  /*  @AfterEach
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }*/





    //VALID EMAIL AND PASSWORD LOGIN

    @Test
    public void testLoginWithValidCredentials() throws NoSuchElementException, InterruptedException {
        webDriver.get("https://www.zara.com/ba/hr/logon");
        webDriver.findElement(By.name("logonId")).sendKeys("nora.zehak@gmail.com");
        Thread.sleep(500);
        webDriver.findElement(By.name("password")).sendKeys("Svvtprojekat1");
        Thread.sleep(500);
        webDriver.findElement(
                By.xpath
                        ("/html/body/div[2]/div[1]/div[1]/div/div/div[3]/" +
                                "main/article/div/div/div[1]/div[1]/section/form/div[2]/button")).click();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe("https://www.zara.com/ba/hr/user/account?authorized=true"));
        Thread.sleep(500);


        String expectedUrl = "https://www.zara.com/ba/hr/user/account?authorized=true";
        String actualUrl = webDriver.getCurrentUrl();
        System.out.println("Actual URL: " + actualUrl); // Debugging line
        assertEquals(expectedUrl, actualUrl, "The user should be redirected to the account page after login.");
        Thread.sleep(500);
    }







    //VALID EMAIL WRONG PASSWORD LOGIN

    @Test
    public void testLoginWithInvalidCredentials() throws InterruptedException {
        webDriver.get("https://www.zara.com/ba/hr/logon");
        Thread.sleep(500);
        webDriver.findElement(By.name("logonId")).sendKeys("nora.zehak@gmail.com");
        Thread.sleep(500);
        webDriver.findElement(By.name("password")).sendKeys("wrongpass");
        Thread.sleep(500);
        webDriver.findElement(
                         By.xpath("/html/body/div[2]/div[1]/div[1]/div/div/div[3]/main/" +
                        "article/div/div/div[1]/div[1]/section/form/div[2]/button")).click();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[4]/div[2]/div")));

        assertTrue(errorMessage.isDisplayed(), "An error message should be displayed for incorrect login credentials.");

        // Asserting that the URL does not change to the account page
        String actualUrl = webDriver.getCurrentUrl();
        String notExpectedUrl = "https://www.zara.com/ba/hr/user/account";
        assertNotEquals(actualUrl, notExpectedUrl,
                "The user should not be redirected to the account page with incorrect login credentials.");
    }






    //EMPTY FIELD LOGIN TEST

    @Test
    public void testLoginWithEmptyFields() throws InterruptedException {
        webDriver.get("https://www.zara.com/ba/hr/logon");
        Thread.sleep(500);
        webDriver.findElement(By.name("password")).sendKeys("Svvtprojekat1");
        Thread.sleep(500);
        webDriver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/div/div/div[3]/" +
                "main/article/div/div/div[1]/div[1]/section/form/div[2]/button")).click();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[2]/div[1]/div[1]/div/div/div[3]/main/" +
                        "article/div/div/div[1]/div[1]/section/form/div[1]/div[1]/div/div/div/div[2]/div/span[2]")));
        // Assert that the error message is displayed
        assertTrue(errorMessage.isDisplayed(), "Error message should be displayed when the email field is empty.");
    }



    // FORGOT PASSWORD TEST
    @Test
    public void testLoginForgotPassword() {
        webDriver.get("https://www.zara.com/ba/hr/logon");
        webDriver.manage().window().maximize();
        webDriver.findElement(By.name("logonId")).sendKeys("nora.zehak@gmail.com");

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("onetrust-policy-text")));

        WebElement closeCookie = webDriver.findElement(By.cssSelector("#onetrust-close-btn-container button"));
        wait.until(ExpectedConditions.elementToBeClickable(closeCookie)).click();

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", closeCookie);
        wait.until(ExpectedConditions.elementToBeClickable(closeCookie)).click();

        // wait for the 'Forgot Password' link to be visible and ensure no overlay is blocking it
        WebElement forgotPasswordLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("action-link")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ot-sdk-row")));


        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", forgotPasswordLink);
        wait.until(ExpectedConditions.urlToBe("https://www.zara.com/ba/hr/user/account/password/recover"));

        String actualUrl = webDriver.getCurrentUrl();
        assertEquals("https://www.zara.com/ba/hr/user/account/password/recover", actualUrl,
                "The user should be redirected to the password recovery page.");
    }





    // THIS TEST IS SUPPOSED TO FAIL SINCE THE BUTTON WE WANT TO CLICK TO PERFORM TESTING IS HIDDEN AND
    // NOT CLICKABLE UNLESS WE PREVIOUSLY CLOSE THE COOKIE WINDOW THAT IS COVERING IT

    @Test
    public void testLoginForgotPasswordCookie() {
        webDriver.get("https://www.zara.com/ba/hr/logon");
        webDriver.findElement(By.name("logonId")).sendKeys("nora.zehak@gmail.com");
        webDriver.findElement(By.className("action-link")).click();
        //BUTTON NOT CLICKABLE AT THIS POINT

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe("https://www.zara.com/ba/hr/user/account/password/recover"));


        String actualUrl = webDriver.getCurrentUrl();
        assertEquals("https://www.zara.com/ba/hr/user/account/password/recover", actualUrl,
                "The user should be redirected to the password recovery page.");
    }

}