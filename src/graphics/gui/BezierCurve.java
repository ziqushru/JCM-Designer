package graphics.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;
import program.Program;
import program.map.Relation;

public class BezierCurve extends CubicCurve
{
	public BezierCurve(double start_position_x, double start_position_y, double control_X1, double control_Y1, double control_X2, double control_Y2, double end_position_x, double end_position_y)
	{
		super(start_position_x, start_position_y, control_X1, control_Y1, control_X2, control_Y2, end_position_x, end_position_y);
		this.setSmooth(true);
		this.setStroke(Color.BLACK);
		this.setStrokeWidth(2);
		this.setStrokeLineCap(StrokeLineCap.ROUND);
		this.setFill(null);
		Program.layout.getChildren().add(this);
	}
	
	public BezierCurve(Relation relation, double start_position_x, double start_position_y, double control_X1, double control_Y1, double control_X2, double control_Y2, double end_position_x, double end_position_y)
	{
		super(start_position_x, start_position_y, control_X1, control_Y1, control_X2, control_Y2, end_position_x, end_position_y);
		this.setSmooth(true);
		this.setStroke(Color.BLACK);
		this.setStrokeWidth(2);
		this.setStrokeLineCap(StrokeLineCap.ROUND);
		this.setFill(null);
		this.setOnMouseClicked(new EventHandler<Event>()
		{
			@Override
			public void handle(Event event)
			{
				Stage settings_stage = new Stage();
				VBox main_comp = new VBox();
				main_comp.setPadding(new Insets(10));
				main_comp.setAlignment(Pos.TOP_CENTER);

				Label weight_value_label = new Label("Weight");
				weight_value_label.setPadding(new Insets(10));
				main_comp.getChildren().add(weight_value_label);

				GridPane weight_value_comp = new GridPane();
				weight_value_comp.setHgap(10);
				weight_value_comp.setVgap(10);
				weight_value_comp.setAlignment(Pos.TOP_CENTER);
				TextField weight_value_text_field = new TextField();
				weight_value_text_field.setPadding(new Insets(10));
				weight_value_text_field.setMinWidth(100);
				weight_value_text_field.setPrefWidth(100);
				weight_value_text_field.setMaxWidth(100);
				weight_value_text_field.setTooltip(new Tooltip(relation.weight + ""));
				weight_value_comp.add(weight_value_text_field, 0, 0);

				ObservableList<String> weight_fuzzy_options = FXCollections.observableArrayList("Low", "Medium", "High");
				ComboBox<String> weight_fuzzy_combo_box = new ComboBox<String>(weight_fuzzy_options);
				weight_fuzzy_combo_box.setPadding(new Insets(10));
				weight_fuzzy_combo_box.setMinWidth(107);
				weight_fuzzy_combo_box.setPrefWidth(107);
				weight_fuzzy_combo_box.setMaxWidth(107);
				weight_value_comp.add(weight_fuzzy_combo_box, 1, 0);

				Button weight_button = new Button("Update Value");
				weight_button.setOnAction(new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent event)
					{
						try
						{
							relation.weight = Double.parseDouble(weight_value_text_field.getText().toString());
							relation.setWeight(relation.weight);
							relation.weight_text.setText(relation.weight + "");
							settings_stage.close();
						}
						catch (Exception exception)	{ return; }
						weight_value_text_field.clear();
						weight_value_text_field.setPromptText(relation.weight + "");
					}
				});
				weight_button.setPadding(new Insets(10));
				weight_button.setAlignment(Pos.TOP_CENTER);
				weight_value_comp.add(weight_button, 0, 1);

				Button delete_relation_button = new Button("Delete Relation");
				delete_relation_button.setOnAction(new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent event)
					{
						relation.remove();
						settings_stage.close();
					}
				});
				delete_relation_button.setPadding(new Insets(10));
				delete_relation_button.setAlignment(Pos.TOP_CENTER);
				weight_value_comp.add(delete_relation_button, 1, 1);
				main_comp.getChildren().add(weight_value_comp);

				settings_stage.setScene(new Scene(main_comp, 300, 175));
				settings_stage.setTitle("Relation Settings");
				settings_stage.setResizable(false);
				settings_stage.show();
			}
		});
		Program.layout.getChildren().add(this);
	}
	
	
}
