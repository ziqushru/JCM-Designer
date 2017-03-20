package program.units;

import java.util.ArrayList;
import java.util.List;

import org.megadix.jfcm.Concept;

import graphics.Screen;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
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
	public boolean				mouse_dragged;

	public Unit(String name, int x, int y, String path)
	{
		super(x, y, path);
		this.concept = new Concept(name, "test_desc");
		this.concept.setInput(0.0);
		this.name_x_offset = (int) (this.size / 2 - name.length() / 2 * 6.5);
		this.name_y_offset = -5;
		this.name_text = new Text(x + name_x_offset, y + name_y_offset, name);
		this.name_text.setFill(Screen.HEX2ARGB(Screen.foreground_color));
		this.name_text.setFont(Screen.font);
		this.name_text.setSmooth(true);
		this.name_text.setFontSmoothingType(FontSmoothingType.LCD);
		Program.layout.getChildren().add(name_text);
		this.relations = new ArrayList<Relation>();
		this.setOnMouseEntered(event ->	Unit.this.setEffect(new DropShadow(15, Screen.HEX2ARGB(Screen.foreground_color))));
		this.setOnMouseExited(event -> Unit.this.setEffect(null));
		this.mouse_dragged = false;
	}
	
	private void openSettings()
	{
		Stage settings_stage = new Stage();

		GridPane main_comp = new GridPane();
		main_comp.setId("pane");
		ColumnConstraints column = new ColumnConstraints();
	    column.setPercentWidth(50);
	    column.setHalignment(HPos.CENTER);
	    main_comp.getColumnConstraints().addAll(column, column);
	    RowConstraints row = new RowConstraints();
	    row.setPercentHeight(33.33);
	    row.setValignment(VPos.CENTER);
	    main_comp.getRowConstraints().addAll(row, row, row);	    
	    
	    Scene scene = new Scene(main_comp, 250, 175);
	    scene.getStylesheets().add(Program.class.getResource("/stylesheets/pop_up.css").toExternalForm());

		Label[] labels = new Label[2];
		TextField[] text_fields = new TextField[2];
		for (int i = 0; i < labels.length; i++)
		{
			labels[i] = new Label();
			main_comp.add(labels[i], 0, i);
			text_fields[i] = new TextField();
			text_fields[i].setId("text_field");
			text_fields[i].setAlignment(Pos.CENTER);
			text_fields[i].setFocusTraversable(false);
			text_fields[i].setOnKeyPressed(event ->
			{
				if (event.getCode() == KeyCode.ENTER) updateValues(settings_stage, text_fields);
			});
			main_comp.add(text_fields[i], 1, i);
		}
		labels[0].setText("Name");
		labels[1].setText("Input");
		text_fields[0].setPromptText(this.getName());
		text_fields[1].setPromptText(this.concept.getInput() + "");
				
		Button update_button = new Button("Update");
		update_button.setOnAction(event -> updateValues(settings_stage, text_fields));
		main_comp.add(update_button, 0, labels.length);

		Button delete_button = new Button("Delete");
		delete_button.setOnAction(event ->
		{
			Unit.this.remove();
			settings_stage.close();
			Map.last_selected_unit = null;
		});
		main_comp.add(delete_button, 1, labels.length);
		
		settings_stage.setScene(scene);
		settings_stage.setTitle("Unit Settings");
		settings_stage.setResizable(false);
		settings_stage.show();
	}
	
	private void updateValues(Stage settings_stage, TextField[] text_fields)
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
					
		settings_stage.close();
	}

	public void remove()
	{
		for (int i = 0; i < this.relations.size(); i++)
		{
			Map.cognitive_map.removeConnection(this.relations.get(i).getName());
			this.relations.get(i--).remove();
		}

		for (Unit unit : Map.units)
		{
			Relation relation = null;
			if ((relation = Unit.hasRelation(unit, this)) != null)
			{
				Map.cognitive_map.removeConnection(relation.getName());
				relation.remove();
			}
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
		int x_position = this.position.x - Screen.X_OFFSET - Screen.WINDOWS_WTF_CONSTANT;
		int y_position = this.position.y - Screen.Y_OFFSET-  Screen.WINDOWS_WTF_CONSTANT;

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
	
	public void tick()
	{
		this.toFront();
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
	
	public static Relation hasRelation(Unit start, Unit end)
	{
		for (Relation relation : start.relations)
			if (relation.getStartUnit() == start && relation.getEndUnit() == end)
				return relation;
		return null;
	}
	
	@Override
	public void handle(MouseEvent event)
	{
		super.handle(event);
		if (event.getEventType() == MouseEvent.MOUSE_DRAGGED)
		{
			if (event.getButton() == MouseButton.PRIMARY)
			{
				mouse_dragged = true;
				this.setPosition();
				event.consume();
				return;
			}
		}
		else if (event.getEventType() == MouseEvent.MOUSE_CLICKED)
		{
			if (event.getButton() == MouseButton.PRIMARY)
			{
				if (mouse_dragged)
				{
					mouse_dragged = false;
					event.consume();
					return;
				}
				if (Map.last_selected_unit != null && Map.last_selected_unit != Unit.this)
				{
					if (Unit.hasRelation(Map.last_selected_unit, Unit.this) != null)
					{
						Map.last_selected_unit = null;
						event.consume();
						return;
					}
					Relation relation = new Relation(1, Map.last_selected_unit, Unit.this);
					Map.cognitive_map.addConnection(relation);
					Map.cognitive_map.connect(relation.getFrom().getName(), relation.getFrom().getName() + " -> " + relation.getTo().getName(), relation.getTo().getName());
					Map.last_selected_unit.relations.add(relation);
					Map.last_selected_unit = null;
					event.consume();
					return;
				}
				else if (Map.last_selected_unit == null && !mouse_dragged)
				{
					Map.last_selected_unit = this;
					this.requestFocus();
					event.consume();
					return;
				}
			}
			else if (event.getButton() == MouseButton.SECONDARY)
			{
				openSettings();
				event.consume();
				return;
			}
		}
	}
}
