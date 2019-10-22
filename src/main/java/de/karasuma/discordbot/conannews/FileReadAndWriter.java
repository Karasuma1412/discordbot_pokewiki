package de.karasuma.discordbot.conannews;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import net.dv8tion.jda.api.entities.Activity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class FileReadAndWriter {
	
	final String filePath = "./info.json";
	boolean readableFile = false;
	Main main;

	public FileReadAndWriter(Main main) {
		this.main = main;
	}
	
	protected JSONObject createJSONObject(String filePathGameStatus) {
		File file = new File(filePathGameStatus);
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		
		try {
			FileReader fileReader = new FileReader(file);
			obj = (JSONObject) parser.parse(fileReader);
			readableFile = true;
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			readableFile = false;
		}
		
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	protected void createJSONFile (Activity.ActivityType gameType, String gameName) {
		JSONObject obj = new JSONObject();
		obj.put("name", gameName);

		ArrayList<Activity.ActivityType> activityTypes = new ArrayList<>(Arrays.asList(Activity.ActivityType.values()));
		
		long index = activityTypes.indexOf(gameType);
		
		obj.put("typeIndex", index);
		
		try {
			FileWriter file = new FileWriter(filePath);
			file.write(obj.toString());
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void initGameStatusFromFile() {
		File file = new File(filePath);
		if (file.isFile()) {
			JSONObject jsonObject = createJSONObject(filePath);
			handleJSONObject(jsonObject);			
		}
	}
	
	private void handleJSONObject(JSONObject jsonObject) {
		try {
			if (jsonObject.containsKey("name") && jsonObject.containsKey("typeIndex")) {
				String gameName = (String) jsonObject.get("name");

				ArrayList<Activity.ActivityType> activityTypes = new ArrayList<>(Arrays.asList(Activity.ActivityType.values()));
				
				Long activityTypeIndex = (Long) jsonObject.get("typeIndex");
				int index = Integer.parseInt(String.valueOf(activityTypeIndex));
				Activity.ActivityType activityType = null;
				
				if (activityTypeIndex != null) {
					if (activityTypeIndex >= 0 && activityTypeIndex <= 3) {
						activityType = activityTypes.get(index);
					} else {
						activityType = activityTypes.get(0);
					}
					
				}
							
				main.getBots().get("welcome").setActivityName(gameName);
				main.getBots().get("welcome").setActivityType(activityType);
				
				readableFile = true;
				
			} else {
				readableFile = false;
			}
		} catch (NullPointerException e) {
			readableFile = false;
		}
	}

	public boolean isReadableFile() {
		return readableFile;
	}

}
