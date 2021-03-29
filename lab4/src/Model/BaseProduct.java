package Model;

public class BaseProduct extends MenuItem {

    public BaseProduct(String name, float price){
        this.name = name;
        this.price = price;
    }

    public float computePrice(){
        return getPrice();
    }
}
