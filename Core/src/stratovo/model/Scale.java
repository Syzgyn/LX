package stratovo.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import heronarts.lx.transform.LXTransform;

public class Scale extends CSVModel {

    private final static String layoutFile = "data/scale_definitions.json";

    public enum Size {
        LARGE,
        MEDIUM,
        SMALL
    };

    public static ArrayList<ArrayList<ScaleLayout>> layouts = null;

    public Scale(String filename) {
        this(filename, 1, 1, new LXTransform());
    }

    public Scale(String filename, float xScale, float yScale) {
        super(filename, xScale, yScale);
    }

    public Scale(String filename, float xScale, float yScale, LXTransform t) {
        super(filename, xScale, yScale, t);
    }

    public static Scale createScale(String size, int index) {
        return createScale(size, index, 0, 0, 0);
    }
    public static Scale createScale(String size, int index, float xPos, float yPos, float zPos) {
        return createScale(size, index, new LXTransform().translate(xPos, yPos, zPos));
    }

    public static Scale createScale(String size, int index, LXTransform t) {
        if (layouts == null) {
            try {
                generateLayouts();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ScaleLayout l = layouts.get(Size.valueOf(size).ordinal()).get(index);
        return new Scale(l.filename, l.xScale, l.yScale, t);
    }

    public static void generateLayouts() throws IOException {
        if (layouts != null) {
            return;
        }
        layouts = new ArrayList<ArrayList<ScaleLayout>>();
        for (int i = 0; i <= Size.values().length; i++) {
            layouts.add(new ArrayList<ScaleLayout>());
        }

        JsonParser p = new JsonParser();
        String json = new String(Files.readAllBytes(Paths.get(basePath, layoutFile)));
        JsonArray arr = p.parse(json).getAsJsonObject().get("scales").getAsJsonArray();

        for (int i = 0; i < arr.size(); i++) {
            JsonObject obj = arr.get(i).getAsJsonObject();
            Size size = Size.valueOf(obj.get("size").getAsString());
            layouts.get(size.ordinal()).add(new ScaleLayout(obj));
        }
    }

    private static class ScaleLayout {
        public String filename;
        public float xScale;
        public float yScale;

        public ScaleLayout(JsonObject obj) {
            this.filename = obj.get("filename").getAsString();
            this.xScale = obj.get("xScale").getAsFloat();
            this.yScale = obj.get("yScale").getAsFloat();
        }
    }

}
