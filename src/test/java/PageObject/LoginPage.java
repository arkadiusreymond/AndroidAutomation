package PageObject;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class LoginPage extends BasePage {

    public LoginPage(AndroidDriver<AndroidElement> driver) {
        super(driver);
    }

    public void tapLoginButton() {
        tapViewWithText("Login");
    }

    public void typeOnUsernameEditText(String username) {
        typeTextOnViewWithIdOnIndex("et", 0, username);
    }

    public void typeOnPasswordEditText(String password) {
        typeTextOnViewWithIdOnIndex("et", 1, password);
    }

}
