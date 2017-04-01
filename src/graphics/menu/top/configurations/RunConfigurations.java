package graphics.menu.top.configurations;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import program.map.inference_rules.Kosko;
import program.map.inference_rules.ModifiedKosko;
import program.map.inference_rules.RescaledKosko;
import program.map.learning_algorithms.HebbianLearning;
import program.map.runnners.Runner;
import program.utils.transferfunctions.Bivalent;
import program.utils.transferfunctions.Continuous;
import program.utils.transferfunctions.NoTransferFunction;
import program.utils.transferfunctions.Sigmoid;
import program.utils.transferfunctions.Trivalent;

public abstract class RunConfigurations extends ConfigurationsUI
{
	protected String 		name;
	protected Runner 		runner;
	protected RadioButton[]	transfer_functions;
	
	protected void setTransferFunctionsUI(GridPane grid_pane)
	{
		grid_pane.add(new Label("Transfer Functions"), 0, 0);
		this.transfer_functions = new RadioButton[4];
		this.transfer_functions[0] = new RadioButton("Bivalent");
		this.transfer_functions[1] = new RadioButton("Trivalent");
		this.transfer_functions[2] = new RadioButton("Sigmoid");
		this.transfer_functions[3] = new RadioButton("Continuous");
		ToggleGroup transfer_functions_group = new ToggleGroup();
		for (int i = 0; i < transfer_functions.length; i++)
		{
			this.transfer_functions[i].setToggleGroup(transfer_functions_group);
			grid_pane.add(this.transfer_functions[i], 0, 1 + i);
		}
	}
	
	protected void setRunner(RadioButton[] inference_rules, HebbianLearning hebbian_learning)
	{
		this.runner = new Runner(this.name, hebbian_learning);
		this.setTransferFunctions();
		this.setInferenceRule(inference_rules);
		this.setParameters();
	}
	
	protected void setTransferFunctions()
	{
		if (transfer_functions[0].isSelected())			this.runner.setTransferFunction(new Bivalent());
		else if (transfer_functions[1].isSelected())	this.runner.setTransferFunction(new Trivalent());
		else if (transfer_functions[2].isSelected())	this.runner.setTransferFunction(new Sigmoid());
		else if (transfer_functions[3].isSelected())	this.runner.setTransferFunction(new Continuous());
		else											this.runner.setTransferFunction(new NoTransferFunction());
	}
	
	protected void setInferenceRule(RadioButton[] inference_rules)
	{
		if (inference_rules[0].isSelected())		this.runner.setInferenceRule(new Kosko());
		else if (inference_rules[1].isSelected())	this.runner.setInferenceRule(new ModifiedKosko());
		else if (inference_rules[2].isSelected())	this.runner.setInferenceRule(new RescaledKosko());
		else										this.runner.setInferenceRule(new ModifiedKosko());
	}
	
	protected void setParameters()
	{
		for (int i = 0; i < text_fields[0].length; i++)
		{
			String parameter = this.text_fields[0][i].getText().toString();
			if (parameter != null && !parameter.isEmpty())	this.runner.parameters.add(Double.parseDouble(parameter));
			else											this.runner.parameters.add(1.0);
		}
	}
}
