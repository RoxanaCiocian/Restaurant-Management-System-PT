package View;

import Model.Restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class ChefGUI extends JFrame implements Observer {
    private JLabel chefTitle =  new JLabel("CHEF");
    private JButton backButton = new JButton("Back");
    private Restaurant restaurant;

    public ChefGUI(StandardInterface standardInterface, Restaurant restaurant){
        this.restaurant = restaurant;
        restaurant.addObserver(this);

        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setBounds(500, 150, 900, 500);
        this.getContentPane().setLayout(null);

        Font titleFont = new Font("Times New Roman", Font.BOLD, 26);

        chefTitle.setBounds(300, 25, 400, 50);
        chefTitle.setFont(titleFont);
        getContentPane().add(chefTitle);


    }
    @Override
    public void update(Observable o, Object arg) {
        this.setVisible(true);
        System.out.println(arg.toString());
        int panel = JOptionPane.showConfirmDialog(null, arg, "Kitchen notification", 2);
        if (panel == 0) {
            System.out.println("Chef will cook!");
            this.setVisible(false);
        }
    }
}
