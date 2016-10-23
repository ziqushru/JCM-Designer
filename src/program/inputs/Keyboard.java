package program.inputs;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public final class Keyboard implements EventHandler<KeyEvent>
{
	/*
	 * 0 -> exit	(ESC)
	 * 1 -> up		(W)(UP)
	 * 2 -> down	(A)(LEFT)
	 * 3 -> left	(R)(DOWN)
	 * 4 -> right	(S)(RIGHT)
	 * 5 -> select	(SPACEBAR)
	 */
	public static boolean keys[] = new boolean[6];

	public static void pressed(KeyEvent event)
	{
		switch (event.getCode())
		{
			case ESCAPE:
				Keyboard.keys[0] = true;
				break;
			case W:
				Keyboard.keys[1] = true;
				break;
			case UP:
				Keyboard.keys[1] = true;
				break;
			case A:
				Keyboard.keys[2] = true;
				break;
			case LEFT:
				Keyboard.keys[2] = true;
				break;
			case R:
				Keyboard.keys[3] = true;
				break;
			case DOWN:
				Keyboard.keys[3] = true;
				break;
			case S:
				Keyboard.keys[4] = true;
				break;
			case RIGHT:
				Keyboard.keys[4] = true;
				break;
			case SPACE:
				Keyboard.keys[5] = true;
				break;
			default:
				break;
		}
		event.consume();
	}

	public static void released(KeyEvent event)
	{
		switch (event.getCode())
		{
			case ESCAPE:
				Keyboard.keys[0] = false;
				break;
			case W:
				Keyboard.keys[1] = false;
				break;
			case UP:
				Keyboard.keys[1] = false;
				break;
			case A:
				Keyboard.keys[2] = false;
				break;
			case LEFT:
				Keyboard.keys[2] = false;
				break;
			case R:
				Keyboard.keys[3] = false;
				break;
			case DOWN:
				Keyboard.keys[3] = false;
				break;
			case S:
				Keyboard.keys[4] = false;
				break;
			case RIGHT:
				Keyboard.keys[4] = false;
				break;
			case SPACE:
				Keyboard.keys[5] = false;
				break;
			default:
				break;
		}
		event.consume();
	}

	public static void typed(KeyEvent event) {}

	@Override
	public void handle(KeyEvent event) {}
}
