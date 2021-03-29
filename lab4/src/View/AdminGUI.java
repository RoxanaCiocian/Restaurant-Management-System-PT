package View;

import Model.BaseProduct;
import Model.CompositeProduct;
import Model.Restaurant;
import Model.MenuItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

public class AdminGUI extends JFrame {
    private JLabel adminTitle;
    private JButton backButton = new JButton("Back");
    private JButton createButton = new JButton("Create product");
    private JButton deleteButton = new JButton("Delete product");
    private JButton editButton = new JButton("Edit product");
    private JButton displayButton = new JButton("Display menu");
    private JLabel nameLabel = new JLabel("Name:");
    private JTextField nameField;
    private JLabel priceLabel = new JLabel("Price:");
    private JTextField priceField;
    private Restaurant restaurant;
    private CompProdGUI compProdGUI;
    DefaultTableModel tabel = new DefaultTableModel();
    JTable table = new JTable(tabel);
    JScrollPane scrollPane = new JScrollPane(table);


    public String getNameField() {
        return this.nameField.getText();
    }

    public String getPriceFiled() {
        return this.priceField.getText();
    }

    public MenuItem findProd(String name) {
        ArrayList<MenuItem> menu = restaurant.getMenu();
        Iterator<MenuItem> iterator = menu.iterator();
        while (iterator.hasNext()) {
            MenuItem item = iterator.next();
            if (item.getName().compareTo(name) == 0)
                return item;
        }
        return null;
    }

