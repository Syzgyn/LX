package stratovo.patterns;

import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.model.LXPoint;

@LXCategory(LXCategory.FORM)
public class PatternMeltDown extends PatternMelt {
  public PatternMeltDown(LX lx) {
    super(lx);
  }
  
  protected float getDist(LXPoint point) {
    return 1 - point.yn;
  }
}