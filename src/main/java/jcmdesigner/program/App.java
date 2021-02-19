package jcmdesigner.program;

import java.awt.event.WindowEvent;
import java.util.Optional;

import javax.swing.JFrame;

import jcmdesigner.graphics.gui.GraphScreen;
import jcmdesigner.graphics.menu.LeftMenu;
import jcmdesigner.graphics.menu.TopMenu;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jcmdesigner.program.map.Map;
import jcmdesigner.program.units.Unit;
import jcmdesigner.program.utils.Log;

public class App extends Application
{
	public static final String			TITLE									= "JCM Designer";
	public static int					WIDTH									= 1024;
	public static int					HEIGHT									= 720;

	public static Log					log;

	public static Stage					window;
	public static BorderPane			main_border_pane;
	public static final String			logo_path								= "images/small-logo";
	public static HostServices 			host_services;

	public static boolean				running									= false;
	public static boolean				paused									= false;
	
	@Override
	public void start(Stage window) throws Exception
	{
		App.log = new Log();
		App.main_border_pane = new BorderPane();
		App.main_border_pane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, null)));
		App.host_services = this.getHostServices();
		new TopMenu();
		new LeftMenu();
		App.window = window;
		App.setUserAgentStylesheet(App.STYLESHEET_CASPIAN);
		App.window.setScene(new Scene(App.main_border_pane, App.WIDTH, App.HEIGHT));
		App.window.getScene().getStylesheets().add(getClass().getResource("/stylesheets/application.css").toExternalForm());
		App.window.getIcons().add(new Image(App.logo_path + ".png"));
		App.window.setTitle(App.TITLE);
		App.main_border_pane.setOnMouseClicked(event ->
		{
			if (Map.last_selected_unit != null)
				for (int i = 0; i < Unit.selected_lines.length; i++)
					App.main_border_pane.getChildren().remove(Unit.selected_lines[i]);
			Map.last_selected_unit = null;
		});
		App.window.widthProperty().addListener((ObservableValue<? extends Number> observable_value, Number old_width, Number new_width) ->
			App.WIDTH = new_width.intValue());
		App.window.heightProperty().addListener((ObservableValue<? extends Number> observable_value, Number old_height, Number new_height) ->
			App.HEIGHT = new_height.intValue());
		App.window.setOnCloseRequest(event ->
		{
			event.consume();
			App.closeProgram();
		});
		App.window.show();
		App.running = true;
	}

	public static void closeProgram()
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(App.window);
		alert.setTitle("Exit Confirmation");
		alert.setHeaderText("Your program is about to exit, be sure to save your map");
		alert.setContentText("Are you sure you want to exit ?");

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK)
		{
			App.running = false;
			for (JFrame frame : GraphScreen.frames)
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			App.window.close();
			Platform.exit();
		}
	}

	public static void main(String... args)
	{
		launch(args);
	}
}
