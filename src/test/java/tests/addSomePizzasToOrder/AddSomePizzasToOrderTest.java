package tests.addSomePizzasToOrder;
import common.testOrder.TestOrder;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import pages.dominosUkDelivery.DeliveryPage;
import tests.base.BaseTest;

import static constants.Constant.Urls.DOMINOS_UK_PIZZA_MENU_PAGE;

public class AddSomePizzasToOrderTest extends BaseTest {

    private TestOrder Order = new TestOrder();
    private DeliveryPage deliveryPage;

    @Test(priority = 0, description = "Order two random pizzas from menu page")
    @Severity(SeverityLevel.BLOCKER)
    public void addSomePizzasToOrderList() throws InterruptedException {
        pizzaMenuPage.open(DOMINOS_UK_PIZZA_MENU_PAGE);
        pizzaMenuPage.selectLocalization("chornomorsk").refresh();
        selectTwoPizzasRandomly();
        deliveryPage = pizzaMenuPage
                .addPizzaToOrderList(Order.getOrderItem(0))
                .addPizzaToOrderList(Order.getOrderItem(1))
                .goToDeliveryPage()
                .checkNameOfPizza(0, Order.getName(0))
                .checkNameOfPizza(1, Order.getName(1));
    }
    @Step("Select 2 pizzas randomly")
    void selectTwoPizzasRandomly(){
        Order.addItem(pizzaMenuPage.selectPizzaCard("random").getOrderItem());
        do { Order.addItem(pizzaMenuPage.selectPizzaCard("random").getOrderItem());
        } while ((Order.getName(1)).equals(Order.getName(0)));
    }

}
