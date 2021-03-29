package View;

import Model.Date;
import Model.MenuItem;
import Model.Order;
import Model.Restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

public class WaiterGUI extends JFrame {
    private JLabel waiterTitle = new JLabel("WAITER");
    private JButton backButton = new JButton("Back");
    private JLabel orderId = new JLabel("OrderID:");
    private JLabel orderDate = new JLabel("OrderDate:");
    private JLabel table = new JLabel("Table:");
    private JLabel items = new JLabel("Products selected from menu:");
    private JTextField idOrder = new JTextField();
    private JTextField dateOrder = new JTextField();
    private JTextField tableOrder = new JTextField();
    private JTextArea selItems = new JTextArea();
    private JButton displayMenu = new JButton("Display menu");
    private JButton createOrder = new JButton("Create order");
    private JButton viewOrders = new JButton("View orders");
    private JButton createBill = new JButton("Create bill");
    private JButton addItem = new JButton("Add item");
    DefaultTableModel tabelMenu = new DefaultTableModel();
    JTable menuJ = new JTable(tabelMenu);
    JScrollPane scrollPane1 = new JScrollPane(menuJ);
    DefaultTableModel tabelOrders = new DefaultTableModel();
    JTable ordersJ = new JTable(tabelOrders);
    JScrollPane scrollPane2 = new JScrollPane(ordersJ);
    private StandardInterface standardInterface;
    private Restaurant restaurant;
    ArrayList<MenuItem> menuProducts = new ArrayList<MenuItem>();
    ArrayList<Order> ordersList = new ArrayList<Order>();

