package graphics.gui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;
import program.Program;
import program.map.Relation;
import program.map.runnners.Parameters;

public class BezierCurve extends CubicCurve
{	
	public BezierCurve(double start_position_x, double start_position_y, double control_X1, double control_Y1, double control_X2, double control_Y2, double end_position_x, double end_position_y, boolean interactive)
	{
		super(start_position_x, start_position_y, control_X1, control_Y1, control_X2, control_Y2, end_position_x, end_position_y);
		this.setSmooth(true);
		if (interactive)
		{
			this.setStroke(null);
			this.setStrokeWidth(5);
		}
		else
		{
			this.setStroke(Color.BLACK);
			this.setStrokeWidth(2);
		}
		this.setStrokeLineCap(StrokeLineCap.ROUND);
		this.setFill(null);
		Program.layout.getChildren().add(this);
	}
	
	public BezierCurve(Relation relation, double start_position_x, double start_position_y, double control_X1, double control_Y1, double control_X2, double control_Y2, double end_position_x, double end_position_y, boolean interactive)
	{
		super(start_position_x, start_position_y, control_X1, control_Y1, control_X2, control_Y2, end_position_x, end_position_y);
		this.setSmooth(true);
		if (interactive)
		{
			this.setStroke(Color.BLACK);
			this.setOpacity(0.1);
			this.setStrokeWidth(15);
		}
		else
		{
			this.setStroke(Color.BLACK);
			this.setStrokeWidth(2);
		}
		this.setStrokeLineCap(StrokeLineCap.ROUND);
		this.setFill(null);
		this.setOnMousePressed(event ->
		{
			if (event.getButton() == MouseButton.PRIMARY)
			{
				event.consume();
				return;
			}
			Stage settings_stage = new Stage();
			
			VBox main_comp = new VBox();
			main_comp.setId("pane");
			main_comp.setAlignment(Pos.CENTER);
			
			Scene scene = new Scene(main_comp, 275 + 60 * Parameters.fuzzy_string_values.length, 175);
		    scene.getStylesheets().add(Program.class.getResource("/stylesheets/pop_up.css").toExternalForm());

		    GridPane numeric_weight_comp = new GridPane();
		    numeric_weight_comp.setId("pane");
		    ColumnConstraints column = new ColumnConstraints();
		    column.setPercentWidth(50);
		    column.setHalignment(HPos.CENTER);
		    numeric_weight_comp.getColumnConstraints().addAll(column, column);
		    RowConstraints row = new RowConstraints();
		    row.setPercentHeight(100);
		    row.setValignment(VPos.CENTER);
		    numeric_weight_comp.getRowConstraints().addAll(row);
		    
			Label weight_value_label = new Label("Weight");
			numeric_weight_comp.add(weight_value_label, 0, 0);
			
			TextField weight_value_text_field = new TextField();
			weight_value_text_field.setId("text_field");
			weight_value_text_field.setAlignment(Pos.CENTER);
			weight_value_text_field.setPromptText(relation.getWeight() + "");
			weight_value_text_field.setFocusTraversable(false);
			weight_value_text_field.setOnKeyPressed(key_event ->
			{
				if (key_event.getCode() == KeyCode.ENTER) updateValues(relation, settings_stage, weight_value_text_field);
			});
			numeric_weight_comp.add(weight_value_text_field, 1, 0);
			main_comp.getChildren().add(numeric_weight_comp);
		  
			if (Parameters.fuzzy_string_values.length != 0)
			{
				GridPane fuzzy_weight_comp = new GridPane();
				fuzzy_weight_comp.setId("pane");
				column = new ColumnConstraints();
			    column.setPercentWidth(100 / Parameters.fuzzy_string_values.length);
			    column.setHalignment(HPos.CENTER);
			    fuzzy_weight_comp.getRowConstraints().addAll(row);
			    
			    RadioButton[] weights = new RadioButton[Parameters.fuzzy_string_values.length];
			    for (int i= 0; i < Parameters.fuzzy_string_values.length; i++)
			    {
			    	fuzzy_weight_comp.getColumnConstraints().add(column);
			    	weights[i] = new RadioButton(Parameters.fuzzy_string_values[i]);
			    }
			    if (Parameters.fuzzy_string_values.length == 2)
			    {
			    	weights[0].setOnAction(event_ -> { relation.setWeight(Parameters.fuzzy_double_values[0]); weight_value_text_field.setPromptText(relation.getWeight() + ""); relation.weight_text.setText(relation.getWeight() + ""); });
			    	weights[1].setOnAction(event_ -> { relation.setWeight(Parameters.fuzzy_double_values[1]); weight_value_text_field.setPromptText(relation.getWeight() + ""); relation.weight_text.setText(relation.getWeight() + ""); });
			    }
			    else if (Parameters.fuzzy_string_values.length == 3)
			    {
			    	weights[0].setOnAction(event_ -> { relation.setWeight(Parameters.fuzzy_double_values[0]); weight_value_text_field.setPromptText(relation.getWeight() + ""); relation.weight_text.setText(relation.getWeight() + ""); });
			    	weights[1].setOnAction(event_ -> { relation.setWeight(Parameters.fuzzy_double_values[1]); weight_value_text_field.setPromptText(relation.getWeight() + ""); relation.weight_text.setText(relation.getWeight() + ""); });
			    	weights[2].setOnAction(event_ -> { relation.setWeight(Parameters.fuzzy_double_values[2]); weight_value_text_field.setPromptText(relation.getWeight() + ""); relation.weight_text.setText(relation.getWeight() + ""); });
			    }
			    else if (Parameters.fuzzy_string_values.length == 4)
			    {
			    	weights[0].setOnAction(event_ -> { relation.setWeight(Parameters.fuzzy_double_values[0]); weight_value_text_field.setPromptText(relation.getWeight() + ""); relation.weight_text.setText(relation.getWeight() + ""); });
			    	weights[1].setOnAction(event_ -> { relation.setWeight(Parameters.fuzzy_double_values[1]); weight_value_text_field.setPromptText(relation.getWeight() + ""); relation.weight_text.setText(relation.getWeight() + ""); });
			    	weights[2].setOnAction(event_ -> { relation.setWeight(Parameters.fuzzy_double_values[2]); weight_value_text_field.setPromptText(relation.getWeight() + ""); relation.weight_text.setText(relation.getWeight() + ""); });
			    	weights[3].setOnAction(event_ -> { relation.setWeight(Parameters.fuzzy_double_values[3]); weight_value_text_field.setPromptText(relation.getWeight() + ""); relation.weight_text.setText(relation.getWeight() + ""); });
			    }
			    else if (Parameters.fuzzy_string_values.length == 5)
			    {
			    	weights[0].setOnAction(event_ -> { relation.setWeight(Parameters.fuzzy_double_values[0]); weight_value_text_field.setPromptText(relation.getWeight() + ""); relation.weight_text.setText(relation.getWeight() + ""); });
			    	weights[1].setOnAction(event_ -> { relation.setWeight(Parameters.fuzzy_double_values[1]); weight_value_text_field.setPromptText(relation.getWeight() + ""); relation.weight_text.setText(relation.getWeight() + ""); });
			    	weights[2].setOnAction(event_ -> { relation.setWeight(Parameters.fuzzy_double_values[2]); weight_value_text_field.setPromptText(relation.getWeight() + ""); relation.weight_text.setText(relation.getWeight() + ""); });
			    	weights[3].setOnAction(event_ -> { relation.setWeight(Parameters.fuzzy_double_values[3]); weight_value_text_field.setPromptText(relation.getWeight() + ""); relation.weight_text.setText(relation.getWeight() + ""); });
			    	weights[4].setOnAction(event_ -> { relation.setWeight(Parameters.fuzzy_double_values[4]); weight_value_text_field.setPromptText(relation.getWeight() + ""); relation.weight_text.setText(relation.getWeight() + ""); });
			    }
			    
			    ToggleGroup weights_group = new ToggleGroup();
				for (int i = 0; i < weights.length; i++)
				{
					weights[i].setToggleGroup(weights_group);
					fuzzy_weight_comp.add(weights[i], i, 0);
				}
				main_comp.getChildren().add(fuzzy_weight_comp);
			}
		    
			GridPane buttons_weight_comp = new GridPane();
			buttons_weight_comp.setId("pane");
		    column = new ColumnConstraints();
		    column.setPercentWidth(50);
		    column.setHalignment(HPos.CENTER);
		    buttons_weight_comp.getColumnConstraints().addAll(column, column);
		    buttons_weight_comp.getRowConstraints().addAll(row);
			
			Button update_button = new Button("Update");
			update_button.setOnAction(event_ -> updateValues(relation, settings_stage, weight_value_text_field));
			buttons_weight_comp.add(update_button, 0, 0);

			Button delete_relation_button = new Button("Delete");
			delete_relation_button.setOnAction(event_ -> { relation.remove(); settings_stage.close(); });
			buttons_weight_comp.add(delete_relation_button, 1, 0);
			main_comp.getChildren().add(buttons_weight_comp);

			settings_stage.setScene(scene);
			settings_stage.setTitle("Relation Settings");
			settings_stage.setResizable(false);
			settings_stage.show();
		});
		Program.layout.getChildren().add(this);
	}
	
	private void updateValues(Relation relation, Stage settings_stage, TextField weight_value_text_field)
	{
		try
		{
			relation.setWeight(Double.parseDouble(weight_value_text_field.getText().toString()));
			relation.weight_text.setText(relation.getWeight() + "");
			settings_stage.close();
		}
		catch (Exception exception)	{ return; }
		weight_value_text_field.clear();
		weight_value_text_field.setPromptText(relation.getWeight() + "");
	}
}
