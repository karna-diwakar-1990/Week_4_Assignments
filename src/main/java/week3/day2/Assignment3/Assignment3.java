package week3.day2.Assignment3;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class Assignment3 {
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
        }else if(identifierType.equalsIgnoreCase("tag")){
            element = driver.findElement(By.tagName(identifier));
        }
        return element;
    }
    public static List<WebElement> getElements(String identifierType, String identifier, ChromeDriver driver){
        List<WebElement> elements = null;
        if(identifierType.equalsIgnoreCase("class")){
            elements = driver.findElements(By.className(identifier));
        }else if(identifierType.equalsIgnoreCase("tagName")){
            elements = driver.findElements(By.tagName(identifier));
        }else if(identifierType.equalsIgnoreCase("id")){
            elements = driver.findElements(By.id(identifier));
        }
        return elements;
    }
    public static void enterText(ChromeDriver driver,String identifierType,String identifier, String input){
        WebElement textBox = getElement(identifierType, identifier, driver );
        textBox.sendKeys(input);
    }
    public static boolean isFramePresent(ChromeDriver driver, String identifierType, String identifier){
        if(getElements(identifierType, identifier, driver).size()>0){
            return true;
        }else{
            return false;
        }
    }
    public static void elementClick(ChromeDriver driver,String identifierType,String identifier){
        WebElement element = getElement(identifierType, identifier, driver );
        element.click();
    }
    public static void selectDropdown(ChromeDriver driver,String identifierType,String identifier, String value){
        Select dropdown = new Select(getElement(identifierType, identifier, driver ));
        dropdown.selectByVisibleText(value);
    }
    public static void main(String[] args) {
        chromedriverSetup();
        ChromeDriver driver = new ChromeDriver();
        initializeDriver(driver, "https://chercher.tech/practice/frames-example-selenium-webdriver");
        if(isFramePresent(driver, "id", "frame1")){
            driver.switchTo().frame("frame1");
        }
        enterText(driver, "tag", "input","lorem");
        if(isFramePresent(driver, "id", "frame3")){
            driver.switchTo().frame("frame3");
        }
        elementClick(driver, "id", "a");
        driver.switchTo().parentFrame();
        driver.switchTo().parentFrame();
        if(isFramePresent(driver, "id", "frame2")){
            driver.switchTo().frame("frame2");
        }
        selectDropdown(driver, "id", "animals", "Avatar");
        driver.switchTo().defaultContent();
        driver.quit();
        }
    }
