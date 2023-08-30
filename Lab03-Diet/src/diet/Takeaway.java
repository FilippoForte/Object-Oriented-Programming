package diet;

import java.util.*;


/**
 * Represents a takeaway restaurant chain.
 * It allows managing restaurants, customers, and orders.
 */
public class Takeaway {

	private Food food;
	private List<Restaurant> restaurantsList = new ArrayList<>();
	private List<String> restaurantsNameList = new ArrayList<>();
	private List<Customer> customersList = new ArrayList<>();
	private List<Order> orderList = new ArrayList<>();
	
	/**
	 * Constructor
	 * @param food the reference {@link Food} object with materials and products info.
	 */
	public Takeaway(Food food){
		this.food=food;
	}

	/**
	 * Creates a new restaurant with a given name
	 *
	 * @param restaurantName name of the restaurant
	 * @return the new restaurant
	 */
	public Restaurant addRestaurant(String restaurantName) {
		Restaurant tmp = new Restaurant(restaurantName);
		restaurantsList.add(tmp);
		restaurantsNameList.add(restaurantName);
		return tmp;
	}

	/**
	 * Retrieves the names of all restaurants
	 *
	 * @return collection of restaurant names
	 */
	public Collection<String> restaurants() {
		return restaurantsNameList;
	}

	/**
	 * Creates a new customer for the takeaway
	 * @param firstName first name of the customer
	 * @param lastName	last name of the customer
	 * @param email		email of the customer
	 * @param phoneNumber mobile phone number
	 *
	 * @return the object representing the newly created customer
	 */
	public Customer registerCustomer(String firstName, String lastName, String email, String phoneNumber) {
		Customer tmp = new Customer(firstName, lastName, email, phoneNumber);
		customersList.add(tmp);
		return tmp;
	}

	/**
	 * Retrieves all registered customers
	 *
	 * @return sorted collection of customers
	 */
	public Collection<Customer> customers(){
		Collections.sort(customersList, ( o1 , o2 ) -> {
			Customer a = (Customer)o1;
			Customer b = (Customer)o2;
			if(a.getLastName().compareTo(b.getLastName())==0)
				return a.getFirstName().compareTo(b.getFirstName());
			else return a.getLastName().compareTo(b.getLastName());
		});
		return customersList;
	}


	/**
	 * Creates a new order for the chain.
	 *
	 * @param customer		 customer issuing the order
	 * @param restaurantName name of the restaurant that will take the order
	 * @param time	time of desired delivery
	 * @return order object
	 */
	public Order createOrder(Customer customer, String restaurantName, String time) {
		int h = Integer.parseInt(time.split(":")[0]);
		String m = time.split(":")[1];
		
		String newTime;
		if(h<10)
			newTime = String.format("0%d:%s", h, m);
		else 
			newTime=time;
		if(customersList.contains(customer)) {
			if(restaurantsNameList.contains(restaurantName)) {
				for(Restaurant r : restaurantsList) {
					if(r.isOpenAt(newTime)) {
						Order tmp = new Order(customer, r, newTime);
						orderList.add(tmp);
						r.addOrder(tmp);
						return tmp;
					}
					else {
						Order tmp = new Order(customer, r, r.getNextTime(newTime));
						orderList.add(tmp);
						r.addOrder(tmp);
						return tmp;
					}
				}
			}
			else
				System.out.println("Resturant not found");
		}else 
			System.out.println("Customer not found");
		
		return null;
	}

	/**
	 * Find all restaurants that are open at a given time.
	 *
	 * @param time the time with format {@code "HH:MM"}
	 * @return the sorted collection of restaurants
	 */
	public Collection<Restaurant> openRestaurants(String time){
		List<Restaurant> tmp = new ArrayList<>();
		
		for(Restaurant r : restaurantsList) 
			if(r.isOpenAt(time))
				tmp.add(r);
		
//		Collections.sort( tmp, ( o1, o2) -> {
//			Restaurant a = (Restaurant)o1;
//			Restaurant b = (Restaurant)o2;
//			return a.getName().compareTo(b.getName());
//			});
		Collections.sort(tmp, Comparator.comparing(Restaurant::getName));
		
		return tmp;
	}
}
