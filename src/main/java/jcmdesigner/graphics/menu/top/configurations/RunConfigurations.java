package jcmdesigner.graphics.menu.top.configurations;

import jcmdesigner.graphics.gui.CustomRadioButton;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import jcmdesigner.program.map.inference_rules.ActiveKosko;
import jcmdesigner.program.map.inference_rules.Kosko;
import jcmdesigner.program.map.inference_rules.ModifiedKosko;
import jcmdesigner.program.map.inference_rules.RescaledKosko;
import jcmdesigner.program.map.learning_algorithms.Active;
import jcmdesigner.program.map.learning_algorithms.HebbianLearning;
import jcmdesigner.program.map.runnners.Runner;
import jcmdesigner.program.utils.transferfunctions.Bivalent;
import jcmdesigner.program.utils.transferfunctions.Continuous;
import jcmdesigner.program.utils.transferfunctions.NoTransferFunction;
import jcmdesigner.program.utils.transferfunctions.Sigmoid;
import jcmdesigner.program.utils.transferfunctions.Trivalent;

public abstract class RunConfigurations extends ConfigurationsUI implements Configurations
{
	protected String 			name;
	protected Runner 			runner;
	protected CustomRadioButton	transfer_functions;
	
	protected void setTransferFunctionsUI(GridPane grid_pane)
	{
		grid_pane.add(new Label("Transfer Functions"), 0, 0);
		this.transfer_functions = new CustomRadioButton(this, "Bivalent", "Trivalent", "Sigmoid", "Continuous");
		for (int i = 0; i < transfer_functions.size(); i++)
			grid_pane.add(this.transfer_functions.get(i), 0, 1 + i);
	}
	
	protected void setRunner(CustomRadioButton inference_rules, HebbianLearning hebbian_learning)
	{
		this.runner = new Runner(this.name, hebbian_learning);
		this.setTransferFunctions();
		this.setParameters();
		if (hebbian_learning instanceof Active)
		{
			this.runner.setInferenceRule(new ActiveKosko(this.runner.parameters));
			hebbian_learning.update_parameters(this.runner.parameters);
		}
		else
			this.setInferenceRule(inference_rules);
	}
	
	private void setTransferFunctions()
	{
		if (transfer_functions.get(0).isSelected())			this.runner.setTransferFunction(new Bivalent());
		else if (transfer_functions.get(1).isSelected())	this.runner.setTransferFunction(new Trivalent());
		else if (transfer_functions.get(2).isSelected())	this.runner.setTransferFunction(new Sigmoid());
		else if (transfer_functions.get(3).isSelected())	this.runner.setTransferFunction(new Continuous());
		else												this.runner.setTransferFunction(new NoTransferFunction());
	}
	
	private void setInferenceRule(CustomRadioButton inference_rules)
	{
		if (inference_rules == null)					this.runner.setInferenceRule(new ModifiedKosko());
		else if (inference_rules.get(0).isSelected())	this.runner.setInferenceRule(new Kosko());
		else if (inference_rules.get(1).isSelected())	this.runner.setInferenceRule(new ModifiedKosko());
		else if (inference_rules.get(2).isSelected())	this.runner.setInferenceRule(new RescaledKosko());										
	}
	
	private void setParameters()
	{
		for (int i = 0; i < text_fields[0].length; i++)
		{
			String parameter = this.text_fields[0][i].getText().toString();
			if (parameter != null && !parameter.isEmpty())	this.runner.parameters.add(Double.parseDouble(parameter));
			else											this.runner.parameters.add(1.0);
		}
	}
}
