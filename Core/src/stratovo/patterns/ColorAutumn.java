package stratovo.patterns;

import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.LXPattern;
import heronarts.lx.color.LXColor;
import heronarts.lx.model.LXPoint;
import heronarts.lx.parameter.CompoundParameter;

@LXCategory(LXCategory.COLOR)
public class ColorAutumn extends LXPattern {

	public final CompoundParameter speed = (CompoundParameter)
			new CompoundParameter("Speed", 1, 5);

	public final CompoundParameter range = new CompoundParameter("Range", 30, 15, 45); 

	public ColorAutumn(LX lx) {
		super(lx);
		addParameter("Range", this.range);
		addParameter("Speed", this.speed);
	}

	private float accum = 0;

	public void run(double deltaMs) {

		accum += this.speed.getValue() * .02 * deltaMs;
		float sat = palette.getSaturationf();
		int li = (int)accum;
		int range = (int) this.range.getValuef();
		for (LXPoint point : model.points) {
			setColor(point.index, LXColor.hsb(li % range, sat, 100));
			++li;
		}
	}

}
