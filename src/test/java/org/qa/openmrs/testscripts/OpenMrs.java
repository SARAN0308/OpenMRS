package org.qa.openmrs.testscripts;

import org.qa.openmrs.base.BaseTest;
import org.qa.openmrs.pageaction.DashBoardPage;
import org.qa.openmrs.pageaction.LoginPage;
import org.qa.openmrs.pageaction.PatientDetailsPage;
import org.qa.openmrs.pageaction.RegisterPatientPage;
import org.testng.annotations.Test;

public class OpenMrs  {
    LoginPage loginPage = new LoginPage();
    DashBoardPage dashBoardPage = new DashBoardPage();
    RegisterPatientPage registerPatientPage = new RegisterPatientPage();
    PatientDetailsPage patientDetailsPage = new PatientDetailsPage();

    @Test
    public void openMrsTest() {
        BaseTest.browserLaunch("chrome");

        loginPage.login("username","password","LoginPage");

        dashBoardPage.clickRegisterPatient("DashBoardPage");

        registerPatientPage.registerPatient("givenName","familyName","birthDay","birthYear","address","city","state","country","postalCode","phoneNumber","registerPatient");

        registerPatientPage.verifyRegisterPatient("verifiedRegisterPatient");

        patientDetailsPage.verifyPatientDetailsAndUploadAttachment("attachmentUploadFile");

        patientDetailsPage.verifyCalculatedBmi("calculateBmi");

        patientDetailsPage.verifyEndMergeAddPastVisit("endMergeAddPastVisit");

        patientDetailsPage.verifyDeletedPatientID("reason","deletedPatientId");

    }
}
