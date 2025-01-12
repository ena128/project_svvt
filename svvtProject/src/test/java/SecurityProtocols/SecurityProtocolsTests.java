package SecurityProtocols;
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

public class SecurityProtocolsTests {

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



    // TEST THAT THE WEBSITE ENFORCES HTTPS TROUGH REDIRECTION
    // TEST THAT ENSURES COMPLIANCE WITH SECURITY PROTOCOLS WITHOUT DENYING ACCESS

    @Test

    public void enforceHttps() throws InterruptedException {

        webDriver.get("http://www.zara.com/ba/");
        webDriver.manage().window().maximize();
        Thread.sleep(1000);

        String currentUrl = webDriver.getCurrentUrl();
        Thread.sleep(1000);

        assertTrue(currentUrl.startsWith("https://"),
                "The website should redirect to the HTTPS version, but it did not. Current URL: " + currentUrl);

        Thread.sleep(500);
        System.out.println("The website correctly redirected to HTTPS: " + currentUrl);
    }




    // TEST HTTP TO HTTPS REDIRECTION FOR SPECIFIC PAGES

    @Test
    public void testHttpLoginRedirectsToHttps() throws InterruptedException {
        webDriver.get("http://www.zara.com/ba/hr/logon");
        webDriver.manage().window().maximize();
        Thread.sleep(1000);

        String currentUrl = webDriver.getCurrentUrl();
        Thread.sleep(1000);

        assertTrue(currentUrl.startsWith("https://"),
                "The login page should redirect to the HTTPS version. Current URL: " + currentUrl);
    }





    // THE TEST VERIFIES WHETHER ZARA'S WEBSITE IMPLEMENTS HTTP STRICT TRANSPORT SECURITY (HSTS)

    @Test
    public void testHstsHeader() throws IOException{
        // Use the HTTPS URL directly since HSTS headers are typically sent in HTTPS responses
        URL url = new URL("https://www.zara.com/ba/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();

        // get the Strict-Transport-Security header
        String hstsHeader = connection.getHeaderField("Strict-Transport-Security");

        // validate the HSTS header
        assertNotNull(hstsHeader, "HSTS header is missing!");
        assertTrue(hstsHeader.contains("max-age="),
                "HSTS header does not contain 'max-age'. Value: " + hstsHeader);

        // validate a minimum max-age value (e.g., 31536000 for one year)
        if (hstsHeader.contains("max-age=")) {
            String maxAgeValue = hstsHeader.split("max-age=")[1].split(";")[0];
            int maxAge = Integer.parseInt(maxAgeValue.trim());
            assertTrue(maxAge >= 31536000, "HSTS max-age is less than one year. Value: " + maxAge);
        }
    }
}