package Model;

import java.io.*;
import java.util.*;
import Controller.IRestaurantProcessing;


public class Restaurant extends Observable implements IRestaurantProcessing {
    private ArrayList<MenuItem> menu;
    private ArrayList<MenuItem> compItem = new ArrayList<MenuItem>();
    private String compName;
    private ArrayList <Order> ordersList;
    private Map<Order, ArrayList<MenuItem>> orderMap;
    private ArrayList<CompositeProduct> cmpPrd = new ArrayList<CompositeProduct>();


    public Restaurant(){
        this.menu = new ArrayList<MenuItem>();
        this.ordersList = new ArrayList<Order>();
        this.orderMap = new HashMap<Order, ArrayList<MenuItem>>();
    }

    public ArrayList<CompositeProduct> getCmpPrd() {
        return cmpPrd;
    }

    public void setCmpPrd(ArrayList<CompositeProduct> cmpPrd) {
        this.cmpPrd = cmpPrd;
    }

    public ArrayList<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<MenuItem> menu) {
        this.menu = menu;
    }

    public ArrayList<MenuItem> getCompItem() {
        return compItem;
    }

    public void setCompItem(ArrayList<MenuItem> compItem) {
        this.compItem = compItem;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public Map<Order, ArrayList<MenuItem>> getOrderMap() {
        return orderMap;
    }

    public void createNewOrder(Order order, ArrayList<MenuItem> products){
        assert (order != null && products != null);
        Order oldOrder = order;
        orderMap.put(order, products);
        assert  oldOrder.equals(order);
    }


    public float computeOrdertotal (Order order){
        assert order != null;
        float price = 0;
        if (this.ordersList.contains(order) == true){
            ArrayList<MenuItem> menuItems = orderMap.get(order);
            Iterator<MenuItem> iterator = menuItems.iterator();
            while (iterator.hasNext()){
                MenuItem item = iterator.next();
                price = price + item.getPrice();
            }
        }
        return price;
    }
    public void orderToList(Order order){
        ordersList.add(order);
        Map<Order, ArrayList<MenuItem>> map = getOrderMap();
        ArrayList<MenuItem> itemsList = map.get(order);
        Iterator<MenuItem> iterator = itemsList.iterator();
        StringBuilder st = new StringBuilder();
        st.append("New order is created\nOrder id: " + order.getOrderID() + "\n");
        st.append("Items ordered: ");
        while (iterator.hasNext()){
            MenuItem item = iterator.next();
            st.append(item.toString() + ", ");
        }

        setChanged();
        notifyObservers(st.toString());
    }

    public void createNewItem (MenuItem newItem){
        assert newItem != null;
        int preSize = menu.size();
        menu.add(newItem);
        int postSize = menu.size();
        assert preSize + 1 == postSize;
    }

    public void deleteExistingItem (MenuItem item){
        assert item != null;
        int preSize = menu.size();
        menu.remove(item);
        int postSize = menu.size();
        assert preSize == postSize + 1;
    }

    public void editExistingItem (MenuItem item, float price){
        assert (item != null && price != 0);
        float prePrice = 0;
        float postPrice = item.getPrice();
        String itemName = item.getName();
        Iterator<MenuItem> myIterator = menu.iterator();
        while (myIterator.hasNext()) {
            MenuItem curentItem = myIterator.next();
            if (curentItem.getName().equals(itemName)) {
                int index = menu.indexOf(curentItem);
                curentItem.setPrice(price);
                prePrice = curentItem.getPrice();
                menu.set(index, curentItem);
            }
        }

        assert prePrice == postPrice;
    }

    public void createBill(String bill){
        assert bill != null;
        try {
            BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("bill.txt")));
            buff.write(bill);
            buff.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "menu=" + menu +
                '}';
    }
}
