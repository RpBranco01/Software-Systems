package facade.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import business.reservation.Reservation;
import business.session.Session;

public class SessionDTO implements Serializable{
	
	private int id;
	private int duration;
	private Date beginTime;
	private Date endTime;
	private int places;
	private InstructorDTO instructor;
	private List<ReservationDTO> reservations = new ArrayList<>();

	public SessionDTO(int id, int duration, Date beginTime, Date endTime, int places,
			InstructorDTO instructor, List<ReservationDTO> reservations) {
		
		this.id = id;
		this.duration = duration;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.places = places;
		this.instructor = instructor;
		this.reservations = reservations;
	}
	
	public SessionDTO(Session s) {
		this.id = s.getID();
		this.duration = s.getDuration();
		this.beginTime = s.getBegin();
		this.endTime = s.getEnd();
		this.places = s.getNumPlaces();
		this.instructor = new InstructorDTO(s.getInstructor());
		
		for (Reservation reservation : s.getReservations()) {
			this.reservations.add(new ReservationDTO(reservation));
		}
	}
	
	public int getID() {
		return this.id;
	}
	
	public int getDuration() {
		return this.duration;
	}
	
	public Date getBeginTime() {
		return this.beginTime;
	}
	
	public Date getEndTime() {
		return this.endTime;
	}
	
	public int getPlaces() {
		return this.places;
	}
	
	public InstructorDTO getInstructor() {
		return this.instructor;
	}
	
	public List<ReservationDTO> getReservations() {
		return this.reservations;
	}
}
