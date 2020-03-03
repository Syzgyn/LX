package stratovo.patterns;

import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.LXPattern;
import heronarts.lx.LXUtils;
import heronarts.lx.color.LXColor;
import heronarts.lx.model.LXPoint;
import heronarts.lx.parameter.CompoundParameter;

@LXCategory(LXCategory.FORM)
public class PatternWaves extends LXPattern {

	private float accum = 0;
	  
	private final LXUtils.LookupTable.Sin sinTable = new LXUtils.LookupTable.Sin(512);

	public final CompoundParameter speed = new CompoundParameter("Speed", 0.2, 0.1, 1.5);

	public final CompoundParameter slope = new CompoundParameter("Slope", 0, -2, 2);
	
	public final CompoundParameter size =
			new CompoundParameter("Size", 1, .1, 5)
			.setDescription("Size");

	public PatternWaves(LX lx) {
		super(lx);

		addParameter(size);
		addParameter(speed);
		addParameter(slope);
	}
	
	@Override
	protected void run(double deltaMs) {
		float _size = size.getValuef(); 
		float _slope = slope.getValuef();

		accum += this.speed.getValue() * .02 * deltaMs;
		
		for (LXPoint point : model.points) {
	      setColor(point.index, LXColor.gray(Math.max(0,sinTable.sin((point.y + point.index * _slope) / _size + accum) * 60 + 40)));
	    }
	}

}
