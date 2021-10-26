package week3.day2.Assignment2;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class ServiceNow {

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
    public static List<WebElement> getElements(String identifierType, String identifier, ChromeDriver driver){
        List<WebElement> elements = null;
        if(identifierType.equalsIgnoreCase("class")){
            elements = driver.findElements(By.className(identifier));
        }else if(identifierType.equalsIgnoreCase("tagName")){
            elements = driver.findElements(By.tagName(identifier));
        }
        return elements;
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
    public boolean isFramePresent(ChromeDriver driver){
        int count = getElements("tagName", "iframe", driver).size();
        if(count>0){
            return true;
        }else{
            return false;
        }
    }
    public static String getText(ChromeDriver driver, String identifierType, String identifier){
        WebElement element = getElement(identifierType, identifier, driver);
        return element.getAttribute("value");
    }
    public static void switchToFrame(ChromeDriver driver, String identifierType, String identifier){
        if(identifierType.equalsIgnoreCase("index")){
            driver.switchTo().frame(Integer.parseInt(identifier));
        }else if((identifierType.equalsIgnoreCase("id")) || (identifierType.equalsIgnoreCase("name"))){
            driver.switchTo().frame(identifier);
        }
    }
    public static void main(String[] args) {
        chromedriverSetup();
        ChromeDriver driver = new ChromeDriver();
        initializeDriver(driver, "https://dev113780.service-now.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        System.out.println("Frame is present");
        switchToFrame(driver,"index", "0");
        enterText(driver, "xpath", "//input[@id='user_name']", "admin");
        enterText(driver, "xpath", "//input[@id = 'user_password']", "Sujay@2021");
        elementClick(driver, "xpath", "//button[@id = 'sysverb_login']");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.switchTo().defaultContent();
        System.out.println(driver.getCurrentUrl());
        enterText(driver, "xpath", "//input[@id = 'filter']", "incident");
        elementClick(driver, "xpath", "(//a//div[contains(text(), 'All')])[2]");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        switchToFrame(driver, "id", "gsft_main");
        elementClick(driver, "xpath", "//button[contains(text(), 'New')]");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        getElement("xpath", "//input[@data-name='caller_id']", driver).sendKeys("admin@example.com");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //getElement("xpath", "//input[@data-name='caller_id']", driver).sendKeys(Keys.ENTER);
        enterText(driver, "xpath", "//input[@aria-label='Short description']", "Lore Ipsum");
        String incidentNumber = getText(driver, "xpath", "//input[@id='incident.number']");
        elementClick(driver, "xpath", "//button[@id='sysverb_insert_bottom']");
        selectDropdown(driver, "xpath", "//div[@data-list_id='incident']//select","Number");
        enterText(driver,"xpath","//div[@data-list_id='incident']//input[@placeholder='Search']", incidentNumber);
        getElement("xpath", "//div[@data-list_id='incident']//input[@placeholder='Search']", driver).sendKeys(Keys.ENTER);
        String testIncidentNumber = getElement("xpath","((//table[@id='incident_table']//tbody//tr)[1]//td[@class='vt'])[1]", driver).getText();
        Assert.assertEquals(testIncidentNumber, incidentNumber, "Test Successfull");
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        //Copy the file to a location and use try catch block to handle exception
        try {
            FileUtils.copyFile(screenshot, new File("C:\\projectScreenshots\\homePageScreenshot.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        driver.quit();

    }
}
