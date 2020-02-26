package stratovo.model;

import java.util.ArrayList;

import heronarts.lx.transform.LXTransform;

public class Lava extends CSVModel {
    public static ArrayList<ArrayList<Layout>> layouts;

    public Lava(String filename) {
        this(filename, 1, 1, new LXTransform());
    }

    public Lava(String filename, float xScale, float yScale) {
        super(filename, xScale, yScale);
    }

    public Lava(String filename, float xScale, float yScale, LXTransform t) {
        super(filename, xScale, yScale, t);
    }
    
    public static Lava createLava(String size, int index) {
        return createLava(size, index, 0, 0, 0);
    }
    public static Lava createLava(String size, int index, float xPos, float yPos, float zPos) {
        return createLava(size, index, new LXTransform().translate(xPos, yPos, zPos));
    }

    public static Lava createLava(String size, int index, LXTransform t) {
        if (layouts == null) {
            try {
                layouts = generateLayouts(basePath, layoutFile, "lava");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Layout l = layouts.get(Size.valueOf(size).ordinal()).get(index);
        return new Lava(l.filename, l.xScale, l.yScale, t);
    }
}
