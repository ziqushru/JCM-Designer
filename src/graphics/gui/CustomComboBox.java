package graphics.gui;

import graphics.menu.top.configurations.Configurations;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;

public class CustomComboBox extends ComboBox<String>
{
	public CustomComboBox(Configurations configuration, String prompt_text, String... list)
	{
		super();
		this.setPromptText(prompt_text);
		this.setItems(FXCollections.observableArrayList(list));
		this.setOnKeyPressed(event ->
		{
			if (event.getCode() == KeyCode.ENTER) configuration.buttonOnAction();
		});
	}
}
