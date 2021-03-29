import Controller.Control;

import Model.Restaurant;
import View.*;


public class MainClass {

    public static void main(String[] args) {
        StandardInterface standardInterface = new StandardInterface();
        Restaurant restaurant = new Restaurant();
        CompProdGUI compProdGUI = new CompProdGUI(restaurant);
        AdminGUI adminGUI = new AdminGUI(standardInterface, restaurant, compProdGUI);
        WaiterGUI waiterGUI = new WaiterGUI(standardInterface, restaurant);
        ChefGUI chefGUI = new ChefGUI(standardInterface,restaurant);

        Control newController = new Control(restaurant, standardInterface, compProdGUI, adminGUI, waiterGUI, chefGUI);
        standardInterface.setVisible(true);
    }
}
