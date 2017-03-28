package graphics.gui;

import graphics.menu.top.configurations.Configurations;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import program.map.runnners.Parameters;

public class CustomTextField extends TextField
{
	public CustomTextField(Configurations configuration)
	{
		super();
		this.setAlignment(Pos.CENTER);
		this.setFocusTraversable(true);
		this.setOnKeyPressed(event ->
		{
			if (event.getCode() == KeyCode.ENTER) configuration.buttonOnAction();
		});
	}
	
	public CustomTextField(Configurations configuration, double prompt_text)
	{
		this(configuration);
		if (prompt_text != Parameters.A_not_estimated)
			this.setPromptText(prompt_text + "");
		
	}
	
	public CustomTextField(Configurations configuration, String prompt_text)
	{
		this(configuration);
		this.setPromptText(prompt_text);
	}
}
