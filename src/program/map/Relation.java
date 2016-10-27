package program.map;

import graphics.gui.Arrow;
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
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import program.Program;
import program.units.Unit;

public class Relation
{
	private double				weight;
	private Text				weight_text;
	private final Unit			start_unit;
	private final Unit			end_unit;
	public final Line			line;
	public final Arrow			arrow;
	public static final Color	color	= Color.web("#AAAA55");

	public Relation(double new_weight, Unit start_unit, Unit end_unit)
	{
		this.weight = new_weight;
		this.weight_text = new Text(weight + "");
		this.weight_text.setFill(Color.WHITE);
		this.weight_text.setSmooth(true);
		this.weight_text.setFontSmoothingType(FontSmoothingType.LCD);
		this.weight_text.setX((start_unit.position.x + end_unit.position.x) / 2);
		this.weight_text.setY((start_unit.position.y + end_unit.position.y) / 2 - 5);
		Program.layout.getChildren().add(weight_text);

		this.start_unit = start_unit;
		this.end_unit = end_unit;
		this.line = new Line(start_unit.position.x + start_unit.size / 2, start_unit.position.y + start_unit.size / 2, end_unit.position.x + start_unit.size / 2, end_unit.position.y + start_unit.size / 2);
		this.line.setSmooth(true);
		this.line.setStroke(Relation.color);
		this.line.setStrokeWidth(2);
		this.line.setStrokeLineCap(StrokeLineCap.ROUND);
		this.arrow = new Arrow(line, 0.5f, new double[] { 0, 0, 5, 10, -5, 10 });
		this.line.setOnMouseClicked(new EventHandler<Event>()
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
				weight_value_text_field.setTooltip(new Tooltip(Relation.this.weight + ""));
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
							Relation.this.weight = Double.parseDouble(weight_value_text_field.getText().toString());
							Relation.this.weight_text.setText(weight + "");
						}
						catch (Exception exception) { return; }
						weight_value_text_field.clear();
						weight_value_text_field.setPromptText(Relation.this.weight + "");
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
						Relation.this.remove();
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
		Program.layout.getChildren().add(this.line);
	}
	
	public void remove()
	{
		start_unit.relations.remove(this);
		Program.layout.getChildren().remove(line);
		Program.layout.getChildren().remove(weight_text);
		Program.layout.getChildren().remove(arrow);
	}

	public void tick()
	{
		this.line.setStartX(start_unit.position.x + start_unit.size / 2);
		this.line.setStartY(start_unit.position.y + start_unit.size / 2);
		this.line.setEndX(end_unit.position.x + start_unit.size / 2);
		this.line.setEndY(end_unit.position.y + start_unit.size / 2);
		this.weight_text.setX((start_unit.position.x + end_unit.position.x) / 2);
		this.weight_text.setY((start_unit.position.y + end_unit.position.y) / 2 - 5);
		this.arrow.tick();
	}
	
	public Unit getStartUnit() { return this.start_unit; }
	public Unit getEndUnit() { return this.end_unit; }
}
