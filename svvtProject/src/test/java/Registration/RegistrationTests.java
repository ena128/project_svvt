package Registration;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTests {

    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/Korisnik/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
    }

 /*   @AfterEach
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }*/


    // VALID REGISTRATION
    // AFTER VALID REGISTRATION, YOU SHOULD BE REDIRECTED TO THE PAGE FOR PHONE VERIFICATION YOU MUST COMPLETE BEFORE ACTIVATING YOUR ACCOUNT
    // PRECONDITION THAT ACCOUNT DOESN'T ALREADY EXIST

    @Test
    public void validRegistration() throws InterruptedException {
        webDriver.get("https://www.zara.com/ba/hr/signup");
        webDriver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        webDriver.findElement(By.name("email")).sendKeys("nora.zehak@gmail.com");
        Thread.sleep(500);
        webDriver.findElement(By.name("password")).sendKeys("Svvtprojekat1");
        Thread.sleep(500);
        webDriver.findElement(By.name("firstName")).sendKeys("Nora");
        Thread.sleep(500);
        webDriver.findElement(By.name("lastName")).sendKeys("Žehak");
        Thread.sleep(500);


        //close cookie
        WebElement closeCookie = webDriver.findElement(By.cssSelector("#onetrust-close-btn-container button"));
        wait.until(ExpectedConditions.elementToBeClickable(closeCookie)).click();
        Thread.sleep(500);

        // input after closing cookie
        webDriver.findElement(By.name("phone.number")).sendKeys("62605889");
        Thread.sleep(500);


        WebElement secondCheckbox = webDriver.findElement(By.xpath("(//input[@type='checkbox'])[2]\n"));

        // using JavaScript to make sure the checkbox is interactable and to click it
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView({block: 'center'});", secondCheckbox);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", secondCheckbox);
        Thread.sleep(500);

        // scrolling registration button into view and clicking it
        WebElement createAccountButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"address-form\"]/div[2]/button")));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView({block: 'center'});",
                createAccountButton);
        wait.until(ExpectedConditions.elementToBeClickable(createAccountButton)).click();
        Thread.sleep(1000);


        // user should receive sms with a verification code
        wait.until(ExpectedConditions.urlToBe("https://www.zara.com/ba/hr/guest-user/profile/phone/verification/code"));
        Thread.sleep(1500);


        assertEquals("https://www.zara.com/ba/hr/guest-user/profile/phone/verification/code", webDriver.getCurrentUrl());

    }


    // TRYING TO ATTEMPT REGISTRATION WITHOUT ACCEPTING WEBSITE'S PRIVACY POLICY

    @Test
    public void privacyCheckboxRegistration() throws InterruptedException {
        webDriver.get("https://www.zara.com/ba/hr/signup");
        webDriver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        webDriver.findElement(By.name("email")).sendKeys("nora.zehak@gmail.com");
        Thread.sleep(500);
        webDriver.findElement(By.name("password")).sendKeys("Svvtprojekat1");
        Thread.sleep(500);
        webDriver.findElement(By.name("firstName")).sendKeys("Nora");
        Thread.sleep(500);
        webDriver.findElement(By.name("lastName")).sendKeys("Žehak");
        Thread.sleep(500);


        //close cookie
        WebElement closeCookie = webDriver.findElement(By.cssSelector("#onetrust-close-btn-container button"));
        wait.until(ExpectedConditions.elementToBeClickable(closeCookie)).click();
        Thread.sleep(500);

        // input after closing cookie
        webDriver.findElement(By.name("phone.number")).sendKeys("62605889");
        Thread.sleep(500);


        WebElement secondCheckbox = webDriver.findElement(By.xpath("(//input[@type='checkbox'])[2]\n"));

        // SKIPPING THE CHECKBOX FOR ACCEPTING PRIVACY POLICY RULES
        // ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView({block: 'center'});", secondCheckbox);
        // ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", secondCheckbox);

        // scrolling registration button into view and clicking it
        WebElement createAccountButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"address-form\"]/div[2]/button")));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView({block: 'center'});", createAccountButton);
        wait.until(ExpectedConditions.elementToBeClickable(createAccountButton)).click();
        Thread.sleep(1000);


        WebElement warningMessage = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("/html/body/div[5]/div[2]/div")));
        String message = warningMessage.getText();
        Thread.sleep(1000);


        assertEquals("UPOZORENJE\n" +
                "Morate prihvatiti Pravila zaštite privatnosti.\n" +
                "PRIHVATI", message);
        assertTrue(warningMessage.isDisplayed(), "Warning message should be displayed");

    }





    // ATTEMPTING REGISTRATION WITH EMAIL THAT ALREADY HAS ACCOUNT RELATED TO IT
    // NOTE THAT ACCOUNT IS ACTIVATED AND EXISTS ONLY AFTER YOU CONFIRM YOUR PHONE NUMBER BY RECEIVING THE CODE ON YOUR MOBILE DEVICE
    // PRECONDITION FOR THIS TEST IS TO HAVE ACTIVATED ACCOUNT

    @Test
    public void occupiedEmailRegistration() throws InterruptedException {
        webDriver.get("https://www.zara.com/ba/hr/signup");
        webDriver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        webDriver.findElement(By.name("email")).sendKeys("nora.zehak@gmail.com");
        Thread.sleep(500);
        webDriver.findElement(By.name("password")).sendKeys("Svvtprojekat1");
        Thread.sleep(500);
        webDriver.findElement(By.name("firstName")).sendKeys("Nora");
        Thread.sleep(500);
        webDriver.findElement(By.name("lastName")).sendKeys("Žehak");
        Thread.sleep(500);



        //close cookie
        WebElement closeCookie = webDriver.findElement(By.cssSelector("#onetrust-close-btn-container button"));
        wait.until(ExpectedConditions.elementToBeClickable(closeCookie)).click();

        // input after closing cookie
        webDriver.findElement(By.name("phone.number")).sendKeys("62605889");
        Thread.sleep(500);

        WebElement secondCheckbox = webDriver.findElement(By.xpath("(//input[@type='checkbox'])[2]\n"));


        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView({block: 'center'});", secondCheckbox);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", secondCheckbox);
        Thread.sleep(500);

        // scrolling registration button into view and clicking it
        WebElement createAccountButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"address-form\"]/div[2]/button")));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView({block: 'center'});", createAccountButton);
        wait.until(ExpectedConditions.elementToBeClickable(createAccountButton)).click();
        Thread.sleep(500);



        WebElement warningMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[5]/div[2]/div")));

        assertTrue(warningMessage.isDisplayed(),
                "The entered address is already in use, please choose another one");

    }





}
