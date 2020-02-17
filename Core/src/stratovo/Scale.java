package stratovo;
import java.io.*;
import heronarts.lx.model.*;
import java.util.List;
import java.util.ArrayList;

public class Scale extends LXModel {

  public Scale(List<LXPoint> points) {
    super(points);
  }

  public Scale() {
    super(makePoints());
  }

  protected static List<LXPoint> makePoints() {
  List<LXPoint> points = new ArrayList<LXPoint>();
    try {
      String file = "/Users/pmurphy/Code/lights/Stratovo/data/layout.csv";
      String line;
      try (BufferedReader br =
        new BufferedReader(new FileReader(file))) {
        br.readLine();
        while ((line = br.readLine()) != null) {
          String[] attributes = line.split(",");
          LXPoint point = new LXPoint(
            Float.parseFloat(attributes[1]) * -10,
            Float.parseFloat(attributes[2]) * 10,
            0
          );
          points.add(point);
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    catch (NullPointerException e) {
      e.printStackTrace();
    }

    return points;
  }
}

