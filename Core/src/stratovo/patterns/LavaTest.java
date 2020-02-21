package stratovo.patterns;

import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.LXPattern;
import heronarts.lx.color.LXColor;
import heronarts.lx.model.LXModel;
import heronarts.lx.model.LXPoint;
import stratovo.model.CSVModel;
import stratovo.model.Scale;

@LXCategory("Lava Patterns")
public class LavaTest extends LXPattern {

	public LavaTest(LX lx) {
		super(lx);
	}
	
	public void run (double deltaMs)
    {
        float Hue = palette.getHuef();
        float Sat = palette.getSaturationf();
        
        for (LXModel m : model.sub("lava"))
        {
        	if (((CSVModel)m).hasKey("lava")) {
	        	for (LXPoint p : m.points ) {
	        		colors[p.index] = LXColor.hsb(Hue + 100, Sat, 100); 
	        	}
        	} else {
        		for (LXPoint p : m.points ) {
	        		colors[p.index] = LXColor.hsb(Hue + 100, Sat, 100); 
	        	}
        	}
        }
    }
}
