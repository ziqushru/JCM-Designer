package program.map;

import org.megadix.jfcm.conn.WeightedConnection;

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
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import program.Program;
import program.units.Unit;
import program.utils.Position;

public class Relation extends WeightedConnection
{
	private double				weight;
	private Text				weight_text;
	private final Unit			start_unit;
	private final Unit			end_unit;
	public final CubicCurve		curve;
	public final Arrow			arrow;
	public static final Color	color	= Color.web("#222222");

	public Relation(double new_weight, Unit start_unit, Unit end_unit)
	{
		super(start_unit.concept.getName() + " -> " + end_unit.concept.getName(), "test_desc", new_weight);
		this.setFrom(start_unit.concept);
		this.setTo(end_unit.concept);
		this.start_unit = start_unit;
		this.end_unit = end_unit;
		Position middle_position = getMiddlePoint();
		this.curve = new CubicCurve(start_unit.position.x + start_unit.size / 2, start_unit.position.y + start_unit.size / 2, middle_position.x, middle_position.y, middle_position.x, middle_position.y, end_unit.position.x + start_unit.size / 2, end_unit.position.y + start_unit.size / 2);
		this.curve.setSmooth(true);
		this.curve.setStroke(Relation.color);
		this.curve.setStrokeWidth(2);
		this.curve.setStrokeLineCap(StrokeLineCap.ROUND);
		this.curve.setFill(null);
		this.curve.setOnMouseClicked(new EventHandler<Event>()
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
							Relation.this.setWeight(Relation.this.weight);
							Relation.this.weight_text.setText(Relation.this.weight + "");
						}
						catch (Exception exception)
						{
							return;
						}
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
		Program.layout.getChildren().add(this.curve);
		this.arrow = new Arrow(this.curve, 0.5f, new double[] { 0, 0, 5, 10, -5, 10 });
		this.weight = new_weight;
		this.weight_text = new Text(middle_position.x, middle_position.y, weight + "");
		this.weight_text.setFill(Color.WHITE);
		this.weight_text.setSmooth(true);
		this.weight_text.setFontSmoothingType(FontSmoothingType.LCD);
		Program.layout.getChildren().add(weight_text);
	}

	public void remove()
	{
		Map.cognitive_map.removeConnection(Relation.this.getName());
		this.start_unit.relations.remove(this);
		Program.layout.getChildren().remove(this.curve);
		Program.layout.getChildren().remove(this.weight_text);
		Program.layout.getChildren().remove(this.arrow);
	}

	public void tick()
	{
		this.curve.setStartX(start_unit.position.x + start_unit.size / 2);
		this.curve.setStartY(start_unit.position.y + start_unit.size / 2);
		Position middle_position = getMiddlePoint();
		this.curve.setControlX1(middle_position.x);
		this.curve.setControlY1(middle_position.y);
		this.curve.setControlX2(middle_position.x);
		this.curve.setControlY2(middle_position.y);
		this.curve.setEndX(end_unit.position.x + start_unit.size / 2);
		this.curve.setEndY(end_unit.position.y + start_unit.size / 2);
		this.weight_text.setX(middle_position.x);
		this.weight_text.setY(middle_position.y - 5);
		this.arrow.tick();
	}

	public Unit getStartUnit() { return this.start_unit; }

	public Unit getEndUnit() { return this.end_unit; }

	public Position getMiddlePoint()
	{
		int x_start = this.start_unit.position.x + this.start_unit.size / 2;
		int y_start = this.start_unit.position.y + this.start_unit.size / 2;
		int x_end = this.end_unit.position.x + this.end_unit.size / 2;
		int y_end = this.end_unit.position.y + this.end_unit.size / 2;
		
		double cx = (x_start + x_end) / 2;
		double cy = (y_start + y_end) / 2;

		int horizontal = x_end - x_start;
		int vertical = y_start - y_end;
		double sin_angle = Math.sin(vertical / Math.sqrt(vertical * vertical + horizontal * horizontal));
		double cos_angle = Math.sin(horizontal / Math.sqrt(vertical * vertical + horizontal * horizontal));
		
		cx -= Arrow.Y_OFFSET * sin_angle;
		cy -= Arrow.Y_OFFSET * cos_angle;
		
		return new Position(cx, cy);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Relation)) return false;
		Relation other = (Relation) obj;
		if (!end_unit.equals(other.end_unit)) return false;
		if (start_unit == null)
		{
			if (other.start_unit != null) return false;
		}
		else if (!start_unit.equals(other.start_unit)) return false;
		
		return true;
	}	
}
