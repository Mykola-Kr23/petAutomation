package pages.dominosUkDelivery;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import pages.base.BasePage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeliveryPage extends BasePage {

    @FindBy (xpath = "//div[@class='order-card-item__title']")
    private List<WebElement> pizzaNameInOrderList;
    @FindBy (xpath = "//div[@class='order-card-item__toppings']/p/span")
    private List<WebElement> toppingsInOrderList;
    /** Delivery info fields */
    @FindBy(xpath = "//input[@name='phone_num']")
    public WebElement phoneField;
    @FindBy(xpath = "//input[@name='phone_num']/../../../../div[@class='checkout-form-grid__error-message']/p")
    public WebElement phoneFieldErrorLabel;
    @FindBy(xpath = "//input[@name='email']")
    public WebElement emailField;
    @FindBy(xpath = "//input[@name='email']/../../../div[@class='checkout-form-grid__error-message']/p")
    public WebElement emailFieldErrorlabel;
    @FindBy(xpath = "//input[@id='react-select-2-input']")
    public WebElement streetSelect;
    @FindBy(xpath = "//input[@name='street_id']/../../../../div[@class='checkout-form-grid__error-message']/p")
    public WebElement streetSelectErrorLabel;
    @FindBy(xpath = "//input[@name='street_number']")
    public WebElement streetNumberField;
    @FindBy(xpath = "//input[@name='street_number']/../../../div[@class='checkout-form-grid__error-message']/p")
    public WebElement streetNumberFiledErrorLabel;
    @FindBy(xpath = "//input[@name='payment_type']")
    public WebElement paymentTypeSelect;
    @FindBy(xpath = "//div[@class='payment-grid__cell checkout-pay__payment-type']/div[@class='payment-grid__error-message']/p")
    public WebElement paymentTypeSelectErrorLabel;
    @FindBy(xpath = "//button[@class='checkout-pay__order-button']")
    private WebElement confirmOrderButton;

    public DeliveryPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String getNameOfOrderedPizza(int id){
        return pizzaNameInOrderList.get(id).getText();
    }
    public String getToppingsOfOrderedPizza(int idInOrderList){
        String pizzaName = getNameOfOrderedPizza(idInOrderList);
        int cycle = driver.findElements(By.xpath("//div[text()=" + '"' + pizzaName + '"' + "]/../div[@class='order-card-item__toppings']/p/span")).size();
        List<String> toppingSet = new ArrayList<String>();
        String toppings;
        for (int i = 0; i < cycle; i++){
            toppingSet.add(toppingsInOrderList.get(i).getAttribute("textContent").trim());
        }
        toppingSet = toppingSet.stream().sorted().collect(Collectors.toList());
        toppings = String.join(", ", toppingSet);
        return toppings;
    }
    @Step("Checking that selected toppings and toppings in the order list - are the same")
    public DeliveryPage checkToppings(int id, String expectedToppings){
        Assert.assertEquals(getToppingsOfOrderedPizza(id), expectedToppings);
        return this;
    }
    @Step("Checking that selected position and position in the order list - are the same")
    public DeliveryPage checkNameOfPizza(int id, String expectedName){
        Assert.assertEquals(getNameOfOrderedPizza(id), expectedName);
        return this;
    }
    public DeliveryPage confirmOrder(){
        confirmOrderButton.click();
        return this;
    }
    public DeliveryPage sleep() throws InterruptedException {
        Thread.sleep(300);
        return this;
    }

    public WebElement getPhoneField() {
        return phoneField;
    }
    public String getPhoneFieldErrorMessage() {
        return phoneFieldErrorLabel.getText();
    }
    public DeliveryPage clearPhoneField() {
        phoneField.sendKeys(Keys.CONTROL + "a");
        phoneField.sendKeys(Keys.DELETE);
        return this;
    }
}

