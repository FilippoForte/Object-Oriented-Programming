package mountainhuts;

import java.util.Optional;

/**
 * Represents a mountain hut
 * 
 * It includes a name, optional altitude, category,
 * number of beds and location municipality.
 *  
 *
 */
public class MountainHut {
	
	private String name;
	private String category;
	private Optional<Integer> altitude;
	private Integer bedsNumber;
	private Municipality municipality;

	public MountainHut(String name, Integer altitude, String category, Integer bedsNumber, Municipality municipality) {
		this.name = name;
		this.altitude = Optional.ofNullable(altitude);
		this.category = category;
		this.bedsNumber = bedsNumber;
		this.municipality = municipality;
	}

	public String getName() {
		return name;
	}

	public Integer getAltitudeOrMunicipality() {
		if(altitude.isPresent()) 
			return altitude.get();
		else		
			return municipality.getAltitude();
	}
	
	public Optional<Integer> getAltitude() {
		return altitude;
	}

	public String getCategory() {
		return category;
	}

	public Integer getBedsNumber() {
		return bedsNumber;
	}

	public Municipality getMunicipality() {
		return municipality;
	}

}
