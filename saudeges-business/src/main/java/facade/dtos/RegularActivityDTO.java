package facade.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import business.activity.RegularActivity;
import business.schedule.Schedule;

/**
 * A class DTO representing a simplified version of a Regular Activity (business side
 * class)
 */
public class RegularActivityDTO implements Serializable{

	private int id;
	private String name;
	private SpecialtyDTO specialty;
	private double price;
	private int numSessions;
	private int sessionDuration;
	
	private int numMaxParticipants;
	private List<ScheduleDTO> scheduleList = new ArrayList<ScheduleDTO>();
	
	
	
	public RegularActivityDTO(int id, String name, SpecialtyDTO specialty, double price, int numSessions,
			int sessionDuration, int numMaxParticipants, List<ScheduleDTO>scheduleList) {
		
		this.id = id;
		this.name = name;
		this.specialty = specialty;
		this.price = price;
		this.numSessions = numSessions;
		this.sessionDuration = sessionDuration;
		this.numMaxParticipants = numMaxParticipants;
		this.scheduleList = scheduleList;
	}
	
	public RegularActivityDTO(RegularActivity ra) {
		this.id = ra.getId();
		this.name = ra.getName();
		this.specialty = new SpecialtyDTO(ra.getSpecialty());
		this.price = ra.getPrice();
		this.numSessions = ra.getNumSessions();
		this.sessionDuration = ra.getDuration();
		this.numMaxParticipants = ra.getNumMaxParticipants();
		
		for (Schedule schedule : ra.getSchedules()) {
			this.scheduleList.add(new ScheduleDTO(schedule));
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
	
	public int getNumMaxParticipants() {
		return this.numMaxParticipants;
	}
	
	public List<ScheduleDTO> getScheduleList() {
		return this.scheduleList;
	}
}
