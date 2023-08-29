package hydraulic;

/**
 * Represents a tap that can interrupt the flow.
 * 
 * The status of the tap is defined by the method
 * {@link #setOpen(boolean) setOpen()}.
 */

public class Tap extends Element {
	private boolean open;


	/**
	 * Constructor
	 * @param name name of the tap element
	 */
	public Tap(String name) {
		super(name);
		this.open=false;
	}

	/**
	 * Set whether the tap is open or not. The status is used during the simulation.
	 *
	 * @param open opening status of the tap
	 */
	public void setOpen(boolean open){
		this.open=open;
	}

	@Override
	public void elementSimulate(double prevFlow, SimulationObserver observer) {
		
		if(open) {
			observer.notifyFlow("Tap", getName(), prevFlow, prevFlow);
			getOutput().elementSimulate(prevFlow, observer);
		}else {
			observer.notifyFlow("Tap", getName(), prevFlow, 0.0);
			getOutput().elementSimulate(0.0, observer);
		}
		
		
	}

	@Override
	protected StringBuffer printLayout(StringBuffer s) {
		s.append("["+getName()+"]Tap");
		if(getOutput()!=null) {
			s.append(" -> ");
			getOutput().printLayout(s);
		}else 
			s.append("*");
		
		return s;
	}

	@Override
	protected void testFlow(double prevFlow, SimulationObserver observer) {
		if(prevFlow>getMaxFlow()) 
			observer.notifyFlowError("Tap", getName(), prevFlow, getMaxFlow());
	
		if(open)
			getOutput().testFlow(prevFlow, observer);
		else
			getOutput().testFlow(0.0, observer);
	}
	
	
	
}
