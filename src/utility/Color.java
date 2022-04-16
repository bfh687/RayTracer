package utility;

/**
 * Color class represented with RGB values.
 * @author bfh687
 */
public class Color {
	
	/**
	 * The red-value of the color.
	 */
	private double r;
	
	/**
	 * The green-value of the color.
	 */
	private double g;
	
	/**
	 * The blue-value of the color.
	 */
	private double b;
	
	/**
	 * Creates a new color, defaulting to black.
	 */
	public Color() {
		r = g = b = 0.0F;
	}
	
	/**
	 * Creates a new color with the given RGB values.
	 * @param r The red-value of the color.
	 * @param g The green-value of the color.
	 * @param b The blue-value of the color.
	 */
	public Color(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	/**
	 * Adds the current color with the given color.
	 * @param otherColor The color to be added.
	 */
	public void add(Color otherColor) {
		r = Math.min(r + otherColor.getR(), 1.0);
		g = Math.min(g + otherColor.getG(), 1.0);
		b = Math.min(b + otherColor.getB(), 1.0);
	}
	
	/**
	 * Multiplies the color by the given scalar.
	 * @param scalar Scalar value to multiply by.
	 * @return The resulting color of the scalar multiplation operation.
	 */
	public Color multiply(double scalar) {
		if (scalar < 0) {
			throw new IllegalArgumentException("Cannot multiply a Color by a negative scalar");
		}
		double r = Math.min(this.r * scalar, 1.0);
		double g = Math.min(this.g * scalar, 1.0);
		double b = Math.min(this.b * scalar, 1.0);
		return new Color((float) r, (float) g, (float) b);
	}
	
	/**
	 * Raises the values of each RGB values to the given exponent.
	 * @param exponent The exponent value.
	 * @return The resulting color of the exponential operation.
	 */
	public Color exp(double exponent) {
		double r = this.r;
		double g = this.g;
		double b = this.b;
		for (int i = 0; i < exponent; i++) {
			r *= r;
			g *= g;
			b *= b;
		}
		return new Color((float) r, (float) g, (float) b);
	}
	
	/**
	 * Gets the red-value of the color.
	 * @return The red-value of the color.
	 */
	public double getR() {
		return r;
	}
	
	/**
	 * Gets the green-value of the color.
	 * @return The green-value of the color.
	 */
	public double getG() {
		return g;
	}
	
	/**
	 * Gets the blue-value of the color.
	 * @return The blue-value of the color.
	 */
	public double getB() {
		return b;
	}
	
	/**
	 * Returns the 16bit integer-value representation of the color.
	 * @return The 16bit integer-value representation of the color.
	 */
	public int toInteger() {
		return (int) (r*255)<<16|(int)(g*255)<<8|(int)(b*255);
	}
}
