package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StandardInterface extends JFrame {
    private JLabel title1;
    private JButton WaiterButton;
    private JButton AdministratorButton;
    private JButton ChefButton;

    public StandardInterface(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(500, 150, 900, 500);
        this.getContentPane().setLayout(null);

        Font titleFont = new Font("Times New Roman", Font.BOLD, 26);
        Font buttonsFont = new Font("Times New Roman", Font.PLAIN, 18);

        title1 = new JLabel("USER IDENTIFICATION");
        title1.setFont(titleFont);
        title1.setBounds(300, 50, 400, 50);
        getContentPane().add(title1);

        WaiterButton = new JButton("Waiter");
        WaiterButton.setFont(buttonsFont);
        WaiterButton.setBounds(100,300,150,50);
        getContentPane().add(WaiterButton);

        AdministratorButton = new JButton("Administrator");
        AdministratorButton.setFont(buttonsFont);
        AdministratorButton.setBounds(350, 300, 200, 50);
        getContentPane().add(AdministratorButton);

        ChefButton = new JButton("Chef");
        ChefButton.setFont(buttonsFont);
        ChefButton.setBounds(650, 300, 150, 50);
        getContentPane().add(ChefButton);
    }

    public void addWaiterActionListener(ActionListener action){
        WaiterButton.addActionListener(action);
    }

    public void addAdministratorListener(ActionListener action){
        AdministratorButton.addActionListener(action);
    }

    public void addChefActionListener(ActionListener action){
        ChefButton.addActionListener(action);
    }
}
