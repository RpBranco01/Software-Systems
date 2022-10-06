package presentation.fx;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import facade.interfaces.ICreateActivityServiceRemote;
import facade.interfaces.ISetNewScheduleServiceRemote;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.fx.inputcontroller.CreateActivityController;
import presentation.fx.inputcontroller.SetNewScheduleController;
import presentation.fx.inputcontroller.IndexController;
import presentation.fx.model.CreateActivityModel;
import presentation.fx.model.SetNewScheduleModel;

public class Startup extends Application {

	public static ICreateActivityServiceRemote activityService;
    public static ISetNewScheduleServiceRemote scheduleService;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("pt", "PT"));
		FXMLLoader indexLoader = new FXMLLoader(getClass().getResource("/fxml/index.fxml"), i18nBundle); // change name
		Parent root = indexLoader.load();
		
		IndexController ic = indexLoader.getController();
		ic.setI18NBundle(i18nBundle);
		
		Scene scene = new Scene(root);

		primaryStage.setTitle(i18nBundle.getString("application.title"));
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	
	public static void startGUI(ICreateActivityServiceRemote createActivityHandler, ISetNewScheduleServiceRemote setScheduleServiceRemote) {
		Startup.activityService = createActivityHandler;
		Startup.scheduleService = setScheduleServiceRemote;
		launch();
	}
    
}
