package Tests;

import Base.BaseTest;
import com.google.common.base.Predicates;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestingWithExcel extends BaseTest {

    @BeforeMethod
    public void pageSetup() {

        driver.navigate().to("https://the-internet.herokuapp.com/login");
    }

    @Test
    public void loggingInWithValidCredentials() {

        String validUsername = excelReader.getStringData("Sheet1", 1, 0);
        String validPassword = excelReader.getStringData("Sheet1", 1, 1);

        loginPage.enterUsername(validUsername);
        loginPage.enterPassword(validPassword);
        loginPage.clickOnLoginButton();
        Assert.assertTrue(loginPage.getFlashLoginMessage().isDisplayed());
        Assert.assertTrue(loginPage.getLogoutButton().isDisplayed());

        String flashMessage = loginPage.getFlashLoginMessage().getText().trim();
        Assert.assertTrue(flashMessage.contains("You logged into a secure area!"));

    }

    @Test
    public void loggingInWithInvalidUsername() {
        for (int i = 1; i <= excelReader.getLastRow("Sheet1"); i++) {

            String invalidUsername = excelReader.getStringData("Sheet1", i, 2);
            String validPassword = excelReader.getStringData("Sheet1", 1, 1);

            loginPage.enterUsername(invalidUsername);
            loginPage.enterPassword(validPassword);
            loginPage.clickOnLoginButton();
            Assert.assertTrue(loginPage.getFlashLoginMessage().isDisplayed());
            Assert.assertTrue(loginPage.getLoginButton().isDisplayed());

            String flashMessage = loginPage.getFlashLoginMessage().getText().trim();
            Assert.assertTrue(flashMessage.contains("Your username is invalid"));

        }
    }

    @Test
    public void loggingInWithInvalidPassword() {
        for (int i = 1; i <= excelReader.getLastRow("Sheet1"); i++) {

            String validUsername = excelReader.getStringData("Sheet1", 1, 0);
            String invalidPassword = excelReader.getStringData("Sheet1", i, 3);

            loginPage.enterUsername(validUsername);
            loginPage.enterPassword(invalidPassword);
            loginPage.clickOnLoginButton();

            Assert.assertTrue(loginPage.getFlashLoginMessage().isDisplayed());
            Assert.assertTrue(loginPage.getLoginButton().isDisplayed());

            String flashMessage = loginPage.getFlashLoginMessage().getText().trim();
            Assert.assertTrue(flashMessage.contains("Your password is invalid"));

        }
    }

    @Test
    public void loggingInWithInvalidCredentials() {
        for (int i = 1; i <= excelReader.getLastRow("Sheet1"); i++) {

            String invalidUsername = excelReader.getStringData("Sheet1", i, 2);
            String invalidPassword = excelReader.getStringData("Sheet1", i, 3);

            loginPage.enterUsername(invalidUsername);
            loginPage.enterPassword(invalidPassword);
            loginPage.clickOnLoginButton();

            Assert.assertTrue(loginPage.getFlashLoginMessage().isDisplayed());
            Assert.assertTrue(loginPage.getLoginButton().isDisplayed());

        }
    }
}
