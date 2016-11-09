package program.map;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.filechooser.FileSystemView;

import org.megadix.jfcm.CognitiveMap;
import org.megadix.jfcm.Concept;
import org.megadix.jfcm.FcmConnection;
import org.megadix.jfcm.act.SignumActivator;
import org.megadix.jfcm.conn.WeightedConnection;
import org.megadix.jfcm.utils.FcmIO;

import graphics.Screen;
import program.units.Unit;
import program.utils.Log;

public final class Map
{
	public static CognitiveMap			cognitive_map		= new CognitiveMap();
	public static List<Unit>			units				= new ArrayList<Unit>();
	public static Unit					last_selected_unit	= null;
	public static final SignumActivator signum_activator	= new SignumActivator();

	public static void clear()
	{
		for (int i = 0; i < Map.units.size(); i++)
		{
			units.get(i--).remove();
		}
		Map.cognitive_map.reset();
		Map.units.clear();
		Map.last_selected_unit = null;
	}

	public static void tick()
	{
		if (last_selected_unit != null) last_selected_unit.drawSelected();
	}

	public static void save()
	{
		FileSystemView filesys = FileSystemView.getFileSystemView();
		try
		{
			FcmIO.saveAsXml(Map.cognitive_map, filesys.getHomeDirectory().getPath() + "\\map");
		}
		catch (FileNotFoundException e) { Log.addLog("Error: couldn't find Desktop"); Log.consoleOut(); }
	}
	
	public static void load()
	{
		Map.clear();
		FileSystemView filesys = FileSystemView.getFileSystemView();
		try
		{
			Map.cognitive_map = FcmIO.loadXml(filesys.getHomeDirectory().getPath() + "\\map").get(0);
		}
		catch (FileNotFoundException e) { Log.addLog("Error: couldn't find Desktop"); Log.consoleOut(); return; }
		catch (ParseException e) { Log.addLog("Error: couldn't load xml"); Log.consoleOut(); return; }
		
		java.util.Map<String, Concept> concepts_map = Map.cognitive_map.getConcepts();		
		java.util.Map<String, FcmConnection> connnections_map = Map.cognitive_map.getConnections();		
		
		int counter = 0;
		for (java.util.Map.Entry<String, Concept> concept : concepts_map.entrySet())
		{
			Unit unit = new Unit(concept.getValue().getName(), (Screen.WIDTH / 2 - concepts_map.size() * 32 + counter++ * 100), Screen.HEIGHT / 2 - concepts_map.size() * 32 / 2, "factor");
			Map.units.add(unit);
		}
	
		for (Entry<String, FcmConnection> connections : connnections_map.entrySet())
			for (Unit start_unit : Map.units)
				if ((connections.getValue().getFrom().getName().equals(start_unit.concept.getName())))
					for (Unit end_unit : Map.units)
						if ((connections.getValue().getTo().getName().equals(end_unit.concept.getName())))
							start_unit.relations.add(new Relation(((WeightedConnection) connections.getValue()).getWeight(), start_unit, end_unit));
	}
}
