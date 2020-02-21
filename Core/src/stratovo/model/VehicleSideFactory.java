package stratovo.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import heronarts.lx.transform.LXTransform;

public class VehicleSideFactory {
    private static JsonObject jsonConfig;

    public static String basePath = "./";
    private static String jsonFilename = "data/vehicle_sides.json";

    public static VehicleSide getVehicleSide(String side) {
        if (jsonConfig == null) {
            parseJson();
            Scale.basePath = basePath;
        }

        JsonObject sideConfig = jsonConfig.getAsJsonObject("sides").getAsJsonObject(side);
        
        LXTransform t = createTransform(sideConfig.getAsJsonArray("transforms"));

        CSVModel[] scales = generateModels(sideConfig.getAsJsonArray("scales"), "Scale", t);
        CSVModel[] lava = generateModels(sideConfig.getAsJsonArray("lava"), "Lava", t);
        
        VehicleSide vehicleSide = new VehicleSide(scales, lava);
        vehicleSide.setKeys(new String[] {"vehicleSide", side});

        return vehicleSide;
    }

    private static void parseJson() {
        JsonParser p = new JsonParser();
        String json;
        try {
            json = new String(Files.readAllBytes(Paths.get(basePath, jsonFilename)));
        } catch (IOException e) {
            e.printStackTrace();
            json = "";
        }
        jsonConfig = p.parse(json).getAsJsonObject();
    }
    
	private static CSVModel[] generateModels(JsonArray jsonConfig, String className, LXTransform t) {
        ArrayList<CSVModel> models = new ArrayList<CSVModel>();

        for(int i = 0; i < jsonConfig.size(); i++) {
            JsonObject config = jsonConfig.get(i).getAsJsonObject();
            String size = config.get("size").getAsString();
            int index = config.get("index").getAsInt();
            JsonArray transforms = config.get("transforms").getAsJsonArray();
            
            t.push();
            applyTransform(transforms, t);
            
            switch (className) {
            case "Scale":
            	models.add(createScale(size, index, t));
            	break;
            case "Lava":
            	models.add(createLava(size, index, t));
            	
            }
            t.pop();
        }

        CSVModel[] array = new CSVModel[models.size()];
        return models.toArray(array);
    }
    
    private static LXTransform createTransform(JsonArray array) {
    	LXTransform t = new LXTransform();
    	applyTransform(array, t);
    	
    	return t;
    }
    
    private static void applyTransform(JsonArray array, LXTransform t) {
        for (int i = 0; i < array.size(); i++) {
            JsonObject j = array.get(i).getAsJsonObject();
            String command = j.get("command").getAsString();
            JsonElement params = j.get("parameter");
            JsonArray arr;
            if (params.isJsonArray()) {
                arr = params.getAsJsonArray();
            } else {
                arr = null;
            }

            switch (command) {
            case "translate":
                if (arr.getAsJsonArray().size() == 3) {
                    t.translate(arr.get(0).getAsFloat(), arr.get(1).getAsFloat(), arr.get(2).getAsFloat());
                } else {
                    t.translate(arr.get(0).getAsFloat(), arr.get(1).getAsFloat());
                }
                break;
            case "rotateX":
                t.rotateX(params.getAsFloat());
                break;
            case "rotateY":
                t.rotateY(params.getAsFloat());
                break;
            case "rotateZ":
                t.rotateZ(params.getAsFloat());
                break;
            case "scale":
                if (params.isJsonArray()) {
                    t.scale(arr.get(0).getAsFloat(), arr.get(1).getAsFloat(), arr.get(2).getAsFloat());
                } else {
                    t.scale(params.getAsFloat());
                }
                break;
            case "scaleX":
                t.scaleX(params.getAsFloat());
                break;
            case "scaleY":
                t.scaleX(params.getAsFloat());
                break;
            case "scaleZ":
                t.scaleX(params.getAsFloat());
                break;
            }
        }
    }

	public static Scale createScale(String size, int index, LXTransform t) {
        Scale scale = Scale.createScale(size, index, t);
        scale.addKey("scale");

        return scale;
    }
    
	private static Scale createLava(String size, int index, LXTransform t) {
        Scale scale = Scale.createScale(size, index, t);
        scale.addKey("lava");

        return scale;
    }
}
