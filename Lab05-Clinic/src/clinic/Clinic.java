package clinic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * Represents a clinic with patients and doctors.
 * 
 */
public class Clinic {

	private Map<String ,Patient> patientMap;
	private Map<Integer ,Doctor> doctorMap;
	private Map<String, Integer> assignedDoctors;
	
	
	public Clinic() {
		this.patientMap = new HashMap<>();
		this.doctorMap = new HashMap<>();
		this.assignedDoctors = new HashMap<>();
	}


	/**
	 * Add a new clinic patient.
	 * 
	 * @param first first name of the patient
	 * @param last last name of the patient
	 * @param ssn SSN number of the patient
	 */
	public void addPatient(String first, String last, String ssn) {
		patientMap.put(ssn ,new Patient(ssn, first, last));
	}


	/**
	 * Retrieves a patient information
	 * 
	 * @param ssn SSN of the patient
	 * @return the object representing the patient
	 * @throws NoSuchPatient in case of no patient with matching SSN
	 */
	public String getPatient(String ssn) throws NoSuchPatient {
		
		if (!patientMap.containsKey(ssn)) {
			throw new NoSuchPatient();
		}
		else return patientMap.get(ssn).getLastName() + " " 
					+ patientMap.get(ssn).getFirstName() + " (" 
					+ patientMap.get(ssn).getSSN() +")" ;
	}

