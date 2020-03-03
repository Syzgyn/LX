package stratovo.patterns;

import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.LXUtils;
import heronarts.lx.color.LXColor;
import heronarts.lx.model.LXPoint;
import heronarts.lx.modulator.DampedParameter;
import heronarts.lx.modulator.LXModulator;
import heronarts.lx.parameter.CompoundParameter;


@LXCategory(LXCategory.FORM)
public class PatternChess extends SpinningPattern {

	public final CompoundParameter numSpots = new CompoundParameter("Spots", 4, 2, 8)
			.setDescription("Number of spots");

	private final LXModulator numSpotsDamped = startModulator(new DampedParameter(numSpots, 12, 20, 6));

	public PatternChess(LX lx) {
		super(lx);
		addParameter("numSpots", this.numSpots);
	}

	public void run(double deltaMs) {
		float azimuth = this.azimuth.getValuef();
		float numSpots = this.numSpotsDamped.getValuef();
		for (LXPoint point : model.points) {
			float az = point.azimuth + azimuth;
			if (az > TWO_PI) {
				az -= TWO_PI;
			}
			float d = LXUtils.wrapdistf(az, 0, TWO_PI);
			d = (float) (Math.abs(d - Math.PI) / Math.PI);
			int add = ((int) (numSpots * point.yn)) % 2;
			float basis = (float) ((numSpots * d + .5 * add) % 1);
			float d2 = (float) (2 * Math.abs(.5 - basis));
			setColor(point.index, LXColor.gray(100 * (1 - d2) * (1 - d2)));
		}
	}
}