package pages.dominosUkDelivery;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.base.BasePage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static constants.Constant.TimeoutVariable.IMPLICIT_WAIT;

public class DeliveryPage extends BasePage {

    @FindBy (xpath = "//div[@class='order-card-item__title']")
    private List<WebElement> pizzaNameInOrderList;
    @FindBy (xpath = "//div[@class='order-card-item__toppings']/p/span")
    private List<WebElement> toppingsInOrderList;
    /** Delivery info fields */
    @FindBy(xpath = "//input[@name='phone_num']")
    private WebElement phoneField;
    @FindBy(xpath = "//input[@name='phone_num']/../../../../div[@class='checkout-form-grid__error-message']/p")
    private WebElement phoneFieldErrorLabel;
    @FindBy(xpath = "//input[@name='email']")
    private WebElement emailField;
    @FindBy(xpath = "//input[@name='email']/../../../div[@class='checkout-form-grid__error-message']/p")
    private WebElement emailFieldErrorlabel;
    @FindBy(xpath = "//input[@id='react-select-2-input']")
    private WebElement streetField;
    @FindBy(xpath = "//input[@name='street_id']/../../../../div[@class='checkout-form-grid__error-message']/p")
    private WebElement streetSelectErrorLabel;
    @FindBy(xpath = "//input[@name='street_number']")
    private WebElement streetNumberField;
    @FindBy(xpath = "//input[@name='street_number']/../../../div[@class='checkout-form-grid__error-message']/p")
    private WebElement streetNumberFiledErrorLabel;
    @FindBy(xpath = "//input[@name='payment_type']")
    private WebElement paymentTypeSelector;
    @FindBy(xpath = "//div[@class='payment-grid__cell checkout-pay__payment-type']/div[@class='payment-grid__error-message']/p")
    private WebElement paymentTypeSelectErrorLabel;
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
        new WebDriverWait(driver, Duration.ofSeconds(IMPLICIT_WAIT)).until(ExpectedConditions.attributeToBe(confirmOrderButton, "disabled", ""));
        return this;
    }
    public DeliveryPage clickField(String field) {
        switch (field){
            case "phone":
                phoneField.click();
                break;
            case "email":
                emailField.click();
                break;
            case "street":
                streetField.click();
                break;
            case "streetNumber":
                streetNumberField.click();
                break;
            case "paymentType":
                paymentTypeSelector.click();
                break;
        }
        return this;
    }
    public DeliveryPage setPhoneNumber(String number){
        phoneField.sendKeys(number);
        return this;
    }
    public DeliveryPage setEmail(String email){
        emailField.sendKeys(email);
        return this;
    }
    public DeliveryPage setStreet(String street){
        streetField.sendKeys(street);
        return this;
    }
    public DeliveryPage setStreerNumber(String streetNumber){
        streetNumberField.sendKeys(streetNumber);
        return this;
    }
    public DeliveryPage selectStreetFromDropDown(int pos){
        int i = 0;
        while (i < pos){
            streetField.sendKeys(Keys.ARROW_DOWN);
            i++;
        }
        streetField.sendKeys(Keys.ENTER);
        return this;
    }
    public String getCurrentNumber(){
        return phoneField.getAttribute("value");
    }
    public String getErrorMessage(String field){
        String actualMessage = null;
            switch (field){
                case "phone":
                    actualMessage = phoneFieldErrorLabel.getText();
                    break;
                case "email":
                    actualMessage = emailFieldErrorlabel.getText();
                    break;
                case "street":
                    actualMessage = streetSelectErrorLabel.getText();
                    break;
                case "streetNumber":
                    actualMessage = streetNumberFiledErrorLabel.getText();
                    break;
                case "paymentType":
                    actualMessage = paymentTypeSelectErrorLabel.getText();
                    break;
            }
            return actualMessage;
        }
    public DeliveryPage clearField(String field) {
        switch (field){
            case "phone":
                phoneField.sendKeys(Keys.CONTROL + "a");
                phoneField.sendKeys(Keys.DELETE);
                break;
            case "email":
                emailField.sendKeys(Keys.CONTROL + "a");
                emailField.sendKeys(Keys.DELETE);
                break;
            case "street":
                streetField.sendKeys(Keys.CONTROL + "a");
                streetField.sendKeys(Keys.DELETE);
                break;
            case "streetNumber":
                streetNumberField.sendKeys(Keys.CONTROL + "a");
                streetNumberField.sendKeys(Keys.DELETE);
                break;
        }
        return this;
    }
}

