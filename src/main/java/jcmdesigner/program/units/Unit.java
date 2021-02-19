package jcmdesigner.program.units;

import java.util.ArrayList;
import java.util.List;

import org.megadix.jfcm.Concept;

import jcmdesigner.graphics.gui.CustomComboBox;
import jcmdesigner.graphics.gui.CustomGridPane;
import jcmdesigner.graphics.gui.CustomStage;
import jcmdesigner.graphics.gui.CustomTextField;
import jcmdesigner.graphics.menu.top.configurations.Configurations;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import jcmdesigner.program.App;
import jcmdesigner.program.map.Map;
import jcmdesigner.program.map.Relation;
import jcmdesigner.program.units.entities.Entity;

public class Unit extends Entity implements Configurations
{
	public Concept					concept;
	private Text					name_text;
	private int						name_x_offset;
	private int						name_y_offset;
	public List<Relation>			relations;
	public boolean					mouse_dragged;
	private CustomStage				configurations_stage;
	private CustomTextField[]		text_fields;
	public static final String		concepts_path			= "/concepts/";
	private String					color					= "blue";
	private static final Effect		shadow_effect			= new DropShadow(10, Color.BLACK);
	public static Line[] 			selected_lines			= new Line[4];

	public Unit(String name, int x, int y, String path)
	{
		super(x, y, path);
		this.concept = new Concept(name, "test_desc");
		this.concept.setInput(0.0);
		this.name_x_offset = (int) (this.size / 2 - name.length() * 5 + name.length() * 0.7);
		this.name_y_offset = -5;
		this.name_text = new Text(x + name_x_offset, y + name_y_offset, name);
		this.name_text.setStyle("-fx-font: 18px Alcubierre;");
		this.name_text.setSmooth(true);
		this.name_text.setFontSmoothingType(FontSmoothingType.LCD);
		App.main_border_pane.getChildren().add(name_text);
		this.relations = new ArrayList<Relation>();
		this.mouse_dragged = false;
		this.setOnMouseEntered(event -> this.setEffect(shadow_effect));
		this.setOnMouseExited(event -> this.setEffect(null));
	}
	
	public Unit(String name, Double input_value, int x, int y, String path)
	{
		this(name, x, y, path);
		if (input_value != null) this.concept.setInput(input_value.doubleValue());
	}
	
	@Override
	public void openConfigurations()
	{		
		VBox main_comp = new VBox();
		main_comp.setId("pane");
		main_comp.setAlignment(Pos.TOP_CENTER);
		
		main_comp.getChildren().add(new Label("Concept Configurations"));
		
		CustomGridPane grid_pane = new CustomGridPane(2, 4);

		grid_pane.add(new Label("Name"), 0, 0);
		grid_pane.add(new Label("Input"), 0, 1);
		this.text_fields = new CustomTextField[2];
		this.text_fields[0] = new CustomTextField(this, this.getName());
		this.text_fields[1] = new CustomTextField(this, this.concept.getInput());
		grid_pane.add(this.text_fields[0], 1, 0);
		grid_pane.add(this.text_fields[1], 1, 1);
		
		grid_pane.add(new Label("Color"), 0, 2);
		CustomComboBox combo_box = new CustomComboBox(this, this.color, "blue", "green", "red", "orange", "yellow");
		combo_box.valueProperty().addListener((ChangeListener<String>) (ov, t, choice) -> this.color = choice);
		grid_pane.add(combo_box, 1, 2);
		
		Button update_button = new Button("Update");
		update_button.setOnAction(event -> buttonOnAction());
		grid_pane.add(update_button, 0, this.text_fields.length + 1);

		Button delete_button = new Button("Delete");
		delete_button.setOnAction(event ->
		{
			Unit.this.remove();
			Map.last_selected_unit = null;
			this.configurations_stage.close();
		});
		grid_pane.add(delete_button, 1, this.text_fields.length + 1);
		main_comp.getChildren().add(grid_pane);
		
		this.configurations_stage = new CustomStage("Concept Configurations", 265, 335, main_comp, "/stylesheets/pop_up.css");
	}
	
