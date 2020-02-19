package stratovo.model;
import java.io.*;
import heronarts.lx.model.*;
import heronarts.lx.transform.LXTransform;
import java.util.List;
import java.util.ArrayList;

public class CSVModel extends LXModel {
    public final static float defaultWidth = 10.0f;
    public final static float defaultHeight = 20.0f;

    public static String basePath = "./";

    public CSVModel(String filename) {
        super(generatePoints(filename, new CSVModel.Orientation(defaultWidth, defaultHeight)));
    }

    public CSVModel(String filename, float xScale, float yScale) {
        super(generatePoints(filename, new CSVModel.Orientation(xScale, yScale)));
    }

    public CSVModel(String filename, float xScale, float yScale, LXTransform t) {
        super(generatePoints(filename, new CSVModel.Orientation(xScale, yScale, t)));
    }

    public CSVModel addKey(String key) {
        String[] oldKeys = this.getKeys();
        String[] newKeys = new String[oldKeys.length + 1];

        for (int i = 0; i < oldKeys.length; i++) {
            newKeys[i] = oldKeys[i];
        }

        newKeys[newKeys.length - 1] = key;

        this.setKeys(newKeys);

        return this;
    }

    protected static List<LXPoint> generatePoints(String filename, Orientation o) {
        List<LXPoint> points = new ArrayList<LXPoint>();
        String line;
        LXTransform t = o.getTransform();

        //Extract the points from the CSV
        try (BufferedReader br = new BufferedReader(new FileReader(basePath + filename))) {
            while ((line = br.readLine()) != null) {
                try {
                    String[] attributes = line.split(",");
                    float xVal = Float.parseFloat(attributes[1]);
                    float yVal = Float.parseFloat(attributes[2]);

                    t.push();
                    t.translate(xVal, yVal);
                    LXPoint point = new LXPoint(t);
                    points.add(point);
                    t.pop();
                }
                catch (NumberFormatException e) {
                    //Ignore any line in the CSV that isn't formatted correctly=
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return points;
    }

    public static class Orientation {
        public float width;
        public float height;

        public float xPos;
        public float yPos;
        public float zPos;

        public float xRotate;
        public float yRotate;
        public float zRotate;

        private LXTransform transform;


        public Orientation(float width, float height) {
            this(width, height, 0, 0, 0, 0, 0, 0);
        }

        public Orientation(float width, float height, float x, float y, float z, float xRotate, float yRotate, float zRotate) {
            this.width = width;
            this.height = height;
            this.xPos = x;
            this.yPos = y;
            this.zPos = z;
            this.xRotate = xRotate;
            this.yRotate = yRotate;
            this.zRotate = zRotate;
        }

        public Orientation(float xScale, float yScale, LXTransform t) {
            this.width = xScale;
            this.height = yScale;

            this.transform = t;
        }

        public LXTransform getTransform() {
            if (this.transform != null) {
                this.transform.scale(this.width, this.height, 1);

                return this.transform;
            }

            LXTransform t = new LXTransform();
            t.translate(this.xPos, this.yPos, this.zPos);
            t.rotateX(this.xRotate);
            t.rotateY(this.yRotate);
            t.rotateZ(this.zRotate);
            t.scale(this.width, this.height, 1);

            return t;
        }
    }
}

