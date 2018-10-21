import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Objects;

/**
 * Created by Arka on 5/14/18.
 */
public class SimpleTest  {

    private static AppiumDriverLocalService service;
    protected static AndroidDriver<AndroidElement> driver;

    @BeforeTest
    public void beforeTest() {
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.usingAnyFreePort();
        builder.withArgument(GeneralServerFlag.LOG_LEVEL, "warn");
        service = AppiumDriverLocalService.buildService(builder);
        if (service == null || !service.isRunning()) {
            System.out.println("Starting Appium Server!");
            service.stop();
            service.start();
        }

        // Set Desired Capabilities
        File appDir = new File("/Users/" + System.getProperty("user.name") + "/AutomationUIAndroid/");
        File app = new File(appDir, Objects.requireNonNull("calculator1.apk"));

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus_4_API_22");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.1");
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
        capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
        capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, Integer.parseInt("9920") + 10);
        capabilities.setCapability(AndroidMobileCapabilityType.ANDROID_INSTALL_TIMEOUT, 240000);
        if (true) {
            capabilities.setCapability(AndroidMobileCapabilityType.AVD, "Nexus_4_API_22");
            capabilities.setCapability(AndroidMobileCapabilityType.AVD_ARGS, "-port " + "9920" + " -no-snapshot-save -netspeed " + true);
        }
        capabilities.setCapability("uiautomator2ServerInstallTimeout", 240000);
        capabilities.setCapability("uiautomator2ServerLaunchTimeout", 240000);
        driver = new AndroidDriver<>(service.getUrl(), capabilities);
    }

    @Test()
    public void simpleTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button_six")));
        driver.findElementById("button_six").click();
        driver.findElementById("button_plus").click();
        driver.findElementById("button_four").click();
        driver.findElementById("button_equals").click();
        Thread.sleep(5000);
    }
}
