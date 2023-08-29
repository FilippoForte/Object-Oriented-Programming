package hydraulic;

import java.util.Arrays;
/**
 * Represents a multisplit element, an extension of the Split that allows many outputs
 * 
 * During the simulation each downstream element will
 * receive a stream that is determined by the proportions.
 */

public class Multisplit extends Split {
	
	private double[] proportion;
	
	
	/**
	 * Constructor
	 * @param name the name of the multi-split element
	 * @param numOutput the number of outputs
	 */
	public Multisplit(String name, int numOutput) {
		super(name, numOutput);
	}
	
	
	@Override
	public Element[] getOutputs(){
		
		return Arrays.copyOf(outputs, outputs.length);
	}

	
	/**
	 * Define the proportion of the output flows w.r.t. the input flow.
	 * 
	 * The sum of the proportions should be 1.0 and 
	 * the number of proportions should be equals to the number of outputs.
	 * Otherwise a check would detect an error.
	 * 
	 * @param proportions the proportions of flow for each output
	 */
	public void setProportions(double... proportions) {
		proportion = new double [proportions.length];
		double s=0;
		for (double d : proportions) 
			s+=d;
		if(s==1.0)
			this.proportion=proportions.clone();
		else {
			System.out.println("Wrong proportions");
			return;
		}
	}
	
	@Override
	public void elementSimulate(double prevFlow, SimulationObserver observer) {
		
		double[] tmp = Arrays.copyOf(proportion, proportion.length);
		for(int i=0; i<proportion.length; ++i)
			tmp[i]*=prevFlow;
		
		observer.notifyFlow("Multi Split", getName(), prevFlow, tmp);
		for(int i=0; i<outputs.length; i++) {
			outputs[i].elementSimulate(prevFlow*proportion[i], observer);
		}
	}
	
	@Override
	protected void testFlow(double prevFlow, SimulationObserver observer) {
		if(prevFlow>getMaxFlow()) 
			observer.notifyFlowError("Multi Split", getName(), prevFlow, getMaxFlow());
		
		for(int i=0; i<outputs.length; i++) {
			if(outputs[i]!=null)
				outputs[i].testFlow(prevFlow*proportion[i], observer);
		}

		
	}
	
}
