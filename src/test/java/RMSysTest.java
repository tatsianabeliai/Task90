import com.sun.org.glassfish.gmbal.Description;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.appears;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class RMSysTest extends TestBase {
    private static final String BASE_URL = "https://192.168.100.26/";
    private static final By USERNAME_FIELD = By.id("Username");
    private static final By PASSWORD_FIELD = By.id("Password");
    private static final By LOGIN_BUTTON = By.id("SubmitButton");
    private static final By SIGN_OUT_BUTTON = By.xpath("//a[@title=\"Sign out\"]");
    private static final By HOME_MENU_TAB = By.id("homeMenu");
    private static final By OFFICE_TAB_LINK = By.id("officeMenu");
    private static final By SEARCH_BY_OFFICE = By.id("input-search");
    private static final String USERNAME = "EugenBorisik";
    private static final String PASSWORD = "qwerty12345";
    private static final int TIMEOUT_5SECONDS = 5000;


    public RMSysTest() {
        super(BASE_URL);
    }

    @Description("Go to RMSsys login page, enter credentials and login")
    @Test
    public void login() {
        $(USERNAME_FIELD).sendKeys(USERNAME);
        try {
            Thread.sleep(TIMEOUT_5SECONDS); //2.	Add Thread.sleep for login test. This is the Explicit Wait type
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        $(PASSWORD_FIELD).sendKeys(PASSWORD);
        $(LOGIN_BUTTON).click();

        // 3.	Add explicit waiter for login test, which will wait until Sign out link appears (after login).
        $(SIGN_OUT_BUTTON).waitUntil(appears, TIMEOUT_5SECONDS);
        $(HOME_MENU_TAB).should(visible);
    }

    @Description("Login RMSys, got to Office tab wait for Search by office input to appear - wait 15 seconds, polling frequence - 2,7 seconds")
    @Test(dependsOnMethods = "login")
    public void waitForSearchByOffice() {
        $(HOME_MENU_TAB).shouldBe(visible);
        $(OFFICE_TAB_LINK).click();
        $(SEARCH_BY_OFFICE).should(appears);
    }
}

