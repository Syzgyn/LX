package stratovo.model;

import java.util.ArrayList;

import heronarts.lx.transform.LXTransform;
import stratovo.model.CSVModel.Layout;

public class Scale extends CSVModel {
    protected static String layoutFile = "data/scale_definitions.json";
    public static ArrayList<ArrayList<Layout>> layouts;

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
                layouts = generateLayouts(basePath, layoutFile, "scales");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Layout l = layouts.get(Size.valueOf(size).ordinal()).get(index);
        return new Scale(l.filename, l.xScale, l.yScale, t);
    }
}
