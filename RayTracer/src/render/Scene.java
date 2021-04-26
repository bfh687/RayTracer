package render;

import java.util.List;

import shapes.Sphere;
import utility.Color;
import utility.Light;
import utility.Material;
import utility.Point3D;

/**
 * Represents a scene consisting of objects (spheres) and lights.
 * @author bfh687
 */
public class Scene {
	
	/**
	 * The scenes light.
	 */
	private Light light;
	
	/**
	 * The list of objects in the scene.
	 */
	private List<Sphere> objList;
	
	/**
	 * Creates a new scene with the given light and list of objects.
	 * @param light The scene's light.
	 * @param objList The list of objects in the scene.
	 */
	public Scene(Light light, List<Sphere> objList) {
		this.light = light;
		this.objList = objList;
		
		// test list of objects
		objList.add(new Sphere(new Point3D(-2.1, 0, 0), 1, new Material(new Color(1, 0, 0))));
		objList.add(new Sphere(new Point3D(0, 0, 0), 1, new Material(new Color(0, 1, 0))));
		objList.add(new Sphere(new Point3D(2.1, 0, 0), 1, new Material(new Color(0, 0, 1))));
		objList.add(new Sphere(new Point3D(0, 5, 10), 5, new Material(new Color(1, 1, 1), .05, 1, .5, .5)));
		objList.add(new Sphere(new Point3D(0, -223300001, 0), 223300000, (new Material(new Color(1, 1, 1), new Color(), .05, 1, 1, .4))));
	}
	
	/**
	 * Adds a object (sphere) to the scene.
	 * @param sphere The sphere to be added to the scene.
	 */
	public void addObj(Sphere sphere) {
		objList.add(sphere);
	}
	
	/**
	 * Gets the scene's light.
	 * @return The scene's light.
	 */
	public Light getLight() {
		return light;
	}
	
	/**
	 * Gets the scene's object list.
	 * @return The scene's object list.
	 */
	public List<Sphere> getObjList() {
		return objList;
	}
}
