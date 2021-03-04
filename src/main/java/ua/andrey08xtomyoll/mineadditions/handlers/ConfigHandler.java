package ua.andrey08xtomyoll.mineadditions.handlers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ua.andrey08xtomyoll.mineadditions.util.Reference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ConfigHandler {
    double damage;
    double digSpeed;
    double labatiumHelmetResistance, labatiumChestplateResistance, labatiumLeggingsResistance, labatiumBootsResistance;
    double mazuriumHelmetResistance, mazuriumChestplateResistance, mazuriumLeggingsResistance, mazuriumBootsResistance;
    boolean canFly, needFillerOres, doAutoSmelt;
    int labatiumMinHeight, labatiumMaxHeight, labatiumPerChunkSpawn;
    int mazuriumMinHeight, mazuriumMaxHeight, mazuriumPerChunkSpawn;

    public void initConfig() {
        JSONObject config = (JSONObject) readConfig();
        //TODO: Assign key to every variable
    }

    private Object readConfig() {
        try {
            File config = new File(Reference.CONFIG_FOLDER + "/" + Reference.NAME + "config.json");
            if (config.exists()) {
                JSONParser configObject = new JSONParser();
                BufferedReader reader = new BufferedReader(new FileReader(config));
                String configData = reader.readLine();
                reader.close();
                configObject.parse(configData);
                return configObject;
            }
        }
        catch (Exception e) { }
        return new JSONObject();
    }
}