	/**
	 * Add a new doctor working at the clinic
	 * 
	 * @param first first name of the doctor
	 * @param last last name of the doctor
	 * @param ssn SSN number of the doctor
	 * @param docID unique ID of the doctor
	 * @param specialization doctor's specialization
	 */
	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
		doctorMap.put(docID, new Doctor(ssn, first, last, docID, specialization));
		patientMap.put(ssn ,new Patient(ssn, first, last));
	}

	/**
	 * Retrieves information about a doctor
	 * 
	 * @param docID ID of the doctor
	 * @return object with information about the doctor
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public String getDoctor(int docID) throws NoSuchDoctor {
		if (!doctorMap.containsKey(docID)) 
			throw new NoSuchDoctor();
		else return doctorMap.get(docID).getLastName() + " " 
					+ doctorMap.get(docID).getFirstName() + " (" 
					+ doctorMap.get(docID).getSSN() +") [" 
					+ doctorMap.get(docID).getBadgeID() + "]: " 
					+ doctorMap.get(docID).getSpecialization();
	}
	
	/**
	 * Assign a given doctor to a patient
	 * 
	 * @param ssn SSN of the patient
	 * @param docID ID of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {

		if(!patientMap.containsKey(ssn))
			throw new NoSuchPatient();
		if(!doctorMap.containsKey(docID))
			throw new NoSuchDoctor();
		
		assignedDoctors.put(ssn, docID);
	}

	/**
	 * Retrieves the id of the doctor assigned to a given patient.
	 * 
	 * @param ssn SSN of the patient
	 * @return id of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor has been assigned to the patient
	 */
	public int getAssignedDoctor(String ssn) throws NoSuchPatient, NoSuchDoctor {
		if(!patientMap.containsKey(ssn))
			throw new NoSuchPatient();
		if (!assignedDoctors.containsKey(ssn))
			throw new NoSuchDoctor();
				
		return assignedDoctors.get(ssn);
	}
	
	/**
	 * Retrieves the patients assigned to a doctor
	 * 
	 * @param id ID of the doctor
	 * @return collection of patient SSNs
	 * @throws NoSuchDoctor in case the {@code id} does not match any doctor 
	 */
	public Collection<String> getAssignedPatients(int id) throws NoSuchDoctor {
		
		if(!doctorMap.containsKey(id))
			throw new NoSuchDoctor();
		
		if(!assignedDoctors.containsValue(id))
			return Collections.emptyList();

		return assignedDoctors.entrySet().stream().collect(
			Collectors.groupingBy(
					p -> p.getValue(),
					Collectors.mapping(
							s -> s.getKey(), 
							Collectors.toList())						
					)).get(id);
	}
	
	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and speciality.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method should be able
	 * to ignore the row and skip to the next one.<br>

	 * 
	 * @param reader reader linked to the file to be read
	 * @throws IOException in case of IO error
	 */
	public int loadData(Reader reader) throws IOException {
		
		BufferedReader r = new BufferedReader(reader);
		List<String> tmp = r.lines().toList();
		int n=0;
		
		for(String line : tmp) {

			String[] fields = line.split("\s*;\s*");
			fields[fields.length-1]=fields[fields.length-1].trim();
			
			if(fields[0].equals("P")) { //patient
				if (fields.length==4) {
					patientMap.put(fields[3], new Patient(fields[3], fields[1], fields[2]));
					n++;
				}
			}else { //doctor
				if (fields.length==6) {
					try {
						doctorMap.put(Integer.parseInt(fields[1]), new Doctor(fields[4], fields[2], fields[3], Integer.parseInt(fields[1]), fields[5]));	
						n++;
					} catch (NumberFormatException e) {
						// TODO: handle exception
					}
					
				}
			}
			
		}
		return n;
	}


	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and speciality.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method calls the
	 * {@link ErrorListener#offending} method passing the line itself,
	 * ignores the row, and skip to the next one.<br>
	 * 
	 * @param reader reader linked to the file to be read
	 * @param listener listener used for wrong line notifications
	 * @throws IOException in case of IO error
	 */
	public int loadData(Reader reader, ErrorListener listener) throws IOException {
		
		Pattern p = Pattern.compile("(?:P|M *; *(?<id>[0-9]+)) *; *(?<name>[a-zA-Z]+) *; *(?<surname>[a-zA-Z]+) *; *(?<ssn>[a-zA-Z0-9]+) *(?: *; *(?<spec>[a-zA-Z]+))?");
		int n=0;
		
		BufferedReader r = new BufferedReader(reader);
			String line;
			while ((line = r.readLine())!=null) {
				Matcher m = p.matcher(line);
				if (m.find()) {
					if(m.group("id")==null) //patient
						patientMap.put(m.group("ssn"), new Patient(m.group("ssn"), m.group("name"), m.group("surname")));
					else //doctor
						doctorMap.put(Integer.parseInt(m.group("id")), new Doctor(m.group("ssn"), m.group("name"), m.group("surname"), Integer.parseInt(m.group("id")), m.group("spec")));
					
					n++;
				}else //wrong format
					listener.offending(line);
			}
		
		
		return n;
	}
	
	/**
	 * Retrieves the collection of doctors that have no patient at all.
	 * The doctors are returned sorted in alphabetical order
	 * 
	 * @return the collection of doctors' ids
	 * @throws NoSuchDoctor 
	 * @throws  
	 */
	public Collection<Integer> idleDoctors(){
		
		return doctorMap.values().stream().filter(doc -> !assignedDoctors.containsValue(doc.getBadgeID())).sorted((o1, o2) -> {
			if(o1.getLastName().compareTo(o2.getLastName())==0) 
				return o1.getLastName().compareTo(o2.getFirstName());
			else 
				return o1.getFirstName().compareTo(o2.getFirstName());
			
		}).map(doc -> doc.getBadgeID()).toList();

	}

	/**
	 * Retrieves the collection of doctors having a number of patients larger than the average.
	 * 
	 * 
	 * @return  the collection of doctors' ids
	 */
	public Collection<Integer> busyDoctors() {
		
		//to be improved
		
		double avg=assignedDoctors.entrySet().stream().collect(
				Collectors.groupingBy(
						doc -> doc.getValue(),
						Collectors.counting())).values().stream().collect(Collectors.averagingInt(doc -> doc.intValue()));
		
		return assignedDoctors.values().stream().collect(
				Collectors.groupingBy(
						doc -> doc,
						Collectors.toList()
						)).entrySet().stream().filter( 
								
								(entry) -> entry.getValue().size() >= avg
								
								).map( entry -> entry.getKey()).toList();
	}

	/**
	 * Retrieves the information about doctors and relative number of assigned patients.
	 * <p>
	 * The method returns list of strings formatted as "{@code ### : ID SURNAME NAME}" where {@code ###}
	 * represent the number of patients (printed on three characters).
	 * <p>
	 * The list is sorted by decreasing number of patients.
	 * 
	 * @return the collection of strings with information about doctors and patients count
	 */
	public Collection<String> doctorsByNumPatients(){
		
		Map<Integer, Long> tmp = assignedDoctors.entrySet().stream().collect(
				Collectors.groupingBy(
						d -> d.getValue(),
						Collectors.counting()));

		List<String> res = new ArrayList<>();
		for( int doc : tmp.keySet()) 
			res.add(String.format("%3d : %d %s %s", tmp.get(doc), doctorMap.get(doc).getBadgeID(), doctorMap.get(doc).getLastName(), doctorMap.get(doc).getFirstName()));
		
		
		//doctor with no patients
		for(int doc : doctorMap.keySet()) 
			if(!assignedDoctors.values().contains(doc)) 
				res.add(String.format("%3d : %d %s %s", 0, doctorMap.get(doc).getBadgeID(), doctorMap.get(doc).getLastName(), doctorMap.get(doc).getFirstName()));
				
		Collections.sort(res, (a,b) -> -a.compareTo(b));
		return res;
	}
	
	/**
	 * Retrieves the number of patients per (their doctor's)  speciality
	 * <p>
	 * The information is a collections of strings structured as {@code ### - SPECIALITY}
	 * where {@code SPECIALITY} is the name of the speciality and 
	 * {@code ###} is the number of patients cured by doctors with such speciality (printed on three characters).
	 * <p>
	 * The elements are sorted first by decreasing count and then by alphabetic speciality.
	 * 
	 * @return the collection of strings with speciality and patient count information.
	 */
	public Collection<String> countPatientsPerSpecialization(){
		Map<String, Doctor> tmp = new HashMap<>();
		assignedDoctors.entrySet().stream().forEach( e -> tmp.put(e.getKey(), doctorMap.get(e.getValue())));
		Collection<String> a = tmp.entrySet().stream().collect(
				Collectors.groupingBy(
						e -> e.getValue().getSpecialization(),
						Collectors.counting()))
		.entrySet().stream()
		.sorted( (entryA, entryB) -> entryB.getValue().compareTo(entryA.getValue()) != 0 ? entryB.getValue().compareTo(entryA.getValue()) : entryA.getKey().compareTo(entryB.getKey()))
		.map( (entry) -> String.format("%3d - %s", entry.getValue(), entry.getKey())).toList();
		return a;
	}

}
