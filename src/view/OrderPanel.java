package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import application.MidtownComics;
import controller.ViewManager;
import model.Customer;
import model.OrderItem;
import model.Product;
import model.Order;

@SuppressWarnings("serial")
public class OrderPanel extends JPanel implements ActionListener {
    
    private ViewManager manager;

    /**
     * Creates an instance of the InventoryItemPanel class.
     * 
     * @param manager the controller
     * @param product the product represented by this panel
     */
    
    public OrderPanel(ViewManager manager, Order order) {
        super(new BorderLayout());
        
        this.manager = manager;
        this.init(order);
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
    }
    
    /*
     * Initializes all UI components.
     */

    private void init(Order o) {
        JPanel content = getContentPanel(o);
        JPanel actions = getActionPanel(o);
        
        this.add(content, BorderLayout.CENTER);
        this.add(actions, BorderLayout.EAST);
        this.add(new JSeparator(), BorderLayout.SOUTH);
    }
    
    /*
     * Initializes the content panel UI components.
     */
    
    private JPanel getContentPanel(Order o) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        
        JLabel order = new JLabel("Customer" + o.getCustomer().getCustomerId() + "purchased order #" + o.getOrderId());
        JLabel purchase = new JLabel("Customer purchased on date: " + convertDate(o.getOrderDate()) + ", Status of the order: " + o.getStatus());
        JLabel total = new JLabel("Total: " + o.getTotal());
        
        order.setFont(new Font("DialogInput", Font.BOLD, 18));
        purchase.setFont(new Font("DialogInput", Font.ITALIC, 12));
        total.setFont(new Font("DialogInput", Font.ITALIC, 12));
        
        panel.add(order);
        panel.add(purchase);
        panel.add(total);
        
        return panel;
    }
    
    private String convertDate(long date) {
        String dateStr = String.valueOf(date);
        
        int year = Integer.valueOf(dateStr.substring(0, 4));
        int month = Integer.valueOf(dateStr.substring(4, 6));
        int day = Integer.valueOf(dateStr.substring(6));
        
        return getMonth(month) + " " + day + ", " + year;
    }
    
    /*
     * Converts a numeric month to its string equivalent.
     */
    
    private String getMonth(int month) {
        switch (month) {
            case 1: return "January";
            case 2: return "February";
            case 3: return "March";
            case 4: return "April";
            case 5: return "May";
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
            default: return "";
        }
    }
    
    
    /*
     * Initializes the action panel UI components.
     */
    
    private JPanel getActionPanel(Order o) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        
//        JLabel name = new JLabel("$" + c.getFirstName() + c.getLastName(), SwingConstants.CENTER);
//        name.setFont(new Font("DialogInput", Font.BOLD, 15));
        
        JButton edit = new JButton("Edit");
        edit.putClientProperty("id", o.getOrderId());
        edit.putClientProperty("type", "EDIT");
        edit.addActionListener(this);

//        panel.add(name);
        panel.add(edit);
        
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        Long id = (Long) source.getClientProperty("id");
        String type = (String) source.getClientProperty("type");
        
        for (Order o : manager.getOrders()) {
            if (id.longValue() == o.getOrderId() && type.equals("EDIT")) {
            					
                manager.attachOrder(o);
                manager.refreshCart();
                manager.switchTo(MidtownComics.OrderView);
            }
        }
        
    }
}