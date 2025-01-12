package AddToCart;

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

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddToCartTests {
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



    // SUCCESSFULLY ADD ITEM TO CART

    @Test
    public void successfullyAddToCart() throws InterruptedException {
        webDriver.get("https://www.zara.com/ba/hr/search?searchTerm=flare&section=WOMAN");
        webDriver.manage().window().maximize();

        WebElement product = webDriver.findElement(
                By.cssSelector("img[alt='Zara HLAČE FLARE S DŽEPOVIMA ZW COLLECTION – Burgundac – Slika 0']"));
        product.click();
        Thread.sleep(500);

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlToBe
                ("https://www.zara.com/ba/hr/hlace-flare-s-dzepovima-zw-collection-p08550187.html?v1=377075157"));

        String currentUrl = webDriver.getCurrentUrl();
        assertTrue(currentUrl.contains
                        ("https://www.zara.com/ba/hr/hlace-flare-s-dzepovima-zw-collection-p08550187.html?v1=377075157"),
                "The URL should take you to page of the product you searched for.");
        Thread.sleep(500);


        WebElement sizeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='size-selector-sizes-size__label']")
        ));

        if (sizeElement.getText().equals("XS")) {
            sizeElement.click();
        }
        Thread.sleep(500);


        WebElement addToCartButton = webDriver.findElement(By.xpath(
                "/html/body/div[2]/div[1]/div[1]/div/" +
                        "div/div[2]/main/article/div/div[1]/div[2]/div/div[3]/div/div/button"
        ));
        addToCartButton.click();
        Thread.sleep(500);

        WebElement confirmationDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".add-to-cart-notification-content")

        ));
        Thread.sleep(1000);
        assertTrue(confirmationDiv.isDisplayed(),
                "The confirmation div should be displayed after successfully adding the product to the cart.");


    }








    // REMOVE PRODUCT FROM CART (first add then test remove, since can't perform remove from an empty cart)

    @Test
    public void removeFromCart() throws InterruptedException {
        webDriver.get("https://www.zara.com/ba/hr/search?searchTerm=flare&section=WOMAN");
        webDriver.manage().window().maximize();

        Thread.sleep(1000);
        WebElement product = webDriver.findElement(By.cssSelector("img[alt='Zara HLAČE FLARE S DŽEPOVIMA ZW COLLECTION – Burgundac – Slika 0']"));
        product.click();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlToBe("https://www.zara.com/ba/hr/hlace-flare-s-dzepovima-zw-collection-p08550187.html?v1=377075157"));

        String actualUrl = webDriver.getCurrentUrl();
        String currentUrl = webDriver.getCurrentUrl();
        assertTrue(currentUrl.contains("https://www.zara.com/ba/hr/hlace-flare-s-dzepovima-zw-collection-p08550187.html?v1=377075157"),
                "The URL should take you to page of the product you searched for.");


        WebElement sizeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='size-selector-sizes-size__label']")
        ));

        if (sizeElement.getText().equals("XS")) {
            sizeElement.click();
        }
        Thread.sleep(500);


        WebElement addToCartButton = webDriver.findElement(By.xpath(
                "/html/body/div[2]/div[1]/div[1]/div/div/div[2]/main/article/div/div[1]/div[2]/div/div[3]/div/div/button"
        ));
        addToCartButton.click();
        Thread.sleep(500);


        WebElement confirmationDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".add-to-cart-notification-content")

        ));
        Thread.sleep(1000);

        // assert the confirmation dialog is visible
        assertTrue(confirmationDiv.isDisplayed(), "The confirmation div was displayed after adding the product to the cart.");




        // part of test for REMOVING from cart

        webDriver.get("https://www.zara.com/ba/hr/shop/cart");

        WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@aria-label='Ukloni stavku']")
        ));

        ((JavascriptExecutor) webDriver).executeScript
                ("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", removeButton);

        Thread.sleep(500);

        removeButton.click();
        Thread.sleep(1000);

        WebElement itemRemovedMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"shopCartView\"]/body/div[5]/div/div")));
       Thread.sleep(1000);

        assertTrue(itemRemovedMessage.isDisplayed(), "Item should be successfully removed from the cart");
    }






    // NOT POSSIBLE TO ADD ITEM TO CART BEFORE SELECTING THE DESIRED SIZE

    @Test
    public void selectSizeBeforeAddingToCart() throws InterruptedException {
        webDriver.get("https://www.zara.com/ba/hr/search?searchTerm=flare&section=WOMAN");
        webDriver.manage().window().maximize();
        Thread.sleep(1000);

        WebElement product = webDriver.findElement(
                By.cssSelector("img[alt='Zara HLAČE FLARE S DŽEPOVIMA ZW COLLECTION – Burgundac – Slika 0']"));
        product.click();
        Thread.sleep(500);

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlToBe(
                "https://www.zara.com/ba/hr/hlace-flare-s-dzepovima-zw-collection-p08550187.html?v1=377075157"));

        String currentUrl = webDriver.getCurrentUrl();
        assertEquals("https://www.zara.com/ba/hr/hlace-flare-s-dzepovima-zw-collection-p08550187.html?v1=377075157",currentUrl);


        // click add to cart before selecting size should display warning message

        WebElement addToCartButton = webDriver.findElement(By.xpath(
                "/html/body/div[2]/div[1]/div[1]/div/div/" +
                        "div[2]/main/article/div/div[1]/div[2]/div/div[3]/div/div/button"
        ));
        addToCartButton.click();
        Thread.sleep(500);


        WebElement warningMessage = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//*[@id=\"product-377061571\"]/body/div[4]/div[2]/div")));
        String message = warningMessage.getText();
        Thread.sleep(1000);


        assertEquals("UPOZORENJE\n" +
                "MORATE ODABRATI VELIČINU.\n" +
                "ZATVORI", message);
        assertTrue(warningMessage.isDisplayed(), "Warning message should be displayed");
    }




}