	@Override
	public void buttonOnAction()
	{
		String new_name = this.text_fields[0].getText().toString();
		if (new_name != null && !new_name.isEmpty())
		{
			Map.cognitive_map.removeConcept(this.getName());
			this.concept.setName(new_name);
			Map.cognitive_map.addConcept(this.concept);
			this.name_text.setText(new_name);
			name_x_offset = (int) (this.size / 2 - new_name.length() * 5 + new_name.length() * 0.7);
			name_text.setX(this.position.x + this.name_x_offset);
			name_text.setY(this.position.y + this.name_y_offset);
		}
		String new_input = this.text_fields[1].getText().toString();
		if (new_input != null && !new_input.isEmpty())
			this.concept.setInput(Double.parseDouble(new_input));
		this.setImage(new Image(this.getClass().getResourceAsStream(Unit.concepts_path + color + "_concept.png")));
		this.configurations_stage.close();
	}
	
	public void remove()
	{
		if (Map.last_selected_unit == this)
			for (int i = 0; i < Unit.selected_lines.length; i++)
				App.main_border_pane.getChildren().remove(Unit.selected_lines[i]);
		
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
		App.main_border_pane.getChildren().remove(this.name_text);
		App.main_border_pane.getChildren().remove(this);
		Map.cognitive_map.removeConcept(this.getName());
		Map.units.remove(this);
		this.relations.clear();
	}

	public void tickRelations()
	{
		for (Relation relation : this.relations)
			relation.tick();
	}
	
	public String getName()
	{
		return this.concept.getName();
	}
	
	private void setPosition()
	{
		if (Map.last_selected_unit == this)
		{
			Unit.selected_lines[0].setStartX(this.position.x);
			Unit.selected_lines[0].setStartY(this.position.y);
			Unit.selected_lines[0].setEndX(this.position.x + this.size);
			Unit.selected_lines[0].setEndY(this.position.y);
			Unit.selected_lines[1].setStartX(this.position.x);
			Unit.selected_lines[1].setStartY(this.position.y + this.size);
			Unit.selected_lines[1].setEndX(this.position.x + this.size);
			Unit.selected_lines[1].setEndY(this.position.y + this.size);
			Unit.selected_lines[2].setStartX(this.position.x);
			Unit.selected_lines[2].setStartY(this.position.y);
			Unit.selected_lines[2].setEndX(this.position.x);
			Unit.selected_lines[2].setEndY(this.position.y + this.size);
			Unit.selected_lines[3].setStartX(this.position.x + this.size);
			Unit.selected_lines[3].setStartY(this.position.y);
			Unit.selected_lines[3].setEndX(this.position.x + this.size);
			Unit.selected_lines[3].setEndY(this.position.y + this.size);
		}
		this.name_text.setX(this.position.x + this.name_x_offset);
		this.name_text.setY(this.position.y + this.name_y_offset);
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
		this.toFront();
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
				if (Map.last_selected_unit != null && Map.last_selected_unit != this)
				{
					if (Unit.hasRelation(Map.last_selected_unit, this) == null)
					{
						Relation relation = new Relation(1, Map.last_selected_unit, this);
						Map.cognitive_map.addConnection(relation);
						Map.cognitive_map.connect(relation.getFrom().getName(), relation.getFrom().getName() + " -> " + relation.getTo().getName(), relation.getTo().getName());
						Map.last_selected_unit.relations.add(relation);
					}
					if (Map.last_selected_unit != null)
						for (int i = 0; i < Unit.selected_lines.length; i++)
							App.main_border_pane.getChildren().remove(selected_lines[i]);
					Map.last_selected_unit = null;
					event.consume();
					return;
				}
				else if (Map.last_selected_unit == null)
				{
					Map.last_selected_unit = this;
					Unit.selected_lines[0] = new Line(this.position.x, 				this.position.y, 				this.position.x + this.size,	this.position.y);
					Unit.selected_lines[1] = new Line(this.position.x, 				this.position.y + this.size, 	this.position.x + this.size,	this.position.y + this.size);
					Unit.selected_lines[2] = new Line(this.position.x, 				this.position.y, 				this.position.x,				this.position.y + this.size);
					Unit.selected_lines[3] = new Line(this.position.x + this.size, 	this.position.y, 				this.position.x + this.size,	this.position.y + this.size);
					for (int i = 0; i < Unit.selected_lines.length; i++)
						App.main_border_pane.getChildren().add(selected_lines[i]);
					this.requestFocus();
					event.consume();
					return;
				}
			}
			else if (event.getButton() == MouseButton.SECONDARY)
			{
				openConfigurations();
				event.consume();
				return;
			}
		}
	}
}
