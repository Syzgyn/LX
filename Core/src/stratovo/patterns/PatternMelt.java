package stratovo.patterns;

import heronarts.lx.LX;
import heronarts.lx.LXUtils;
import heronarts.lx.model.LXPoint;
import heronarts.lx.modulator.DampedParameter;
import heronarts.lx.modulator.LXModulator;
import heronarts.lx.modulator.SawLFO;
import heronarts.lx.modulator.SinLFO;
import heronarts.lx.modulator.TriangleLFO;
import heronarts.lx.parameter.BooleanParameter;
import heronarts.lx.parameter.CompoundParameter;

public abstract class PatternMelt extends BufferPattern {

	protected static final float TWO_PI = (float) (Math.PI * 2);

	private final float[] multipliers = new float[32];

	public final CompoundParameter level = new CompoundParameter("Level", 0)
			.setDescription("Level of the melting effect");

	public final BooleanParameter auto = new BooleanParameter("Auto", true)
			.setDescription("Automatically make content");

	public final CompoundParameter melt = new CompoundParameter("Melt", .5).setDescription("Amount of melt distortion");

	private final LXModulator meltDamped = startModulator(new DampedParameter(this.melt, 2, 2, 1.5));
	private LXModulator rot = startModulator(new SawLFO(0, 1, 39000));
	private LXModulator autoLevel = startModulator(
			new TriangleLFO(-.5, 1, startModulator(new SinLFO(3000, 7000, 19000))));

	public PatternMelt(LX lx) {
		super(lx);
		addParameter("level", this.level);
		addParameter("auto", this.auto);
		addParameter("melt", this.melt);
		for (int i = 0; i < this.multipliers.length; ++i) {
			float r = (float) LXUtils.random(.6, 1);
			this.multipliers[i] = r * r * r;
		}
	}

	public void onRun(double deltaMs) {
		float speed = this.speed.getValuef();
		float rot = this.rot.getValuef();
		float melt = this.meltDamped.getValuef();
		for (LXPoint point : model.points) {
			float az = point.azimuth;
			float maz = (az / TWO_PI + rot) * this.multipliers.length;
			float lerp = maz % 1;
			int floor = (int) (maz - lerp);
			float m = (float) LXUtils.lerp(1, LXUtils.lerp(this.multipliers[floor % this.multipliers.length],
					this.multipliers[(floor + 1) % this.multipliers.length], lerp), melt);
			float d = getDist(point);
			int offset = Math.round(d * speed * m);
			setColor(point.index, this.history[(this.cursor + offset) % this.history.length]);
		}
	}

	protected abstract float getDist(LXPoint point);

	public float getLevel() {
		if (this.auto.isOn()) {
			float autoLevel = this.autoLevel.getValuef();
			if (autoLevel > 0) {
				return (float) Math.pow(autoLevel, .5);
			}
			return 0;
		}
		return this.level.getValuef();
	}
}