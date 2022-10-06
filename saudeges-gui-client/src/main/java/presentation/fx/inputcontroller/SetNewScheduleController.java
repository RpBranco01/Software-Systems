package presentation.fx.inputcontroller;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.UnaryOperator;

import facade.dtos.Pair;
import facade.exceptions.ApplicationException;
import facade.interfaces.ISetNewScheduleServiceRemote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import presentation.fx.model.SetNewScheduleModel;

public class SetNewScheduleController extends BaseController{
	

    @FXML
    private ComboBox<String> activities;

    @FXML
    private DatePicker dateStart;

    @FXML
    private TextField duration;

    @FXML
    private TextField instructorId;

    @FXML
    private TextField instructorDuration;

    @FXML
    private ComboBox<String> dayOfWeek1;

    @FXML
    private TextField hour1;

    @FXML
    private ComboBox<String> dayOfWeek2;

    @FXML
    private TextField hour2;

    @FXML
    private ComboBox<String> dayOfWeek3;

    @FXML
    private TextField hour3;

    @FXML
    private ComboBox<String> dayOfWeek4;

    @FXML
    private TextField hour4;

    @FXML
    private ComboBox<String> dayOfWeek5;

    @FXML
    private TextField hour5;
    
    private SetNewScheduleModel model;
    private ISetNewScheduleServiceRemote scheduleService;
    
    public void setModel(SetNewScheduleModel model) { 
    	this.model = model;
    	
    	activities.setItems(model.activitiesProperty());
    	
    	System.out.println("ACTIVITIES IN CONTROLLER");
    	System.out.println(model.activitiesProperty());
    	
    	dateStart.valueProperty().bindBidirectional(model.startDateProperty());
    	
    	duration.textProperty().bindBidirectional(model.durationProperty(), new NumberStringConverter());
    	instructorId.textProperty().bindBidirectional(model.instructorIdProperty(), new NumberStringConverter());
    	instructorDuration.textProperty().bindBidirectional(model.instructorDurationProperty(), new NumberStringConverter());
    	
    	dayOfWeek1.setItems(model.daysOfWeekProperty());
    	dayOfWeek1.setValue(model.getDayOfWeek1());
    	
    	dayOfWeek2.setItems(model.daysOfWeekProperty());
    	dayOfWeek2.setValue(model.getDayOfWeek2());
    	
    	dayOfWeek3.setItems(model.daysOfWeekProperty());
    	dayOfWeek3.setValue(model.getDayOfWeek3());
    	
    	dayOfWeek4.setItems(model.daysOfWeekProperty());
    	dayOfWeek4.setValue(model.getDayOfWeek4());
    	
    	dayOfWeek5.setItems(model.daysOfWeekProperty());
    	dayOfWeek5.setValue(model.getDayOfWeek5());
    	
    	hour1.textProperty().bindBidirectional(model.hour1Property());
    	hour2.textProperty().bindBidirectional(model.hour2Property());
    	hour3.textProperty().bindBidirectional(model.hour3Property());
    	hour4.textProperty().bindBidirectional(model.hour4Property());
    	hour5.textProperty().bindBidirectional(model.hour5Property());
    	  	
    }
    
