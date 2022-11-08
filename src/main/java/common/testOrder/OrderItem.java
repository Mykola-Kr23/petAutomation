package common.testOrder;

public class OrderItem {
    private int idInMenu;
    private String name;
    private String toppings;

    public OrderItem(int idInMenu, String name, String toppings) {
        this.idInMenu = idInMenu;
        this.name = name;
        this.toppings = toppings;
    }

    public String getName(){
        return name;
    }

    public String getToppings() {
        return toppings;
    }

    public int getIdInMenu() {
        return idInMenu;
    }

    public void setToppings(String toppings) {
        this.toppings = toppings;
    }
}
