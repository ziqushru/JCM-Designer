package program.map;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.megadix.jfcm.CognitiveMap;
import org.megadix.jfcm.Concept;
import org.megadix.jfcm.ConceptActivator;
import org.megadix.jfcm.FcmConnection;
import org.megadix.jfcm.act.SigmoidActivator;
import org.megadix.jfcm.conn.WeightedConnection;
import org.megadix.jfcm.utils.FcmIO;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import program.Program;
import program.map.runnners.Parameters;
import program.map.runnners.Runner;
import program.units.Unit;
import program.utils.Log;

public final class Map
{
	public static CognitiveMap	cognitive_map		= new CognitiveMap();
	public static List<Unit>	units				= new ArrayList<Unit>();
	public static Unit			last_selected_unit	= null;
	public static Runner		runner				= new Runner();
	
	public static void clear()
	{
		for (int i = 0; i < Map.units.size(); i++)
			units.get(i--).remove();
		Map.cognitive_map.reset();
		Map.units.clear();
		Map.last_selected_unit = null;
	}

	public static void save()
	{
		for (Unit unit : Map.units)
			if (unit.concept.getConceptActivator() == null)
				unit.concept.setConceptActivator(new SigmoidActivator());
		FileChooser file_chooser = new FileChooser();
		file_chooser.setTitle("Save Cognitive Map");
		file_chooser.getExtensionFilters().add(new ExtensionFilter("XML", "*.xml"));
		File map_file = file_chooser.showSaveDialog(Program.window);
		if (map_file == null) return;
		try
		{
			FcmIO.saveAsXml(Map.cognitive_map, map_file.getPath());
		}
		catch (Exception e) { e.printStackTrace(); Log.addLog("Error: couldn't save file"); Log.consoleOut(); return; }
	}

	public static void load()
	{
		FileChooser file_chooser = new FileChooser();
		file_chooser.setTitle("Open Cognitive Map");
		file_chooser.getExtensionFilters().add(new ExtensionFilter("XML", "*.xml"));
		File map_file = file_chooser.showOpenDialog(Program.window);
		if (map_file == null) return;
		Map.clear();
		try
		{
			Map.cognitive_map = FcmIO.loadXml(map_file.getPath()).get(0);
		}
		catch (Exception e) { e.printStackTrace(); Log.addLog("Error: couldn't load file"); Log.consoleOut(); return; }

		java.util.Map<String, Concept> concepts_map = Map.cognitive_map.getConcepts();
		java.util.Map<String, FcmConnection> connnections_map = Map.cognitive_map.getConnections();

		int counter = 0;
		for (java.util.Map.Entry<String, Concept> concept : concepts_map.entrySet())
			Map.units.add(new Unit(concept.getValue().getName(), concept.getValue().getInput(), (Program.WIDTH / 2 - concepts_map.size() * 32 + counter++ * 100), Program.HEIGHT / 2 - concepts_map.size() * 32 / 2, Unit.concept_path));
	
		Parameters.A_estimated = new double[2][counter];
		for (int i = 0; i < counter; i++)
		{
			Parameters.A_estimated[0][i] = Parameters.A_not_estimated;
			Parameters.A_estimated[1][i] = Parameters.A_not_estimated;
		}
			
		for (Entry<String, FcmConnection> connections : connnections_map.entrySet())
			for (Unit start_unit : Map.units)
				if ((connections.getValue().getFrom().getName().equals(start_unit.concept.getName())))
					for (Unit end_unit : Map.units)
						if ((connections.getValue().getTo().getName().equals(end_unit.concept.getName())))
							start_unit.relations.add(new Relation(((WeightedConnection) connections.getValue()).getWeight(), start_unit, end_unit));
	}

	public static void setActivators(ConceptActivator activator)
	{
		for (Unit unit : Map.units)
			unit.concept.setConceptActivator(activator);
	}
}
