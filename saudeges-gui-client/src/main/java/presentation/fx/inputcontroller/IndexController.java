package presentation.fx.inputcontroller;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import facade.interfaces.ICreateActivityServiceRemote;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import presentation.fx.Startup;
import presentation.fx.model.CreateActivityModel;
import presentation.fx.model.SetNewScheduleModel;

public class IndexController extends BaseController {

	@FXML
	public void switchToCreateActivityScene(ActionEvent event) throws IOException {
		ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("pt", "PT"));
		URL activityURL = getClass().getResource("/fxml/createActivity.fxml");
		FXMLLoader createActivityLoader = new FXMLLoader(activityURL, i18nBundle); // change name
		Parent createActivityRoot = createActivityLoader.load();
		
		CreateActivityController createActivityController = createActivityLoader.getController();
		CreateActivityModel createActivityModel = new CreateActivityModel(Startup.activityService);
		createActivityController.setModel(createActivityModel);
		createActivityController.setService(Startup.activityService);
		createActivityController.setI18NBundle(i18nBundle);
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(createActivityRoot);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToSetNewScheduleScene(ActionEvent event) throws IOException {
		ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("pt", "PT"));
		URL scheduleURL = getClass().getResource("/fxml/setNewSchedule.fxml");
		FXMLLoader setScheduleLoader = new FXMLLoader(scheduleURL, i18nBundle); // change name
		Parent setScheduleRoot = setScheduleLoader.load();
		
		SetNewScheduleController setNewScheduleController = setScheduleLoader.getController();
		SetNewScheduleModel setScheduleModel = new SetNewScheduleModel(Startup.scheduleService);
		setNewScheduleController.setModel(setScheduleModel);
		setNewScheduleController.setService(Startup.scheduleService);
		setNewScheduleController.setI18NBundle(i18nBundle);
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(setScheduleRoot);
		stage.setScene(scene);
		stage.show();
	}
}
