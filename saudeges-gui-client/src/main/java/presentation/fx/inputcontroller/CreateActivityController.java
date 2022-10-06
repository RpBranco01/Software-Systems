package presentation.fx.inputcontroller;

import java.io.IOException;
import java.util.function.UnaryOperator;

import facade.exceptions.ApplicationException;
import facade.exceptions.InvalidDurationException;
import facade.exceptions.InvalidNumParticipantsException;
import facade.exceptions.SpecialtyNotFoundException;
import facade.interfaces.ICreateActivityServiceRemote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import presentation.fx.model.CreateActivityModel;

public class CreateActivityController extends BaseController {


    @FXML
    private TextField name;

    @FXML
    private TextField numSessions;

    @FXML
    private TextField sessionDuration;

    @FXML
    private TextField price;

    @FXML
    private CheckBox isRegular;

    @FXML
    private TextField numParticipants;

    @FXML
    private ComboBox<String> specialty;

    @FXML
    private Button back;

    @FXML
    private Button create;
     
	private CreateActivityModel model;
	private ICreateActivityServiceRemote service;

    @FXML
    void test(ActionEvent event) {
    	System.out.println("COMBO CHANGED");
    }
    
	public void setService(ICreateActivityServiceRemote service) {
		this.service = service;
	}
    
    public void setModel(CreateActivityModel model) {
		this.model = model;
		name.textProperty().bindBidirectional(model.designationProperty());
		
		numSessions.textProperty().bindBidirectional(model.numSessionsProperty(), new NumberStringConverter());   	
		sessionDuration.textProperty().bindBidirectional(model.sessionDurationProperty(), new NumberStringConverter()); 
		price.textProperty().bindBidirectional(model.priceProperty(), new NumberStringConverter()); 
		numParticipants.textProperty().bindBidirectional(model.numParticipantsProperty(), new NumberStringConverter());
		isRegular.selectedProperty().bindBidirectional(model.isRegularProperty());
		
		//specialty.getSelectionModel().getSelectedItem().bindBidirectional(model.specialtyProperty());
		// specialty.valueProperty().get().bindBidirectional(model.specialtyProperty());
		specialty.setItems(model.specialtiesProperty());

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
		
		numSessions.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),
				0, integerFilter));
		sessionDuration.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),
				0, integerFilter));
		price.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),
				0, integerFilter));
		numParticipants.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),
				0, integerFilter));
		
		numParticipants.setDisable(true);
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
	void createActivityAction(ActionEvent event) {
		String errorMessages = validateInput();

		if (errorMessages.length() == 0) {
			try {
				service.addActivity(model.getDesignation(), 
						model.getSpecialty(), model.getIsRegular(), model.getNumSessions(), model.getSessionDuration(), model.getPrice(), model.getNumParticipants()); 
				model.clearProperties();
				showInfo(i18nBundle.getString("create.activity.success"));
			} catch (SpecialtyNotFoundException e) {
				showError(i18nBundle.getString("create.activity.error.specialtyNotFound") + ": " + e.getMessage());
			} catch (InvalidDurationException e) {
				showError(i18nBundle.getString("create.activity.error.invalidDuration") + ": " + e.getMessage());
			} catch (InvalidNumParticipantsException e) {
				showError(i18nBundle.getString("create.activity.error.invalidNumParticipants") + ": " + e.getMessage());
			} catch (ApplicationException e) {
				showError(i18nBundle.getString("create.activity.error.generic") + ": " + e.getMessage() + "\n" + e.getCause());
			}
		} else
			showError(i18nBundle.getString("create.activity.error.validating") + ":\n" + errorMessages);
	}
	
	@FXML
	void selectSpecialty(ActionEvent event) {
		model.setSpecialty(specialty.getValue());
	}
	
	private String validateInput() {	
		StringBuilder sb = new StringBuilder();
		String designation = model.getDesignation();
		if (designation == null || designation.length() == 0)
			sb.append(i18nBundle.getString("create.activity.invalid.designation"));
		
		String specialty = model.getSpecialty();
		if (specialty == null || specialty.length() == 0)
			sb.append(i18nBundle.getString("create.activity.invalid.specialty"));
		
		if((isRegular.isSelected() && (model.getNumSessions() == 0 || model.getNumSessions() > 5)) ||
				(!isRegular.isSelected() && (model.getNumSessions() == 0 || model.getNumSessions() > 20))) {	
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("create.activity.invalid.numSessions"));
		}
		
		if (model.getSessionDuration() == 0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("create.activity.invalid.sessionDuration"));
		}
		
		if (model.getPrice() == 0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("create.activity.invalid.price"));
		}
		
		if (isRegular.isSelected()) {
			if (model.getNumParticipants() == 0) {
				if (sb.length() > 0)
					sb.append("\n");
				sb.append(i18nBundle.getString("create.activity.invalid.numParticipants"));
			}
		}
		
		return sb.toString();
	}
	
	@FXML
	private void checkBoxClick(ActionEvent event) {
		
		numParticipants.setText("0");
		numParticipants.setDisable(!isRegular.isSelected());
		
	}

}