    public AdminGUI(StandardInterface standardInterface, Restaurant restaurant, CompProdGUI compProdGUI) {
        this.restaurant = restaurant;
        this.compProdGUI = compProdGUI;

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(500, 150, 900, 500);
        this.getContentPane().setLayout(null);

        Font titleFont = new Font("Times New Roman", Font.BOLD, 26);

        adminTitle = new JLabel("ADMINISTRATOR");
        adminTitle.setFont(titleFont);
        adminTitle.setBounds(300, 25, 400, 50);
        getContentPane().add(adminTitle);

        displayButton.setBounds(50, 400, 200, 50);
        getContentPane().add(displayButton);
        getContentPane().add(scrollPane);
        addDisplayListener(new DsiplayListener());

        createButton.setBounds(50, 220, 200, 50);
        getContentPane().add(createButton);
        addCreateListener(new CreateListener());

        deleteButton.setBounds(50, 340, 200, 50);
        getContentPane().add(deleteButton);
        addDeleteListener(new DeleteListener());

        editButton.setBounds(50, 280, 200, 50);
        getContentPane().add(editButton);
        addEditListener(new EditListener());

        backButton.setBounds(10, 10, 75, 25);
        getContentPane().add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                standardInterface.setVisible(true);
            }
        });

        nameLabel.setBounds(50, 100, 50, 50);
        getContentPane().add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(100, 110, 150, 30);
        getContentPane().add(nameField);

        priceLabel.setBounds(50, 150, 50, 50);
        getContentPane().add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(100, 160, 150, 30);
        getContentPane().add(priceField);
    }

    public void addCreateListener(ActionListener create_listener) {
        createButton.addActionListener(create_listener);
    }

    public void addDisplayListener(ActionListener display_listener) {
        displayButton.addActionListener(display_listener);
    }

    public void addDeleteListener(ActionListener delete_listener) {
        deleteButton.addActionListener(delete_listener);
    }

    public void addEditListener(ActionListener edit_listener) {
        editButton.addActionListener(edit_listener);
    }

    class CreateListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = getNameField();
            String price = getPriceFiled();
            if(name.compareTo("") == 0){
                JOptionPane.showMessageDialog(null, "Input error");
            }
            else if (price.compareTo("") == 0)  {
                try {
                    compProdGUI.setVisible(true);
                    restaurant.setCompName(name);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    System.out.println("here");
                }
            } else {
                float newPrice = 0;
                try{
                    newPrice = Float.parseFloat(price);
                    BaseProduct newProd = new BaseProduct(name, newPrice);
                    restaurant.createNewItem(newProd);
                    JOptionPane.showMessageDialog(null, "Product added");
                } catch (NumberFormatException numberFormatException) {
                        JOptionPane.showMessageDialog(null, "Invalid price format");
                }
            }
            nameField.setText("");
            priceField.setText("");
        }
    }

    class DsiplayListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String columns[] = {"Name", "Price"};
            ArrayList<Model.MenuItem> items = restaurant.getMenu();
            tabel.setColumnIdentifiers(columns);
            Object[] obj = new Object[2];
            Iterator<MenuItem> iterator = items.iterator();
            tabel.setRowCount(0);
             while(iterator.hasNext()){
                 MenuItem item = iterator.next();
                 obj[0] = item.getName();
                 obj[1] = item.getPrice();
                 tabel.addRow(obj);
             }
            scrollPane.setVisible(true);
            scrollPane.setBounds(275, 100, 600, 350);
            table.setFillsViewportHeight(true);
            JOptionPane.showMessageDialog(null, "Menu displayed");
        }
    }
    public class DeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = getNameField();
            if (name.compareTo("") == 0){
                JOptionPane.showMessageDialog(null, "Invalid input");
            }
            else {
                ArrayList<MenuItem> menu = restaurant.getMenu();
                ArrayList<CompositeProduct> cmpList = restaurant.getCmpPrd();
                Iterator<CompositeProduct> itC = cmpList.iterator();
                while (itC.hasNext()) {
                    CompositeProduct newP = itC.next();
                    newP.setCompItem(compProdGUI.getCompositeProduct());
                    ArrayList<MenuItem> composition = newP.getCompItem();
                    Iterator<MenuItem> itP = composition.iterator();
                    while (itP.hasNext()) {
                        MenuItem item = itP.next();
                        if (item.getName().equals(name)) {
                            Iterator<MenuItem> itMenu = menu.iterator();
                            while (itMenu.hasNext()) {
                                MenuItem meniu = itMenu.next();
                                if (meniu.getName().compareTo(newP.getName()) == 0)
                                    itMenu.remove();
                            }
                        }
                    }
                }
                restaurant.deleteExistingItem(findProd(name));
                JOptionPane.showMessageDialog(null, "Product(s) deleted");
            }
        }
    }
    class EditListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String p = priceField.getText();
            try {
                float price = Float.parseFloat(p);
                int row = table.getSelectedRow();
                String prod = table.getModel().getValueAt(row, 0).toString();
                MenuItem item = new MenuItem();
                item.setName(prod);
                MenuItem old = findProd(prod);
                restaurant.editExistingItem(old, price);
                ArrayList<MenuItem> menu = restaurant.getMenu();
                ArrayList<CompositeProduct> cmpList = restaurant.getCmpPrd();
                Iterator<CompositeProduct> itC = cmpList.iterator();
                while (itC.hasNext()) {
                    CompositeProduct newP = itC.next();
                    ArrayList<MenuItem> composition = newP.getCompItem();
                    Iterator<MenuItem> itP = composition.iterator();
                    while(itP.hasNext()){
                        MenuItem t = itP.next();
                        if(t.getName().equals(item.getName())){
                            t.setPrice(price);
                            newP.setPrice(newP.computePrice());
                            Iterator<MenuItem> itMenu = menu.iterator();
                            while (itMenu.hasNext()){
                                MenuItem meniu = itMenu.next();
                                if(meniu.getName().compareTo(newP.getName()) == 0) {
                                    int index = menu.indexOf(meniu);
                                    menu.set(index, newP);
                                }
                            }
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, "Product(s) edited");
            } catch (NumberFormatException numberFormatException) {
                JOptionPane.showMessageDialog(null, "Invalid price format");
            }
            priceField.setText("");
        }
    }
}


