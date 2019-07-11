package io.github.cobblecracker.Carto;

import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MapRegistry {

    private String pluginFolder;

    MapRegistry(String pluginFolder) {
        this.pluginFolder = pluginFolder;
    }

    public boolean register(MapRecord newRecord) {

        List<MapRecord> records = readFile();
        for(MapRecord record: records){
            if(record.getMapId() == newRecord.getMapId()) {
                return false;
            }
        }

        records.add(newRecord);
        writeFile(records);
        return true;
    }

    public boolean unregister() {
        return false;
    }

    private List<MapRecord> readFile() {
        Gson gson = new Gson();
        File file = new File(this.pluginFolder, "map_registry.json");
        String encoded = null;
        try {
            encoded = FileUtils.readFileToString(file);
        } catch (FileNotFoundException e) {
            return new ArrayList<MapRecord>();

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<MapRecord>();
        }
        Type listType = new TypeToken<List<MapRecord>>(){}.getType();
        return gson.fromJson(encoded, listType);
    }

    private void writeFile(List<MapRecord> records) {
        Gson gson = new Gson();
        File file = new File(this.pluginFolder, "map_registry.json");
        String encoded = gson.toJson(records);
        try {
            FileUtils.writeStringToFile(file, encoded);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
