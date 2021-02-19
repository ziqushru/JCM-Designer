package jcmdesigner.graphics.gui;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jcmdesigner.program.App;

public class CustomStage extends Stage
{
	public CustomStage(String title, int width, int height, Pane main_comp, String css_path)
	{
		super();
		this.setTitle(title);
		this.getIcons().add(new Image(App.logo_path + ".png"));
		this.setResizable(false);
		Scene scene = new Scene(main_comp, width, height);
		scene.getStylesheets().add(App.class.getResource(css_path).toExternalForm());
		this.setScene(scene);
		this.show();
	}
}
