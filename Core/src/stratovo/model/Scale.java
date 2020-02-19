package stratovo.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.json.*;

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

    public static void generateLayouts() throws JSONException, IOException {
        if (layouts != null) {
            return;
        }
        layouts = new ArrayList<ArrayList<ScaleLayout>>();
        for (int i = 0; i <= Size.values().length; i++) {
            layouts.add(new ArrayList<ScaleLayout>());
        }

        JSONObject json = new JSONObject(new String(Files.readAllBytes(Paths.get(basePath, layoutFile))));
        JSONArray arr = json.getJSONArray("scales");

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            Size size = Size.valueOf(obj.getString("size"));
            layouts.get(size.ordinal()).add(new ScaleLayout(obj));
        }
    }

    private static class ScaleLayout {
        public String filename;
        public float xScale;
        public float yScale;

        public ScaleLayout(JSONObject obj) {
            this.filename = obj.getString("filename");
            this.xScale = obj.getFloat("xScale");
            this.yScale = obj.getFloat("yScale");
        }
    }

}
