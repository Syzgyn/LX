package stratovo.patterns;

import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.model.LXPoint;

@LXCategory(LXCategory.FORM)
public class PatternMeltUp extends PatternMelt {
  public PatternMeltUp(LX lx) {
    super(lx);
  }
  
  protected float getDist(LXPoint point) {
    return point.yn;
  }
}