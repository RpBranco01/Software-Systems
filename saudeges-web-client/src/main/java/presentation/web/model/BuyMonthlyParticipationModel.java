package presentation.web.model;

import java.util.ArrayList;
import java.util.List;

import facade.dtos.ScheduleDTO;

public class BuyMonthlyParticipationModel extends Model {
	
	private List<ScheduleDTO> schedules;
	
	private String date;
	private String email;
	
	private String rasID;
	private String duration;
	private String activityName;

	public BuyMonthlyParticipationModel() {
		this.date = "";
		this.email = "";
		this.rasID = "";
		this.duration = "";
		this.activityName = "";
		this.schedules = new ArrayList<>();
	}
	
	public String getDate() {
		return this.date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getRASID() {
		return this.rasID;
	}
	
	public void setRASID(String rasID) {
		this.rasID = rasID;
	}
	
	public String getDuration() {
		return this.duration;
	}
	
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public String getActivityName() {
		return this.activityName;
	}
	
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	public List<ScheduleDTO> getSchedules() {
		return this.schedules;
	}
	
	public void setSchedules(List<ScheduleDTO> schedules) {
		this.schedules = schedules;
	}
	
	public void clearFields() {
		this.date = "";
		this.email = "";
		this.rasID = "";
		this.duration = "";
		this.activityName = "";
	}
}
