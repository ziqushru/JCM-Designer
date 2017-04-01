package graphics.gui;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import program.Program;

public class CustomStage extends Stage
{
	public CustomStage(String title, int width, int height, Pane main_comp, String css_path)
	{
		super();
		this.setTitle(title);
		this.getIcons().add(new Image(Program.logo_path + ".png"));
		this.setResizable(false);
		Scene scene = new Scene(main_comp, width, height);
		scene.getStylesheets().add(Program.class.getResource(css_path).toExternalForm());
		this.setScene(scene);
		this.show();
	}
}
