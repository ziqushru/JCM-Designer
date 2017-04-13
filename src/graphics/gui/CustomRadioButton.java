package graphics.gui;

import java.util.ArrayList;

import graphics.menu.top.configurations.Configurations;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;

@SuppressWarnings("serial")
public class CustomRadioButton extends ArrayList<RadioButton>
{
	public CustomRadioButton(Configurations configurations, String... list)
	{
		final ToggleGroup toogle_group = new ToggleGroup();
		for (int i = 0; i < list.length; i++)
		{
			this.add(new RadioButton(list[i]));
			this.get(i).setToggleGroup(toogle_group);			
			this.get(i).setOnKeyPressed(event ->
			{
				if (event.getCode() == KeyCode.ENTER) configurations.buttonOnAction();
			});
		}
	}
}
