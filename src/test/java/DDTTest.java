import com.codeborne.selenide.Configuration;
import dataprovider.DataProviderXML;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.codeborne.selenide.Selenide.*;

public class DDTTest extends DataProviderXML {
    private static final String BASE_URL = "https://192.168.100.26/";
    private static final By USERNAME_FIELD = By.id("Username");
    private static final By PASSWORD_FIELD = By.id("Password");
    private static final By LOGIN_BUTTON = By.id("SubmitButton");
    private static final By SIGN_OUT_BUTTON = By.xpath("//a[@title=\"Sign out\"]");
    private static final By USERNAME_VALIDATION_ERROR = By.id("user-box-validation");
    private static final By PASSWORD_VALIDATION_ERROR = By.id("password-box-validation");
    private static final By INVALID_CREDENTIALS_ERROR = By.cssSelector(".validation-summary-errors");

    @BeforeMethod
    public void init() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.holdBrowserOpen = false;
        Configuration.timeout = 1500;
        open(BASE_URL);
    }

    @Test(dataProvider = "xmlDataProvider")
    public void login(String username, String password, String validationType) {
        $(USERNAME_FIELD).sendKeys(username);
        $(PASSWORD_FIELD).sendKeys(password);
        $(LOGIN_BUTTON).click();
        SoftAssert softAssert = new SoftAssert();
        switch (validationType) {
            case "correctCreds":
                softAssert.assertTrue($(SIGN_OUT_BUTTON).isDisplayed());
                break;
            case "incorrectPass":
                softAssert.assertEquals("Password is required", $(PASSWORD_VALIDATION_ERROR).getText());
                break;
            case "incorrectUsername":
                softAssert.assertEquals("*Invalid credentials.", $(INVALID_CREDENTIALS_ERROR).getText());
                break;
            case "noUsernameAndPass":
                softAssert.assertEquals("Username is required", $(USERNAME_VALIDATION_ERROR).getText());
                softAssert.assertEquals("Password is required", $(PASSWORD_VALIDATION_ERROR).getText());
                break;
            case "incorrectUsernameAndPass":
                softAssert.assertEquals("*Invalid credentials.", $(INVALID_CREDENTIALS_ERROR).getText());
                break;
            default:
                softAssert.fail("incorrect validation type");
                softAssert.assertAll();
        }
    }

    @AfterMethod
    public void quit() {
        close();
    }
}