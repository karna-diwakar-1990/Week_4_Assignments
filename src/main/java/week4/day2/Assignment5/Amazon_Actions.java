package week4.day2.Assignment5;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import week4.day2.Assignment1.Helper_Class;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

public class Amazon_Actions {
    public static void main(String[] args) {
        Helper_Class.chromedriverSetup();
        ChromeDriver driver = new ChromeDriver();
        Helper_Class.initializeDriver(driver, "https://www.amazon.in/");
        Helper_Class.enterText(driver, "xpath", "//div[@class='nav-search-field ']/input", "oneplus 9 pro");
        Helper_Class.elementClick(driver, "xpath", "//span[@id='nav-search-submit-text']/input");
        //Fetching price of first result
        String price = Helper_Class.getElement("xpath", "(((//div[@data-component-type='s-search-result'])[1]//div[@class='a-section']/div)[2]/div)[2]//span[@class='a-price']/span", driver).getText();
        String numberOfRatings = Helper_Class.getElement("xpath", "(((((//div[@data-component-type='s-search-result'])[1]//div[@class='a-section']/div)[2]/div)[2]/div/div/div)[2]//div/span)[2]//span", driver).getText();
        System.out.println("Total number of cutomer ratings "+ numberOfRatings);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        //WebElement stars = Helper_Class.getElement("xpath", "(((((//div[@data-component-type='s-search-result'])[1]//div[@class='a-section']/div)[2]/div)[2]/div/div/div)[2]//div/span)[1]//a", driver);
        Helper_Class.moveToAndClickElement(driver, "xpath", "((((((//div[@data-component-type='s-search-result'])[1]//div[@class='a-section']/div)[2]/div)[2]/div/div/div)[2]//div/span)[1]//a/i)[2]");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        String percentage_5_star = Helper_Class.getElement("xpath", "((//table[@id='histogramTable']//tr)[1]//td)[3]//a", driver).getText();
        Helper_Class.moveOverElement(driver, "tag", "body");
        Helper_Class.elementClick(driver, "xpath", "(((//div[@data-component-type='s-search-result'])[1]//div[@class='a-section']/div)[2]/div)[2]/div/div//h2/a");
        ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(newTab.get(1));
        WebElement itemImage = Helper_Class.getElement("xpath","//div[@id='imgTagWrapperId']",driver );
        File screenshot = itemImage.getScreenshotAs(OutputType.FILE);

        //Copy the file to a location and use try catch block to handle exception
        try {
            FileUtils.copyFile(screenshot, new File("C:\\projectScreenshots\\homePageScreenshot.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        String itemPrice = Helper_Class.getElement("xpath", "(((//div[@id='price']/table/tbody/tr)[2]/td)[2]/span)[1]", driver).getText();
        Helper_Class.elementClick(driver, "xpath", "//input[@id='add-to-cart-button']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='attach-added-to-cart-message']//span[@id='attach-accessory-cart-subtotal']")));
        String subTotal = Helper_Class.getElement("xpath", "//div[@id='attach-added-to-cart-message']//span[@id='attach-accessory-cart-subtotal']", driver).getText();
        if(itemPrice.equalsIgnoreCase(subTotal)){
            System.out.println("Test Successful!!!!");
        }
        driver.quit();
    }
}
