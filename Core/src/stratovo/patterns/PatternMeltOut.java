package stratovo.patterns;

import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.model.LXPoint;

@LXCategory(LXCategory.FORM)
public class PatternMeltOut extends PatternMelt {
  public PatternMeltOut(LX lx) {
    super(lx);
  }
  
  protected float getDist(LXPoint point) {
    return (float) (2*Math.abs(point.yn - 0.5));
  }
}