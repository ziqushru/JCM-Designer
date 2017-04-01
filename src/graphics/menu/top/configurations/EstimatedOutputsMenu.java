package graphics.menu.top.configurations;

import graphics.gui.CustomGridPane;
import graphics.gui.CustomStage;
import graphics.gui.CustomTextField;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import program.map.Map;
import program.map.runnners.Parameters;

public class EstimatedOutputsMenu extends ConfigurationsUI implements Configurations
{
	@Override
	public void openConfigurations()
	{
		final VBox main_comp = new VBox();
		main_comp.setId("pane");
		main_comp.setAlignment(Pos.TOP_CENTER);

		main_comp.getChildren().add(new Label("Estimated Outputs"));

		final int concepts_length = Map.units.size();
		if (concepts_length == 0) return;
		final CustomGridPane grid_pane = new CustomGridPane(1 + concepts_length, 3);
		
		grid_pane.add(new Label("Limits"), 0, 0);
		grid_pane.add(new Label("Upper"), 0, 1);
		grid_pane.add(new Label("Lower"), 0, 2);
		
		this.text_fields = new CustomTextField[2][concepts_length];

		int width = ("Limits".length() + 25) * 6;
		for (int i = 0; i < concepts_length; i++)
		{
			width += (Map.units.get(i).getName().length() + 25) * 6;
			grid_pane.add(new Label(Map.units.get(i).getName()), 1 + i, 0);
			for (int j = 0; j < text_fields.length; j++)
			{	
				this.text_fields[j][i] = new CustomTextField(this, Parameters.A_estimated[j][i]);
				grid_pane.add(this.text_fields[j][i], 1 + i, 1 + j);
			}
		}
		main_comp.getChildren().add(grid_pane);

		final Button update_button = new Button("Update Values");
		update_button.setOnAction(event -> this.buttonOnAction());
		main_comp.getChildren().add(update_button);

		this.configurations_stage = new CustomStage("Estimated Outputs", width, 320, main_comp, "/stylesheets/pop_up.css");
	}

	@Override
	public void buttonOnAction()
	{
		final String[][] texts = new String[this.text_fields.length][this.text_fields[0].length];
		for (int i = 0; i < texts.length; i++)
			for (int j = 0; j < texts[0].length; j++)
				texts[i][j] = this.text_fields[i][j].getText().toString();
		for (int i = 0; i < texts.length; i++)
			for (int j = 0; j < texts[0].length; j++)
				if (texts[i][j] != null && !texts[i][j].isEmpty())
					Parameters.A_estimated[i][j] = Double.parseDouble(texts[i][j]);
		this.configurations_stage.close();
	}
}
