package facade.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import business.activity.OccasionalActivity;
import business.session.Session;

/**
 * A class DTO representing a simplified version of a Occasional Activity (business side
 * class)
 */
public class OccasionalActivityDTO implements Serializable {

	private int id;
	private String name;
	private SpecialtyDTO specialty;
	private double price;
	private int numSessions;
	private int sessionDuration;
	
	private List<SessionDTO> sessions = new ArrayList<>();
	
	
	
	public OccasionalActivityDTO(int id, String name, SpecialtyDTO specialty, double price, int numSessions,
			int sessionDuration, List<SessionDTO> sessions) {
		
		this.id = id;
		this.name = name;
		this.specialty = specialty;
		this.price = price;
		this.numSessions = numSessions;
		this.sessionDuration = sessionDuration;
		this.sessions = sessions;
	}
	
	public OccasionalActivityDTO(OccasionalActivity oa) {
		this.id = oa.getId();
		this.name = oa.getName();
		this.specialty = new SpecialtyDTO(oa.getSpecialty());
		this.price = oa.getPrice();
		this.numSessions = oa.getNumSessions();
		this.sessionDuration = oa.getDuration();
		
		for (Session session : oa.getAllSessions()) {
			this.sessions.add(new SessionDTO(session));
		}
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public SpecialtyDTO getSpecialty() {
		return this.specialty;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public int getNumSessions() {
		return this.numSessions;
	}
	
	public int getDuration() {
		return this.sessionDuration;
	}
	
	public List<SessionDTO> getSessions() {
		return this.sessions;
	}
}
