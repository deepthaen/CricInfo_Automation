package driverManager;

import common.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebManager {
    private static final Logger logger = LogManager.getLogger(WebManager.class);

    ConfigReader configReader = new ConfigReader();
    public String getUrl(){
        String url = configReader.getProperty("url");
        logger.info("Launching Url :: "+url);
        return url;
    }
    public  WebDriver getDriver(String browser) {
        WebDriver driver = null;

        switch (browser.toUpperCase()) {
            case "CHROME":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-extensions");
                options.setBinary("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
                driver = new ChromeDriver();
                break;

            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            case "EDGE":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;

            case "SAFARI":
                driver = new SafariDriver();
                break;

            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
        driver.manage().window().maximize();
        return driver;
    }

    public void waitUntil(WebDriver driver, By by){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 seconds timeout
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

}
