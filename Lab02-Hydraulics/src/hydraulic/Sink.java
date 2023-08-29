package hydraulic;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends Element {

	/**
	 * Constructor
	 * @param name name of the sink element
	 */
	public Sink(String name) {
		super(name);
	}
	
	@Override
	public void connect(Element elem) {	
		return;
	}

	@Override
	public void elementSimulate(double prevFlow, SimulationObserver observer) {
		observer.notifyFlow("Sink", getName(), prevFlow, SimulationObserver.NO_FLOW);
	}

	@Override
	protected StringBuffer printLayout(StringBuffer s) {
		s.append("["+getName()+"]Sink");
		return s;		
	}

	@Override
	protected void testFlow(double prevFlow, SimulationObserver observer) {
		if(prevFlow>getMaxFlow()) 
			observer.notifyFlowError("Sink", getName(), prevFlow, getMaxFlow());
	}
	
	@Override
	public boolean delete(String name) {
		if(getInput() instanceof Split || getInput() instanceof Multisplit ) {
			return getInput().deleteSink(getName());
		}else
			getInput().connect(null);
		return true;
	}
}