	@FXML
	private void initialize() {
		UnaryOperator<Change> integerFilter = change -> {
			String newText = change.getControlNewText();
			if (newText.matches("[0-9]*")) { 
				return change;
			}
			return null;
		};
		
		duration.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),
				0, integerFilter));
		instructorId.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),
				0, integerFilter));
		instructorDuration.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),
				0, integerFilter));
	}
    
    @FXML
    void selectActivity(ActionEvent event) {
    	model.setActivity(activities.getValue());
    }
    
    @FXML
    void selectDayOfWeek1(ActionEvent event) {
    	model.setDayOfWeek1(dayOfWeek1.getValue());
    }
    
    @FXML
    void selectDayOfWeek2(ActionEvent event) {
    	model.setDayOfWeek1(dayOfWeek2.getValue());
    }
    
    @FXML
    void selectDayOfWeek3(ActionEvent event) {
    	model.setDayOfWeek3(dayOfWeek3.getValue());
    }
    
    @FXML
    void selectDayOfWeek4(ActionEvent event) {
    	model.setDayOfWeek4(dayOfWeek4.getValue());
    }
    
    @FXML
    void selectDayOfWeek5(ActionEvent event) {
    	model.setDayOfWeek5(dayOfWeek5.getValue());
    }

	public void setService(ISetNewScheduleServiceRemote scheduleService) {
		this.scheduleService = scheduleService;
	}
	
	@FXML
	void back(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/index.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	void setNewSchedule(ActionEvent event) throws IOException {
		String errorMessages = validateInput();
		
		if(errorMessages.length() == 0) {
			List<Pair<DayOfWeek, LocalTime>> weekDays = new ArrayList<>();
			
			if(!model.getDayOfWeek1().equals("")) {
				DayOfWeek dow  = getDayOfWeek(model.getDayOfWeek1());
				Pair<DayOfWeek, LocalTime> pair1 = new Pair<>(dow, LocalTime.parse(model.hour1Property().get()));
				weekDays.add(pair1);
			}
			if(!model.getDayOfWeek2().equals("")) {
				DayOfWeek dow  = getDayOfWeek(model.getDayOfWeek2());
				Pair<DayOfWeek, LocalTime> pair2 = new Pair<>(dow, LocalTime.parse(model.hour2Property().get()));
				weekDays.add(pair2);
			}
			if(!model.getDayOfWeek3().equals("")) {
				DayOfWeek dow  = getDayOfWeek(model.getDayOfWeek3());
				Pair<DayOfWeek, LocalTime> pair3 = new Pair<>(dow, LocalTime.parse(model.hour3Property().get()));
				weekDays.add(pair3);
			}
			if(!model.getDayOfWeek4().equals("")) {
				DayOfWeek dow  = getDayOfWeek(model.getDayOfWeek4());
				Pair<DayOfWeek, LocalTime> pair4 = new Pair<>(dow, LocalTime.parse(model.hour4Property().get()));
				weekDays.add(pair4);
			}
			if(!model.getDayOfWeek5().equals("")) {
				DayOfWeek dow  = getDayOfWeek(model.getDayOfWeek5());
				Pair<DayOfWeek, LocalTime> pair5 = new Pair<>(dow, LocalTime.parse(model.hour5Property().get()));
				weekDays.add(pair5);
			}
			
			try {
				ZoneId defaultZoneId = ZoneId.systemDefault();
				Date date = Date.from(model.startDateProperty().get().atStartOfDay(defaultZoneId).toInstant());
				scheduleService.setSchedule(model.getActivity(), weekDays, date, model.durationProperty().get(), 
						model.instructorIdProperty().get(), model.instructorDurationProperty().get());
				showInfo(i18nBundle.getString("create.activity.success"));
			}
			catch(ApplicationException e) {
				showError(i18nBundle.getString("create.activity.error.generic") + ": " + e.getMessage() + "\n" + e.getCause());
			}
		}else {
			showError(i18nBundle.getString("create.activity.error.validating") + ":\n" + errorMessages);
		}
	}
	
	private String validateInput() {	
		StringBuilder sb = new StringBuilder();
		String activity = model.getActivity();
		if (activity == null || activity.length() == 0)
			sb.append(i18nBundle.getString("create.activity.invalid.designation"));
		
		LocalDate startDate = model.startDateProperty().get();
		if (startDate == null)
			sb.append(i18nBundle.getString("create.activity.invalid.specialty"));
		
		if (model.durationProperty().get() == 0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("create.activity.invalid.sessionDuration"));
		}
		
		if (model.instructorIdProperty().get() == 0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("create.activity.invalid.price"));
		}
		
		if (model.instructorDurationProperty().get() == 0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("create.activity.invalid.numParticipants"));
		}
		
		
		return sb.toString();
	}
	
	
	private DayOfWeek getDayOfWeek(String weekDay) {
		DayOfWeek result = null;
		
		switch (weekDay) {
		case "Segunda-feira":
			result = DayOfWeek.MONDAY;
			break;
		case "Terça-feira":
			result = DayOfWeek.TUESDAY;
			break;
		case "Quarta-feira":
			result = DayOfWeek.WEDNESDAY;
			break;
		case "Quinta-feira":
			result = DayOfWeek.THURSDAY;
			break;		
		case "Sexta-feira":
			result = DayOfWeek.FRIDAY;
			break;
		default:
			break;
		}
		return result;
	}
}
