package org.qa.openmrs.constants;

public class FilePathConstant {
    public static String configPath = "./config.properties";

    public static String locatorPath = "./src/test/resources/locators/";

    public static String testDataPath = "./src/test/resources/testdata/";

    public static final String loginPageLocators = locatorPath + "loginPage.properties";
    public static final String dashBoardPageLocators = locatorPath + "dashBoardPage.properties";
    public static final String registerPatientPageLocators = locatorPath + "registerPatientPage.properties";
    public static final String patientDetailsPageLocators = locatorPath + "patientDetailsPage.properties";

    public static final String loginPageTestData = testDataPath + "loginPageTestData.properties";
    public static final String dashBoardPageTestData = testDataPath + "dashBoardPageTestData.properties";
    public static final String registerPatientPageTestData = testDataPath + "registerPatientPageTestData.properties";
    public static final String patientDetailsPageTestData = testDataPath + "patientDetailsPageTestData.properties";
    public static final String uploadFile = "C:\\Users\\venka\\Downloads\\TechAssessmentExp__2_.txt";
}
