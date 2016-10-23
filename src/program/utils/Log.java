package program.utils;

import java.util.ArrayList;
import java.util.List;

import program.inputs.Mouse;

public final class Log
{
	private static List<String> logs = new ArrayList<String>();

	public static void addLog(String string)
	{
		Log.logs.add(string);
	}

	public static void addLog(int number)
	{
		addLog(number + "");
	}

	public static void addLog(float number)
	{
		addLog(number + "");
	}

	public static void addLog(double number)
	{
		addLog(number + "");
	}

	public static void addLog(long number)
	{
		addLog(number + "");
	}

	public static void consoleOut()
	{
		for (String string : Log.logs)
			System.out.print(string + "\t");
		System.out.println();
		Log.logs.clear();
	}

	public static void consoleOutMousePosition()
	{
		Log.addLog("Unit(x: " + Mouse.position.x + " , y: " + Mouse.position.y + ")");
		Log.consoleOut();
	}
}
