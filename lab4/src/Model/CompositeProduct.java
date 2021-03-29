package Model;

import java.util.ArrayList;

public class CompositeProduct extends MenuItem {
    private ArrayList<MenuItem> compositeProducts;
    float totalPrice = 0;
    public CompositeProduct( ArrayList<MenuItem> products){
        this.compositeProducts = products;
    }

    public ArrayList<MenuItem> getCompItem() {
        return compositeProducts;
    }

    public void setCompItem(ArrayList<MenuItem> compItem) {
        this.compositeProducts = compItem;
    }

    public float computePrice(){
        totalPrice = 0;
        for(MenuItem item : compositeProducts){
            totalPrice = totalPrice + item.getPrice();
        }
        return totalPrice;
    }
}
