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
public class PatternLighthouse extends SpinningPattern {
	private static final double HALF_PI = Math.PI / 2;

	public final CompoundParameter size = new CompoundParameter("Size", HALF_PI, Math.PI / 8,
			TWO_PI).setDescription("Size of lighthouse arc");

	public final CompoundParameter slope = new CompoundParameter("Slope", 0, -1, 1)
			.setDescription("Slope of gradient");

	private final LXModulator sizeDamped = startModulator(
			new DampedParameter(this.size, 3 * Math.PI, 4 * Math.PI, TWO_PI));
	private final LXModulator slopeDamped = startModulator(new DampedParameter(this.slope, 2, 4, 2));

	public PatternLighthouse(LX lx) {
		super(lx);
		addParameter("size", this.size);
		addParameter("slope", this.slope);
	}

	public void run(double deltaMs) {
		float azimuth = this.azimuth.getValuef();
		float falloff = 100 / this.sizeDamped.getValuef();
		float slope = (float) (Math.PI * this.slopeDamped.getValuef());
		for (LXPoint point : model.points) {
			float az = (float) ((TWO_PI + point.azimuth + Math.abs(point.yn - .5) * slope) % TWO_PI);
			float b = Math.max(0, 100 - falloff * LXUtils.wrapdistf(az, azimuth, TWO_PI));
			setColor(point.index, LXColor.gray(b));
		}
	}
}