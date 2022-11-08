package tests.changeToppings;

import common.testOrder.OrderItem;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.changeToppings.ChangeToppingsPage;
import pages.PizzaMenu.PizzaOrderCard;
import pages.dominosUkDelivery.DeliveryPage;
import tests.base.BaseTest;

import static constants.Constant.Urls.DOMINOS_UK_PIZZA_MENU_PAGE;

public class ChangeToppingsTest extends BaseTest {
    OrderItem customPizza;
    ChangeToppingsPage changeToppingsPage;

    @Test
    void changeToppingsTest(){
        pizzaMenuPage.open(DOMINOS_UK_PIZZA_MENU_PAGE);
        pizzaMenuPage.selectLocalization("chornomorsk").refresh();
        customPizza = pizzaMenuPage.selectPizzaCard("random").getOrderItem();
        changeToppingsPage = PizzaOrderCard.changeToppingsButtonClick(customPizza.getName());
        changeSomeToppings();
        customPizza.setToppings(changeToppingsPage.getActualToppings());
        changeToppingsPage.addToOrderList()
                .goToDeliveryPage()
                .checkToppings(0, customPizza.getToppings());
    }
    @Step("Make changes randomly: add 1 topping, remove 1 topping")
    void changeSomeToppings(){
        int countToAdd;
        int countToRemove;
        countToAdd = (int) (1 + Math.random()* changeToppingsPage.getToppingsCount());
        do {
            countToRemove = (int) (1 + Math.random()* changeToppingsPage.getToppingsCount());
        } while (countToRemove == countToAdd);
        changeToppingsPage.ChangeTopping(countToAdd, "add");
        changeToppingsPage.ChangeTopping(countToRemove, "remove");
    }
}
