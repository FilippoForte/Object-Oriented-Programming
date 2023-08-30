package mountainhuts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * Class {@code Region} represents the main facade
 * class for the mountains hut system.
 * 
 * It allows defining and retrieving information about
 * municipalities and mountain huts.
 *
 */
public class Region {
	
	public String name;
	List<List<Integer>> altitudesRanges = new ArrayList<>();
	Map<String, Municipality> municipalityList = new HashMap<>();
	Map<String, MountainHut> mountainHutList = new HashMap<>();

	/**
	 * Create a region with the given name.
	 * 
	 * @param name
	 *            the name of the region
	 */
	public Region(String name) {
		this.name=name;
	}

	/**
	 * Return the name of the region.
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return name;
	}

	/**
	 * Create the ranges given their textual representation in the format
	 * "[minValue]-[maxValue]".
	 * 
	 * @param ranges
	 *            an array of textual ranges
	 */
	public void setAltitudeRanges(String... ranges) {
		
		for(String range : ranges) {
			List<Integer> tmp = new ArrayList<>();
			tmp.add(Integer.parseInt((range).split("-")[0]));
			tmp.add(Integer.parseInt((range).split("-")[1]));
			altitudesRanges.add(tmp);
		}

	}

	/**
	 * Return the textual representation in the format "[minValue]-[maxValue]" of
	 * the range including the given altitude or return the default range "0-INF".
	 * 
	 * @param altitude
	 *            the geographical altitude
	 * @return a string representing the range
	 */
	public String getAltitudeRange(Integer altitude) {
		
		if(altitudesRanges == null)
			return "0-INF";
		
		for (List<Integer> tmp: altitudesRanges) {
			if(tmp.get(0)<=altitude && tmp.get(1)>=altitude) {
				return tmp.get(0) + "-" + tmp.get(1);
			}
		}
		return "0-INF";
	}

	/**
	 * Return all the municipalities available.
	 * 
	 * The returned collection is unmodifiable
	 * 
	 * @return a collection of municipalities
	 */
	public Collection<Municipality> getMunicipalities() {
		return municipalityList.values();
	}

	/**
	 * Return all the mountain huts available.
	 * 
	 * The returned collection is unmodifiable
	 * 
	 * @return a collection of mountain huts
	 */
	public Collection<MountainHut> getMountainHuts() {
		return mountainHutList.values();
	}

	/**
	 * Create a new municipality if it is not already available or find it.
	 * Duplicates must be detected by comparing the municipality names.
	 * 
	 * @param name
	 *            the municipality name
	 * @param province
	 *            the municipality province
	 * @param altitude
	 *            the municipality altitude
	 * @return the municipality
	 */
	public Municipality createOrGetMunicipality(String name, String province, Integer altitude) {
		if(municipalityList.containsKey(name)) 
			return municipalityList.get(name);
		else {
			Municipality tmp = new Municipality(name, province, altitude);
			municipalityList.put(name, tmp);
			return tmp;
		}
		
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 *
	 * @param name
	 *            the mountain hut name
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return the mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, String category, Integer bedsNumber,
			Municipality municipality) {
		
		if(mountainHutList.containsKey(name)) 
			return mountainHutList.get(name); 
		else {
			MountainHut tmp = new MountainHut(name, null, category, bedsNumber, municipality);
			mountainHutList.put(name, tmp);
			return tmp;
		}
		
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 * 
	 * @param name
	 *            the mountain hut name
	 * @param altitude
	 *            the mountain hut altitude
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return a mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, Integer altitude, String category, Integer bedsNumber,
			Municipality municipality) {
		if(mountainHutList.containsKey(name)) 
			return mountainHutList.get(name);
		else {
			MountainHut tmp = new MountainHut(name, altitude, category, bedsNumber, municipality);
			mountainHutList.put(name, tmp);
			return tmp;
		}
	}

