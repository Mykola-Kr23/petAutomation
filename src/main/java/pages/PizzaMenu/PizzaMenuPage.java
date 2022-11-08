package pages.PizzaMenu;

import common.testOrder.OrderItem;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.base.BasePage;
import pages.dominosUkDelivery.DeliveryPage;

import java.util.List;

public class PizzaMenuPage extends BasePage {

    @FindBy(xpath = "//div[@class='dp-product-block__toppings-row']")
    private List<WebElement> orderCardLocator;
    @FindBy(xpath = "//button[@class='button-cart__order-button']")
    private WebElement toDeliveryPageButton;
    @FindBy(xpath = "//span[@class='fake-header__city-name']")
    private WebElement localizationSelector;

    public PizzaMenuPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    private int getNumberOfPizzaCards(){
        return orderCardLocator.size();
    }
    public PizzaOrderCard selectPizzaCard(int id){
        return new PizzaOrderCard(driver, id);
    }
    public PizzaOrderCard selectPizzaCard(String random){
        int id = 0;
        if (random.equals("random")){
            id = (int) (Math.random()*getNumberOfPizzaCards());
        }
        return new PizzaOrderCard(driver, id);
    }
    @Step("Go to Delivery Page")
    public DeliveryPage goToDeliveryPage(){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click()", toDeliveryPageButton);
        return new DeliveryPage(driver);
    }
    @Step("Add selected pizza: {pizza.name} to order list by button")
    public PizzaMenuPage addPizzaToOrderList(OrderItem pizza) throws InterruptedException {
        PizzaOrderCard.toOrderButtonClick(pizza.getName());
        Thread.sleep(300);
        return this;
    }
    @Step("Select Local: {0}")
    public PizzaMenuPage selectLocalization(String local){
        localizationSelector.click();
        switch (local){
            case "chornomorsk":
                driver.findElement(By.xpath("//div[text()='Чорноморськ']")).click();
                break;
            case "odesa":
                driver.findElement(By.xpath("//div[text()='Одеса']")).click();
                break;
            default:
                driver.findElement(By.xpath("//div[text()='Київ']")).click();
                break;
        }
        return this;
    }
}
