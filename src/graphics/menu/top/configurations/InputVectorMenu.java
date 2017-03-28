package graphics.menu.top.configurations;

import graphics.gui.CustomGridPane;
import graphics.gui.CustomTextField;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import program.Program;
import program.map.Map;

public class InputVectorMenu implements Configurations
{
	private Stage				configurations_stage;
	private CustomTextField[] 	text_fields;
	
	@Override
	public void openConfigurations()
	{
		VBox main_comp = new VBox();
		main_comp.setId("pane");
		main_comp.setAlignment(Pos.TOP_CENTER);
		
		main_comp.getChildren().add(new Label("Input Vector"));
				
		final int concepts_length = Map.units.size();
		if (concepts_length == 0) return;
		CustomGridPane grid_pane = new CustomGridPane(concepts_length, 2);

		this.text_fields = new CustomTextField[concepts_length];

		int width = 0;
		for (int i = 0; i < concepts_length; i++)
		{
			width += (Map.units.get(i).getName().length() + 25) * 6;
			grid_pane.add(new Label(Map.units.get(i).getName()), i, 0);
			this.text_fields[i] = new CustomTextField(this, Map.units.get(i).concept.getInput());
			grid_pane.add(this.text_fields[i], i, 1);
		}
		main_comp.getChildren().add(grid_pane);

		Button update_button = new Button("Update Values");
		update_button.setOnAction(event -> this.buttonOnAction());
		main_comp.getChildren().add(update_button);

		Scene scene = new Scene(main_comp, width, 255);
		scene.getStylesheets().add(Program.class.getResource("/stylesheets/pop_up.css").toExternalForm());
		this.configurations_stage = new Stage();
		this.configurations_stage.getIcons().add(new Image(Program.logo_path + ".png"));
		this.configurations_stage.setScene(scene);
		this.configurations_stage.setTitle("Input Vector");
		this.configurations_stage.setResizable(false);
		this.configurations_stage.show();
	}

	@Override
	public void buttonOnAction()
	{
		String[] texts = new String[this.text_fields.length];
		for (int i = 0; i < texts.length; i++)
			texts[i] = this.text_fields[i].getText().toString();
		for (int i = 0; i < texts.length; i++)
			if (texts[i] != null && !texts[i].isEmpty())
				Map.units.get(i).concept.setInput(Double.parseDouble(texts[i]));
		this.configurations_stage.close();
	}
}
