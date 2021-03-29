package Controller;

import Model.Restaurant;
import View.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Control {
    private StandardInterface standardInterface;
    private AdminGUI adminGUI;
    private Restaurant restaurant;
    private CompProdGUI compProdGUI;
    private WaiterGUI waiterGUI;
    private ChefGUI chefGUI;

    public Control(Restaurant restaurant, StandardInterface standardInterface, CompProdGUI compProdGUI, AdminGUI adminGUI, WaiterGUI waiterGUI, ChefGUI chefGUI){
        this.restaurant = restaurant;
        this.standardInterface = standardInterface;
        this.compProdGUI = compProdGUI;
        this. adminGUI = adminGUI;
        this.waiterGUI = waiterGUI;
        this.chefGUI = chefGUI;


        initializeStandardButtons();
    }

    public void initializeStandardButtons(){
        standardInterface.addAdministratorListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                standardInterface.setVisible(false);
                adminGUI.setVisible(true);
            }
        });
        standardInterface.addWaiterActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                standardInterface.setVisible(false);
                waiterGUI.setVisible(true);
            }
        });
       standardInterface.addChefActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                standardInterface.setVisible(false);
                chefGUI.setVisible(true);
            }
        });
    }
}
