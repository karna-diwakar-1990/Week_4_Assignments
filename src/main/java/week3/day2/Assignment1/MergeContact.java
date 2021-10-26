package week3.day2.Assignment1;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MergeContact {

    public static void chromedriverSetup() {
        WebDriverManager.chromedriver().setup();
    }
    public static void initializeDriver(ChromeDriver driver, String url){
        driver.get(url);
        driver.manage().window().maximize();
    }
    public static WebElement getElement(String identifierType, String identifier, ChromeDriver driver){
        WebElement element = null;
        if(identifierType.equalsIgnoreCase("class")){
            element = driver.findElement(By.className(identifier));
        }else if(identifierType.equalsIgnoreCase("id")){
            element = driver.findElement(By.id(identifier));
        }else if(identifierType.equalsIgnoreCase("link-text")){
            element = driver.findElement(By.linkText(identifier));
        }else if(identifierType.equalsIgnoreCase("xpath")){
            element = driver.findElement(By.xpath(identifier));
        }
        return element;
    }
    public static void enterText(ChromeDriver driver,String identifierType,String identifier, String input){
        WebElement textBox = getElement(identifierType, identifier, driver );
        textBox.sendKeys(input);
    }
    public static void elementClick(ChromeDriver driver,String identifierType,String identifier){
        WebElement element = getElement(identifierType, identifier, driver );
        element.click();
    }
    public static void selectDropdown(ChromeDriver driver,String identifierType,String identifier, String value){
        Select dropdown = new Select(getElement(identifierType, identifier, driver ));
        dropdown.selectByVisibleText(value);
    }

    public static void clearText(ChromeDriver driver, String identifierType, String identifier){
        WebElement el = getElement(identifierType, identifier, driver);
        el.clear();
    }

    public static void main(String[] args) {
        chromedriverSetup();
        ChromeDriver driver = new ChromeDriver();
        initializeDriver(driver, "http://leaftaps.com/opentaps/control/login");
        enterText(driver, "class", "inputLogin", "Demosalesmanager");
        enterText(driver, "id", "password", "crmsfa");
        elementClick(driver, "class", "decorativeSubmit");
        elementClick(driver, "link-text", "CRM/SFA");
        elementClick(driver, "link-text", "Contacts");
        elementClick(driver, "xpath", "//ul[@class='shortcuts']//li//a[contains(text(), 'Merge Contacts')]");
        elementClick(driver, "xpath", "((//form[@name='MergePartyForm']//table//tbody)[1]//tr//td)[2]//a");
        String parent=driver.getWindowHandle();

        Set<String>s=driver.getWindowHandles();
        Iterator<String> I1= s.iterator();
        while(I1.hasNext()) {

            String child_window = I1.next();


            if (!parent.equals(child_window)) {
                driver.switchTo().window(child_window);
            }
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        elementClick(driver,"xpath", "(((//div[@class='x-grid3-body']//div)//table)[1]//td)[1]//div//a");
        driver.switchTo().window(parent);
        elementClick(driver, "xpath", "(//form[@name='MergePartyForm']//table//tbody//tr)[3]//a");
        parent=driver.getWindowHandle();

        s=driver.getWindowHandles();
        I1= s.iterator();
        while(I1.hasNext()) {

            String child_window = I1.next();


            if (!parent.equals(child_window)) {
                driver.switchTo().window(child_window);
            }
        }
        elementClick(driver,"xpath", "(((//div[@class='x-grid3-body']//div)//table)[2]//td)[1]//div//a");
        driver.switchTo().window(parent);
        elementClick(driver, "xpath","//a[normalize-space(text())='Merge']");
        Alert al = driver.switchTo().alert();
        al.accept();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Assert.assertEquals(driver.getTitle(), "View Contact | opentaps CRM", "Test Successful");
        driver.quit();

    }
}
