package diet;

import java.util.*;

import diet.Order.OrderStatus;

/**
 * Represents a restaurant class with given opening times and a set of menus.
 */
public class Restaurant {
	
	private String name;
	private List<List<String>> openingHours = new ArrayList<>();
	private Map<String, Menu> menuList = new HashMap<>();
	private List<Order> ordersList = new ArrayList<>();
	
	public Restaurant(String restaurantName) {
		this.name=restaurantName;
	}

	/**
	 * retrieves the name of the restaurant.
	 *
	 * @return name of the restaurant
	 */
	public String getName() {
		return name;
	}

	/**
	 * Define opening times.
	 * Accepts an array of strings (even number of elements) in the format {@code "HH:MM"},
	 * so that the closing hours follow the opening hours
	 * (e.g., for a restaurant opened from 8:15 until 14:00 and from 19:00 until 00:00,
	 * arguments would be {@code "08:15", "14:00", "19:00", "00:00"}).
	 *
	 * @param hm sequence of opening and closing times
	 */
	public void setHours(String ... hm) {
		int i=0;
		while(i<hm.length) {
			List<String> tmp = new ArrayList<>(2);
			tmp.add(hm[i++]);
			tmp.add(hm[i++]);
			openingHours.add(tmp);
		}
	}
	

	/**
	 * Checks whether the restaurant is open at the given time.
	 *
	 * @param time time to check
	 * @return {@code true} is the restaurant is open at that time
	 */
	public boolean isOpenAt(String time){
		for(List<String> l : openingHours) {
			if(l.get(0).compareTo(time)<=0 && l.get(1).compareTo(time)>=0)
				return true;
			if(l.get(1).equals("00:00"))
				return true;
		}
		return false;
	}

	public String getNextTime(String time) {
		int max=Integer.MAX_VALUE;
		Collections.sort(openingHours, (a,b) -> a.get(0).compareTo(b.get(0)));
		
		for(List<String> l : openingHours) {
			int diff=0;
			diff=l.get(0).compareTo(time);
			if(diff>0) 
				return l.get(0);
		}
		return openingHours.get(0).get(0);
	}
	

	
	/**
	 * Adds a menu to the list of menus offered by the restaurant
	 *
	 * @param menu the menu
	 */
	public void addMenu(Menu menu) {
		menuList.put(menu.getName(), menu);
	}

	/**
	 * Gets the restaurant menu with the given name
	 *
	 * @param name	name of the required menu
	 * @return menu with the given name
	 */
	public Menu getMenu(String name) {
		return menuList.get(name);
	}
	
	public void addOrder(Order o) {
		ordersList.add(o);
	}

	/**
	 * Retrieve all order with a given status with all the relative details in text format.
	 *
	 * @param status the status to be matched
	 * @return textual representation of orders
	 */
	public String ordersWithStatus(OrderStatus status) {
		
		//ordersList.sort();
		Collections.sort(ordersList, (a , b ) -> a.compare(a, b));
		
		StringBuilder s = new StringBuilder();
		for(Order o : ordersList) 
			if(o.getStatus().equals(status)) 
				s.append(o.toString());
			
		return s.toString();
	}
}
