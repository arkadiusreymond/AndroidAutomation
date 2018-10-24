import PageObject.HomePage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class Bukalapak {
    private final AndroidDriver<AndroidElement> driver;

    public Bukalapak(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
    }

    public HomePage homePage() {
        return new HomePage(driver);
    }
}
