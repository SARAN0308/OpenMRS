package org.qa.openmrs.pageaction;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.qa.openmrs.constants.FilePathConstant;
import org.qa.openmrs.dataprovider.PropertyParser;
import org.qa.openmrs.messages.InfoMessage;
import org.qa.openmrs.messages.VerifyMessage;
import java.time.Month;
import static org.qa.openmrs.base.BaseTest.driver;
import static org.qa.openmrs.utils.ActionElement.*;

public class RegisterPatientPage {
    static Logger logger = Logger.getLogger(RegisterPatientPage.class);
    static PropertyParser registerPatientPageLocator = new PropertyParser(FilePathConstant.registerPatientPageLocators);
    static PropertyParser registerPatientPagePageTestData = new PropertyParser(FilePathConstant.registerPatientPageTestData);

    /**
     * This method is used to enter givenName and FamilyName
     * used to select gender and enter Date of Birth,Address,PhoneNumber
     * @ param giveName,familyName,birthDay,birthMonth,birthYear,address,city,state,country,postalCode,phoneNumber
     * @ author saran
     */
    public void registerPatient(String givenName,String familyName,String birthDay,String birthYear,String address,String city,String state,String country,String postalCode,String phoneNumber,String fileName){

        String giveNameText = enterText(driver.findElement(By.xpath(registerPatientPageLocator.getPropertyValue(("givenName")))),
                registerPatientPagePageTestData.getPropertyValue(givenName));
        logger.info(InfoMessage.ENTER_GIVEN_NAME +(giveNameText)+ InfoMessage.TEXT_BOX);

        String familyNameText = enterText(driver.findElement(By.xpath(registerPatientPageLocator.getPropertyValue(("familyName")))),
                registerPatientPagePageTestData.getPropertyValue(familyName));
        logger.info(InfoMessage.ENTER_FAMILY_NAME +(familyNameText)+ InfoMessage.TEXT_BOX);

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_GIVEN_FAMILY_NAME + fileName);

        confirmRightButton();

        selectByElementValue(driver.findElement(By.xpath(registerPatientPageLocator.getPropertyValue(("gender")))),registerPatientPagePageTestData.getPropertyValue("gender"));
        logger.info(InfoMessage.SELECT_GENDER);

