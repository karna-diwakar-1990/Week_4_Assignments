package week3.day2.Assignment4;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Assignment4 {
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
        }else if(identifierType.equalsIgnoreCase("tag")){
            elements = driver.findElements(By.tagName(identifier));
        }
        return elements;
    }

    public static void main(String[] args) {
        chromedriverSetup();
        ChromeDriver driver = new ChromeDriver();
        initializeDriver(driver, "http://leafground.com/pages/frame.html");
        WebElement frame1 = getElement("xpath", "(//div[@id='wrapframe']//iframe)[1]", driver);
        driver.switchTo().frame(frame1);
        WebElement button1_frame1 = getElement("tag","button",driver );
        File screenshot = button1_frame1.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("C:\\projectScreenshots\\homePageScreenshot.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        driver.switchTo().defaultContent();
        //Task 2
        List<WebElement> frames = getElements("tag", "iframe", driver);
        System.out.println("Total number of frames in this page is "+ frames.size());
        driver.quit();
    }
}
