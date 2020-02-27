package stratovo;

public class StratovoUtils {
	static public final float map(float value,
			float start1, float stop1,
			float start2, float stop2) {
		float outgoing =
				start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
		String badness = null;
		if (outgoing != outgoing) {
			badness = "NaN (not a number)";

		} else if (outgoing == Float.NEGATIVE_INFINITY ||
				outgoing == Float.POSITIVE_INFINITY) {
			badness = "infinity";
		}
		if (badness != null) {
			throw new ArithmeticException();
		}
		return outgoing;
	}
}
