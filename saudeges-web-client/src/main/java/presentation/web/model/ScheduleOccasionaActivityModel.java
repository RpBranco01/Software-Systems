package presentation.web.model;

import java.util.ArrayList;
import java.util.List;

import facade.dtos.InstructorDTO;
import facade.dtos.OccasionalActivityDTO;

public class ScheduleOccasionaActivityModel extends Model{
	
	private List<OccasionalActivityDTO> occasionalActivities;
	private List<InstructorDTO> instructors;
	private String email;
	private String specialty;
	private String activity;
	private int numDates;
	private String[] dates;
	
	private int instructor;
	
	public ScheduleOccasionaActivityModel() {
		this.occasionalActivities = new ArrayList<>();
		this.instructors = new ArrayList<>();
		this.email = "";
		this.specialty = "";
		this.activity = "";
		this.instructor = 0;
	}
	
	public int getNumDates() {
		return this.numDates;
	}
	
	public String[] getDates(){
		return this.dates;
	}
	
	public String getActivity() {
		return this.activity;
	}
	
	public String getSpecialty() {
		return this.specialty;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public List<OccasionalActivityDTO> getOccasionalActivities(){
		return this.occasionalActivities;
	}
	
	public List<InstructorDTO> getInstructors(){
		return this.instructors;
	}
	
	public int getInstructor() {
		return this.instructor;
	}
	
	public void setNumDates(String numDates) {
		this.numDates = Integer.parseInt(numDates);
		this.dates = new String[this.numDates];
	}
	
	public void setDates(String[] dates) {
		this.dates = dates;
	}
	
	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setOccasionalActivities(List<OccasionalActivityDTO> occasionalActivities){
		this.occasionalActivities = occasionalActivities;
	}
	
	public void setInstructors(List<InstructorDTO> instructors){
		this.instructors = instructors;
	}
	
	public void setInstructor(int instructor) {
		this.instructor = instructor;
	}
}
