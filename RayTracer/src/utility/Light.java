package utility;

// represents a light
public class Light {
	private Point3D position;
	private Color color;
	
	public Light(Point3D position) {
		this.position = position;
		color = new Color(1, 1, 1);
	}
	
	public Light(Point3D position, Color color) {
		this.position = position;
		this.color = color;
	}
	
	public Point3D getPosition() {
		return position;
	}
	
	public Color getColor() {
		return color;
	}
}
