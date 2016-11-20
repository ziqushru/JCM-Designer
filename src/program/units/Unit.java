package program.units;

import java.util.ArrayList;
import java.util.List;

import org.megadix.jfcm.Concept;
import org.megadix.jfcm.act.CauchyActivator;
import org.megadix.jfcm.act.GaussianActivator;
import org.megadix.jfcm.act.HyperbolicTangentActivator;
import org.megadix.jfcm.act.IntervalActivator;
import org.megadix.jfcm.act.LinearActivator;
import org.megadix.jfcm.act.NaryActivator;
import org.megadix.jfcm.act.SigmoidActivator;
import org.megadix.jfcm.act.SignumActivator;

import graphics.Screen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import program.Program;
import program.map.Map;
import program.map.Relation;
import program.units.entities.Mob;

public class Unit extends Mob
{
	public Concept				concept;
	private Text				name_text;
	private int					name_x_offset;
	private int					name_y_offset;
	private static final int	selected_color	= 0xFFFF2222;
	public List<Relation>		relations;

	public Unit(String name, int x, int y, String path)
	{
		super(x, y, path);
		this.concept = new Concept(name, "test_desc");
		this.name_x_offset = (int) (this.size / 2 - name.length() / 2 * 6.5);
		this.name_y_offset = -5;
		this.name_text = new Text(x + name_x_offset, y + name_y_offset, name);
		this.name_text.setFill(Color.BLACK);
		this.name_text.setSmooth(true);
		this.name_text.setFontSmoothingType(FontSmoothingType.LCD);
		Program.layout.getChildren().add(name_text);

		this.relations = new ArrayList<Relation>();

		this.setOnMouseEntered(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				Unit.this.setEffect(new DropShadow(15, Color.LIGHTBLUE));
			}
		});
		this.setOnMouseExited(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				Unit.this.setEffect(null);
			}
		});
		
		this.setOnKeyPressed(event ->
		{
			if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP)
				this.moveUp();
			if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT)
				this.moveLeft();
			if (event.getCode() == KeyCode.R || event.getCode() == KeyCode.DOWN)
				this.moveDown();
			if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.RIGHT)
				this.moveRight();
			this.setPosition();
			event.consume();
		});
	}
	
	private void openSettings()
	{
		Stage settings_stage = new Stage();

		GridPane main_comp = new GridPane();
		main_comp.setPadding(new Insets(10));
		main_comp.setHgap(10);
		main_comp.setVgap(10);
		main_comp.setAlignment(Pos.TOP_CENTER);
		
		Label[] labels = new Label[3];
		TextField[] text_fields = new TextField[3];
		
		for (int i = 0; i < labels.length; i++)
		{
			labels[i] = new Label();
			labels[i].setPadding(new Insets(10));
			main_comp.add(labels[i], 0, i);

			text_fields[i] = new TextField();
			text_fields[i].setPadding(new Insets(10));
			text_fields[i].setMinWidth(100);
			text_fields[i].setPrefWidth(100);
			text_fields[i].setMaxWidth(100);
			main_comp.add(text_fields[i], 1, i);			
		}
		labels[0].setText("Name");
		labels[1].setText("Input");
		labels[2].setText("Output");
		text_fields[0].setPromptText(this.getName());
		text_fields[1].setPromptText(this.concept.getInput() + "");
		text_fields[2].setPromptText(this.concept.getOutput() + "");
		
		Label activator_label = new Label();
		activator_label.setText("Activator");
		activator_label.setPadding(new Insets(10));
		main_comp.add(activator_label, 0, labels.length);
		
		ObservableList<String> activator_options = FXCollections.observableArrayList("Signum", "Linear", "Sigmoid", "Hyperbolic Tangent", "Gaussian", "Cauchy", "Internal", "Nary");
		ComboBox<String> activator_combo_box = new ComboBox<String>(activator_options);
		activator_combo_box.setPadding(new Insets(10));
		activator_combo_box.setMinWidth(107);
		activator_combo_box.setPrefWidth(107);
		activator_combo_box.setMaxWidth(107);
		activator_combo_box.setOnAction(e -> 
		{
			String option = activator_combo_box.getSelectionModel().getSelectedItem();
			
			if(option.equals("Signum"))						this.concept.setConceptActivator(new SignumActivator());
			else if(option.equals("Linear"))				this.concept.setConceptActivator(new LinearActivator());
			else if(option.equals("Sigmoid"))				this.concept.setConceptActivator(new SigmoidActivator());
			else if(option.equals("Hyperbolic Tangent"))	this.concept.setConceptActivator(new HyperbolicTangentActivator());
			else if(option.equals("Gaussian"))				this.concept.setConceptActivator(new GaussianActivator());
			else if(option.equals("Cauchy"))				this.concept.setConceptActivator(new CauchyActivator());
			else if(option.equals("Interval"))				this.concept.setConceptActivator(new IntervalActivator());
			else if(option.equals("Nary"))					this.concept.setConceptActivator(new NaryActivator());
		});
		main_comp.add(activator_combo_box, 1, labels.length);
		
		Button update_button = new Button("Update Values");
		update_button.setPadding(new Insets(10));
		update_button.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				String new_name = text_fields[0].getText().toString();
				if (new_name != null && !new_name.isEmpty())
				{
					int counter = 0;
					for (int i = 0; i < new_name.length(); i++)
						if (new_name.charAt(i) == ' ') counter++;
					if (counter != new_name.length())
					{
						Unit.this.concept.setName(new_name);
						Unit.this.name_text.setText(new_name);
						name_x_offset = (int) (Unit.this.size / 2 - Unit.this.name_text.getText().length() / 2 * 6.5);
						name_text.setX(Unit.this.position.x + Unit.this.name_x_offset);
						name_text.setY(Unit.this.position.y + Unit.this.name_y_offset);
					}
				}
				text_fields[0].setPromptText(Unit.this.getName());
				
				String new_input = text_fields[1].getText().toString();
				if (new_input != null && !new_input.isEmpty())
				{
					try
					{
						Unit.this.concept.setInput(Double.parseDouble(new_input));
					}
					catch (Exception exception) { text_fields[1].clear(); return; }
					text_fields[1].setPromptText(Unit.this.concept.getInput() + "");
				}
				
				String new_output = text_fields[2].getText().toString();
				if (new_output != null && !new_output.isEmpty())
				{
					try
					{
						Unit.this.concept.setOutput(Double.parseDouble(new_output));
					}
					catch (Exception exception) { text_fields[2].clear(); return; }
					text_fields[2].setPromptText(Unit.this.concept.getOutput() + "");
				}

				for (TextField text_field : text_fields)
					text_field.clear();
			}
		});
		main_comp.add(update_button, 0, labels.length + 1);

		Button delete_button = new Button("Delete Unit");
		delete_button.setPadding(new Insets(10));
		delete_button.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				Unit.this.remove();
				settings_stage.close();
				Map.last_selected_unit = null;
			}
		});
		main_comp.add(delete_button, 1, labels.length + 1);

		settings_stage.setScene(new Scene(main_comp, 300, 250));
		settings_stage.setTitle("Unit Settings");
		settings_stage.setResizable(false);
		settings_stage.show();
	}

	public void remove()
	{
		for (int i = 0; i < this.relations.size(); i++)
		{
			Map.cognitive_map.removeConnection(this.relations.get(i).getName());
			this.relations.get(i--).remove();
		}

		for (Unit unit : Map.units)
			for (int i = 0; i < unit.relations.size(); i++)
				if (unit.relations.get(i).getEndUnit() == this)
				{
					Map.cognitive_map.removeConnection(unit.relations.get(i).getName());
					unit.relations.get(i--).remove();
				}

		Program.layout.getChildren().remove(this.name_text);
		Program.layout.getChildren().remove(this);
		Map.cognitive_map.removeConcept(this.getName());
		Map.units.remove(this);
		this.relations.clear();
	}

	public void tickRelations()
	{
		for (Relation relation : this.relations)
			relation.tick();
	}

	private void drawOutline(int offset, int color)
	{
		int x_position = this.position.x - Screen.X_OFFSET;
		int y_position = this.position.y - Screen.Y_OFFSET;

		for (int y = 0; y < this.size; y++)
		{
			int yy = y + y_position;
			if (yy < 0) break;
			if (yy > Screen.HEIGHT - this.size) break;
			
			int xx = 0 + x_position - offset;
			if (xx < 0) break;
			if (xx > Screen.WIDTH - this.size) break;
			Screen.pixels[xx + yy * Screen.WIDTH] = color;

			xx = this.size + x_position + offset - 1;
			if (xx < 0) break;
			if (xx > Screen.WIDTH - this.size) break;
			Screen.pixels[xx + yy * Screen.WIDTH] = color;
		}

		for (int x = 0; x < this.size; x++)
		{
			int xx = x + x_position;
			if (xx < 0) break;
			if (xx > Screen.WIDTH - this.size) break;
			
			int yy = 0 + y_position - offset;
			if (yy < 0) break;
			if (yy > Screen.HEIGHT - this.size) break;
			Screen.pixels[xx + yy * Screen.WIDTH] = color;

			yy = this.size + y_position + offset - 1;
			if (yy < 0) break;
			if (yy > Screen.HEIGHT - this.size) break;
			Screen.pixels[xx + yy * Screen.WIDTH] = color;
		}
	}

	public void drawSelected()
	{
		drawOutline(1, Unit.selected_color);
	}
	
	public String getName()
	{
		return this.concept.getName();
	}
	
	private void setPosition()
	{
		Unit.this.name_text.setX(Unit.this.position.x + Unit.this.name_x_offset);
		Unit.this.name_text.setY(Unit.this.position.y + Unit.this.name_y_offset);

		for (Unit unit : Map.units)
			unit.tickRelations();
	}
	
	@Override
	public void handle(MouseEvent event)
	{
		super.handle(event);
		
		if (event.getEventType() == MouseEvent.MOUSE_DRAGGED)
		{			
			this.setPosition();
		}
		else if (event.getEventType() == MouseEvent.MOUSE_CLICKED)
		{
			if (event.getButton() == MouseButton.PRIMARY)
			{
				if (Map.last_selected_unit != null && Map.last_selected_unit != Unit.this)
				{
					for (Relation relation : Map.last_selected_unit.relations)
						if (relation.getStartUnit() == Map.last_selected_unit && relation.getEndUnit() == Unit.this)
						{
							Map.last_selected_unit = null;
							return;
						}
					Relation relation = new Relation(1, Map.last_selected_unit, Unit.this);
					Map.cognitive_map.addConnection(relation);
					Map.cognitive_map.connect(relation.getFrom().getName(), relation.getFrom().getName() + " -> " + relation.getTo().getName(), relation.getTo().getName());
					Map.last_selected_unit.relations.add(relation);
					Map.last_selected_unit = null;
				}
				else if (Map.last_selected_unit == null)
				{
					Map.last_selected_unit = this;
					this.requestFocus();
				}
			}
			else if (event.getButton() == MouseButton.SECONDARY)
				openSettings();
		}
	}
}
