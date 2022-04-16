package utility;

/**
 * Material class representing different characteristics of an object.
 * @author bfh687
 */
public class Material {
	
	/**
	 * The base color of the material.
	 */
	private Color color1;
	
	/**
	 * The second color of the material (checkerboard).
	 */
	private Color color2;
	
	/**
	 * The ambient value of the material.
	 */
	private double ambient;
	
	/**
	 * The diffuse value of the material.
	 */
	private double diffuse;
	
	/**
	 * The specular value of the material.
	 */
	private double specular;
	
	/**
	 * The reflection value of the material.
	 */
	private double reflection;
	
	/**
	 * Creates a new material with default color and attribute values.
	 */
	public Material() {
		new Material(new Color(1, 1, 1));
	}
	
	/**
	 * Creates a new material with the given color and default attribute values.
	 * @param color The color of the material.
	 */
	public Material(Color color) {
		this.color1 = color;
		ambient = .05;
		diffuse = 1.0;
		specular = 1;
		reflection = .2;
	}
	
	/**
	 * Creates a new material with the given color and attribute values.
	 * @param color The color of the material.
	 * @param ambient The ambient value of the material.
	 * @param diffuse The diffuse value of the material.
	 * @param specular The specular value of the material.
	 * @param reflection The reflection value of the material.
	 */
	public Material(Color color, double ambient, double diffuse, double specular, double reflection) {
		this.color1 = color;
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
		this.reflection = reflection;
	}
	
	/**
	 * Creates a new checkerboard material with the given colors and attribute values.
	 * @param color1 The first checkerboard color.
	 * @param color2 The second checkerboard color.
	 * @param ambient The ambient value of the material.
	 * @param diffuse The diffuse value of the material.
	 * @param specular The specular value of the material.
	 * @param reflection The reflection value of the material.
	 */
	public Material(Color color1, Color color2, double ambient, double diffuse, double specular, double reflection) {
		this.color1 = color1;
		this.color2 = color2;
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
		this.reflection = reflection;
	}
	
	/**
	 * Calculates the color at the given position.
	 * @param hitPosition The position to calculate the color of.
	 * @return The color at the hit position.
	 */
	public Color colorAt(Vector3D hitPosition) {
		if (color2 == null) {
			return color1;
		} else {
			return colorAtCheck(hitPosition);
		}
	}
	
	/**
	 * Calculates the checkerboard color at the given position.
	 * @param hitPosition The position to calculate the color of.
	 * @return The color at the hit position.
	 */
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
	
	/**
	 * Checks whether the material is checkered.
	 * @return Whether the material is checkered.
	 */
	public boolean isCheckered() {
		return color2 != null;
	}
	
	/**
	 * Gets the base color of the material.
	 * @return The base color of the material.
	 */
	public Color getColor() {
		return color1;
	}
	
	/**
	 * Gets the ambient value of the material.
	 * @return The ambient value of the material.
	 */
	public double getAmbient() {
		return ambient;
	}
	
	/**
	 * Gets the diffuse value of the material.
	 * @return The diffuse value of the material.
	 */
	public double getDiffuse() {
		return diffuse;
	}
	
	/**
	 * Gets the specular value of the material.
	 * @return The specular value of the material.
	 */
	public double getSpecular() {
		return specular;
	}
	
	/**
	 * Gets the reflection value of the material.
	 * @return The reflection value of the material.
	 */
	public double getReflection() {
		return reflection;
	}
}