    public WaiterGUI(StandardInterface standardInterface, Restaurant restaurant){
        this.standardInterface = standardInterface;
        this.restaurant = restaurant;

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(500, 150, 900, 500);
        this.getContentPane().setLayout(null);

        Font titleFont = new Font("Times New Roman", Font.BOLD, 26);

        waiterTitle.setFont(titleFont);
        waiterTitle.setBounds(350, 25, 400, 50);
        getContentPane().add(waiterTitle);

        backButton.setBounds(10, 10, 75, 25);
        getContentPane().add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                standardInterface.setVisible(true);
            }
        });

        orderId.setBounds(315, 80, 75, 30);
        getContentPane().add(orderId);
        idOrder.setBounds(380, 80, 150, 25);
        getContentPane().add(idOrder);
        orderDate.setBounds(315, 105, 75, 30);
        getContentPane().add(orderDate);
        dateOrder.setBounds(380,105, 150, 25 );
        getContentPane().add(dateOrder);
        table.setBounds(315,130,75,30);
        getContentPane().add(table);
        tableOrder.setBounds(380,130, 150,25);
        getContentPane().add(tableOrder);

        createOrder.setBounds(305,165,120,40);
        getContentPane().add(createOrder);
        addCreateOrderListener(new CreateOrderListener());

        createBill.setBounds(435,165, 120, 40);
        getContentPane().add(createBill);
        addCreateBill(new CreateBill());

        displayMenu.setBounds(435, 210, 120,40);
        getContentPane().add(displayMenu);
        getContentPane().add(scrollPane1);
        addDisplayMenuListener(new DisplayMenuListener());

        viewOrders.setBounds(305, 210, 120,40);
        getContentPane().add(viewOrders);
        getContentPane().add(scrollPane2);
        addViewOrderListener(new ViewOrderListener());

        selItems.setBounds(315,260,240,200);
        selItems.setEditable(false);
        getContentPane().add(selItems);
        items.setBounds(130,400, 200, 30 );
        getContentPane().add(items);
        addItem.setBounds(575,400, 120,40 );
        getContentPane().add(addItem);
        addAddItemListener(new AddItemListener());
    }
    public String getOrderIdField (){
        return this.idOrder.getText();
    }
    public String getDateFiled(){
        return this.dateOrder.getText();
    }
    public String getTableField(){
        return this.tableOrder.getText();
    }
    public Order findOrder (int id){
        Order newOrder = null;
        Iterator<Order> iterator = ordersList.iterator();
        while (iterator.hasNext()){
            Order order = iterator.next();
            if(order.getOrderID() == id)
                newOrder = order;
        }
        return newOrder;
    }
    public void addCreateOrderListener(ActionListener create_listener){
        createOrder.addActionListener(create_listener);
    }
    public void addCreateBill(ActionListener bill_listener){
        createBill.addActionListener(bill_listener);
    }
    public void addViewOrderListener(ActionListener viewOrd_listener){
        viewOrders.addActionListener(viewOrd_listener);
    }
    public void addDisplayMenuListener(ActionListener dispMenu_listener){
        displayMenu.addActionListener(dispMenu_listener);
    }
    public void addAddItemListener(ActionListener additem_listener){
        addItem.addActionListener(additem_listener);
    }

    class CreateOrderListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            ArrayList<MenuItem> selectedProd = new ArrayList<MenuItem>();
            String a = getOrderIdField();
            if(a.compareTo("") == 0){
                JOptionPane.showMessageDialog(null, "Invalid input");
            }
            else {
                String date = getDateFiled();
                StringTokenizer d = new StringTokenizer(date, ".");
                try{
                   int id = Integer.parseInt(getOrderIdField());
                   int month = Integer.parseInt(d.nextToken());
                   int day = Integer.parseInt(d.nextToken());
                   int year = Integer.parseInt(d.nextToken());
                    int tableNr = Integer.parseInt(getTableField());
                    Date newDate = new Date(month, day, year);
                    Order newOrder = new Order(id, newDate, tableNr);
                    Iterator<MenuItem> iterator = menuProducts.iterator();
                    while (iterator.hasNext()) {
                        MenuItem item = iterator.next();
                        selectedProd.add(item);
                    }
                    restaurant.createNewOrder(newOrder, selectedProd);
                    menuProducts.removeAll(menuProducts);
                    restaurant.orderToList(newOrder);
                    ordersList.add(newOrder);
                } catch (NumberFormatException numberFormatException) {
                    JOptionPane.showMessageDialog(null,"Invalid input format");
                }

                idOrder.setText("");
                dateOrder.setText("");
                tableOrder.setText("");
                selItems.setText("");
            }
        }
    }

    public class CreateBill implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            Map<Order, ArrayList<MenuItem>> orderMap = restaurant.getOrderMap();
            String a = getDateFiled();
            String b = getTableField();
            if(a.compareTo("") == 0 && b.compareTo("") == 0) {
                try{
                    int id = Integer.parseInt(getOrderIdField());
                    Order newOrder = findOrder(id);
                    ArrayList<MenuItem> prodlist = orderMap.get(newOrder);
                    Iterator<MenuItem> iterator = prodlist.iterator();
                    StringBuilder st = new StringBuilder();
                    st.append("BILL\nOrder id: " + id + "\n");
                    st.append("Order date: " + newOrder.getDate().getMonth() + "." + newOrder.getDate().getDay() + "." + newOrder.getDate().getYear() + "\n");
                    st.append("Table number: " + newOrder.getTable() + "\nProducts ordered " + "\n");
                    while (iterator.hasNext()) {
                        MenuItem item = iterator.next();
                        st.append(item.toString());
                        st.append(", ");
                    }
                    st.append("\nTotal price: " + restaurant.computeOrdertotal(newOrder));
                    restaurant.createBill(st.toString());
                    JOptionPane.showMessageDialog(null, "Bill created");
                } catch (NumberFormatException numberFormatException) {
                    JOptionPane.showMessageDialog(null, "Invalid id format");
                }

            }
            else
                JOptionPane.showMessageDialog(null, "Error! Introduce only the id");
            idOrder.setText("");
            dateOrder.setText("");
            tableOrder.setText("");
        }
    }

    public class DisplayMenuListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String columns[] = {"Name", "Price"};
            ArrayList<MenuItem> items = restaurant.getMenu();
            tabelMenu.setColumnIdentifiers(columns);
            Object[] obj = new Object[2];
            Iterator<MenuItem> iterator = items.iterator();
            tabelMenu.setRowCount(0);
            while(iterator.hasNext()){
                MenuItem item = iterator.next();
                obj[0] = item.getName();
                obj[1] = item.getPrice();
                tabelMenu.addRow(obj);
            }

            scrollPane1.setVisible(true);
            scrollPane1.setBounds(560, 80, 320, 300);
            menuJ.setFillsViewportHeight(true);
            JOptionPane.showMessageDialog(null, "Menu displayed");
        }
    }

    public class ViewOrderListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String columns[] = {"ID", "Date", "Table"};
            tabelOrders.setColumnIdentifiers(columns);
            Object[] obj = new Object[3];
            Iterator<Order> iterator = ordersList.iterator();
            tabelOrders.setRowCount(0);
            while(iterator.hasNext()){
                Order order = iterator.next();
                obj[0] = order.getOrderID();
                String date = order.getDate().getMonth() + "." + order.getDate().getDay() + "." + order.getDate().getYear();
                obj[1] = date;
                obj[2] = order.getTable();
                tabelOrders.addRow(obj);
            }

            scrollPane2.setVisible(true);
            scrollPane2.setBounds(5, 80, 300, 300);
            ordersJ.setFillsViewportHeight(true);
            JOptionPane.showMessageDialog(null, "Order displayed");
        }
    }
    public class AddItemListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            int row = menuJ.getSelectedRow();
            String obj = menuJ.getModel().getValueAt(row, 0).toString();
            MenuItem item = new MenuItem();
            item.setName(obj);
            String p = menuJ.getModel().getValueAt(row, 1).toString();
            float price = Float.parseFloat(p);
            item.setPrice(price);
            System.out.println(item.getName());
            menuProducts.add(item);
            selItems.append(item.getName());
            selItems.append("\n");
        }
    }


}
