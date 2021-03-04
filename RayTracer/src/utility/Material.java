package utility;

// class that lets us know how the object will react to light
public class Material {
	private Color color;
	private double ambient;
	private double diffuse;
	private double specular;
	
	public Material() {
		new Material(new Color(1, 1, 1));
	}
	
	public Material(Color color) {
		this.color = color;
		ambient = .05;
		diffuse = 1.0;
		specular = 1.0;
	}
	
	public Material(Color color, double ambient, double diffuse, double specular) {
		this.color = color;
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
	}
	
	public Color colorAt(Vector3D hitPosition) {
		return color;
	}
	
	public Color getColor() {
		return color;
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
}
