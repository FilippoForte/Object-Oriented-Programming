package hydraulic;

/**
 * Represents a source of water, i.e. the initial element for the simulation.
 *
 * Lo status of the source is defined through the method
 * {@link #setFlow(double) setFlow()}.
 */
public class Source extends Element {
	
	private double flow;
	
	/**
	 * constructor
	 * @param name name of the source element
	 */
	public Source(String name) {
		super(name);
	}

	
	/**
	 * Define the flow of the source to be used during the simulation
	 *
	 * @param flow flow of the source (in cubic meters per hour)
	 */
	public void setFlow(double flow){
		this.flow=flow;
	}


	@Override
	public void elementSimulate(double prevFlow, SimulationObserver observer) {
		observer.notifyFlow("Source", getName(), prevFlow, this.flow);
		getOutput().elementSimulate(this.flow, observer);
	}


	@Override
	protected StringBuffer printLayout(StringBuffer s) {
		
		
		s.append("["+getName()+"]Source ");
		if(getOutput()!=null) {
			s.append(" -> ");
			getOutput().printLayout(s);
		}else 
			s.append("*");
	
		return s;		
	}

	
	@Override
	public void setMaxFlow(double maxFlow) {
		this.setFlow(Double.MAX_VALUE);
	}


	@Override
	protected void testFlow(double prevFlow, SimulationObserver observer) {
			getOutput().testFlow(flow, observer);
	}

}
