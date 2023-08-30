package diet;

import java.util.*;

/**
 * Represents and order issued by an {@link Customer} for a {@link Restaurant}.
 *
 * When an order is printed to a string is should look like:
 * <pre>
 *  RESTAURANT_NAME, USER_FIRST_NAME USER_LAST_NAME : DELIVERY(HH:MM):
 *  	MENU_NAME_1->MENU_QUANTITY_1
 *  	...
 *  	MENU_NAME_k->MENU_QUANTITY_k
 * </pre>
 */
public class Order implements Comparator<Order> {

	/**
	 * Possible order statuses
	 */
	public enum OrderStatus {
		ORDERED, READY, DELIVERED
	}

	/**
	 * Accepted payment methods
	 */
	public enum PaymentMethod {
		PAID, CASH, CARD
	}
	
	private Customer customer;
	private Restaurant restaurant;
	private String time;
	private OrderStatus status;
	private PaymentMethod payment;
	private Map<String, Integer> menuList = new HashMap<>();

	public Order(Customer customer, Restaurant restaurant, String time) {
		this.customer=customer;
		this.restaurant=restaurant;
		this.time=time;
		setStatus(OrderStatus.ORDERED);
		setPaymentMethod(PaymentMethod.CASH);
	}

	/**
	 * Set payment method
	 * @param pm the payment method
	 */
	public void setPaymentMethod(PaymentMethod pm) {
		this.payment=pm;
	}

	/**
	 * Retrieves current payment method
	 * @return the current method
	 */
	public PaymentMethod getPaymentMethod() {
		return payment;
	}

	/**
	 * Set the new status for the order
	 * @param os new status
	 */
	public void setStatus(OrderStatus os) {
		this.status=os;
	}

	/**
	 * Retrieves the current status of the order
	 *
	 * @return current status
	 */
	public OrderStatus getStatus() {
		return status;
	}

	/**
	 * Add a new menu to the order with a given quantity
	 *
	 * @param menu	menu to be added
	 * @param quantity quantity
	 * @return the order itself (allows method chaining)
	 */
	public Order addMenus(String menu, int quantity) {
		if(menuList.containsKey(menu))
			menuList.put(menu, quantity);
		else
			menuList.put(menu, quantity);
		return this;
	}
	
	@Override
	public String toString() {
		
		StringBuilder s = new StringBuilder();
		
		s.append(restaurant.getName()+", "+customer.getFirstName()+" "+customer.getLastName()+" : ("+time+"):\n");
		for(String menuName : menuList.keySet())
			s.append("\t"+menuName+"->"+menuList.get(menuName)+"\n");
		
		return s.toString();		
	}

	@Override
	public int compare(Order o1, Order o2) {
		if(o1.restaurant.getName().compareTo(o2.restaurant.getName())==0) 
			if(o1.customer.getLastName().compareTo(o2.customer.getLastName())==0)
				if(o1.customer.getFirstName().compareTo(o2.customer.getFirstName())==0)
					return o1.time.compareTo(o2.time);
				else return o1.customer.getFirstName().compareTo(o2.customer.getFirstName());
			else return o1.customer.getLastName().compareTo(o2.customer.getLastName());
		else return o1.restaurant.getName().compareTo(o2.restaurant.getName());
	}

}
