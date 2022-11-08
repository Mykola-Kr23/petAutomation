package pages.changeToppings;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.PizzaMenu.PizzaMenuPage;
import pages.base.BasePage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChangeToppingsPage extends BasePage {
    @FindBy (xpath = "//div[@class='dp-product-modifier__ingredients-item']")
    private List<WebElement> toppings;
    @FindBy (xpath = "//div[@class='dp-ing-block__qty-text']/../../../../../div[@class='dp-ing-block__title']")
    private List<WebElement> toppingNameByCount;
    @FindBy (xpath = "//div[@class='dp-ing-block__qty-text']")
    private List<WebElement> toppingCount;
    @FindBy(xpath = "//div[@class='btn-body']")
    private WebElement addToOrderListButton;

    public ChangeToppingsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public int getToppingsCount(){
        return toppings.size();
    }
    public void ChangeTopping (int id, String type){
        byte t = 0;
        if (type == "add"){
            t = 2;
        } else if (type == "remove") {
            t = 1;
        }
        Actions actions = new Actions(driver);
        WebElement S = driver.findElement(By.xpath("//div[@class='dp-product-modifier__ingredients-item'][" + id + "]//div[@class='dp-ing-block__round-icon-wrap'][" + t + "]"));
        actions.moveToElement(S).click().build().perform();
    }
    public PizzaMenuPage addToOrderList(){
        addToOrderListButton.click();
        driver.findElement(By.xpath("//div[@class='dp-product-details']//div[@class='order-button-wrap']")).click();
        return new PizzaMenuPage(driver);
    }
    public String getActualToppings(){
        int cycle = toppingCount.size();
        List<String> toppingSet = new ArrayList<String>();
        String toppings;
        String addCount;
        for (int i = 0; i < cycle; i++){
            String count = toppingCount.get(i).getAttribute("innerText");
            if ("1".equals(count)){
                addCount = "";
            } else {
                addCount = 'x' + count;
            }
            toppingSet.add((toppingNameByCount.get(i).getText() + addCount).trim());
        }
        toppingSet = toppingSet.stream().sorted().collect(Collectors.toList());
        toppings = String.join(", ", toppingSet);
        return toppings;
    }
}
