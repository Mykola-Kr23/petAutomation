package pages.base;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.PizzaMenu.PizzaMenuPage;

import java.time.Duration;

import static constants.Constant.TimeoutVariable.EXPLICIT_WAIT;
import static constants.Constant.Urls.DOMINOS_UK_PIZZA_MENU_PAGE;

abstract public class BasePage {
    public static WebDriver driver;
    public BasePage(WebDriver driver){
        this.driver = driver;
    }

    @Step("Open web page {0}")
    public BasePage open(String url){
        Object page = new Object();
        driver.get(url);
        switch (url){
            case DOMINOS_UK_PIZZA_MENU_PAGE:
                page = new PizzaMenuPage(driver);
                break;
        }
        return (BasePage) page;
    }

    public BasePage refresh(){
        driver.navigate().refresh();
        return this;
    }
    public static WebElement waitElementIsClickable(By locator){
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT)).until(ExpectedConditions.elementToBeClickable(locator));
        return element;
    }
    public static WebElement waitElementIsVisible(By locator){
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT)).until(ExpectedConditions.presenceOfElementLocated(locator));
        return element;
    }
}
