package tests.orderForm;

import common.testOrder.OrderItem;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.dominosUkDelivery.DeliveryPage;
import tests.base.BaseTest;

import static constants.Constant.Urls.DOMINOS_UK_PIZZA_MENU_PAGE;

public class OrderFormTest extends BaseTest {
    DeliveryPage deliveryPage = new DeliveryPage(driver);

    @BeforeClass (description = "add 1 item to order list and open delivery page")
    void precondition() throws InterruptedException {
        pizzaMenuPage.open(DOMINOS_UK_PIZZA_MENU_PAGE).refresh();
        pizzaMenuPage.addPizzaToOrderList(pizzaMenuPage.selectPizzaCard("random").getOrderItem())
                .goToDeliveryPage();
    }

    @Test (description = "Phone number text box testing", priority = 0)
    @Severity(SeverityLevel.BLOCKER)
    void phoneInputFieldTest() throws InterruptedException {
        phoneIsEmpty();
        phoneShowMask();
        phoneNoNumberInput();
        phoneZeroNumberInput();
        phoneIncompleteInput();
        phoneMaximumLengthInput();
        phoneValidNumberInput();
        //clearPhoneField();
    }
    @Test (description = "Email text box testing", priority = 1)
    void emailInputFieldTest() throws InterruptedException {
        boolean validError = false;
        boolean invalidError = false;
        emailIsEmpty();
        validError = validEmailInput();
        invalidError = invalidEmailInput();
        Assert.assertTrue(validError & invalidError);
    }
    @Test (description = "Street select testing", priority = 2)
    void streetInputFieldTest() throws InterruptedException {
        streetIsEmpty();
        streetInput("Миру","Миру",true);
        driver.navigate().refresh();
        streetInput("abr", "Абр", true);
        driver.navigate().refresh();
        streetInput("123","123", false);
    }
    @Test (description = "Street Number Field testing", priority = 3)
    void streetNumberFiledTest() throws InterruptedException {
        streetNumberIsEmpty();
        streetNumberMaximumSizeInput();
        streetNumberValidInput("9");
    }
    @Test (description = "Payment method select testing", priority = 4)
    @Description("If selected nothing - get error message")
    void paymentMethodTest() throws InterruptedException {
        driver.navigate().refresh();
        deliveryPage.confirmOrder();
        Thread.sleep(500);
        Assert.assertEquals(deliveryPage.paymentTypeSelectErrorLabel.getText(), "Це поле є обов'язковим.");
    }

    /** Phone number input steps */

    @Step("Empty field -> get message if confirm")
    void phoneIsEmpty() throws InterruptedException {
        String errorMessage = deliveryPage.clearPhoneField()
                .confirmOrder()
                .sleep()
                .getPhoneFieldErrorMessage();
        Assert.assertEquals(errorMessage, "Це поле є обов'язковим.");
    }
    @Step("Phone mask is shown - if click")
    void phoneShowMask(){
        deliveryPage.clearPhoneField();
        deliveryPage.phoneField.click();
        Assert.assertEquals(deliveryPage.phoneField.getAttribute("value"), "380             ");
    }
    @Step("Letters and Chars input(negative)")
    void phoneNoNumberInput() throws InterruptedException {
        deliveryPage.clearPhoneField();
        deliveryPage.phoneField.click();
        deliveryPage.phoneField.sendKeys("AbЫз?#`/$");
        Thread.sleep(500);
        Assert.assertEquals(deliveryPage.phoneField.getAttribute("value"), "380             ");
    }
    @Step("Input number use only zero: 380000000000")
    void phoneZeroNumberInput() throws InterruptedException {
        deliveryPage.clearPhoneField();
        deliveryPage.phoneField.sendKeys("000000000");
        deliveryPage.confirmOrder();
        Thread.sleep(500);
        Assert.assertEquals(deliveryPage.phoneFieldErrorLabel.getText(), "Невірний номер телефону");
    }
    @Step("Incomplete input")
    void phoneIncompleteInput() throws InterruptedException {
        deliveryPage.clearPhoneField();
        deliveryPage.phoneField.sendKeys("9");
        deliveryPage.confirmOrder();
        Thread.sleep(500);
        Assert.assertEquals(deliveryPage.phoneFieldErrorLabel.getText(), "Значення не відповідає необхідному патерну.");
    }
    @Step("maximum length + 1 input")
    void phoneMaximumLengthInput(){
        deliveryPage.clearPhoneField();
        deliveryPage.phoneField.sendKeys("9381206625");
        Assert.assertEquals(deliveryPage.phoneField.getAttribute("value"), "380 93 812 06 62");
    }
    @Step("Valid phone number input")
    void phoneValidNumberInput() throws InterruptedException {
        deliveryPage.clearPhoneField();
        deliveryPage.phoneField.sendKeys("938120661");
        deliveryPage.confirmOrder();
        Thread.sleep(500);
        Assert.assertEquals(deliveryPage.phoneField.getAttribute("value"), "380 93 812 06 61");
        Assert.assertEquals(deliveryPage.phoneFieldErrorLabel.getText(), "");
    }
/*    void clearPhoneField(){
        deliveryPage.phoneField.sendKeys(Keys.CONTROL + "a");
        deliveryPage.phoneField.sendKeys(Keys.DELETE);
    }*/

