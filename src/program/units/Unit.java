package program.units;

import java.util.ArrayList;
import java.util.List;

import graphics.Screen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import program.Program;
import program.inputs.Mouse;
import program.map.Map;
import program.map.Relation;
import program.units.entities.Entity;

public class Unit extends Entity
{
	private Text				name_text;
	private int					name_x_offset;
	private int					name_y_offset;
	private static final int	selected_color	= 0xFFFF2222;
	public List<Relation>		relations;

	public Unit(String name, int x, int y, String path)
	{
		super(x, y, path);
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
		this.setOnMouseDragged(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				Unit.this.setX(Mouse.position.x - Unit.this.size / 2);
				Unit.this.setY(Mouse.position.y - Unit.this.size / 2);
				Unit.this.name_text.setX(Unit.this.position.x + Unit.this.name_x_offset);
				Unit.this.name_text.setY(Unit.this.position.y + Unit.this.name_y_offset);

				for (Unit unit : Map.units)
					unit.tickRelations();
			}
		});
		this.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				if (event.getButton() == MouseButton.PRIMARY && Map.last_selected_unit != null && Map.last_selected_unit != Unit.this)
				{
					for (Relation relation : Map.last_selected_unit.relations)
						if (relation.getStartUnit() == Map.last_selected_unit && relation.getEndUnit() == Unit.this)
						{
							Map.last_selected_unit = null;
							return;
						}
					Map.last_selected_unit.relations.add(new Relation(1, Map.last_selected_unit, Unit.this));
					Map.last_selected_unit = null;
				}
				else if (event.getButton() == MouseButton.PRIMARY && Map.last_selected_unit == null)
					Map.last_selected_unit = Unit.this;
				else if (event.getButton() == MouseButton.SECONDARY) openSettings();
			}
		});
	}

	private void openSettings()
	{
		Stage settings_stage = new Stage();

		VBox main_comp = new VBox();
		main_comp.setPadding(new Insets(10));
		main_comp.setAlignment(Pos.TOP_CENTER);

		Label name_label = new Label("Name");
		name_label.setPadding(new Insets(10));
		main_comp.getChildren().add(name_label);

		TextField name_text_field = new TextField();
		name_text_field.setPadding(new Insets(10));
		name_text_field.setMinWidth(100);
		name_text_field.setPrefWidth(100);
		name_text_field.setMaxWidth(100);
		name_text_field.setPromptText(this.name_text.getText());
		main_comp.getChildren().add(name_text_field);

		GridPane buttons_comp = new GridPane();
		buttons_comp.setHgap(10);
		buttons_comp.setVgap(10);
		buttons_comp.setAlignment(Pos.TOP_CENTER);

		Button name_button = new Button("Update Value");
		name_button.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				String new_name = name_text_field.getText().toString();
				int counter = 0;
				if (new_name != null && !new_name.isEmpty())
				{
					for (int i = 0; i < new_name.length(); i++)
						if (new_name.charAt(i) == ' ') counter++;
					if (counter != new_name.length())
					{
						Unit.this.name_text.setText(new_name);
						name_x_offset = (int) (Unit.this.size / 2 - Unit.this.name_text.getText().length() / 2 * 6.5);
						name_text.setX(Unit.this.position.x + Unit.this.name_x_offset);
						name_text.setY(Unit.this.position.y + Unit.this.name_y_offset);
					}
				}
				name_text_field.clear();
				name_text_field.setPromptText(Unit.this.name_text.getText());
			}
		});
		name_button.setPadding(new Insets(10));
		name_button.setAlignment(Pos.TOP_CENTER);
		buttons_comp.add(name_button, 0, 1);

		Button delete_button = new Button("Delete Unit");
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
		delete_button.setPadding(new Insets(10));
		delete_button.setAlignment(Pos.TOP_CENTER);
		buttons_comp.add(delete_button, 1, 1);
		main_comp.getChildren().add(buttons_comp);

		settings_stage.setScene(new Scene(main_comp, 300, 175));
		settings_stage.setTitle("Unit Settings");
		settings_stage.setResizable(false);
		settings_stage.show();
	}

	public void remove()
	{
		for (int i = 0; i < this.relations.size(); i++)
			this.relations.get(i--).remove();

		for (Unit unit : Map.units)
			for (int i = 0; i < unit.relations.size(); i++)
				if (unit.relations.get(i).getEndUnit() == this) unit.relations.get(i--).remove();

		Program.layout.getChildren().remove(this.name_text);
		Program.layout.getChildren().remove(this);
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
			int yy = 0 + y_position - offset;
			int xx = x + x_position;
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
}
