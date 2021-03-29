package Controller;

import Model.MenuItem;
import Model.Order;

import java.util.ArrayList;
import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

public interface IRestaurantProcessing {

    /**
     *
      * @param order
     * @param products
     *
     *    order != null
     *   products != null
     */
    public void createNewOrder(Order order, ArrayList<MenuItem> products);

    /**
     *
     * @param order
     *
     *  order != null
     */
    public float computeOrdertotal(Order order);

    /**
     *
     * @param bill
     *
     *  bill != null
     */
    public void createBill(String bill);

    /**
     *
     * @param newItem
     *
     * newItem != null
     * list.size@pre == list.size@post - 1
     */
    public void createNewItem(MenuItem newItem);

    /**
     *
     * @param item
     *
     *  item != null
     * list.size@post == list.size@pre + 1
     */
    public void deleteExistingItem (MenuItem item);

    /**
     *
     * @param item
     * @param price
     *
     *  item != null
     *  price != null
     *
     *  item@pre.price != item@post.price
     */
    public void editExistingItem (MenuItem item, float price);

}
