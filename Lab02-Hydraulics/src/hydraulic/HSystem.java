package hydraulic;

import java.util.Arrays;

/**
 * Main class that acts as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystem {
	private static final int MAX_ELEMENTS=100;

	private Element system[] = new Element[MAX_ELEMENTS];
	int nElements=0;
// R1
	/**
	 * Adds a new element to the system
	 * @param elem the new element to be added to the system
	 */
	public void addElement(Element elem){
		system[nElements++]=elem;
	}
	
	/**
	 * returns the element added so far to the system
	 * @return an array of elements whose length is equal to 
	 * 							the number of added elements
	 */
	public Element[] getElements(){
		return Arrays.copyOf(system, nElements);
	}

// R4
	/**
	 * starts the simulation of the system
	 */
	public void simulate(SimulationObserver observer){
		
		for(Element x : system) {
			if(x instanceof  Source )
				x.elementSimulate(SimulationObserver.NO_FLOW, observer);	
		}
	}
	

// R6
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		//TODO: to be implemented
		StringBuffer s = new StringBuffer();
		
		for(Element x : system) {
			if(x instanceof  Source )
				s=x.printLayout(s);	
		}
		
		return s.toString();
	}

// R7
	/**
	 * Deletes a previously added element with the given name from the system
	 */
	public boolean deleteElement(String name) {
		boolean out=true;
		Element[] tmp = new Element[MAX_ELEMENTS];
		int i=0;
		for( Element x : system ) {
			if( x!=null ) {
				if(x.getName().equals(name) ) {
					out=x.delete(name);
					if(!out)
						tmp[i++]=x;	
				}
				else
					tmp[i++]=x;
			}
		}
		system=tmp;
		nElements=i;
		return out;
	}

// R8
	/**
	 * starts the simulation of the system; if {@code enableMaxFlowCheck} is {@code true},
	 * checks also the elements maximum flows against the input flow
	 */
	public void simulate(SimulationObserver observer, boolean enableMaxFlowCheck) {
		simulate(observer);
		if(enableMaxFlowCheck) {
			for(Element x : system) {
				if(x instanceof  Source )
					x.testFlow(0.0, observer);	
			}
		}

	}
}
