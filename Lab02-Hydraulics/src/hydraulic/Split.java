package hydraulic;

import java.util.Arrays;

/**
 * Represents a split element, a.k.a. T element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends Element{
	
	protected Element[] outputs;
	private int nOutputs;
	
	/**
	 * Constructor
	 * @param name name of the split element
	 */
	public Split(String name) {
		super(name);
		outputs = new Element[2];
		this.nOutputs=0;
	}
	
	
	/**
	 * Contructor
	 * @param name name of the split element
	 * @param nOutputs number of outputs
	 */
	public Split(String name, int nOutputs) {
		super(name);
		outputs = new Element[nOutputs];
	}

	/**
	 * Connects a specific output of this element to a given element.
	 * The given element will be connected downstream of this element
	 * 
	 * @param elem the element that will be placed downstream
	 * @param index the output index that will be used for the connection
	 */
	public void connect(Element elem, int index){
		this.outputs[index]=elem;
		nOutputs++;
		if(elem!=null)
			elem.setInput(this);
	}
	
	/**
	 * Retrieves the elements connected downstream of this element
	 * 
	 * @return downstream element
	 */
	public Element[] getOutputs(){
		return Arrays.copyOf(outputs, 2);
	}

	@Override
	public void elementSimulate(double prevFlow, SimulationObserver observer) {

		observer.notifyFlow("Split", getName(), prevFlow, new double []{prevFlow/2, prevFlow/2});
		outputs[0].elementSimulate(prevFlow/2, observer);
		outputs[1].elementSimulate(prevFlow/2, observer);
	}

	@Override
	protected StringBuffer printLayout(StringBuffer s) {
		s.append("["+getName()+"]Split ");
		int l=s.length();
		String padding = String.format("%"+l+"s","");
		for( int i=0; i<outputs.length; i++) {
			if(outputs[i]!=null) {
				s.append("+-> ");
				outputs[i].printLayout(s);
				if(i<outputs.length-1)
					s.append("\n"+padding+"|\n"+padding+"");
			}else 
				s.append("+-> *\n"+padding+"|\n"+padding+"");
		}
		return s;		
	}
	
	@Override
	public boolean delete(String name) {
		if(nOutputs==1) {
			getInput().connect(outputs[0]);
			outputs[0].setInput(getInput());
			return true;
		} else if(nOutputs==0) {
			getInput().connect(null);
			return true;
		}else
			return false;

	}


	@Override
	protected void testFlow(double prevFlow, SimulationObserver observer) {
		if(prevFlow>getMaxFlow()) 
			observer.notifyFlowError("Split", getName(), prevFlow, getMaxFlow());
		
		for(Element x : outputs)
			if(x!=null)
				x.testFlow(prevFlow, observer);	
	}
	

	@Override
	protected boolean deleteSink(String name) {
		Element[] tmp = new Element[nOutputs-1];
		int j=0;
		for(int i=0; i<nOutputs; ++i) {
			if(outputs[i].getName().equals(name)) {
				outputs[i]=null;
			}
			else
				tmp[j++]=outputs[i];
		}
		outputs=tmp;
		nOutputs--;
		return true;
	}
	
}