	/**
	 * Creates a new region and loads its data from a file.
	 * 
	 * The file must be a CSV file and it must contain the following fields:
	 * <ul>
	 * <li>{@code "Province"},
	 * <li>{@code "Municipality"},
	 * <li>{@code "MunicipalityAltitude"},
	 * <li>{@code "Name"},
	 * <li>{@code "Altitude"},
	 * <li>{@code "Category"},
	 * <li>{@code "BedsNumber"}
	 * </ul>
	 * 
	 * The fields are separated by a semicolon (';'). The field {@code "Altitude"}
	 * may be empty.
	 * 
	 * @param name
	 *            the name of the region
	 * @param file
	 *            the path of the file
	 */
	public static Region fromFile(String name, String file) {
		Region tmpRegion = new Region(name);
		for(String line : readData(file).subList(1, readData(file).size())) {
			String[] data = line.split(";");
			Municipality tmpMunicipality = tmpRegion.createOrGetMunicipality(data[1],data[0], Integer.parseInt(data[2]));
			tmpRegion.createOrGetMountainHut(data[3], data[4] != "" ? Integer.parseInt(data[4]) : null , data[5], Integer.parseInt(data[6]), tmpMunicipality);			
		}
		return tmpRegion;
		
	}

	/**
	 * Reads the lines of a text file.
	 *
	 * @param file path of the file
	 * @return a list with one element per line
	 */
	public static List<String> readData(String file) {
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			return in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return new ArrayList<>();
		}
	}

	/**
	 * Count the number of municipalities with at least a mountain hut per each
	 * province.
	 * 
	 * @return a map with the province as key and the number of municipalities as
	 *         value
	 */
	public Map<String, Long> countMunicipalitiesPerProvince() {		
		return municipalityList.values().stream().collect(groupingBy(p -> p.getProvince(), counting()));
	}

	/**
	 * Count the number of mountain huts per each municipality within each province.
	 * 
	 * @return a map with the province as key and, as value, a map with the
	 *         municipality as key and the number of mountain huts as value
	 */
	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {
		
		return mountainHutList.values().stream()
				.collect(
						groupingBy(
								p -> p.getMunicipality().getProvince(),
								groupingBy(
										p -> p.getMunicipality().getName(),
										counting()
										)
								)
						);

	}

	/**
	 * Count the number of mountain huts per altitude range. If the altitude of the
	 * mountain hut is not available, use the altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the number of mountain huts
	 *         as value
	 */
	public Map<String, Long> countMountainHutsPerAltitudeRange() {
		return mountainHutList.values().stream().collect(groupingBy(p -> getAltitudeRange(p.getAltitudeOrMunicipality()),counting()));
	}

	/**
	 * Compute the total number of beds available in the mountain huts per each
	 * province.
	 * 
	 * @return a map with the province as key and the total number of beds as value
	 */
	public Map<String, Integer> totalBedsNumberPerProvince() {
		return mountainHutList.values().stream().collect(groupingBy(p -> p.getMunicipality().getProvince(), summingInt(MountainHut::getBedsNumber)));
	}

	/**
	 * Compute the maximum number of beds available in a single mountain hut per
	 * altitude range. If the altitude of the mountain hut is not available, use the
	 * altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the maximum number of beds
	 *         as value
	 */
	public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {

		
		return mountainHutList.values()
				.stream().collect(
						groupingBy(
								p -> getAltitudeRange(p.getAltitudeOrMunicipality()),
								HashMap::new,
								mapping(
										p -> p.getBedsNumber(), 
										maxBy((a, b) -> a-b)
										)
								)
						);
	}

	/**
	 * Compute the municipality names per number of mountain huts in a municipality.
	 * The lists of municipality names must be in alphabetical order.
	 * 
	 * @return a map with the number of mountain huts in a municipality as key and a
	 *         list of municipality names as value
	 */
	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {
		
		Map<Long, List<String>> tmp = mountainHutList.values().stream().collect(
				groupingBy(
						h -> h.getMunicipality().getName(),
						counting()
						)).entrySet().stream().collect(
								groupingBy(
										entry -> entry.getValue(),
										mapping(
											hut -> hut.getKey(),
											toList())
										));
		
		for (List<String> list : tmp.values())
			Collections.sort(list, (a,b) -> a.compareTo(b));
		
		return tmp;
	}
}
