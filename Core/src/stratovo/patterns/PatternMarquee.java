package stratovo.patterns;

import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.LXPattern;
import heronarts.lx.LXUtils;
import heronarts.lx.color.LXColor;
import heronarts.lx.model.LXPoint;
import heronarts.lx.parameter.CompoundParameter;

@LXCategory(LXCategory.FORM)
public class PatternMarquee extends LXPattern {
	
	private float accum = 0;
	
	private final LXUtils.LookupTable.Sin sinTable = new LXUtils.LookupTable.Sin(512);
	
	public final CompoundParameter speed = (CompoundParameter)
			new CompoundParameter("Speed", 1, 5);

	public final CompoundParameter size = new CompoundParameter("Size", 30, 15, 45); 

	public PatternMarquee(LX lx) {
		super(lx);
		addParameter("Size", this.size);
		addParameter("Speed", this.speed);
	}

	@Override
	protected void run(double deltaMs) {
		float _size = size.getValuef(); 
		accum += this.speed.getValue() * .02 * deltaMs;
		
		for (LXPoint point : model.points) {
	      setColor(point.index, LXColor.gray(Math.max(0,sinTable.sin((float) (point.index * _size + accum))) * 50 + 50));
	      
	    }
	}

}
