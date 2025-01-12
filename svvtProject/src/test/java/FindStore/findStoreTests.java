package FindStore;
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


public class findStoreTests {
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
    public void searchExistingStore() throws InterruptedException {

        webDriver.get("https://www.zara.com/ba/");
        webDriver.manage().window().maximize();
        Thread.sleep(1500);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        WebElement woman = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"main\"]/article/div[2]/ul[2]/li[1]/button")));
        woman.click();
        Thread.sleep(1500);

        WebElement stores = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"theme-app\"]/div/div/div[1]/div/" +
                        "div/div[2]/nav/div[2]/div/div/div/div[1]/ul/li[8]/a/span")));

        ((JavascriptExecutor) webDriver).executeScript("arguments[0]." +
                "scrollIntoView({ behavior: 'smooth', block: 'center' });", stores);
        Thread.sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(stores)).click();
        Thread.sleep(500);


        webDriver.getCurrentUrl();
        Thread.sleep(500);

        WebElement searchStore = webDriver.findElement(By.name("search"));
        searchStore.sendKeys("71000 Sarajevo");
        Thread.sleep(500);


        WebElement storesFound = webDriver.findElement(By.xpath("//*[text()='SARAJEVO CITY CENTER']"));
        String store = storesFound.getText();
        Thread.sleep(1000);

        assertEquals("SARAJEVO CITY CENTER",store);
        Thread.sleep(500);

    }



    // SEARCH FOR A STORE THAT DOESN'T EXIST

    @Test
    public void searchNonExistingStore() throws InterruptedException {

        webDriver.get("https://www.zara.com/ba/");
        webDriver.manage().window().maximize();
        Thread.sleep(1500);

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        WebElement woman = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"main\"]/article/div[2]/ul[2]/li[1]/button")));
        woman.click();
        Thread.sleep(1500);
        WebElement stores = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"theme-app\"]/div/div/div[1]/div/" +
                        "div/div[2]/nav/div[2]/div/div/div/div[1]/ul/li[8]/a/span")));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0]." +
                "scrollIntoView({ behavior: 'smooth', block: 'center' });", stores);
        Thread.sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(stores)).click();
        Thread.sleep(500);


        webDriver.getCurrentUrl();
        Thread.sleep(500);

        WebElement searchStore = webDriver.findElement(By.name("search"));
        searchStore.sendKeys("71200"+ Keys.RETURN);
        Thread.sleep(1000);


        WebElement storesFound = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("#main > article > div.e-store-stores-locator__location-search-container > div" +
                        " > div.zds-empty-state.zds-empty-state--align-left.e-store-search__empty-state." +
                        "zds-empty-state--with-description > div.zds-empty-state__title")));
        String store = storesFound.getText();
        Thread.sleep(1000);

        assertEquals("NIJE MOGUĆE PRONAĆI POVEZANU LOKACIJU.",store);
        Thread.sleep(500);

    }



}
