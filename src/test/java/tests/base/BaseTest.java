package tests.base;

import common.CommonActions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import pages.dominosUkDelivery.DeliveryPage;
import pages.PizzaMenu.PizzaMenuPage;

import java.time.Duration;

import static common.Config.CLEAR_COOKIES_AND_STORAGE;
import static common.Config.HOLD_BROWSER_OPEN;
import static constants.Constant.TimeoutVariable.EXPLICIT_WAIT;

abstract public class BaseTest {
    public static WebDriver driver = CommonActions.createDriver();
    protected PizzaMenuPage pizzaMenuPage = new PizzaMenuPage(driver);

    @AfterClass
    public void clearCookiesAndLocalStorage(){
        if (CLEAR_COOKIES_AND_STORAGE) {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            driver.manage().deleteAllCookies();
            javascriptExecutor.executeScript("window.sessionStorage.clear()");
        }
    }
    @AfterSuite
    public void close() {
        if (HOLD_BROWSER_OPEN) {
            driver.quit();
        }
    }
}
