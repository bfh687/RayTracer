package utility;

public class Color {
	private double r, g, b;
	
	public Color() {
		r = g = b = 0.0F;
	}
	
	public Color(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	// used for '+=' operations
	public void add(Color otherColor) {
		r = Math.min(r + otherColor.getR(), 1.0);
		g = Math.min(g + otherColor.getG(), 1.0);
		b = Math.min(b + otherColor.getB(), 1.0);
	}
	
	public Color multiply(double scalar) {
		if (scalar < 0) {
			throw new IllegalArgumentException("Cannot multiply a Color by a negative scalar");
		}
		
		double r = Math.min(this.r * scalar, 1.0);
		double g = Math.min(this.g * scalar, 1.0);
		double b = Math.min(this.b * scalar, 1.0);
		return new Color((float) r, (float) g, (float) b);
	}
	
	// used for specular shading exponent
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
	
	public double getR() {
		return r;
	}
	
	public double getG() {
		return g;
	}
	
	public double getB() {
		return b;
	}
	
	public int toInteger() {
		return (int) (r*255)<<16|(int)(g*255)<<8|(int)(b*255);
	}
	
	@Override 
	public String toString() {
		return "(" + r + "F, " + g + "F, " + b + "F)";
	}
}
