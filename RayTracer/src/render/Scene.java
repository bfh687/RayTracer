package render;

import java.util.List;

import shapes.Sphere;
import utility.Color;
import utility.Light;
import utility.Material;
import utility.Point3D;

public class Scene {
	private Light light;
	private List<Sphere> objList;
	
	public Scene(Light light, List<Sphere> objList) {
		this.light = light;
		this.objList = objList;
		
		objList.add(new Sphere(new Point3D(-2.1, 0, 0), 1, new Material(new Color(1, 0, 0))));
		objList.add(new Sphere(new Point3D(0, 0, 0), 1, new Material(new Color(0, 1, 0))));
		objList.add(new Sphere(new Point3D(2.1, 0, 0), 1, new Material(new Color(0, 0, 1))));
		objList.add(new Sphere(new Point3D(0, 5, 10), 5, new Material(new Color(1, 1, 1), .05, 1, .5, .5)));
		objList.add(new Sphere(new Point3D(0, -223300001, 0), 223300000, (new Material(new Color(1, 1, 1), new Color(), .05, 1, 1, .4))));
	}
	
	public void addObj(Sphere sphere) {
		objList.add(sphere);
	}
	
	public Light getLight() {
		return light;
	}
	
	public List<Sphere> getObjList() {
		return objList;
	}
}
