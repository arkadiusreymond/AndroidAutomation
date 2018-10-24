package PageObject;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class HomePage extends BasePage {

    public HomePage(AndroidDriver<AndroidElement> driver) {
        super(driver);
    }

    public void tapOnOkeButtonOnboarding() {
        tapViewWithIdAndTimeOut("button_showcase", 30);
    }
}
