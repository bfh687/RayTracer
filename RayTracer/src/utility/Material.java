package utility;

// class that lets us know how the object will react to light
public class Material {
	private Color color1; // weird
	private Color color2;
	private double ambient;
	private double diffuse;
	private double specular;
	private double reflection;
	
	public Material() {
		new Material(new Color(1, 1, 1));
	}
	
	public Material(Color color) {
		this.color1 = color;
		ambient = .05;
		diffuse = 1.0;
		specular = 1;
		reflection = .2;
	}
	
	public Material(Color color, double ambient, double diffuse, double specular, double reflection) {
		this.color1 = color;
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
		this.reflection = reflection;
	}
	
	public Material(Color color1, Color color2, double ambient, double diffuse, double specular, double reflection) {
		this.color1 = color1;
		this.color2 = color2;
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
		this.reflection = reflection;
	}
	
	public Color colorAt(Vector3D hitPosition) {
		if (color2 == null) {
			return color1;
		} else {
			return colorAtCheck(hitPosition);
		}
	}
	
	// TODO: fix checkboard issue
	public Color colorAtCheck(Vector3D hitPosition) {
		double x = hitPosition.getX();
		double z = hitPosition.getZ();
		int cond1 = (int) (((x + -50) * 1) % 2);
		int cond2 = (int) (((z + -50) * 1) % 2);
		
		if (cond1 == cond2) {
			return color1;
		} else {
			return color2;
		}
	}
	
	public boolean isCheckered() {
		return color2 != null;
	}
	
	public Color getColor() {
		return color1;
	}
	
	public double getAmbient() {
		return ambient;
	}
	
	public double getDiffuse() {
		return diffuse;
	}
	
	public double getSpecular() {
		return specular;
	}
	
	public double getReflection() {
		return reflection;
	}
}
