package org.qa.openmrs.pageaction;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.qa.openmrs.constants.FilePathConstant;
import org.qa.openmrs.constants.Timeout;
import org.qa.openmrs.dataprovider.PropertyParser;
import org.qa.openmrs.messages.ErrorMessage;
import org.qa.openmrs.messages.InfoMessage;
import org.qa.openmrs.messages.VerifyMessage;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.nio.file.LinkOption;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.qa.openmrs.base.BaseTest.driver;
import static org.qa.openmrs.constants.Timeout.*;
import static org.qa.openmrs.pageaction.RegisterPatientPage.confirmRightButton;
import static org.qa.openmrs.utils.ActionElement.*;

public class PatientDetailsPage {
    static Logger logger = Logger.getLogger(PatientDetailsPage.class);
    static PropertyParser patientDetailsPageLocator = new PropertyParser(FilePathConstant.patientDetailsPageLocators);
    static PropertyParser patientDetailsPageTestData = new PropertyParser(FilePathConstant.patientDetailsPageTestData);

    /**
     * This method is used to verify whether its redirected to Patient details page
     * used to verify age calculated based on date of birth
     * used to click start visit and click on attachment to upload file
     * used to verify toaster message of successful attachment
     *
     * @param fileName
     * @ author saran
     */
    public void verifyPatientDetailsAndUploadAttachment(String fileName) {

        verifyString(patientDetailsPageTestData.getPropertyValue("actualPatientPageTitle"), driver.getTitle(), VerifyMessage.VERIFY_PATIENT_PAGE_TITLE);

        readText(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("dateOfBirth"))));
        String dobString = patientDetailsPageTestData.getPropertyValue("expectedDateOfBirth");
        LocalDate dob = LocalDate.parse(dobString);
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dob, currentDate);

        int years = period.getYears();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patientDetailsPageTestData.getPropertyValue("dateTimePattern"));
        String formattedDob = dob.format(formatter);

        String formattedAge = String.format(patientDetailsPageTestData.getPropertyValue("ageFormat"), years," " +formattedDob);

        int expectedYears = Integer.parseInt(patientDetailsPageTestData.getPropertyValue("expectedAge"));
        if (years == expectedYears && formattedAge.equals(readText(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("dateOfBirth")))))) {
            logger.info(VerifyMessage.VERIFY_AGE_CALCULATE + formattedAge);
        } else {
            logger.error(ErrorMessage.AGE_CALCULATED_INCORRECTLY + readText(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("dateOfBirth")))) + formattedAge);
        }
        logger.info(InfoMessage.VERIFIED_AGE);

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_PATIENT_DETAIL + fileName);

        startVisitConfirmButton();

        click(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("attachment"))));
        logger.info(InfoMessage.CLICKED_ATTACHMENT);

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_ATTACHMENT + fileName);

        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException awtException) {
            throw new RuntimeException(awtException);
        }
        WebElement element = driver.findElement(By.id(patientDetailsPageLocator.getPropertyValue("dropFile")));
        element.click();
        robot.setAutoDelay(Timeout.Max_WAIT);
        StringSelection stringSelection = new StringSelection(FilePathConstant.uploadFile);

        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

        robot.setAutoDelay(Timeout.Max_WAIT);

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.setAutoDelay(Timeout.Max_WAIT);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        logger.info(InfoMessage.UPLOADED_FILE + stringSelection);


        String captionText = enterText(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue(("caption")))),
                patientDetailsPageTestData.getPropertyValue("caption"));
        logger.info(InfoMessage.ENTER_CAPTION + (captionText) + InfoMessage.TEXT_BOX);

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_UPLOAD_FILE);

        click(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("uploadFileButton"))));
        logger.info(InfoMessage.CLICK_UPLOAD_FILE);

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_TOASTER_MESSAGE);

        verifyString(patientDetailsPageTestData.getPropertyValue("toasterMessage"), readText(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue(("toasterMessage"))))), VerifyMessage.VERIFY_TOASTER_MESSAGE);

        patientDetailsScreen();

        verifyBoolean(isDisplayed(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("verifyAttachment")))), true, VerifyMessage.VERIFY_DISPLAYED_ATTACHMENT);

        String formattedCurrentDate = currentDate.format(formatter);
        readText(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("recentVisitDate"))));

        if (formattedCurrentDate.equals(readText(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("recentVisitDate")))))) {
            logger.info(VerifyMessage.VERIFY_AGE_CALCULATE + formattedCurrentDate);
        } else {
            logger.error(ErrorMessage.AGE_CALCULATED_INCORRECTLY + formattedCurrentDate + readText(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("recentVisitDate")))));
        }
        verifyBoolean(isDisplayed(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("attachmentUpload")))), true, VerifyMessage.VERIFY_RECENT_VISIT_CURRENT_DATE);

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_ATTACHMENT_SESSION);
    }

    /**
     * This method is used to click start visit and start visit confirm button
     *
     * @ author saran
     */
    public void startVisitConfirmButton() {
        click(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("startVisitButton"))));
        logger.info(InfoMessage.CLICK_START_VISIT);

        click(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("startVisitConfirmButton"))));
        logger.info(InfoMessage.CLICK_START_VISIT_CONFIRM);
    }

    /**
     * This method is used to click start visit and start visit confirm button
     *
     * @ author saran
     */
    public static void patientDetailsScreen() {
        click(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("patientLinkButton"))));
        logger.info(InfoMessage.CLICK_PATIENT_LINK);
    }

    /**
     * This method is used to click end visit and end visit confirm button
     * used to click start visit and vitals button
     * used to enter height,weight and calculated BMI
     * used to click on save form and vitals save button
     *
     * @param fileName
     * @ author saran
     */
    public void verifyCalculatedBmi(String fileName) {

        click(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("endVisitButton"))));
        logger.info(InfoMessage.CLICK_END_VISIT);

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_END_VISIT);

        click(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("endVisitConfirmButton"))));
        logger.info(InfoMessage.CLICK_END_VISIT_CONFIRM);

        startVisitConfirmButton();
        click(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("vitalsButton"))));
        logger.info(InfoMessage.CLICK_VITAL_BUTTON);

        String heightText = enterText(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue(("height")))),
                patientDetailsPageTestData.getPropertyValue("height"));
        logger.info(InfoMessage.ENTER_HEIGHT + (heightText) + InfoMessage.TEXT_BOX);
        confirmRightButton();

        String weightText = enterText(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue(("weight")))),
                patientDetailsPageTestData.getPropertyValue("weight"));
        logger.info(InfoMessage.ENTER_WEIGHT + (weightText) + InfoMessage.TEXT_BOX);
        confirmRightButton();

        double heightInCm = Double.parseDouble(heightText.replace("\"", ""));

        double weightInKg = Double.parseDouble(weightText.replace("\"", ""));

        double heightInMeters = heightInCm / 100.0;

        double expectedBMI = weightInKg / (heightInMeters * heightInMeters);

        double displayedBMIValue = Double.parseDouble(readText(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("calculatedBmi")))));
        if (Math.abs(displayedBMIValue - expectedBMI) < 0.1) {
            logger.info(VerifyMessage.VERIFY_BMI + readText(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("calculatedBmi")))));
        } else {
            logger.error(ErrorMessage.BMI_CALCULATED_INCORRECTLY + expectedBMI + displayedBMIValue);
        }

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_BMI);
        confirmRightButton();

        click(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("saveForm"))));
        logger.info(InfoMessage.CLICK_SAVE_FORM);

        click(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("vitalsSaveButton"))));
        logger.info(InfoMessage.CLICK_VITALS_SAVE);
    }

    /**
     * This method is used to click vital end visit and confirm button
     * used to patient detail screen
     * used to verify current date and vital tag
     * used to click merge visit and selected merge visit
     * used to click add past visit
     * @param fileName
     * @ author saran
     */
    public void verifyEndMergeAddPastVisit(String fileName) {
        click(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("vitalsEndVisitButton"))));
        logger.info(InfoMessage.CLICK_END_VISIT);

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_END_VISIT);

        click(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("endVisitConfirmButton"))));
        logger.info(InfoMessage.CLICK_END_VISIT_CONFIRM);

        try {
            Thread.sleep(Max_WAIT);
        } catch (InterruptedException interruptedException) {
            throw new RuntimeException(interruptedException);
        }

        click(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("patientDetailScreen"))));
        logger.info(InfoMessage.PATIENT_DETAIL_SCREEN);

        logger.info(InfoMessage.PATIENT_DETAIL_SCREEN);

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patientDetailsPageTestData.getPropertyValue("dateTimePattern"));
        String formattedCurrentDate = currentDate.format(formatter);

        readText(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("recentVisitDate"))));

        if (formattedCurrentDate.equals(readText(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("recentVisitDate")))))) {
            logger.info(VerifyMessage.VERIFY_RECENT_VISIT_CURRENT_DATE + formattedCurrentDate);
        } else {
            logger.error(ErrorMessage.DATE_CALCULATED_INCORRECTLY + formattedCurrentDate + readText(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("recentVisitDate")))));
        }
        verifyBoolean(isDisplayed(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("vitalTag")))), true, VerifyMessage.VERIFY_VITAL_TAG);

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_RECENT_VISIT_VITAL_TAG);

        click(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("mergeVisitButton"))));
        logger.info(InfoMessage.CLICK_MERGE_VISIT);
        click(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("firstMergeVisitCheckBox"))));
        logger.info(InfoMessage.CLICK_FIRST_MERGE_CHECKBOX);
        click(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("secondMergeVisitCheckBox"))));
        logger.info(InfoMessage.CLICK_SECOND_MERGE_CHECKBOX);

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_MERGE_VISIT);

        click(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("mergeSelectedVisitButton"))));
        logger.info(InfoMessage.CLICK_MERGE_SELECTED_VISIT);

        click(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("returnButton"))));
        logger.info(InfoMessage.CLICK_MERGE_VISIT_RETURN);

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_MERGE_VISIT_RETURN);

        if (formattedCurrentDate.equals(readText(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("recentVisitDate")))))) {
            logger.info(VerifyMessage.VERIFY_RECENT_VISIT_CURRENT_DATE + formattedCurrentDate);
        } else {
            logger.error(ErrorMessage.DATE_CALCULATED_INCORRECTLY + formattedCurrentDate + readText(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("recentVisitDate")))));
        }

        verifyBoolean(isDisplayed(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("vitalAttachment")))), true, VerifyMessage.VERIFY_DISPLAYED_ATTACHMENT);

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_VITAL_ATTACH_UPLOAD);

        click(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("addPastVisitButton"))));
        logger.info(InfoMessage.CLICK_ADD_PAST_VISIT);

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_ADD_PAST_VISIT);

        List<WebElement> datePicker = driver.findElements(By.xpath(patientDetailsPageLocator.getPropertyValue("datePicker")));
            for (WebElement date : datePicker) {
                String isFutureDatesDisabled = date.getAttribute("disabled");
                if (isFutureDatesDisabled != null && isFutureDatesDisabled.equals("false")) {
                    logger.info(InfoMessage.FUTURE_DATE_ENABLE);
                } else {
                    logger.error(ErrorMessage.FUTURE_DATE_DISABLE);
                }
                break;
            }

            click(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("cancelButton"))));
            logger.info(InfoMessage.CLICK_CANCEL);
        }

    /**
     * This method is used to get patientId and click delete patient
     * used to verify deleted patient id in matched records
     *
     * @param fileName
     * @author saran
     */
    public void verifyDeletedPatientID(String reason, String fileName) {

        String patientId = readText(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("patientId"))));
        logger.info(InfoMessage.PATIENT_ID + (patientId));

        click(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("deletePatientButton"))));
        logger.info(InfoMessage.CLICK_DELETE_PATIENT);

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_DELETE_PATIENT);

        String reasonText = enterText(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue(("reason")))),
                patientDetailsPageTestData.getPropertyValue(reason));
        logger.info(InfoMessage.ENTER_REASON + (reasonText) + InfoMessage.TEXT_BOX);

        click(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("deletePatientConfirmButton"))));
        logger.info(InfoMessage.CLICK_DELETE_PATIENT_CONFIRM);

        verifyString(patientDetailsPageTestData.getPropertyValue("patientToastMessage"), readText(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue(("deleteToasterMessage"))))), VerifyMessage.VERIFY_TOASTER_MESSAGE);

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_DELETE_PATIENT);

        String patientIdText = enterText(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue(("patientIdSearch")))),
                patientId);
        logger.info(InfoMessage.PATIENT_ID + (patientIdText) + InfoMessage.TEXT_BOX);

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_MATCH_RECORD);

        verifyString(patientDetailsPageTestData.getPropertyValue("matchRecord"), readText(driver.findElement(By.xpath(patientDetailsPageLocator.getPropertyValue("noMatchingRecords")))), VerifyMessage.VERIFY_MATCH_FOUND);
    }
}

