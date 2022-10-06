package presentation.fx.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import facade.exceptions.ApplicationException;
import facade.interfaces.ISetNewScheduleServiceRemote;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SetNewScheduleModel {
	
    private ObservableList<String> activities;
    private String activity;
    
    private ObjectProperty<LocalDate> dateStart;
    private IntegerProperty duration;
    private IntegerProperty instructorId;
    private IntegerProperty instructorDuration;

    private ObservableList<String> daysOfWeek;
    
    private String dayOfWeek1;
    private StringProperty hour1;
    private String dayOfWeek2;
    private StringProperty hour2;
    private String dayOfWeek3;
    private StringProperty hour3;
    private String dayOfWeek4;
    private StringProperty hour4;
    private String dayOfWeek5;
    private StringProperty hour5;
	
	public SetNewScheduleModel(ISetNewScheduleServiceRemote service) {
		try {
			List<String> sortedActivities = new ArrayList<String>();
			service.getRegularActivities().forEach(ra -> {
				sortedActivities.add(ra.getName());
			});
			this.activities = FXCollections.observableArrayList(sortedActivities);
			
		}
		catch(ApplicationException e) {
			//no activities found
		}
		
		System.out.println(activities);
		
		this.activity = "";
		
		dateStart = new SimpleObjectProperty<>(null);
		
		this.duration = new SimpleIntegerProperty();
		this.instructorId = new SimpleIntegerProperty();
		this.instructorDuration = new SimpleIntegerProperty();
		
		List<String> dow = new ArrayList<String>();
		dow.add("Segunda-feira");
		dow.add("Terça-feira");
		dow.add("Quarta-feira");
		dow.add("Quinta-feira");
		dow.add("Sexta-feira");
		
		this.daysOfWeek = FXCollections.observableArrayList(dow);
		
		this.dayOfWeek1 = "";
		this.dayOfWeek2 = "";
		this.dayOfWeek3 = "";
		this.dayOfWeek4 = "";
		this.dayOfWeek5 = "";
		
		this.hour1 = new SimpleStringProperty();
		this.hour2 = new SimpleStringProperty();
		this.hour3 = new SimpleStringProperty();
		this.hour4 = new SimpleStringProperty();
		this.hour5 = new SimpleStringProperty();
	}
	
	
	//GETTERS
	public ObservableList<String> activitiesProperty() {
		return this.activities;
	}
	
	public ObjectProperty<LocalDate> startDateProperty(){
		return dateStart;
	}

	public IntegerProperty durationProperty() {
		return duration;
	}

	public IntegerProperty instructorIdProperty() {
		return instructorId;
	}

	public IntegerProperty instructorDurationProperty() {
		return instructorDuration;
	}
	
	public ObservableList<String> daysOfWeekProperty() {
		return daysOfWeek;
	}
	
	public String getDayOfWeek1() {
		return dayOfWeek1;
	}
	
	public String getDayOfWeek2() {
		return dayOfWeek2;
	}
	
	public String getDayOfWeek3() {
		return dayOfWeek3;
	}
	
	public String getDayOfWeek4() {
		return dayOfWeek4;
	}
	
	public String getDayOfWeek5() {
		return dayOfWeek5;
	}
	
	public StringProperty hour1Property() {
		return hour1;
	}
	
	public StringProperty hour2Property() {
		return hour2;
	}
	
	public StringProperty hour3Property() {
		return hour3;
	}
	
	public StringProperty hour4Property() {
		return hour4;
	}
	
	public StringProperty hour5Property() {
		return hour5;
	}
	
	public String getActivity() {
		return this.activity;
	}
	
	
	//SETTERS
	public void setActivity(String activity) {
		this.activity = activity;
	}

	public void setDayOfWeek1(String dayOfWeek1) {
		this.dayOfWeek1 = dayOfWeek1;
	}

	public void setDayOfWeek2(String dayOfWeek2) {
		this.dayOfWeek2 = dayOfWeek2;
	}
	
	public void setDayOfWeek3(String dayOfWeek3) {
		this.dayOfWeek3 = dayOfWeek3;
	}
	
	public void setDayOfWeek4(String dayOfWeek4) {
		this.dayOfWeek4 = dayOfWeek4;
	}
	
	public void setDayOfWeek5(String dayOfWeek5) {
		this.dayOfWeek5 = dayOfWeek5;
	}
}
