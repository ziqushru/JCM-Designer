package program.units;

import java.util.ArrayList;
import java.util.List;

import graphics.Screen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import program.inputs.Mouse;
import program.map.Connection;
import program.map.Map;
import program.units.entities.Entity;

public class Unit extends Entity
{
	private String				name;
	private static final int	pressed_color		= 0xFF2222FF;
	private static final int	selected_color		= 0xFFFF2222;
	private List<Connection>	connections;
	private Stage				settings_stage;
	private boolean				settings_displayed	= false;

	public Unit(String name, int x, int y, int size, int color)
	{
		super(x, y, size, color);
		this.name = name;
		this.connections = new ArrayList<Connection>();
	}

	public Unit(String name, int x, int y, String path)
	{
		super(x, y, path);
		this.name = name;
		this.connections = new ArrayList<Connection>();
	}

	@Override
	public void tick()
	{
		if (checkPressed(MouseButton.PRIMARY))
		{
			if (check()) return;
			drawPressed();
			drag();
		}
		else if (checkPressed(MouseButton.SECONDARY))
		{
			openSettings();
		}

		normalizePosition();

		tickPixels();
	}

	private void openSettings()
	{
		if (settings_stage == null && !settings_displayed)
		{
			settings_stage = new Stage();

			VBox main_comp = new VBox();

			HBox name_comp = new HBox();
			Label name_label = new Label("Name");
			TextField name_text_field = new TextField();
			name_text_field.setPromptText(this.name);
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
						if (counter != new_name.length()) name = new_name;
					}
					name_text_field.clear();
					name_text_field.setPromptText(name);
				}
			});
			name_label.setPadding(new Insets(10));
			name_text_field.setPadding(new Insets(10));
			name_button.setPadding(new Insets(10));
			name_comp.getChildren().add(name_label);
			name_comp.getChildren().add(name_text_field);
			name_comp.getChildren().add(name_button);

			VBox delete_comp = new VBox();
			Button delete_connection_button = new Button("Delete connection");
			delete_connection_button.setOnAction(new EventHandler<ActionEvent>()
			{
				@Override
				public void handle(ActionEvent event)
				{
					System.out.println("Remove Connection");
				}
			});
			Button delete_unit_button = new Button("Delete unit");
			delete_unit_button.setOnAction(new EventHandler<ActionEvent>()
			{
				@Override
				public void handle(ActionEvent event)
				{
					Map.checkRemoveUnit(Mouse.position);
					settings_stage = null;
					settings_displayed = false;
				}
			});

			delete_comp.getChildren().add(delete_connection_button);
			delete_comp.getChildren().add(delete_unit_button);

			main_comp.getChildren().add(name_comp);
			main_comp.getChildren().add(delete_comp);

			Scene stageScene = new Scene(main_comp, 300, 300);
			settings_stage.setScene(stageScene);
			settings_stage.setTitle(name + "Settings");
			settings_stage.setResizable(false);
			settings_stage.show();
			settings_displayed = true;
		}
	}

	public void drawConnections()
	{
		for (Connection connection : connections)
			connection.render();
	}

	private boolean check()
	{
		if (Map.last_selected_unit != null) if (this.equals(Map.last_selected_unit) == false)
		{
			Connection connection = new Connection(1, this, Map.last_selected_unit);
			connection.drawLine();
			this.connections.add(connection);
			
			Map.last_selected_unit = null;
			return true;
		}
		return false;
	}

	private boolean checkPressed(MouseButton mouse_button)
	{
		if (Mouse.pressed && Mouse.button == mouse_button) if (Mouse.position.x >= this.position.x && Mouse.position.x < this.position.x + this.size) if (Mouse.position.y >= this.position.y && Mouse.position.y < this.position.y + this.size) return true;
		return false;
	}

	private void drag()
	{
		this.position.x = Mouse.position.x - this.size / 2;
		this.position.y = Mouse.position.y - this.size / 2;
	}

	private void drawOutline(int offset, int color)
	{
		for (int y = 0; y < this.size; y++)
		{
			int yy = y + this.position.y;
			int xx = 0 + this.position.x - offset;
			Screen.pixels[xx + yy * Screen.WIDTH] = color;
		}

		for (int y = 0; y < this.size; y++)
		{
			int yy = y + this.position.y;
			int xx = this.size + this.position.x + offset - 1;
			Screen.pixels[xx + yy * Screen.WIDTH] = color;
		}

		for (int x = 0; x < this.size; x++)
		{
			int yy = 0 + this.position.y - offset;
			int xx = x + this.position.x;
			Screen.pixels[xx + yy * Screen.WIDTH] = color;
		}

		for (int x = 0; x < this.size; x++)
		{
			int yy = this.size + this.position.y + offset - 1;
			int xx = x + this.position.x;
			Screen.pixels[xx + yy * Screen.WIDTH] = color;
		}
	}

	private void drawPressed()
	{
		drawOutline(1, Unit.pressed_color);
	}

	public void drawSelected()
	{
		if (!checkPressed(MouseButton.PRIMARY)) drawOutline(1, Unit.selected_color);
	}

	public void tickName()
	{
		Screen.graphics_context.fillText(this.name, this.position.x + this.size / 2 - this.name.length() / 2 * 7.5, this.position.y - 5);
	}
}
