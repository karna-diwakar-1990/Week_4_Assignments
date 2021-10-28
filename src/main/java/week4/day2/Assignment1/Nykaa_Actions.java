package week4.day2.Assignment1;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;

public class Nykaa_Actions {
    public static void main(String[] args) {
        Helper_Class.chromedriverSetup();
        ChromeDriver driver = new ChromeDriver();
        Helper_Class.initializeDriver(driver, "https://www.nykaa.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Helper_Class.moveOverElement(driver, "xpath", "((((//div[@id='app']//header)[1]//div[@id='header_id']//div)[7]//div)[3]//ul)[2]//a[contains(text(), 'brands')]");

        WebElement element = driver.findElement(By.xpath("//a[contains(text(), 'brands')]"));
        WebElement parent = (WebElement) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].parentNode;", element);
        //WebElement target = parent.findElement(By.xpath("//div[@class='BrandsnameWrapper']//img[@src='https://adn-static2.nykaa.com/media/wysiwyg/2019/lorealparis.png']"));
        Helper_Class.moveToAndClickElement(driver, "xpath", "//div[@class='BrandsnameWrapper']//img[@src='https://adn-static2.nykaa.com/media/wysiwyg/2019/lorealparis.png']");
        Assert.assertTrue(driver.getTitle().contains("L'Oreal Paris"), "Title contains L'Oreal Paris");
        Helper_Class.moveToAndClickElement(driver, "xpath", "//div[@id='filter-sort']//button");
        Helper_Class.elementClick(driver, "xpath", "//div[@id='filter-sort']//ul//span[contains(text(), 'customer top rated')]");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Helper_Class.moveToAndClickElement(driver, "xpath", "//div[@id='filters-strip']//span[contains(text(), 'Category')]");
        Helper_Class.elementClick(driver, "xpath", "//div[@id='filters-strip']//ul/ul//span[text() = 'Hair']");
        Helper_Class.moveToAndClickElement(driver, "xpath", "//div[@id='filters-strip']//ul/ul/li/ul//span[text() = 'Hair Care']");
        Helper_Class.elementClick(driver, "xpath", "//div[@id='filters-strip']//ul/ul/li/ul/li/ul//span[text() = 'Shampoo']");
        Helper_Class.moveToAndClickElement(driver, "xpath", "//div[@id='filters-strip']//span[contains(text(), 'Concern')]");
        Helper_Class.elementClick(driver, "xpath", "(//div[@class = 'sidebar__inner']/div/div)[6]/ul/div//span[text() = 'Color Protection']");
        Assert.assertEquals(Helper_Class.getElements("xpath", "((//div[@id= 'filters-listing']/div)[1]/div)[2]//span[text() = 'Color Protection']", driver).size(), 1, "Color Protection Concern Filter is applied");
        Helper_Class.elementClick(driver, "xpath", "(//div[@id='product-list-wrap']//div[contains(text(), 'Paris Colour Protect Shampoo')])[1]");
        //Helper_Class.elementClick(driver, "xpath", "//div[@class='product-listing']//a[@href='/l-oreal-paris-color-protect-shampoo/p/6282?productId=6282&pps=1']/parent::div");
        ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(newTab.get(1));
        Helper_Class.selectDropdown(driver, "xpath", "//div[@id='app']//select[@title='SIZE']", "175ml");
        System.out.println("MRP for the selected item is Rs: "+Helper_Class.getElement("xpath", "//div[@id='app']//span[contains(text(), 'MRP')]/following::span", driver).getText());
        Helper_Class.elementClick(driver, "xpath", "((//div[@id='app']/div/div)[2]/div)[2]//span[text() = 'ADD TO BAG']");
        Helper_Class.elementClick(driver, "xpath", "(//div[@id='header_id']/div)[2]/div/div[@class != 'megaMenu_main css-e11zu']//button");
        WebElement cartFrame = Helper_Class.getElement("xpath", "//div[@id = 'portal-root']//iframe", driver);
        driver.switchTo().frame(cartFrame);
        String grandTotal = Helper_Class.getElement("xpath", "//div[@class= 'payment-tbl-data']//div[@class = 'value medium-strong']",driver).getText();
        System.out.println("Grand Total is "+ grandTotal );
        Helper_Class.elementClick(driver, "xpath", "//div[@class= 'fixrow']//div[@class = 'second-col']");
        driver.switchTo().defaultContent();
        Helper_Class.elementClick(driver, "xpath", "//div[contains(text(), 'Checkout as guest')]/parent::div//button");
        String finalTotal = Helper_Class.getElement("xpath", "//div[contains(text(), 'Grand Total')]/parent::div/div[@class='value']", driver).getText();
        if(finalTotal.equalsIgnoreCase(grandTotal)){
            System.out.println("Test Successfull");
        }
        driver.quit();
    }
}
