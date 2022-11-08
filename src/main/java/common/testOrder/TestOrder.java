package common.testOrder;

import java.util.ArrayList;

public class TestOrder {
    private ArrayList<OrderItem> testOrder = new ArrayList<OrderItem>();

    public TestOrder addItem(OrderItem x){
        testOrder.add(x);
        return this;
    }

    public OrderItem getOrderItem(int x){
        return testOrder.get(x);
    }
    public String getName(int x){
        return testOrder.get(x).getName();
    }

    public String getToppings(int x){return testOrder.get(x).getToppings();}

    public int getId(int x){ return testOrder.get(x).getIdInMenu();}

    public void clearOrder(){
        testOrder.removeAll(testOrder);
    }
}


