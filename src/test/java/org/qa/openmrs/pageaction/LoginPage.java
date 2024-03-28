package org.qa.openmrs.pageaction;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.qa.openmrs.constants.FilePathConstant;
import org.qa.openmrs.dataprovider.PropertyParser;
import org.qa.openmrs.messages.InfoMessage;

import static org.qa.openmrs.base.BaseTest.driver;
import static org.qa.openmrs.utils.ActionElement.*;

public class LoginPage {
    static Logger logger = Logger.getLogger(LoginPage.class);
    static PropertyParser loginPageLocator = new PropertyParser(FilePathConstant.loginPageLocators);
    static PropertyParser loginPageTestData = new PropertyParser(FilePathConstant.loginPageTestData);

    /**
     * This method is used to enter username and password
     * used to select random element from location session
     * used to click login button
     * Attached screenshot
     * @ param userName,password,fileName
     * @ author saran
     */
    public void login(String userName,String password,String fileName)  {

        String loginUserName = enterText(driver.findElement(By.xpath(loginPageLocator.getPropertyValue(("username")))),
                loginPageTestData.getPropertyValue(userName));
        logger.info(InfoMessage.ENTER_USERNAME + (loginUserName) + InfoMessage.TEXT_BOX);

        String loginPassword = enterText(driver.findElement(By.xpath(loginPageLocator.getPropertyValue(("password")))),
                loginPageTestData.getPropertyValue(password));
        logger.info(InfoMessage.ENTER_PASSWORD+ (loginPassword) + InfoMessage.TEXT_BOX);

        clickRandomElement(driver.findElements(By.xpath(loginPageLocator.getPropertyValue(("listOfSessionLocation")))));
        logger.info(InfoMessage.RANDOM_LOCATION_SESSION);

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_lOGIN_PAGE + fileName);

        click(driver.findElement(By.xpath(loginPageLocator.getPropertyValue("loginButton"))));
        logger.info(InfoMessage.LOGIN_BUTTON);
    }
}
