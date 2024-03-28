package org.qa.openmrs.utils;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.qa.openmrs.dataprovider.PropertyParser;
import org.qa.openmrs.messages.ErrorMessage;
import org.qa.openmrs.messages.InfoMessage;
import org.testng.Assert;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Random;
import static org.qa.openmrs.base.BaseTest.driver;

public class ActionElement {
        static Logger logger = Logger.getLogger(ActionElement.class);
        static WebDriverWait wait;

        /**
         * This method is used to click the element using action class
         *
         * @param locateElement :- Move the element using action class.
         */
        public static void click(WebElement locateElement) {
            try {
                Actions action = new Actions(driver);
                isDisplayed(locateElement);
                elementToBeClickable(locateElement);
                action.moveToElement((locateElement)).click().build().perform();
            } catch (Exception exception) {
                logger.error(ErrorMessage.UNABLE_TO_CLICK + locateElement + exception);
            }

        }

        /**
         * This method is used for select WebElement value
         *
         * @param locator :- the locator used to select the value
         * @param value   :- Expected value to selected
         */
        public static void selectByElementValue(WebElement locator, String value) {
            try {
                Select select = new Select((locator));
                select.selectByValue(value);
            } catch (Exception exception) {
                logger.error(ErrorMessage.UNABLE_TO_SELECT_WEB_ELEMENT+ value  + exception);
            }
        }

        /**
         * This method is used for clicking random element
         *
         * @param locator The locator is used to select random element
         */
        public static void clickRandomElement(List<WebElement> locator) {
            try {
                List<WebElement> elements = (locator);
                int randomIndex = new Random().nextInt(elements.size());
                elements.get(randomIndex).click();

            } catch (Exception exception) {
                logger.error(ErrorMessage.UNABLE_TO_CLICK + locator  + exception);
            }
        }


        /**
         * This method is used to taking a screenshot in run time
         *
         * @param fileName : file name of the screenshot
         * @return
         */
        public static String screenShot(String fileName) {
            String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
            String destination = System.getProperty("user.dir") + "\\ScreenShot\\" + fileName + "_" + dateName + ".png";
            File finalDestination = new File(destination);
            try {
                FileUtils.copyFile(source, finalDestination);
            } catch (Exception exception) {
                logger.error(ErrorMessage.UNABLE_TO_TAKE_SCREENSHOT+ exception);
            }
            return destination;
        }

        /**
         * This method is used for enter the text
         *
         * @param locator              : The locator is used to read text
         * @param expectedValueToEnter : Expected value to enter in the textbox
         * @return
         */
        public static String enterText(WebElement locator, String expectedValueToEnter) {
            try {
                if (isDisplayed(locator)) {
                    (locator).clear();
                    (locator).sendKeys(expectedValueToEnter);
                } else {
                    logger.error(ErrorMessage.UNABLE_TO_ENTER + expectedValueToEnter );
                }
            } catch (Exception exception) {
                logger.error(ErrorMessage.UNABLE_TO_ENTER  + expectedValueToEnter + exception);
            }
            return expectedValueToEnter;
        }

        /**
         * This method is used for read the text in page.
         *
         * @param locator :- This is used to read the text in page.
         * @return readText
         */
        public static String readText(WebElement locator) {

            String readText = null;
            try {
                if (isDisplayed(locator)) {
                    readText = (locator).getText();
                }
            } catch (Exception exception) {
                logger.error(ErrorMessage.UNABLE_TO_READ_TEXT + exception);
            }
            return readText;
        }

        /**
         * The method is used to verify is displayed or not
         *
         * @param locator : - This is locator is used to verify the element is displayed
         *                or not
         */
        public static boolean isDisplayed(WebElement locator) {
            try {
                (locator).isDisplayed();
                return true;
            } catch (Exception exception) {
                logger.error( locator + ErrorMessage.ELEMENT_NOT_PRESENT+ exception);
                return false;
            }

        }


        /**
         * Explicit Wait
         *
         * @return :-
         */
        public static WebDriverWait explicitWait() {
            return wait = new WebDriverWait(driver,
                    Duration.ofSeconds(Integer.parseInt(PropertyParser.readLocators("explicitwaitSec"))));
        }

        /**
         * This method is used to elementToBeClickable  using explicit wait
         *
         * @param locator :- This locator is used to click the element using explicit wait
         * @return
         */
        public static boolean elementToBeClickable(WebElement locator) {
            try {
                explicitWait().until(ExpectedConditions.elementToBeClickable(locator));
                return true;
            } catch (Exception exception) {
                logger.error(ErrorMessage.UNABLE_TO_CLICK + exception);
                return false;
            }
        }

    /**
     * This method is used to verify string
     *
     * @param  :- actualText,expectedText
     * @return
     */
        public static void verifyString(String actualText, String expectedText, String messageInfo) {
            try {
                Assert.assertEquals(actualText, expectedText);
                logger.info(messageInfo + InfoMessage.PASS + InfoMessage.ACTUAL_RESULT + expectedText + InfoMessage.COLON + InfoMessage.EXPECTED_RESULT
                        + actualText);
            } catch (AssertionError assertionError) {

                logger.info(messageInfo + InfoMessage.FAIL + InfoMessage.ACTUAL_RESULT + expectedText + InfoMessage.COLON + InfoMessage.EXPECTED_RESULT
                        + actualText);
            }
        }

    /**
     * This method is used to verify Boolean
     *
     * @param  :- actualText,expectedText
     * @return
     */
        public static boolean verifyBoolean(Boolean actualText, Boolean expectedText, String messageInfo) {
            try {
                Assert.assertEquals(actualText, expectedText);
                logger.info(messageInfo + InfoMessage.PASS + InfoMessage.ACTUAL_RESULT + expectedText + InfoMessage.COLON + InfoMessage.EXPECTED_RESULT
                        + actualText);
                return true;
            } catch (AssertionError assertionError) {

                logger.info(messageInfo + InfoMessage.FAIL+ InfoMessage.ACTUAL_RESULT  + expectedText +InfoMessage.COLON + InfoMessage.EXPECTED_RESULT
                        + actualText);
                return false;
            }
        }
}
