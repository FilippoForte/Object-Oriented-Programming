package hydraulic;


/**
 * Represents the generic abstract element of an hydraulics system.
 * It is the base class for all elements.
 *
 * Any element can be connect to a downstream element
 * using the method {@link #connect(Element) connect()}.
 * 
 * The class is abstract since it is not intended to be instantiated,
 * though all methods are defined to make subclass implementation easier.
 */
public abstract class Element {
	
	private String name;
	private Element output;
	private Element input;


	private double maxFlow;

	public Element(String name) {
		this.name=name;
	}

	/**
	 * getter method for the name of the element
	 * 
	 * @return the name of the element
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Connects this element to a given element.
	 * The given element will be connected downstream of this element
	 * 
	 * In case of element with multiple outputs this method operates on the first one,
	 * it is equivalent to calling {@code connect(elem,0)}. 
	 * 
	 * @param elem the element that will be placed downstream
	 */
	public void connect(Element elem) {
		this.output=elem;
		if(elem!=null)
			elem.input=this;
	}
	
	/**
	 * Retrieves the single element connected downstream of this element
	 * 
	 * @return downstream element
	 */
	public Element getOutput(){
		return this.output;
	}

	/**
	 * @return input element
	 */
	public Element getInput() {
		return input;
	}
	
	public void setInput(Element input) {
		this.input = input;
	}
	
	/**
	 * Defines the maximum input flow acceptable for this element
	 * 
	 * @param maxFlow maximum allowed input flow
	 */
	public void setMaxFlow(double maxFlow) {
		this.maxFlow=maxFlow;
	}
	

	/**
	 * Returns the maxFlow possible
	 * @return maxFlow
	 */
	public double getMaxFlow() {
		return maxFlow;
	}
	
	public abstract void elementSimulate(double prevFlow, SimulationObserver observer);

	protected abstract StringBuffer printLayout(StringBuffer s);

	public boolean delete(String name) {
		input.output=this.getOutput();
		this.getOutput().input=this.getInput();
		return true;
	}

	protected abstract void testFlow(double d, SimulationObserver observer);

	protected boolean deleteSink(String name) {
		return true;
	};

}
