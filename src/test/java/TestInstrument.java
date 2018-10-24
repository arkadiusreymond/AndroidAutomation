import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TestInstrument {

    private static AppiumDriverLocalService service;
    protected static AndroidDriver<AndroidElement> driver;
    protected static Bukalapak bukalapak;


    /**
     * Initialization
     */
    @BeforeSuite
    public void beforeSuite() {
        // Start the server with the builder
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
        File appDir = new File("/Users/" + System.getProperty("user.name") + "/AndroidAutomation/");
        File app = new File(appDir, Objects.requireNonNull("bukalapak.apk"));

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
//        capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, Integer.parseInt(port) + 10);
        capabilities.setCapability(AndroidMobileCapabilityType.ANDROID_INSTALL_TIMEOUT, 240000);
        if (true) {
            capabilities.setCapability(AndroidMobileCapabilityType.AVD, "Nexus_4_API_22");
            capabilities.setCapability(AndroidMobileCapabilityType.AVD_ARGS, "-port " + "9920" + " -no-snapshot-save -netspeed " + true);
        }
        capabilities.setCapability("uiautomator2ServerInstallTimeout", 240000);
        capabilities.setCapability("uiautomator2ServerLaunchTimeout", 240000);
        driver = new AndroidDriver<>(service.getUrl(), capabilities);
        bukalapak = new Bukalapak(driver);
    }

    @AfterMethod
    public void afterMethod(ITestContext context, ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            String suiteName = context.getCurrentXmlTest().getSuite().getName();
            String testCaseName = context.getCurrentXmlTest().getName();
            File srcFile = driver.getScreenshotAs(OutputType.FILE);
            File targetFile = new File("/Users/" + System.getProperty("user.name") + "/Documents/Screenshot/"
                    + suiteName + "/" + testCaseName + ".jpg");
            try {
                FileUtils.copyFile(srcFile, targetFile);
                System.out.println("Screenshot taken");
            } catch (IOException e) {
                System.out.println("Exception while taking screenshot " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @AfterClass
    public void afterClass() {
        if (driver != null && true) {
            driver.resetApp();
        }
    }

    /**
     * Finishing
     */
    @AfterSuite
    public void afterSuite() {
        // Stop the appium server and close app
        if (service != null && driver != null) {
            driver.quit();
            service.stop();
        }

        // Kill Emulator
        if (true) {
            String adbPath = "/Users/" + System.getProperty("user.name") + "/library/android/sdk/platform-tools"
                    + File.separator + "adb";
            String[] command = new String[]{adbPath, "-s", "emulator-" + "9920", "emu", "kill"};
            try {
                Process process = new ProcessBuilder(command).start();
                process.waitFor(5, TimeUnit.SECONDS);
                System.out.println("Emulator closed successfully!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}