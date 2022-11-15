package tests.orderForm;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
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
    void phoneInputFieldTest(){
        phoneIsEmpty();
        phoneShowMask();
        phoneNoNumberInput();
        phoneZeroNumberInput();
        phoneIncompleteInput();
        phoneMaximumLengthInput();
        phoneValidNumberInput();
    }
    @Test (description = "Email text box testing", priority = 1)
    void emailInputFieldTest(){
        emailIsEmpty();
        Assert.assertTrue(validEmailInput() && invalidEmailInput());
    }
    @Test (description = "Street input testing", priority = 2)
    void streetInputFieldTest(){
        streetIsEmpty();
        streetInput("Миру","Миру",true);
        streetInput("abr", "Абр", true);
        streetInput("123","123", false);
    }
    @Test (description = "Street Number text box testing", priority = 3)
    void streetNumberFiledTest(){
        streetNumberIsEmpty();
        streetNumberMaximumSizeInput();
        streetNumberValidInput("9");
    }
    @Test (description = "Payment method selector testing", priority = 4)
    @Description("If selected nothing - get error message")
    void paymentMethodTest(){
        deliveryPage.refresh();
        String actualMessage = deliveryPage.confirmOrder()
                .getErrorMessage("paymentType");
        Assert.assertEquals(actualMessage, "Це поле є обов'язковим.");
    }

    /** Phone number input steps */

    @Step("Empty field -> get message if confirm")
    void phoneIsEmpty(){
        String actualMessage = deliveryPage.clearField("phone")
                .confirmOrder()
                .getErrorMessage("phone");
        Assert.assertEquals(actualMessage,"Це поле є обов'язковим.");
    }
    @Step("Phone mask is shown - if click")
    void phoneShowMask(){
        String mask = deliveryPage.clearField("phone")
                .clickField("phone")
                .getCurrentNumber();
        Assert.assertEquals(mask, "380             ");
    }
    @Step("Letters and Chars input(negative)")
    void phoneNoNumberInput(){
        String inputValue = deliveryPage.clearField("phone")
                .setPhoneNumber("AbЫз?#`/$")
                .confirmOrder()
                .getCurrentNumber();
        Assert.assertEquals(inputValue, "");
    }
    @Step("Input number use only zero: 380000000000")
    void phoneZeroNumberInput(){
        String actualMessage = deliveryPage.clearField("phone")
                .setPhoneNumber("000000000")
                .confirmOrder()
                .getErrorMessage("phone");
        Assert.assertEquals(actualMessage,"Невірний номер телефону");
    }
    @Step("Incomplete input")
    void phoneIncompleteInput(){
        String actualMessage = deliveryPage.clearField("phone")
                .setPhoneNumber("9")
                .confirmOrder()
                .getErrorMessage("phone");
        Assert.assertEquals(actualMessage,"Значення не відповідає необхідному патерну.");
    }
    @Step("maximum length + 1 input")
    void phoneMaximumLengthInput(){
        String inputValue = deliveryPage.clearField("phone")
                .setPhoneNumber("9381209925")
                .getCurrentNumber();
        Assert.assertEquals(inputValue, "380 93 812 09 92");
    }
    @Step("Valid phone number input")
    void phoneValidNumberInput(){
        String actualMessage = deliveryPage.clearField("phone")
                .setPhoneNumber("938120991")
                .confirmOrder()
                .getErrorMessage("phone");
        Assert.assertEquals(actualMessage,"");
        Assert.assertEquals(deliveryPage.getCurrentNumber(), "380 93 812 09 91");
    }

    /** Email input steps */

    @Step("Empty field -> get message if confirm")
    void emailIsEmpty(){
        String actualMessage = deliveryPage.clearField("email")
                .clickField("email")
                .confirmOrder()
                .getErrorMessage("email");
        Assert.assertEquals(actualMessage, "Поле не може бути порожнім");
    }
    boolean validEmailInput(){
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
                System.out.println("Input: " + validEmailTypes[i] + ". Actual: invalid. Expected: valid.");
            }
        }
        deliveryPage.clearField("email");
        return (error);
    }
    boolean invalidEmailInput(){
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
                System.out.println("Input: " + invalidEmailTypes[i] + ". Actual: valid. Expected: invalid.");
            }
        }
        deliveryPage.clearField("email");
        return (error);
    }
    @Step("input type: {1}, email: {0}")
    void emailInput(String email, String type){
        String expectedMessage = null;
        String actualMessage = deliveryPage.clearField("email")
                .setEmail(email)
                .confirmOrder()
                .getErrorMessage("email");
        if (type.equals("valid")){
            expectedMessage = "";
        } else if (type.equals("invalid")){
            expectedMessage = "Введіть коректну адресу електронної пошти.";
        }
        Assert.assertEquals(actualMessage, expectedMessage);
    }

    /** Street input steps */

    @Step("Empty field -> get message if confirm")
    void streetIsEmpty(){
        String actualMessage = deliveryPage.clearField("street")
                .confirmOrder()
                .getErrorMessage("street");
        Assert.assertEquals(actualMessage, "Це поле є обов'язковим.");
    }
    @Step("Input {0}, {2}")
    void streetInput(String inputStreet, String expectedStreet, boolean valid){
        deliveryPage.refresh();
        String actualMessage = deliveryPage.setStreet(inputStreet)
                .selectStreetFromDropDown(1)
                .confirmOrder()
                .getErrorMessage("street");
        if (valid){
            Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(), '" + expectedStreet + "')]")).isDisplayed());
            Assert.assertEquals(actualMessage, "");
        } else {
            Assert.assertEquals(actualMessage, "Це поле є обов'язковим.");
        }
    }

    /**street number input steps*/

    @Step("Empty field -> get message if confirm")
    void streetNumberIsEmpty(){
        String actualMessage = deliveryPage.clearField("streetNumber")
                .confirmOrder()
                .getErrorMessage("streetNumber");
        Assert.assertEquals(actualMessage, "Це поле є обов'язковим.");
    }
    @Step("Maximum length is 10 chars, if more - get message")
    void streetNumberMaximumSizeInput(){
        String actualMessage = deliveryPage.clearField("streetNumber")
                .setStreerNumber("1000000000А")
                .confirmOrder()
                .getErrorMessage("streetNumber");
        Assert.assertEquals(actualMessage, "Переконайтесь, що кількість символів в цьому полі не перевищує 10.");
    }
    @Step("Valid input without error message")
    void streetNumberValidInput(String streetNumber){
        String actualMessage = deliveryPage.clearField("streetNumber")
                .setStreerNumber(streetNumber)
                .confirmOrder()
                .getErrorMessage("streetNumber");
        Assert.assertEquals(actualMessage, "");
    }
}

