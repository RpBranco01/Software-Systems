package presentation.fx.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import facade.dtos.SpecialtyDTO;
import facade.exceptions.ApplicationException;
import facade.interfaces.ICreateActivityServiceRemote;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class CreateActivityModel {

	private final StringProperty designation;
	private String specialty;
	private final BooleanProperty isRegular;
	private final IntegerProperty numSessions;
	private final IntegerProperty sessionDuration;
	private final IntegerProperty price;
	private final IntegerProperty numParticipants;
	private final ObservableList<String> specialties;

	public CreateActivityModel(ICreateActivityServiceRemote service) {
		designation = new SimpleStringProperty();
		specialty = new String();
		isRegular = new SimpleBooleanProperty();
		numSessions = new SimpleIntegerProperty();
		sessionDuration = new SimpleIntegerProperty();
		price = new SimpleIntegerProperty();
		numParticipants = new SimpleIntegerProperty();

		List<String> sortedSpecialties = new ArrayList<String>();
		service.getSpecialities().forEach(s -> {
			sortedSpecialties.add(s.getDesignation());
		});

		java.util.Collections.sort(sortedSpecialties);
		specialties = FXCollections.observableArrayList(sortedSpecialties);

		/*
		 * specialties = FXCollections.observableArrayList(); String x = "ABCDE"; for
		 * (int i = 0; i < x.length(); i++) { specialties.add(x.substring(i, i + 1)); }
		 * 
		 * System.out.println(specialties);
		 */
	}

	public String getDesignation() {
		return designation.get();
	}

	public String getSpecialty() {
		return specialty;
	}

	public Boolean getIsRegular() {
		return isRegular.get();
	}

	public int getNumSessions() {
		return numSessions.get();
	}

	public int getSessionDuration() {
		return sessionDuration.get();
	}

	public int getPrice() {
		return price.get();
	}

	public int getNumParticipants() {
		return numParticipants.get();
	}

	public StringProperty designationProperty() {
		return designation;
	}

	/*
	 * public StringProperty specialtyProperty() { return specialty; }
	 */

	public BooleanProperty isRegularProperty() {
		return isRegular;
	}

	public IntegerProperty numSessionsProperty() {
		return numSessions;
	}

	public IntegerProperty sessionDurationProperty() {
		return sessionDuration;
	}

	public IntegerProperty priceProperty() {
		return price;
	}

	public IntegerProperty numParticipantsProperty() {
		return numParticipants;
	}

	public ObservableList<String> specialtiesProperty() {
		return specialties;
	}

	public void clearProperties() {
		designation.set("");
		specialty = "";
		isRegular.set(false);
		numSessions.set(0);
		sessionDuration.set(0);
		price.set(0);
		numParticipants.set(0);
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	/*
	 * public ObservableList<String> getSpecialties() { List<String> list = new
	 * ArrayList<String>(); list.add("Pizza A"); list.add("Pizza B");
	 * list.add("Pizza C");
	 * 
	 * ObservableList obList = FXCollections.observableList(list);
	 * 
	 * 
	 * return obList; }
	 */
}
