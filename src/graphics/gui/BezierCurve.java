package graphics.gui;

import graphics.menu.top.configurations.Configurations;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;
import program.Program;
import program.map.Relation;
import program.map.runnners.Parameters;

public class BezierCurve extends CubicCurve implements Configurations
{
	private CustomStage		configurations_stage;
	private CustomTextField	weight_value_text_field;
	private Relation		relation;

	public BezierCurve(double start_position_x, double start_position_y, double control_X1, double control_Y1, double control_X2, double control_Y2, double end_position_x, double end_position_y)
	{
		super(start_position_x, start_position_y, control_X1, control_Y1, control_X2, control_Y2, end_position_x, end_position_y);
		this.setSmooth(true);
		this.setStroke(Color.BLACK);
		this.setStrokeLineCap(StrokeLineCap.ROUND);
		this.setFill(null);
		Program.main_border_pane.getChildren().add(this);
		this.setTranslateZ(0);
	}
	
	public BezierCurve(Relation relation, double start_position_x, double start_position_y, double control_X1, double control_Y1, double control_X2, double control_Y2, double end_position_x, double end_position_y, boolean interactive)
	{
		this(start_position_x, start_position_y, control_X1, control_Y1, control_X2, control_Y2, end_position_x, end_position_y);
		if (interactive)
		{
			this.relation = relation;
			this.setOpacity(0);
			this.setStrokeWidth(20);
			this.relation.getStartUnit().toFront();
			this.relation.getEndUnit().toFront();
			this.setOnMousePressed(event ->
			{
				if (event.getButton() == MouseButton.SECONDARY)
					this.openConfigurations();
			});
		}
		else
			this.setStrokeWidth(2);
	}
	
	@Override
	public void openConfigurations()
	{		
		VBox main_comp = new VBox();
		main_comp.setId("pane");
		main_comp.setAlignment(Pos.TOP_CENTER);
		
		main_comp.getChildren().add(new Label("Relation Configurations"));
		
	    CustomGridPane numeric_weight_comp = new CustomGridPane(2, 1);
		numeric_weight_comp.add(new Label("Weight"), 0, 0);
		this.weight_value_text_field = new CustomTextField(this, relation.getWeight());
		numeric_weight_comp.add(this.weight_value_text_field, 1, 0);
		main_comp.getChildren().add(numeric_weight_comp);
	  
		if (Parameters.fuzzy_string_values.length != 0)
		{
			CustomGridPane fuzzy_weight_comp = new CustomGridPane(Parameters.fuzzy_string_values.length, 1);
		    RadioButton[] weights = new RadioButton[Parameters.fuzzy_string_values.length];
		    for (int i = 0; i < weights.length; i++)
		    	weights[i] = new RadioButton(Parameters.fuzzy_string_values[i]);
		    	
		    weights[0].setOnAction(event_ ->
	     	{
	     		this.weight_value_text_field.setText(Parameters.fuzzy_double_values[0] + "");
	     		this.weight_value_text_field.setPromptText(Parameters.fuzzy_double_values[0] + "");
	     	});
		    weights[1].setOnAction(event_ ->
	     	{
	     		this.weight_value_text_field.setText(Parameters.fuzzy_double_values[1] + "");
	     		this.weight_value_text_field.setPromptText(Parameters.fuzzy_double_values[1] + "");
	     	});
		    if (weights.length >= 3)
		    	weights[2].setOnAction(event_ ->
		     	{
		     		this.weight_value_text_field.setText(Parameters.fuzzy_double_values[2] + "");
		     		this.weight_value_text_field.setPromptText(Parameters.fuzzy_double_values[2] + "");
		     	});
		    if (weights.length >= 4)
		        weights[3].setOnAction(event_ ->
		     	{
		     		this.weight_value_text_field.setText(Parameters.fuzzy_double_values[3] + "");
		     		this.weight_value_text_field.setPromptText(Parameters.fuzzy_double_values[3] + "");
		     	});
		    if (weights.length == 5)
		        weights[4].setOnAction(event_ ->
		     	{
		     		this.weight_value_text_field.setText(Parameters.fuzzy_double_values[4] + "");
		     		this.weight_value_text_field.setPromptText(Parameters.fuzzy_double_values[4] + "");
		     	});
		    
		    ToggleGroup weights_group = new ToggleGroup();
			for (int i = 0; i < weights.length; i++)
			{
				weights[i].setToggleGroup(weights_group);
				fuzzy_weight_comp.add(weights[i], i, 0);
			}
			main_comp.getChildren().add(fuzzy_weight_comp);
		}
	    
		CustomGridPane buttons_weight_comp = new CustomGridPane(2, 1);
		Button update_button = new Button("Update");
		update_button.setOnAction(event_ -> this.buttonOnAction());
		buttons_weight_comp.add(update_button, 0, 0);
		Button delete_relation_button = new Button("Delete");
		delete_relation_button.setOnAction(event_ ->
		{
			this.relation.remove();
			this.configurations_stage.close();
		});
		buttons_weight_comp.add(delete_relation_button, 1, 0);
		main_comp.getChildren().add(buttons_weight_comp);

		int width = 275 + 60 * Parameters.fuzzy_string_values.length;
		int height = 190;
		if (Parameters.fuzzy_string_values.length != 0) height += 40;
		this.configurations_stage = new CustomStage("Relation Configurations", width, height, main_comp, "/stylesheets/pop_up.css");
	}
	
	@Override
	public void buttonOnAction()
	{
		String weight = this.weight_value_text_field.getText().toString();
		if (weight != null && !weight.isEmpty())
		{
			relation.setWeight(Double.parseDouble(weight));
			relation.weight_text.setText(weight);
		}
		this.configurations_stage.close();
	}
}
