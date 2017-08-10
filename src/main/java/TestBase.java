import com.codeborne.selenide.Configuration;
import dataprovider.DataProviderXML;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Selenide.open;

public class TestBase extends DataProviderXML {

    private String baseUrl;

    TestBase(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @BeforeSuite
    public void init() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.holdBrowserOpen = false;
        Configuration.timeout = 1500;
        open(baseUrl);
    }

    @AfterSuite
    public void quit() {
        close();
    }
}