    /** Email input steps */

    @Step("Empty field -> get message if confirm")
    void emailIsEmpty(){
        clearEmailField();
        deliveryPage.emailField.click();
        deliveryPage.confirmOrder();
    }
    @Step("input type: {1}, email: {0}")
    void emailInput(String email, String type) throws InterruptedException{
            String expectedMessage = "";
            clearEmailField();
            deliveryPage.emailField.sendKeys(email);
            deliveryPage.confirmOrder();
            Thread.sleep(500);
                if (type.equals("valid")){
                        expectedMessage = "";
                } else if (type.equals("invalid")){
                        expectedMessage = "Введіть коректну адресу електронної пошти.";
                }
                Assert.assertEquals(deliveryPage.emailFieldErrorlabel.getText(), expectedMessage);
            }
    void clearEmailField(){
        deliveryPage.emailField.sendKeys(Keys.CONTROL + "a");
        deliveryPage.emailField.sendKeys(Keys.DELETE);
    }
    boolean validEmailInput() throws InterruptedException {
        boolean error = false;
        String[] validEmailTypes = {
                "abc@gmail.com",
                "ABC@gmail.com",
                "123@gmail.com",
                "abc@123.com",
                "a-b-c@gmail.com",
                "abc@g-mail.com",
                "_abc@gmail.com",
                "abc@g_mail.com",
                "a.bc@gmail.com",
                "abc@gma.il.com",
                "!#$%&'*+-/=?^_`{|}~@gmail.com"
        };
        for(int i = 0; i < validEmailTypes.length; i++){
            try {
                emailInput(validEmailTypes[i], "valid");
            } catch (AssertionError e){
                error = true;
            }
        }
        clearEmailField();
        return (error);
    }
    boolean invalidEmailInput() throws InterruptedException {
        boolean error = false;
        String[] invalidEmailTypes = {
                "abc@gmailcom",
                "ABСmail.com",
                "ab c@gmail.com",
                "abc@g mail.com",
                "@gmail.com",
                "abc@",
                "абв@gmail.com"
        };
        for(int i = 0; i < invalidEmailTypes.length; i++){
            try {
                emailInput(invalidEmailTypes[i], "invalid");
            } catch (AssertionError e){
                error = true;
            }
        }
        clearEmailField();
        return (error);
    }

    /** Street input steps */

    @Step("Empty field -> get message if confirm")
    void streetIsEmpty() throws InterruptedException {
        deliveryPage.confirmOrder();
        Thread.sleep(500);
        Assert.assertEquals(deliveryPage.streetSelectErrorLabel.getText(),"Це поле є обов'язковим.");
    }
    @Step("Input {0}, {2}")
    void streetInput(String inputStreet, String expectedStreet, boolean valid) throws InterruptedException {
        deliveryPage.streetSelect.sendKeys(inputStreet);
        deliveryPage.streetSelect.sendKeys(Keys.ARROW_DOWN);
        deliveryPage.streetSelect.sendKeys(Keys.ENTER);
        deliveryPage.confirmOrder();
        Thread.sleep(500);
        //Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(), '" + expectedStreet + "')]")).isDisplayed());
        if (valid == true){
            Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(), '" + expectedStreet + "')]")).isDisplayed());
            Assert.assertEquals(deliveryPage.streetSelectErrorLabel.getText(), "");
        } else {
            Assert.assertEquals(deliveryPage.streetSelectErrorLabel.getText(), "Це поле є обов'язковим.");
        }
    }

    /**street number input steps*/

    void clearStreetNumberField(){
        deliveryPage.streetNumberField.sendKeys(Keys.CONTROL + "a");
        deliveryPage.streetNumberField.sendKeys(Keys.DELETE);
    }
    @Step("Empty field -> get message if confirm")
    void streetNumberIsEmpty() throws InterruptedException {
        clearStreetNumberField();
        deliveryPage.confirmOrder();
        Thread.sleep(500);
        Assert.assertEquals(deliveryPage.streetNumberFiledErrorLabel.getText(), "Це поле є обов'язковим.");
    }
    @Step("Maximum length is 10 chars, if more - get message")
    void streetNumberMaximumSizeInput() throws InterruptedException {
        clearStreetNumberField();
        deliveryPage.streetNumberField.sendKeys("1000000000А");
        deliveryPage.confirmOrder();
        Thread.sleep(500);
        Assert.assertEquals(deliveryPage.streetNumberFiledErrorLabel.getText(), "Переконайтесь, що кількість символів в цьому полі не перевищує 10.");
    }
    @Step("Valid input without error message")
    void streetNumberValidInput(String streetNumber) throws InterruptedException {
        clearStreetNumberField();
        deliveryPage.streetNumberField.sendKeys(streetNumber);
        deliveryPage.confirmOrder();
        Thread.sleep(500);
        Assert.assertEquals(deliveryPage.streetNumberFiledErrorLabel.getText(), "");
    }
}

