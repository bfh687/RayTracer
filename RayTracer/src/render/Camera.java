package render;

import utility.Vector3D;

/**
 * The camera class used to render the view of the scene.
 * @author bfh687
 */
public class Camera {
	
	/**
	 * Up vector used for vector calculations.
	 */
	public static final Vector3D UP_VECTOR = new Vector3D(0, 1, 0);
	
	/**
	 * Vector representation of the camera's origin.
	 */
	private Vector3D lookFrom;
	
	/**
	 * Vector representation of where the camera is looking.
	 */
	private Vector3D lookTo;
	
	/**
	 * Creates a camera object with default values.
	 */
	public Camera() {
		lookFrom = (new Vector3D(0, -.5, -10));
		lookTo = new Vector3D(0, 0, 0);
	}
	
	/**
	 * Creates a camera object with the given lookFrom and lookTo vectors.
	 * @param lookFrom The camera's origin.
	 * @param lookTo Where the camera is looking.
	 */
	public Camera(Vector3D lookFrom, Vector3D lookTo) {
		this.lookFrom = lookFrom;
		this.lookTo = lookTo;
	}
	
	/**
	 * Gets the lookFrom vector.
	 * @return The lookFrom vector.
	 */
	public Vector3D getLookFrom() { 
		return lookFrom;
	}
	
	/**
	 * Gets the lookTo vector.
	 * @return The lookTo vector.
	 */
	public Vector3D getLookTo() {
		return lookTo;
	}
	
	/**
	 * Sets the lookFrom vector to the given value.
	 * @param lookFrom The vector to set lookFrom to.
	 */
	public void setLookFrom(Vector3D lookFrom) {
		this.lookFrom = lookFrom;
	}
	
	/**
	 * Sets the lookTo vector to the given value.
	 * @param lookTo The vector to set lookTo to.
	 */
	public void setLookTo(Vector3D lookTo) {
		this.lookTo = lookTo;
	}
}
