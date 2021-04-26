package utility;

/**
 * Light class.
 * @author bfh687
 */
public class Light {
	
	/**
	 * The position of the light.
	 */
	private Point3D position;
	
	/**
	 * The color of the light.
	 */
	private Color color;
	
	/**
	 * Creates a new light with the given position and default color.
	 * @param position The position of the light.
	 */
	public Light(Point3D position) {
		this.position = position;
		color = new Color(1, 1, 1);
	}
	
	/**
	 * Creates a new light with the given position and color
	 * @param position The position of the light.
	 * @param color The color of the light.
	 */
	public Light(Point3D position, Color color) {
		this.position = position;
		this.color = color;
	}
	
	/**
	 * Gets the position of the light.
	 * @return The position of the light.
	 */
	public Point3D getPosition() {
		return position;
	}
	
	/**
	 * Gets the color of the light.
	 * @return The color of the light.
	 */
	public Color getColor() {
		return color;
	}
}
