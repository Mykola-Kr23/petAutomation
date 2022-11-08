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
import pages.changeToppings.ChangeToppingsPage;

import java.util.List;

public class PizzaOrderCard extends BasePage {
    private int idCard;
    private String name;
    private String toppings;

    @FindBy(xpath = "//a[@class='dp-product-block__title-text']")
    private List<WebElement> nameLocators;
    @FindBy(xpath = "//div[@class='dp-product-block__toppings-row']/span[1]")
    private List<WebElement> toppingsLocators;

    public PizzaOrderCard(WebDriver driver, int idCard) {
        super(driver);
        this.idCard = idCard;
        PageFactory.initElements(driver, this);
    }

    public OrderItem getOrderItem(){
        OrderItem Pizza = new OrderItem(this.idCard, this.getName(), this.getToppings());
        return Pizza;
    }
    public int getIdCard() {
        return idCard;
    }

    public String getName(){
        this.name = nameLocators.get(idCard).getText();
        return name;
    }
    public String getToppings(){
        this.toppings = toppingsLocators.get(idCard).getText();
        return toppings;
    }
    public static void toOrderButtonClick(String pizzaName){
        WebElement Button = waitElementIsClickable(By.xpath("//div[@class='dp-product-block__title-row']/a[text()=" + '"' + pizzaName + '"' + "]/../..//button[@class='dp-product-block__cart-button']"));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click()", Button);
        // '"' - to skip apostrophe in some pizza name.
    }
    @Step("Select any pizza and use option 'Change Toppings'")
    public static ChangeToppingsPage changeToppingsButtonClick(String pizzaName){
        WebElement Button = waitElementIsClickable(By.xpath("//div[@class='dp-product-block__title-row']/a[text()=" + '"' + pizzaName + '"' + "]/../../div[@class='dp-product-block__toppings-row']/span[2]/a"));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click()", Button);
        return new ChangeToppingsPage(driver);
    }
}



