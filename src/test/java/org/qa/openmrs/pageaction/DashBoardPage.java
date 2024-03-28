package org.qa.openmrs.pageaction;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.qa.openmrs.constants.FilePathConstant;
import org.qa.openmrs.dataprovider.PropertyParser;
import org.qa.openmrs.messages.InfoMessage;
import org.qa.openmrs.messages.VerifyMessage;

import static org.qa.openmrs.base.BaseTest.driver;
import static org.qa.openmrs.utils.ActionElement.*;

public class DashBoardPage {
    static Logger logger = Logger.getLogger(DashBoardPage.class);
    static PropertyParser dashBoardPageLocator = new PropertyParser(FilePathConstant.dashBoardPageLocators);
    static PropertyParser dashBoardPageTestData = new PropertyParser(FilePathConstant.dashBoardPageTestData);

    /**
     * This is used to verify whether it's redirected to home page
     * Click Register a patient
     * @ param fileName
     * @ author Saran
     */
    public void clickRegisterPatient(String fileName){

        verifyString(dashBoardPageTestData.getPropertyValue("actualHomePageTitle"),driver.getTitle() , VerifyMessage.VERIFY_HOME_PAGE_TITLE);
        logger.info(InfoMessage.VERIFY_DASHBOARD_PAGE);

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_DASHBOARD_PAGE + fileName);

        click(driver.findElement(By.xpath(dashBoardPageLocator.getPropertyValue("registerPatient"))));
        logger.info(InfoMessage.REGISTER_PATIENT);
    }
}
