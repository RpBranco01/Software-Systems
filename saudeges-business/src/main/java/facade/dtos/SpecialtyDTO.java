package facade.dtos;

import java.io.Serializable;

import business.instructor.Certification;
import business.specialty.Specialty;

public class SpecialtyDTO implements Serializable{

	private int id;
	private String designation;
	private int minDuration;
	private Certification certification;
	
	public SpecialtyDTO(int id, String designation, int minDuration, Certification certification) {
		this.id = id;
		this.designation = designation;
		this.minDuration = minDuration;
		this.certification = certification;
	}
	
	public SpecialtyDTO(Specialty s) {
		this.id = s.getID();
		this.designation = s.getDesignation();
		this.minDuration = s.getMinDuration();
		this.certification = s.getCertification();
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getDesignation() {
		return this.designation;
	}
	
	public int getMinDuration() {
		return this.minDuration;
	}
	
	public Certification getCertification() {
		return this.certification;
	}
}