        confirmRightButton();

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_GENDER + fileName);

        String birthDayText = enterText(driver.findElement(By.xpath(registerPatientPageLocator.getPropertyValue(("birthDay")))),
                registerPatientPagePageTestData.getPropertyValue(birthDay));
        logger.info(InfoMessage.ENTER_BIRTH_DAY +(birthDayText)+ InfoMessage.TEXT_BOX);

        selectByElementValue(driver.findElement(By.xpath(registerPatientPageLocator.getPropertyValue(("birthMonth")))),registerPatientPagePageTestData.getPropertyValue("birthMonth"));
        logger.info(InfoMessage.SELECT_BIRTH_MONTH);

        String birthYearText = enterText(driver.findElement(By.xpath(registerPatientPageLocator.getPropertyValue(("birthYear")))),
                registerPatientPagePageTestData.getPropertyValue(birthYear));
        logger.info(InfoMessage.ENTER_BIRTH_YEAR +(birthYearText)+ InfoMessage.TEXT_BOX);

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_DATE_OF_BIRTH + fileName);

        confirmRightButton();

        String addressText = enterText(driver.findElement(By.xpath(registerPatientPageLocator.getPropertyValue(("address")))),
                registerPatientPagePageTestData.getPropertyValue(address));
        logger.info(InfoMessage.ENTER_ADDRESS +(addressText)+ InfoMessage.TEXT_BOX);

        String cityText = enterText(driver.findElement(By.xpath(registerPatientPageLocator.getPropertyValue(("city")))),
                registerPatientPagePageTestData.getPropertyValue(city));
        logger.info(InfoMessage.ENTER_CITY +(cityText)+ InfoMessage.TEXT_BOX);

        String stateText = enterText(driver.findElement(By.xpath(registerPatientPageLocator.getPropertyValue(("state")))),
                registerPatientPagePageTestData.getPropertyValue(state));
        logger.info(InfoMessage.ENTER_STATE +(stateText)+ InfoMessage.TEXT_BOX);

        String countryText = enterText(driver.findElement(By.xpath(registerPatientPageLocator.getPropertyValue(("country")))),
                registerPatientPagePageTestData.getPropertyValue(country));
        logger.info(InfoMessage.ENTER_COUNTRY+(countryText)+ InfoMessage.TEXT_BOX);

        String postalCodeText = enterText(driver.findElement(By.xpath(registerPatientPageLocator.getPropertyValue(("postalCode")))),
                registerPatientPagePageTestData.getPropertyValue(postalCode));
        logger.info(InfoMessage.ENTER_POSTAL_CODE +(postalCodeText)+ InfoMessage.TEXT_BOX);

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_ADDRESS + fileName);

        confirmRightButton();

        String phoneNumberText = enterText(driver.findElement(By.xpath(registerPatientPageLocator.getPropertyValue(("phoneNumber")))),
                registerPatientPagePageTestData.getPropertyValue(phoneNumber));
        logger.info(InfoMessage.ENTER_PHONE_NUMBER +(phoneNumberText)+ InfoMessage.TEXT_BOX);

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_PHONE_NUMBER + fileName);

        confirmRightButton();
    }

    /**
     * This method is used to click Right Button
     * @ author saran
     */
    public static void confirmRightButton(){

        click(driver.findElement(By.xpath(registerPatientPageLocator.getPropertyValue(("confirmRightButton")))));
        logger.info(InfoMessage.CLICK_RIGHT_BUTTON);
    }

    /**
     * This method is used to verify givenName,familyName,gender,date of birth,address,phoneNumber
     * @ author saran
     */
    public void verifyRegisterPatient(String fileName){

        confirmRightButton();

        logger.info(InfoMessage.START_REGISTER_PATIENT_VERIFICATION);
        WebElement firstName = driver.findElement(By.xpath(registerPatientPageLocator.getPropertyValue(("verifyGivenName"))));
        String text = firstName.getText().replace(PropertyParser.readLocators("name"),PropertyParser.readLocators("replacement"));
        String givenName = registerPatientPagePageTestData.getPropertyValue("givenName");
        String familyName = registerPatientPagePageTestData.getPropertyValue("familyName");
        String actualName =  " " +givenName+ ","+ " "+familyName;
        verifyString(actualName,text,VerifyMessage.VERIFY_GIVEN_NAME);

        WebElement gender = driver.findElement(By.xpath(registerPatientPageLocator.getPropertyValue(("verifyGender"))));
        String genderText = gender.getText().replace(PropertyParser.readLocators("gender"),PropertyParser.readLocators("replacement"));
        String actualGender = " " +registerPatientPagePageTestData.getPropertyValue("genderName");
        verifyString(actualGender,genderText,VerifyMessage.VERIFY_GENDER);

        WebElement dateOfBirth = driver.findElement(By.xpath(registerPatientPageLocator.getPropertyValue(("verifyDateOfBirth"))));
        String dateOfBirthText = dateOfBirth.getText().replace(PropertyParser.readLocators("birthDate"),PropertyParser.readLocators("replacement"));
        String birthDay = registerPatientPagePageTestData.getPropertyValue("birthDay");
        String birthMonth = registerPatientPagePageTestData.getPropertyValue("birthMonth");
        Month month = Month.of(Integer.parseInt(birthMonth));
        String monthName = month.toString().toLowerCase();
        monthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1);
        String birthYear = registerPatientPagePageTestData.getPropertyValue("birthYear");
        String actualDateOfBirth = " " +birthDay + ","+ " "+monthName + ","+" "+birthYear;
        verifyString(actualDateOfBirth,dateOfBirthText,VerifyMessage.VERIFY_DATE_OF_BIRTH);

        WebElement addressName = driver.findElement(By.xpath(registerPatientPageLocator.getPropertyValue(("verifyAddress"))));
        String addressText = addressName.getText().replace(PropertyParser.readLocators("address"),PropertyParser.readLocators("replacement"));
        String address = registerPatientPagePageTestData.getPropertyValue("address");
        String city = registerPatientPagePageTestData.getPropertyValue("city");
        String state = registerPatientPagePageTestData.getPropertyValue("state");
        String country = registerPatientPagePageTestData.getPropertyValue("country");
        String postalCode = registerPatientPagePageTestData.getPropertyValue("postalCode");
        String actualAddress = " "+address + ","+ " "+city+ ","+ " "+state+ ","+ " "+country+ ","+ " "+postalCode;
        verifyString(actualAddress,addressText,VerifyMessage.VERIFY_ADDRESS);

        WebElement phoneNumber = driver.findElement(By.xpath(registerPatientPageLocator.getPropertyValue(("verifyPhoneNumber"))));
        String phoneNumberText = phoneNumber.getText().replace(PropertyParser.readLocators("phoneNumber"),PropertyParser.readLocators("replacement"));
        String actualPhoneNumber = " "+registerPatientPagePageTestData.getPropertyValue("phoneNumber");
        verifyString(actualPhoneNumber,phoneNumberText, VerifyMessage.VERIFY_PHONE_NUMBER);

        screenShot(fileName);
        logger.info(InfoMessage.SCREENSHOT_VERIFY_REGISTER_PATIENT);

        click(driver.findElement(By.xpath(registerPatientPageLocator.getPropertyValue(("submitButton")))));
        logger.info(InfoMessage.CLICK_REGISTER_PATIENT_SUBMIT);
    }
}
