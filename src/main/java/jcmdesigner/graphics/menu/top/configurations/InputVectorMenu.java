package jcmdesigner.graphics.menu.top.configurations;

import jcmdesigner.graphics.gui.CustomGridPane;
import jcmdesigner.graphics.gui.CustomStage;
import jcmdesigner.graphics.gui.CustomTextField;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jcmdesigner.program.map.Map;

public class InputVectorMenu extends ConfigurationsUI implements Configurations
{	
	@Override
	public void openConfigurations()
	{
		final VBox main_comp = new VBox();
		main_comp.setId("pane");
		main_comp.setAlignment(Pos.TOP_CENTER);
		
		main_comp.getChildren().add(new Label("Input Vector"));
				
		final int concepts_length = Map.units.size();
		if (concepts_length == 0) return;
		final CustomGridPane grid_pane = new CustomGridPane(concepts_length, 2);

		this.text_fields = new CustomTextField[1][concepts_length];

		int width = 0;
		for (int i = 0; i < concepts_length; i++)
		{
			width += (Map.units.get(i).getName().length() + 25) * 6;
			grid_pane.add(new Label(Map.units.get(i).getName()), i, 0);
			this.text_fields[0][i] = new CustomTextField(this, Map.units.get(i).concept.getInput());
			grid_pane.add(this.text_fields[0][i], i, 1);
		}
		main_comp.getChildren().add(grid_pane);

		final Button update_button = new Button("Update Values");
		update_button.setOnAction(event -> this.buttonOnAction());
		main_comp.getChildren().add(update_button);

		this.configurations_stage = new CustomStage("Input Vector", width, 260, main_comp, "/stylesheets/pop_up.css");
	}

	@Override
	public void buttonOnAction()
	{
		final String[] texts = new String[this.text_fields[0].length];
		for (int i = 0; i < texts.length; i++)
			texts[i] = this.text_fields[0][i].getText().toString();
		for (int i = 0; i < texts.length; i++)
			if (texts[i] != null && !texts[i].isEmpty())
				Map.units.get(i).concept.setInput(Double.parseDouble(texts[i]));
		this.configurations_stage.close();
	}
}
