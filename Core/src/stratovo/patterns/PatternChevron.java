package stratovo.patterns;

import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.color.LXColor;
import heronarts.lx.model.LXPoint;
import heronarts.lx.modulator.DampedParameter;
import heronarts.lx.modulator.LXModulator;
import heronarts.lx.parameter.CompoundParameter;

@LXCategory(LXCategory.FORM)
public class PatternChevron extends SpinningPattern {
	private static final double HALF_PI = Math.PI/2;
	private static final double PI = Math.PI;
	private static final double QUARTER_PI = Math.PI/4;

	public final CompoundParameter slope =
			new CompoundParameter("Slope", 0, -HALF_PI, HALF_PI)
			.setDescription("Slope of the chevron shape");

	public final CompoundParameter sharp =
			new CompoundParameter("Sharp", 200, 100, 800)
			.setDescription("Sharpness of the lines");

	private final LXModulator slopeDamped = startModulator(new DampedParameter(this.slope, PI, TWO_PI, PI));
	private final LXModulator sharpDamped = startModulator(new DampedParameter(this.sharp, 300, 400, 200));

	public PatternChevron(LX lx) {
		super(lx);
		addParameter("slope", this.slope);
		addParameter("sharp", this.sharp);
	}

	public void run(double deltaMs) {
		float azimuth = this.azimuth.getValuef();
		float slope = this.slopeDamped.getValuef();
		float sharp = this.sharpDamped.getValuef();
		for (LXPoint point : model.points) {
			float az = (float) ((TWO_PI + point.azimuth + azimuth + Math.abs(point.yn - .5) * slope) % QUARTER_PI);
			setColor(point.index, LXColor.gray(Math.max(0, 100 - sharp * Math.abs(az - PI/8.))));
		}
	}
}