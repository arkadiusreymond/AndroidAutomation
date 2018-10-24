package PageObject;


import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.Duration;
import java.util.*;

/**
 * Created by Aldo Christian on 12/06/17.
 */

public class BasePage {

    protected final AndroidDriver<AndroidElement> driver;
    private int maxSwipeCount = 15;
    private boolean isScreenshotEnabled = false;

    public BasePage(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
//        dotenv = Dotenv.configure().directory("./").load();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    //region Private Find Element Function

    private AndroidElement findElementWithId(String id) {

        AndroidElement element = null;
        for (int i = 0; i < maxSwipeCount; i++) {
            try {
                element = driver.findElementById(id);
                if (isElementPresent(element)) break;
            } catch (NoSuchElementException e) {
                swipeUp();
            }
        }
        return element;

    }

    private List<AndroidElement> findElementsWithId(String id) {

        List<AndroidElement> element = null;
        for (int i = 0; i < maxSwipeCount; i++) {
            try {
                element = driver.findElementsById(id);
                if (isElementPresent(element.get(0))) break;
            } catch (NoSuchElementException e) {
                swipeUp();
            }
        }
        return element;

    }

    private AndroidElement findElementWithXpath(String xpath) {
        AndroidElement element = null;
        for (int i = 0; i < maxSwipeCount; i++) {
            try {
                element = driver.findElementByXPath(xpath);
                if (isElementPresent(element)) break;
            } catch (NoSuchElementException e) {
                swipeUp();
            }
        }
        return element;
    }

    private List<AndroidElement> findElementsWithXpath(String xpath) {

        List<AndroidElement> element = null;
        for (int i = 0; i < maxSwipeCount; i++) {
            try {
                element = driver.findElementsByXPath(xpath);
                if (isElementPresent(element.get(0))) break;
            } catch (NoSuchElementException e) {
                swipeUp();
            }
        }
        return element;

    }

    private AndroidElement findElementContainsText(String text) {

        AndroidElement element = null;
        for (int i = 0; i < maxSwipeCount; i++) {

            try {
                element = driver.findElementByAndroidUIAutomator(
                        "new UiSelector().textContains(\"" + text + "\")");
                if (isElementPresent(element)) break;
            } catch (NoSuchElementException e) {
                swipeUp();
            }
        }
        return element;

    }

    private AndroidElement findElementContainsText(String text, int loop) {

        AndroidElement element = null;
        for (int i = 0; i < loop; i++) {

            try {
                element = driver.findElementByAndroidUIAutomator(
                        "new UiSelector().textContains(\"" + text + "\")");
                if (isElementPresent(element)) break;
            } catch (NoSuchElementException e) {
                swipeUp();
            }
        }
        return element;

    }

    private List<AndroidElement> findElementsUsingXpath(String title) {
        List<AndroidElement> list;
        list = driver.findElementsByXPath("/*//*[@resource-id='com.bukalapak.android:id/" + title + "']");
        return list;
    }

    private List<AndroidElement> findElementsContainsText(String text) {

        List<AndroidElement> element = null;
        for (int i = 0; i < maxSwipeCount; i++) {
            try {
                element = driver.findElementsByAndroidUIAutomator(
                        "new UiSelector().textContains(\"" + text + "\")");
                if (isElementPresent(element.get(0))) break;
            } catch (NoSuchElementException e) {
                swipeUp();
            }
        }
        return element;

    }


    private AndroidElement findElementWithText(String text) {

        AndroidElement element = null;
        for (int i = 0; i < maxSwipeCount; i++) {
            try {
                element = driver.findElementByAndroidUIAutomator(
                        "new UiSelector().text(\"" + text + "\")");
                if (isElementPresent(element)) break;
            } catch (NoSuchElementException e) {
                swipeUp();
            }
        }
        return element;

    }

    private List<AndroidElement> findElementsWithText(String text) {

        List<AndroidElement> element = null;
        for (int i = 0; i < maxSwipeCount; i++) {
            try {
                element = driver.findElementsByAndroidUIAutomator(
                        "new UiSelector().text(\"" + text + "\")");
                if (isElementPresent(element.get(0))) break;
            } catch (NoSuchElementException e) {
                swipeUp();
            }
        }
        return element;
    }

    private AndroidElement findElementByXpath(String xpath) {
        return driver.findElementByXPath(xpath);
    }

    private List<AndroidElement> findElementsByXpath(String xpath) {
        return driver.findElementsByXPath(xpath);
    }

    private AndroidElement findElementByClassName(String className) {
        return driver.findElementByClassName(className);
    }

    private List<AndroidElement> findElementsByClassName(String className) {
        return driver.findElementsByClassName(className);
    }

    private AndroidElement findElementWithIdWithTimeout(String id, int timeOutInSeconds) {
        By by = By.id(id);
        return (AndroidElement) (new WebDriverWait(driver, timeOutInSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    private AndroidElement findElementWithXpathWithTimeout(String xpath, int timeOutInSeconds) {
        AndroidElement element = null;
        try {
            element = (AndroidElement) (new WebDriverWait(driver, timeOutInSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        } catch (NoSuchElementException e) {
            e.getStackTrace();
        }
        return element;
    }

    private AndroidElement findElementWithClassNameWithTimeout(String className, int timeOutInSeconds) {
        By by = By.className(className);
        return (AndroidElement) (new WebDriverWait(driver, timeOutInSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    private AndroidElement findElementWithTextWithTimeout(String text, int timeOutInSeconds) {
        By by = By.xpath("/*//*[@text=\"" + text + "\"]");
        return (AndroidElement) (new WebDriverWait(driver, timeOutInSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    private AndroidElement findElementContainsTextWithTimeout(String text, int timeOutInSeconds) {
        By by = By.xpath("/*//*[contains(@text=\"" + text + "\")]");
        return (AndroidElement) (new WebDriverWait(driver, timeOutInSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    private AndroidElement findElementByXpathWhileSwipeDown(String xpath, int loop) {
        AndroidElement element = null;
        for (int i = 0; i < loop; i++) {
            try {
                element = driver.findElementByXPath(xpath);
                if (isElementPresent(element)) break;
            } catch (NoSuchElementException e) {
                swipeUp();
            }
        }
        return element;
    }

    private AndroidElement findElementContainsTextWhileSwipeDown(String text) {

        AndroidElement element = null;
        for (int i = 0; i < maxSwipeCount; i++) {
            try {
                element = driver.findElementByAndroidUIAutomator(
                        "new UiSelector().textContains(\"" + text + "\")");
                if (isElementPresent(element)) break;
            } catch (NoSuchElementException e) {
                swipeDown();
            }
        }

        if (element == null) System.out.println("Failed to find text : " + text);
        return element;

    }

    private AndroidElement findElementByTextWhileSwipeLeft(String text) {

        AndroidElement element = null;
        for (int i = 0; i < maxSwipeCount; i++) {
            try {
                element = findElementWithText(text);
                if (isElementPresent(element)) break;
            } catch (NoSuchElementException e) {
                swipeLeft();
            }
        }

        if (element == null) System.out.println("Failed to find text : " + text);
        return element;
    }

    private AndroidElement findElementContainsTextWhileSwipeLeft(String text) {

        AndroidElement element = null;
        for (int i = 0; i < maxSwipeCount; i++) {
            try {
                element = driver.findElementByAndroidUIAutomator(
                        "new UiSelector().textContains(\"" + text + "\")");
                if (isElementPresent(element)) break;
            } catch (NoSuchElementException e) {
                swipeLeft();
            }
        }

        if (element == null) System.out.println("Failed to find text : " + text);
        return element;

    }

    private AndroidElement findElementByTextWhileSwipeRight(String text) {

        AndroidElement view = null;
        for (int i = 0; i < maxSwipeCount; i++) {
            try {
                view = findElementWithText(text);
                if (isElementPresent(view)) break;
            } catch (NoSuchElementException e) {
                swipeRight();
            }
        }

        if (view == null) System.out.println("Failed to find text : " + text);
        return view;
    }

    private AndroidElement findElementContainsTextWhileSwipeRight(String text) {

        AndroidElement element = null;
        for (int i = 0; i < maxSwipeCount; i++) {
            try {
                element = driver.findElementByAndroidUIAutomator(
                        "new UiSelector().textContains(\"" + text + "\")");
                if (isElementPresent(element)) break;
            } catch (NoSuchElementException e) {
                swipeRight();
            }
        }

        if (element == null) System.out.println("Failed to find text : " + text);
        return element;

    }

    private AndroidElement findViewByTextUsingTranslate(String text) {
        AndroidElement view = null;
        for (int i = 0; i < 5; i++) {
            try {
                view = waitViewInsensitiveCaseText(text);
                if (isElementPresent(view)) break;
            } catch (NoSuchElementException e) {
                swipeUp();
            }
        }
        if (view == null) System.out.println("failed to find text : " + text);
        return view;
    }

    private AndroidElement findViewByTextV4UsingTranslate(String text) {
        AndroidElement view = null;
        for (int i = 0; i <5; i++) {
            try {
                view = waitViewInsensitiveCaseTextV4(text);
                if (isElementPresent(view)) break;
            } catch (NoSuchElementException e) {
                swipeUp();
            }
        }

        if (view == null) System.out.println("failed to find text : " + text);
        return view;
    }

    private String findTextFromSiblingFrameLayoutWithContainsTranslateText(String text) {
        return getTextFromViewByXpathWithTimeOut("//android.widget.TextView[contains(@text, '" + text + "') " +
                "or contains(@text, translate('" + text + "', 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'))]" +
                "/following-sibling::android.widget.FrameLayout/android.widget.TextView", 3);
    }

    private String findTextFromSiblingTextViewThatContainsText(String text) {
        return getTextFromViewByXpath("//android.widget.TextView[contains(@text, '" + text + "')]" +
                "/following-sibling::android.widget.TextView");
    }

    private String findTextFromSiblingTextViewWithContainsTranslateText(String text) {
        try {
            return getTextFromViewByXpathWithTimeOut("//android.widget.TextView[contains(@text, '" + text + "')]" +
                    "/following-sibling::android.widget.TextView", 10);
        } catch (Exception e) {
            return getTextFromViewByXpathWithTimeOut("//android.widget.TextView[contains(@text, translate('" + text + "', " +
                    "'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'))]" +
                    "/following-sibling::android.widget.TextView", 10);
        }
    }

    private String findTextFromSiblingTextViewThatContainTextWithHeader(String header, String title) {
        waitViewInsensitiveCaseTextV4WithTimeOut(title, 5);
        try {
            return getTextFromViewByXpath("//android.widget.TextView[contains(@text,'" + header + "')]" +
                    "/following-sibling::android.widget.TableLayout/android.widget.TableRow" +
                    "/android.widget.TextView[contains(@text,'" + title + "')]/following-sibling::android.widget.TextView");
        } catch (Exception e) {
            return getTextFromViewByXpath("//android.widget.TextView[contains(@text, " +
                    "translate('" + header + "', 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'))]" +
                    "/following-sibling::android.widget.TableLayout/android.widget.TableRow/android.widget.TextView[contains(@text, " +
                    "translate('" + title + "', 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'))]" +
                    "/following-sibling::android.widget.TextView");
        }
    }

    private String findTextFromSearchPageResultWithIndex(int index) {
        return findElementsByXpath("//android.widget.FrameLayout" +
                "/android.widget.RelativeLayout/android.widget.LinearLayout" +
                "/android.widget.TextView[@resource-id='com.bukalapak.android:id/tvTitle']").get(index).getText();
    }

    // Private Wait Text (?)

    private AndroidElement waitViewInsensitiveCaseText(String text) {
        return getElementWithXpathWithTimeout("//*[contains(@text,'" + text + "') or contains(@text, " +
                "translate('" + text + "', 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ')) or " +
                "contains(@text, translate('" + text + "', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'))]", 3);
    }

    private AndroidElement waitViewInsensitiveCaseTextV4(String text) {
        return findElementByXpath("//*[contains(@text,'" + text + "') or contains(@text, " +
                "translate('" + text + "', 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ')) or " +
                "contains(@text, translate('" + text + "', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'))]");
    }

    private AndroidElement waitViewInsensitiveCaseTextV4WithTimeOut(String text, int timeOutInSeconds) {
        try {
            return (AndroidElement) (new WebDriverWait(driver, timeOutInSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/*//*[contains(@text,'" + text + "')]")));
        } catch (Exception e) {
            return (AndroidElement) (new WebDriverWait(driver, timeOutInSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/*//*[contains(@text, " +
                            "translate('" + text + "', 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'))]")));
        }
    }

    //endregion

    //region Private Nominal Function

    // To get Integer value from String format of Rupiah
    private int getIntFromRp(String text) {
        if (text.contains("Rp")) {
            if (text.contains(".")) {
                return Integer.parseInt(text.substring(text.indexOf("p") + 1)
                        .replace(".", "")
                        .trim());
            } else {
                return Integer.parseInt(text.substring(text.indexOf("p") + 1)
                        .trim());
            }
        } else {
            return 0;
        }
    }

    // To get Double value from String format of Rupiah
    private double getDoubleFromRp(String text) {
        if (text.contains("Rp")) {
            if (text.contains(".")) {
                return Double.parseDouble(text.substring(text.indexOf("p") + 1)
                        .replace(".", "")
                        .replace(",", ".")
                        .trim());
            } else {
                return Double.parseDouble(text.substring(text.indexOf("p") + 1)
                        .replace(",", ".")
                        .trim());
            }
        } else {
            return 0;
        }
    }

    private String getRpFromPrice(int money) {
        String moneyString = (String.format("%,d", money)).replace(',', '.');
        return "Rp" + moneyString;
    }

    /**
     * To get Decimal Format places from double value
     *
     * @param locale  the pattern for the default locale.
     * @param nominal the values as a double type data
     * @param scale   the places of decimal behind the comma's
     * @return value with decimal format
     */
    protected String setDecimalFormatPlaces(Locale locale, double nominal, int scale) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        numberFormat.setMaximumFractionDigits(scale);
        return numberFormat.format(nominal);
    }

    /**
     * To rounding up or down the decimal number
     *
     * @param value     is decimal values
     * @param precision is decimal places
     * @param up        boolean condition for round up or down
     * @return decimal value with round format
     */
    protected double round(double value, int precision, boolean up) {
        int scale = (int) Math.pow(10, precision);
        if (up) {
            // true -> round up
            return Math.ceil(value * scale) / scale;
        } else {
            // false -> round down
            return Math.floor(value * scale) / scale;
        }
    }

    //endregion

    //region Global Element Getter Function

    public AndroidElement getElementWithId(String id) {
        return findElementWithId(id);
    }

    public List<AndroidElement> getMultipleElementsWithId(String id) {
        return findElementsWithId(id);
    }

    public AndroidElement getElementWithText(String text) {
        return findElementWithText(text);
    }

    public List<AndroidElement> getMultipleElementsWithText(String text) {
        return findElementsWithText(text);
    }

    public AndroidElement getElementContainsText(String text) {
        return findElementContainsText(text);
    }

    public List<AndroidElement> getMultipleElementsContainsText(String text) {
        return findElementsContainsText(text);
    }

    public AndroidElement getElementWithIdWithTimeout(String id, int timeout) {
        return findElementWithIdWithTimeout(id, timeout);
    }

    public AndroidElement getElementWithTextWithTimeout(String text, int timeout) {
        return findElementWithTextWithTimeout(text, timeout);
    }

    public AndroidElement getElementContainsTextWithTimeout(String text, int timeout) {
        return findElementContainsTextWithTimeout(text, timeout);
    }

    public AndroidElement getElementWithXpathWithTimeout(String xpath, int timeout) {
        return findElementWithXpathWithTimeout(xpath, timeout);
    }

    public AndroidElement getElementWithClassNameWithTimeout(String className, int timeout) {
        return findElementWithClassNameWithTimeout(className, timeout);
    }

    public AndroidElement getElementByXpathWhileSwipeDown(String xpath, int loop) {
        return findElementByXpathWhileSwipeDown(xpath, loop);
    }

    public AndroidElement getElementByTextWhileSwipeDown(String text) {
        return findElementWithText(text);
    }

    public AndroidElement getElementContainsTextWhileSwipeDown(String text) {
        return findElementContainsTextWhileSwipeDown(text);
    }

    public AndroidElement getElementByTextWhileSwipeLeft(String text) {
        return findElementByTextWhileSwipeLeft(text);
    }

    public AndroidElement getElementContainsTextWhileSwipeLeft(String text) {
        return findElementContainsTextWhileSwipeLeft(text);
    }

    public AndroidElement getElementByTextWhileSwipeRight(String text) {
        return findElementByTextWhileSwipeRight(text);
    }

    public AndroidElement getElementContainsTextWhileSwipeRight(String text) {
        return findElementContainsTextWhileSwipeRight(text);
    }

    //endregion

    //region Global Atribute Getter Function

    public Boolean getCheckedAttributeFromViewWithId(String id) {
        return Boolean.valueOf(
                findElementWithId(id).getAttribute("checked")
        );
    }

    public Boolean getCheckedAttributeFromViewWithIdWithIndex(String id, int index) {
        return Boolean.valueOf(
                findElementsWithId(id).get(index).getAttribute("checked")
        );
    }

    //endregion

    //region Global Element Checker Function

    public boolean isElementPresent(AndroidElement androidElement) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOf(androidElement));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementPresent(AndroidElement androidElement, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOut);
            wait.until(ExpectedConditions.visibilityOf(androidElement));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementPresentByXpath(String xpath, int timeOut) {
        By by = By.xpath(xpath);
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOut);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementPresentById(String id, int timeOut) {
        By by = By.id(id);
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOut);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementPresentByText(String text, int timeOut) {
        By by = By.xpath("/*//*[@text=\"" + text + "\"]");
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOut);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementPresentContainsText(String text, int timeOut) {
        By by = By.xpath("/*//*[contains(@text, '" + text + "')]");
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOut);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementPresentByTranslationText(String text, int timeOut) {
        By by = By.xpath("//*[contains(@text,'" + text + "') or contains(@text, " +
                "translate('" + text + "', 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'))]");
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOut);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //endregion

    //region Global Text Getter Function

    public String getTextFromViewWithId(String id) {
        return findElementWithId(id).getText();
    }

    public String getTextFromViewWithIdOnIndex(String id, int index) {
        return findElementsWithId(id).get(index).getText();
    }

    public String getTextFromViewWithIdOnIndexUsingXpath(String title, int index) {
        return findElementsUsingXpath(title).get(index).getText();
    }

    public String getTextFromViewWithIdOnLastIndexUsingXpath(String title) {
        List<AndroidElement> elements = findElementsUsingXpath(title);
        return findElementsUsingXpath(title).get(elements.size() - 1).getText();
    }

    public String getTextFromViewWithIdWithTimeOut(String id, int timeout) {
        return findElementWithIdWithTimeout(id, timeout).getText();
    }

    public String getTextFromViewByXpath(String xpath) {
        return findElementByXpath(xpath).getText();
    }

    public String getTextFromViewXpathWithIndex(String xpath, int index) {
        return findElementsWithXpath(xpath).get(index).getText();
    }

    public String getTextFromViewByXpathWithTimeOut(String xpath, int timeout) {
        return findElementWithXpathWithTimeout(xpath, timeout).getText();
    }

    public String getTextFromViewByXpathWhileSwipeDown(String xpath, int loop) {
        return findElementByXpathWhileSwipeDown(xpath, loop).getText();
    }

    public String getTextFromViewByClassName(String className) {
        return findElementByClassName(className).getText();
    }

    public String getTextFromViewByClassNameWithTimeOut(String className, int timeout) {
        return findElementWithClassNameWithTimeout(className, timeout).getText();
    }

    public String getTextFromSiblingFrameLayoutWithContainsText(String text) {
        return findTextFromSiblingFrameLayoutWithContainsTranslateText(text);
    }

    public String getTextFromSiblingTextViewThatContainsText(String text) {
        findElementContainsText(text);
        return findTextFromSiblingTextViewThatContainsText(text);
    }

    public String getTextFromSiblingTextViewWithContainsTranslateText(String text) {
        return findTextFromSiblingTextViewWithContainsTranslateText(text);
    }

    public String getTextFromSiblingTextViewThatContainTextWithHeader(String header, String title) {
        findElementContainsText(title);
        return findTextFromSiblingTextViewThatContainTextWithHeader(header, title);
    }

    public String getProductNameFromSearchResultWithIndex(int index) {
        return findTextFromSearchPageResultWithIndex(index);
    }

    public String getRpFromNumber(int text) {
        return getRpFromPrice(text);
    }

    public int getIntNumberFromRp(String text) {
        return getIntFromRp(text);
    }

    public double getDoubleNumberFromRp(String text) {
        return getDoubleFromRp(text);
    }

    public int getIntFromAlphanumeric(String text) {
        text = text.replaceAll("[^\\d]", StringUtils.EMPTY);
        return (Integer.parseInt(text));
    }

    //endregion

    //region Global Tap Function
    public void tapViewWithXpath(String xpath) {
        findElementWithXpath(xpath).click();
    }

    public void tapViewWithXpathAndTimeOut(String xpath, int timeout) {
        findElementWithXpathWithTimeout(xpath, timeout);
    }

    public void tapViewWithId(String id) {
        findElementWithId(id).click();
    }

    public void tapViewWithIdOnIndex(String id, int index) {
        findElementsWithId(id).get(index).click();
    }

    public void tapViewWithIdAndTimeOut(String id, int timeout) {
        findElementWithIdWithTimeout(id, timeout).click();
    }

    public void tapViewWithText(String text) {
        findElementWithText(text).click();
    }

    public void tapViewWithTextOnIndex(String text, int index) {
        findElementsWithText(text).get(index).click();
    }

    public void tapViewWithTextAndTimeOut(String text, int timeout) {
        findElementWithTextWithTimeout(text, timeout).click();
    }

    public void tapViewContainsText(String text) {
        findElementContainsText(text).click();
    }

    public void tapViewContainsText(String text, int loop) {
        findElementContainsText(text, loop).click();
    }

    public void tapViewContainsTextWhileSwipeDown(String text) {
        findElementContainsTextWhileSwipeDown(text).click();
    }

    public void tapViewContainsTextWhileSwipeLeft(String text) {
        findElementContainsTextWhileSwipeLeft(text).click();
    }

    public void tapViewContainsTextWhileSwipeRight(String text) {
        findElementContainsTextWhileSwipeRight(text).click();
    }

    public void tapViewContainsTextOnIndex(String text, int index) {
        findElementsContainsText(text).get(index).click();
    }

    public void tapViewContainsTextAndTimeOut(String text, int timeout) {
        findElementContainsTextWithTimeout(text, timeout).click();
    }

    public void tapViewByPackageIdAndTimeOut(String id, int timeout) {
        findElementWithIdWithTimeout(id, timeout).click();
    }

    public void longTapOnElement(AndroidElement element) {
        TouchAction action = new TouchAction(driver);
        action.longPress(new ElementOption().withElement(element)).release().perform();
    }

    public void tapUsingLocation(Point location, Duration time) {
        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(new PointOption().withCoordinates(location.x, location.y)).waitAction(new WaitOptions().withDuration(time)).release().perform();
    }

    public void tapViewWithTranslationText(String text) {
        findViewByTextUsingTranslate(text).click();
    }

    public void tapViewWithIdOnIndexUsingXpath(String title, int index) {
        findElementWithId(title);
        findElementsUsingXpath(title).get(index).click();
    }

    //endregion

    //region Global Type Function

    public void typeTextOnViewWithId(String id, String value) {
        AndroidElement element = findElementWithId(id);
        element.click();
        element.clear();
        element.sendKeys(value);
    }

    public void typeTextOnViewWithIdAndEnter(String id, String value) {
        AndroidElement element = findElementWithId(id);
        element.click();
        element.clear();
        element.sendKeys(value);
        pressEnterButton();
    }

    public void typeTextOnViewWithIdOnIndex(String id, int index, String value) {
        AndroidElement fields = getMultipleElementsWithId(id).get(index);
        fields.click();
        fields.clear();
        fields.sendKeys(value);
    }

    public void typeTextOnViewWithIdOnIndexAndEnter(String id, int index, String value) {
        AndroidElement fields = getMultipleElementsWithId(id).get(index);
        fields.click();
        fields.clear();
        fields.sendKeys(value);
        pressEnterButton();
    }

    public void typeTextOnViewWithXpath(String xpath, String value) {
        if (!findElementWithXpath(xpath).getText().equals("")) {
            findElementWithXpath(xpath).clear();
        }
        // Enter the text
        findElementWithXpath(xpath).sendKeys(value);
    }

    public void typeTextOnViewWithXpathAndEnter(String xpath, String value) {
        if (!findElementWithXpath(xpath).getText().equals("")) {
            findElementWithXpath(xpath).clear();
        }
        // Enter the text
        findElementWithXpath(xpath).sendKeys(value);
        hideKeyboard();
    }

    public void typeTextOnViewWithTitleUsingXpath(String title, String text) {
        try {
            AndroidElement element = driver.findElement(
                    By.xpath("//android.widget.TextView[contains(@text,'" + title + "')]" +
                            "/following-sibling::android.widget.LinearLayout/android.widget.LinearLayout" +
                            "/android.widget.FrameLayout/android.widget.EditText"));
            element.click();
            element.clear();
            element.sendKeys(text);
            hideKeyboard();
        } catch (Exception e) {
            AndroidElement element = driver.findElement(
                    By.xpath("//android.widget.TextView[contains(@text, " +
                            "translate('" + title + "', 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'))]" +
                            "/following-sibling::android.widget.LinearLayout/android.widget.LinearLayout" +
                            "/android.widget.FrameLayout/android.widget.EditText"));
            element.click();
            element.clear();
            element.sendKeys(text);
            hideKeyboard();
        }
    }

    //endregion

    //region Global Spinner Function

    public void chooseItemInsideSpinnerWithId(String id, String item) {
        tapViewWithIdAndTimeOut(id, 10);
        findElementWithTextWithTimeout(item, 10).click();
    }

    //endregion

    //region Global Keyboard Function

    public void pressEnterButton() {
        driver.pressKey(new KeyEvent().withKey(AndroidKey.ENTER));
    }

    public void pressSearchButton() {
        driver.pressKey(new KeyEvent().withKey(AndroidKey.SEARCH));
    }

    public void pressBackButton() {
        driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }

    public void hideKeyboard() {
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
            System.out.println("Keyboard already hide");
        }
    }

    //endregion

    //region Global Screenshot Function

    public void takeScreenshot(String screenshotName) {
        if (isScreenshotEnabled) {
            delay(500);
            File srcFile = driver.getScreenshotAs(OutputType.FILE);
            File targetFile = new File("/Users/bukalapak/Documents/Screenshot/" + screenshotName + ".jpg");
            try {
                FileUtils.copyFile(srcFile, targetFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //endregion

    //region Global Checker Function

    public void checkFirstAppLoad() {
        isElementPresentById("tv_see_all_banner", 60);
        checkShowCaseButton();
        checkShowCaseButton();
        checkShowCaseButton();
    }

    public void checkShowCaseButton() {
        if (isElementPresentById("button_showcase", 5)) {
            try {
                tapViewWithId("button_showcase");
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
        }
    }

    public void checkShowCaseMenuFavorit() {
        if (isElementPresentByText("Oke", 5)) {
            try {
                tapViewWithText("Oke");
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
        }
    }

    public String getPageHeaderText() {
        return getTextFromViewWithIdWithTimeOut("toolbar_main_view", 5);
    }

    //endregion

    //region Global Navigation Function

    public void swipeToViewWithId(String id) {
        findElementWithId(id);
    }

    public void swipeToViewWithText(String text) {
        findElementWithText(text);
    }

    public void swipeToViewWithTranslationText(String text) {
        findViewByTextV4UsingTranslate(text);
    }

    public void swipe(double xStart, double xEnd, double yStart, double yEnd) {

        Dimension size = driver.manage().window().getSize();

        int x0 = (int) (size.width * xStart);
        int x1 = (int) (size.width * xEnd);
        int y0 = (int) (size.height * yStart);
        int y1 = (int) (size.height * yEnd);

        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(new PointOption().withCoordinates(x0, y0)).waitAction(new WaitOptions().withDuration(Duration.ofSeconds(1))).moveTo(new PointOption().withCoordinates(x1, y1)).release().perform();
    }

    public void swipeUp() {

        Dimension size = driver.manage().window().getSize();

        int y0 = (int) (size.height * 0.7);
        int y1 = (int) (size.height * 0.3);
        int x = (size.width / 2);

        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(new PointOption().withCoordinates(x, y0)).waitAction(new WaitOptions().withDuration(Duration.ofSeconds(1))).moveTo(new PointOption().withCoordinates(x, y1)).release().perform();
    }

    public void swipeUp(float yStart, float yEnd) {

        Dimension size = driver.manage().window().getSize();

        int y0 = (int) (size.height * yStart);
        int y1 = (int) (size.height * yEnd);
        int x = (size.width / 2);

        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(new PointOption().withCoordinates(x, y0)).waitAction(new WaitOptions().withDuration(Duration.ofSeconds(1))).moveTo(new PointOption().withCoordinates(x, y1)).release().perform();
    }


    public void swipeDown() {

        Dimension size = driver.manage().window().getSize();

        int y0 = (int) (size.height * 0.7);
        int y1 = (int) (size.height * 0.3);
        int x = (size.width / 2);

        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(new PointOption().withCoordinates(x, y1)).waitAction(new WaitOptions().withDuration(Duration.ofSeconds(1))).moveTo(new PointOption().withCoordinates(x, y0)).release().perform();
    }

    public void swipeDown(double yStart, double yEnd) {

        Dimension size = driver.manage().window().getSize();

        int y0 = (int) (size.height * yEnd);
        int y1 = (int) (size.height * yStart);
        int x = (size.width / 2);

        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(new PointOption().withCoordinates(x, y1)).waitAction(new WaitOptions().withDuration(Duration.ofSeconds(1))).moveTo(new PointOption().withCoordinates(x, y0)).release().perform();
    }

    public void swipeLeft() {

        Dimension size = driver.manage().window().getSize();

        int x0 = (int) (size.width * 0.8);
        int x1 = (int) (size.width * 0.2);
        int y = (size.height / 2);

        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(new PointOption().withCoordinates(x0, y)).waitAction(new WaitOptions().withDuration(Duration.ofSeconds(1))).moveTo(new PointOption().withCoordinates(x1, y)).release().perform();
    }

    public void swipeLeft(double xStart, double xEnd) {

        Dimension size = driver.manage().window().getSize();

        int x0 = (int) (size.width * xStart);
        int x1 = (int) (size.width * xEnd);
        int y = (size.height / 2);

        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(new PointOption().withCoordinates(x0, y)).waitAction(new WaitOptions().withDuration(Duration.ofSeconds(1))).moveTo(new PointOption().withCoordinates(x1, y)).release().perform();
    }

    public void swipeRight() {

        Dimension size = driver.manage().window().getSize();

        int x0 = (int) (size.width * 0.8);
        int x1 = (int) (size.width * 0.2);
        int y = (size.height / 2);

        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(new PointOption().withCoordinates(x1, y)).waitAction(new WaitOptions().withDuration(Duration.ofSeconds(1))).moveTo(new PointOption().withCoordinates(x0, y)).release().perform();
    }

    public void swipeRight(double xStart, double xEnd) {

        Dimension size = driver.manage().window().getSize();

        int x0 = (int) (size.width * xEnd);
        int x1 = (int) (size.width * xStart);
        int y = (size.height / 2);

        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(new PointOption().withCoordinates(x1, y)).waitAction(new WaitOptions().withDuration(Duration.ofSeconds(1))).moveTo(new PointOption().withCoordinates(x0, y)).release().perform();
    }

    public void goToDeeplink(String deeplink) {
        driver.get(deeplink);
    }

    public void goToHomeTab() {
        for (int i = 0; i < 5; i++) {
            try {
                tapViewWithIdAndTimeOut("bottombartab_home", 1);
                break;
            } catch (Exception e) {
                pressBackButton();
            }
        }
    }

    public void goToTransaksiTab() {
        for (int i = 0; i < 5; i++) {
            try {
                tapViewWithIdAndTimeOut("bottombartab_transaction", 1);
                break;
            } catch (Exception e) {
                pressBackButton();
            }
        }
    }

    public void goToFavoriteTab() {
        for (int i = 0; i < 5; i++) {
            try {
                tapViewWithIdAndTimeOut("bottombartab_favorite", 1);
                break;
            } catch (Exception e) {
                pressBackButton();
            }
        }
    }

    public void goToCartTab() {
        for (int i = 0; i < 5; i++) {
            try {
                tapViewWithIdAndTimeOut("bottombartab_cart", 1);
                break;
            } catch (Exception e) {
                pressBackButton();
            }
        }
    }

    public void goToProfileTab() {
        for (int i = 0; i < 5; i++) {
            try {
                tapViewWithIdAndTimeOut("bottombartab_profile", 1);
                break;
            } catch (Exception e) {
                pressBackButton();
            }
        }
    }

    public void goToChatTab() {
        for (int i = 0; i < 5; i++) {
            try {
                tapViewWithIdAndTimeOut("bottombartab_chat", 1);
                break;
            } catch (Exception e) {
                pressBackButton();
            }
        }
    }

    //endregion

    //region Global Dialog Function

    public void chooseConfirmDialog(boolean isOk) {
        if (isOk) {
            tapViewWithIdAndTimeOut("android:id/button1", 10);
        } else {
            tapViewWithIdAndTimeOut("android:id/button2", 10);
        }
    }

    //endregion

    //region Global Wait Function

    public void delay(long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitProgressBar() {
        for (int i = 0; i < 5; i++) {
            delay(1000);
            try {
                AndroidElement progressBar = findElementWithId("progressbar_loading_image");
                System.out.println("loading...");
            } catch (NoSuchElementException e) {
                break; //finished loading
            }
        }
    }

    public void waitLoadProcess(String loadProcessId) {
        for (int i = 0; i < 60; i++) {
            delay(1000);
            try {
                AndroidElement progressBar = getElementWithIdWithTimeout(loadProcessId, 10);
                System.out.println("loading...");
            } catch (NoSuchElementException e) {
                break; //finished loading
            }
        }
    }

    //endregion

    //region Global Hard Refresh

    public void doHardRefresh(int refreshCount) {
        for (int i = 1; i <= refreshCount; i++) {
            delay(2000);
            swipeDown();
            delay(2000);
        }
    }



    //endregion

    //region Global Image Function

    public void captureImageFromCamera() {
        try {
            tapViewByPackageIdAndTimeOut("com.android.camera:id/shutter_button", 10);
            tapViewByPackageIdAndTimeOut("com.android.camera:id/done_button", 10);
        } catch (Exception e) {
            tapViewByPackageIdAndTimeOut("com.android.camera2:id/shutter_button", 10);
            tapViewByPackageIdAndTimeOut("com.android.camera2:id/done_button", 10);
        }
    }

    public void selectImageThumbnail() {
        findElementByClassName("android.widget.ImageView").click();
    }

    //endregion

    //region Global Utility

    public void resetApp() {
        driver.resetApp();
    }

    public String getRandom(String[] array) {
        int random = new Random().nextInt(array.length);
        return array[random];
    }

    public void setDriverLocation(double latitude, double longitude, double altitude) {
        Location location = new Location(latitude, longitude, altitude);
        driver.setLocation(location);
    }

    public int getTotalTagihanByText(String title) {
        int totalTagihan;
        try {
            totalTagihan = getIntNumberFromRp(driver.findElement(By.xpath("//*[@text='" + title + "']" +
                    "/following-sibling::android.widget.TextView[contains(@text,'Rp')]")).getText());
        } catch (Exception e) {
            totalTagihan = getIntNumberFromRp(driver.findElement(By.xpath("//*[contains(@text, " +
                    "translate('" + title + "', 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'))]" +
                    "/following-sibling::android.widget.TextView[contains(@text,'Rp')]")).getText());
        }
        return totalTagihan;
    }
    //endregion


    //region check tracker
    public boolean verifiesTDTrackerSent(Map<String, String> params) {
        List<LogEntry> logEntries = driver.manage().logs().get("logcat").getAll();
        System.out.println("Logcat Entries: \n" + logEntries.toString());
        for (LogEntry logEntry : logEntries) {
            int paramsVerified = 0;
            for (Map.Entry<String, String> param : params.entrySet()) {
                System.out.println(param.toString());
                if (logEntry.toString().contains("\"" + param.getKey() + "\":\"" + param.getValue())) {
                    paramsVerified++;
                    if (paramsVerified == params.size())
                        System.out.println("Tracker found -> " + "\"" + param.getKey() + "\":\"" + param.getValue());
                    return true;
                }
                System.out.println("Tracker not found -> " + "\"" + param.getKey() + "\":\"" + param.getValue());
            }
        }
        return false;
    }
    //endregion

    //region Global Context Changer
    public void changeContextToWebView() {
        Set<String> contextHandles = driver.getContextHandles();

        for (String s : contextHandles) {
            System.out.println("Context : " + s);
            if (s.contains("WEBVIEW")) {
                driver.context(s);
            }
        }
    }

    public void changeContextToNative() {
        Set<String> contextHandles = driver.getContextHandles();

        for (String s : contextHandles) {
            System.out.println("Context : " + s);
            if (s.contains("NATIVE_APP")) {
                driver.context(s);
            }
        }
    }
    //endregion
}
