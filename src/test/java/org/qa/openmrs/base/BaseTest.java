package org.qa.openmrs.base;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.qa.openmrs.dataprovider.PropertyParser;
import org.qa.openmrs.messages.ErrorMessage;
import org.qa.openmrs.messages.InfoMessage;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.time.Duration;


public class BaseTest {
    public static WebDriver driver;
    static Logger logger = Logger.getLogger(BaseTest.class);

    /*
     * This method is used to launch the browser based on the input
     *
     * @parameters need to send while calling this method
     */
    @BeforeTest
    public static void  browserLaunch(String browserName) {
        try {
            switch (browserName.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver(chromeOptions);
                    logger.info(InfoMessage.LAUNCH + browserName + InfoMessage.BROWSER);
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    logger.info(InfoMessage.LAUNCH + browserName + InfoMessage.BROWSER);
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    logger.info(InfoMessage.LAUNCH + browserName + InfoMessage.BROWSER);
                    break;
                default:
                    logger.error(ErrorMessage.UNABLE_TO_LAUNCH);
                    break;
            }
            logger.info(InfoMessage.LAUNCH_OPEN_MRS);
            driver.get(PropertyParser.readLocators("QA_URL"));
            driver.manage().window().maximize();
            logger.info(InfoMessage.REDRICT + PropertyParser.readLocators("QA_URL"));
            driver.manage().timeouts().implicitlyWait(
                    Duration.ofSeconds(Integer.parseInt(PropertyParser.readLocators("implicitWaitSec"))));
            Assert.assertEquals(driver.getCurrentUrl(), PropertyParser.readLocators("QA_URL"));
        } catch (Exception exception) {
            logger.error(ErrorMessage.UNABLE_TO_LAUNCH + exception);
        }
    }

    /**
     * This method is used to close the browser
     */
    @AfterTest
    public static void tearDown() {
        driver.quit();
        logger.info(InfoMessage.CLOSE);
    }
}
