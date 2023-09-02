package social;

import java.util.*;

//model

public class Social {

	private Map<String, Person> personsList;
	private Map<String, Group> groupList;
	
	public Social() {
		this.personsList = new HashMap<String, Person>();
		this.groupList = new HashMap<>();
	}
	
	/**
	 * Creates a new account for a person
	 * 
	 * @param code	nickname of the account
	 * @param name	first name
	 * @param surname last name
	 * @throws PersonExistsException in case of duplicate code
	 */
	public void addPerson(String code, String name, String surname) throws PersonExistsException {
		if (!personsList.containsKey(code)) 
			personsList.put(code, new Person(code, name, surname));
		else 
			throw new PersonExistsException();
	}

	/**
	 * Retrieves information about the person given their account code.
	 * The info consists in name and surname of the person, in order, separated by blanks.
	 * 
	 * @param code account code
	 * @return the information of the person
	 * @throws NoSuchCodeException
	 */
	public String getPerson(String code) throws NoSuchCodeException {
		if (!personsList.containsKey(code)) 
			throw new NoSuchCodeException();
		else 
			return personsList.get(code).getCode() + " " + personsList.get(code).getName() + " " + personsList.get(code).getSurname();
		
		
	}

	/**
	 * Define a friendship relationship between to persons given their codes.
	 * 
	 * Friendship is bidirectional: if person A is friend of a person B, that means that person B is a friend of a person A.
	 * 
	 * @param codePerson1	first person code
	 * @param codePerson2	second person code
	 * @throws NoSuchCodeException in case either code does not exist
	 */
	public void addFriendship(String codePerson1, String codePerson2) throws NoSuchCodeException {
		if(!personsList.containsKey(codePerson1))
			throw new NoSuchCodeException();
		if(!personsList.containsKey(codePerson2))
			throw new NoSuchCodeException();
		personsList.get(codePerson1).addFriend(codePerson2);
		personsList.get(codePerson2).addFriend(codePerson1);
	}

	/**
	 * Retrieve the collection of their friends given the code of a person.
	 * 
	 * 
	 * @param codePerson code of the person
	 * @return the list of person codes
	 * @throws NoSuchCodeException in case the code does not exist
	 */
	public Collection<String> listOfFriends(String codePerson) throws NoSuchCodeException {
		if(!personsList.containsKey(codePerson))
			throw new NoSuchCodeException();
		return personsList.get(codePerson).getFriends();
	}

	/**
	 * Retrieves the collection of the code of the friends of the friends
	 * of the person whose code is given, i.e. friends of the second level.
	 * 
	 * 
	 * @param codePerson code of the person
	 * @return collections of codes of second level friends
	 * @throws NoSuchCodeException in case the code does not exist
	 */
	public Collection<String> friendsOfFriends(String codePerson) throws NoSuchCodeException {
		if(!personsList.containsKey(codePerson))
			throw new NoSuchCodeException();
		
		List<String> tmp = new ArrayList<>();
		
		for(String friend : personsList.get(codePerson).getFriends()) 
			tmp.addAll(personsList.get(friend).getFriends());

		return tmp;
	}

	/**
	 * Retrieves the collection of the code of the friends of the friends
	 * of the person whose code is given, i.e. friends of the second level.
	 * The result has no duplicates.
	 * 
	 * 
	 * @param codePerson code of the person
	 * @return collections of codes of second level friends
	 * @throws NoSuchCodeException in case the code does not exist
	 */
	public Collection<String> friendsOfFriendsNoRepetition(String codePerson) throws NoSuchCodeException {
		if(!personsList.containsKey(codePerson))
			throw new NoSuchCodeException();
		
		List<String> tmp = new ArrayList<>();
		
		for(String friend : personsList.get(codePerson).getFriends()) 
			tmp.addAll(personsList.get(friend).getFriends());
		Set<String> tmpSet = new HashSet<>(tmp);
		tmp.clear();
		tmpSet.remove(codePerson);
		tmp.addAll(tmpSet);
		return tmp;
	}

	/**
	 * Creates a new group with the given name
	 * 
	 * @param groupName name of the group
	 */
	public void addGroup(String groupName) {
		groupList.put(groupName, new Group(groupName));

	}

	/**
	 * Retrieves the list of groups.
	 * 
	 * @return the collection of group names
	 */
	public Collection<String> listOfGroups() {
		return groupList.keySet();
	}

	/**
	 * Add a person to a group
	 * 
	 * @param codePerson person code
	 * @param groupName  name of the group
	 * @throws NoSuchCodeException in case the code or group name do not exist
	 */
	public void addPersonToGroup(String codePerson, String groupName) throws NoSuchCodeException {
		
		if(!groupList.containsKey(groupName) || !personsList.containsKey(codePerson))
			throw new NoSuchCodeException();
		else 
			groupList.get(groupName).addMember(codePerson);
		

	}

	/**
	 * Retrieves the list of people on a group
	 * 
	 * @param groupName name of the group
	 * @return collection of person codes
	 */
	public Collection<String> listOfPeopleInGroup(String groupName) {
		if(!groupList.containsKey(groupName))
			return null;
		return groupList.get(groupName).getListOfMembers();
	}

	/**
	 * Retrieves the code of the person having the largest
	 * group of friends
	 * 
	 * @return the code of the person
	 */
	public String personWithLargestNumberOfFriends() {
		return personsList.values().stream().max((a,b) -> a.getFriends().size()-b.getFriends().size()).map(a -> a.getCode()).get();
	}

	/**
	 * Find the code of the person with largest number
	 * of second level friends
	 * 
	 * @return the code of the person
	 */
	public String personWithMostFriendsOfFriends() {
		int max=0;
		String res="";
		for(Person p : personsList.values()) {
			int sum=0;
			for(String f : p.getFriends()) 
				sum+=personsList.get(f).getFriends().size();
			
			if(sum>max) {
				max=sum;
				res=p.getCode();
			}
		}
		return res;
	}

	/**
	 * Find the name of group with the largest number of members
	 * 
	 * @return the name of the group
	 */
	public String largestGroup() {
		return groupList.values().stream().max((a, b) -> a.getListOfMembers().size() - b.getListOfMembers().size()).map(a -> a.getName()).get();
	}

	/**
	 * Find the code of the person that is member of
	 * the largest number of groups
	 * 
	 * @return the code of the person
	 */
	public String personInLargestNumberOfGroups() {
		int max=0;
		String res = "";
		for(String p : personsList.keySet()) {
			int sum=0;
			for(Group g : groupList.values()) 
				if(g.getListOfMembers().contains(p)) 
					sum++;
			if(sum>max) {
				max=sum;
				res=p;
			}
		}
		return res;
	}
}