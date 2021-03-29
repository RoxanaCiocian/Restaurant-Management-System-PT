package View;

import Model.CompositeProduct;
import Model.MenuItem;
import Model.Restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

public class CompProdGUI extends JFrame {
    private Restaurant restaurant;
    private StandardInterface standardInterface;
    private ArrayList<MenuItem> compositeProduct;
    private JLabel title;
    private JButton backButton;
    private JComboBox<MenuItem> products = new JComboBox<MenuItem>();
    private JButton addCompProd;
    private JButton prodAdded;
    private JLabel prodName;
    private JTextArea name;
    private JButton displayButton;
    DefaultTableModel tabel = new DefaultTableModel();
    JTable table = new JTable(tabel);
    public ArrayList<CompositeProduct> prList = new ArrayList<CompositeProduct>();
    private ArrayList<MenuItem> compItem = new ArrayList<MenuItem>();
    CompositeProduct finalProd;

    public CompProdGUI(Restaurant restaurant){
        this.restaurant = restaurant;
        compositeProduct = new ArrayList<MenuItem>();
        finalProd = new CompositeProduct(compositeProduct);


        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setBounds(500, 150, 900, 500);
        this.getContentPane().setLayout(null);

        Font titleFont = new Font("Times New Roman", Font.BOLD, 26);

        title = new JLabel("Composite product operations:");
        title.setFont(titleFont);
        title.setBounds(280, 25, 400, 50);
        getContentPane().add(title);

        backButton = new JButton("Back");
        backButton.setBounds(10, 10, 75, 25);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        getContentPane().add(backButton);

        displayButton = new JButton("Display menu items");
        displayButton.setBounds(50, 400, 200, 50);
        getContentPane().add(displayButton);
        addDisplayListener(new DisplayListener());

        addCompProd = new JButton("Add Item");
        addCompProd.setBounds(50,340,200,50);
        getContentPane().add(addCompProd);
        addAddCompProdListener(new AddCompProdListener());

        name = new JTextArea();
        name.setBounds(275,280,175,170);
        name.setEditable(false);
        getContentPane().add(name);

        prodAdded = new JButton("Create product");
        prodAdded.setBounds(50,280,200,50);
        getContentPane().add(prodAdded);
        addProdAddedListener(new ProdAddedListener());

    }

    public void addAddCompProdListener(ActionListener addComp_listener){
        addCompProd.addActionListener(addComp_listener);
    }

    public void addProdAddedListener(ActionListener prodAdded_listener){
        prodAdded.addActionListener(prodAdded_listener);
    }
    public void addDisplayListener(ActionListener display_listener){
        displayButton.addActionListener(display_listener);
    }

    public class AddCompProdListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            int row = table.getSelectedRow();
            String obj = table.getModel().getValueAt(row, 0).toString();
            MenuItem item = new MenuItem();
            item.setName(obj);
            String p = table.getModel().getValueAt(row, 1).toString();
            float price = Float.parseFloat(p);
            item.setPrice(price);
            compositeProduct.add(item);
            name.append(item.getName());
            name.append("\n");
        }
    }
    public class ProdAddedListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            ArrayList<MenuItem> prod = compositeProduct;
            String prodName = restaurant.getCompName();
            finalProd = new CompositeProduct(prod);
            finalProd.setName(prodName);
            float priceF = finalProd.computePrice();
            finalProd.setPrice(priceF);
            finalProd.setCompItem(prod);
            restaurant.createNewItem(finalProd);
            prList.add(finalProd);
            name.setText("");
            tabel.setRowCount(0);
            restaurant.setCmpPrd(prList);
            JOptionPane.showMessageDialog(null, "Composite item created successfully!");
        }
    }
    public class DisplayListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            compositeProduct.clear();
            String columns[] = {"Name", "Price"};
            ArrayList<Model.MenuItem> items = restaurant.getMenu();

            tabel.setColumnIdentifiers(columns);
            Object[] obj = new Object[2];
            Iterator<MenuItem> iterator = items.iterator();

            while(iterator.hasNext()){
                MenuItem item = iterator.next();
                obj[0] = item.getName();
                obj[1] = item.getPrice();
                tabel.addRow(obj);
            }
            table.setVisible(true);
            table.setBounds(475,100,400,350);
            getContentPane().add(table);
            table.setFillsViewportHeight(true);
        }
    }
    public ArrayList<MenuItem> getCompositeProduct(){
        return finalProd.getCompItem();
    }
}
