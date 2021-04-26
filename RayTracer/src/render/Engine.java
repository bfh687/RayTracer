package render;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shapes.Sphere;
import utility.Color;
import utility.Material;
import utility.Ray;
import utility.Vector3D;

public class Engine {
	
	private Scene scene;
	private Camera camera;
	
	
	public Engine(Scene scene, Camera camera) {
		this.scene = scene;
		this.camera = camera;
	}
	
	public BufferedImage render(int width, int height, double resolution) {
		
		if (resolution < .01 || resolution > 1.0) {
			throw new IllegalArgumentException();
		}
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		List<Sphere> objList = scene.getObjList();
		
		// ray tracing algorithm
		for (int y = 0; y < height; y += 1 / resolution) {
			for (int x = 0; x < width; x+= 1 / resolution) {
				
				// values for mapping viewing plane to pixels
				float a = (float) x / (float) width;
				float b = (float) y / (float) height;

				Vector3D w = ((camera.getLookFrom()).subtract(camera.getLookTo())).normalize();
				Vector3D u = ((Camera.UP_VECTOR).cross(w));
				Vector3D v = w.cross(u);
				
				Vector3D origin = camera.getLookFrom();
				Vector3D horizontal = u.multiply(2);
				Vector3D vertical = v.multiply(1);
				Vector3D startPos = origin.subtract(horizontal.divide(2)).subtract(vertical.divide(2)).subtract(w);
				
				Ray ray = new Ray(camera.getLookFrom(), (startPos.add(horizontal.multiply(a)).add(vertical.multiply(b)).subtract(origin)).normalize()); //SHJAKLFHKASJDHF
				Color color = raytrace(ray, objList, 0);
				image.setRGB(x, y, (int) color.toInteger());
			}
		}
		
		return image;
	}	
	
	public Color raytrace(Ray ray, List<Sphere> objList, int depth) {
		Color color = new Color();
		
		Map<Double, Sphere> map = findNearest(ray, objList);
		double distHit = 0;;
		Sphere objHit = null;
		
		for (double d : map.keySet()) {
			distHit = d;
			objHit = map.get(d);
		}
		
		if (objHit == null) {
			return color;
		}

		Vector3D hitPos = ray.getOrigin().add(ray.getDirection().multiply(distHit));
		Vector3D hitNormal = null;
		hitNormal = objHit.normal(hitPos);
		
		boolean isShadow = isShadow(hitPos, hitNormal, objList);
		color.add(colorAt(objHit, hitPos, hitNormal, isShadow));
		
		// reflections via recursion
		if (depth < 3) {
			Vector3D newRayPos = hitPos.add(hitNormal.multiply(.0001));
			Vector3D newRayDir = ray.getDirection().subtract((hitNormal.multiply((ray.getDirection().dotProduct(hitNormal)) * 2)));
			Ray newRay = new Ray(newRayPos, newRayDir);
			color.add((raytrace(newRay, objList, depth + 1)).multiply(objHit.getMaterial().getReflection()));
		}
		
		return color;
	}
	
	public Color colorAt(Sphere objHit, Vector3D hitPos, Vector3D normal, boolean isShadow) {
		Material mat = objHit.getMaterial();
		Color objColor = mat.colorAt(hitPos); mat.getColor();
		
		Vector3D toCam = camera.getLookFrom().subtract(hitPos).normalize();
		Color color = (new Color(0, 0, 0)).multiply(mat.getAmbient());
		int specularK = 50;
		
		Ray toLight = new Ray(hitPos, (scene.getLight().getPosition().toVector()).subtract(hitPos));
		
		// diffuse shading
		color.add(objColor.multiply(mat.getDiffuse()).multiply(Math.max(normal.dotProduct(toLight.getDirection()), 0)));
		
		// specular shading
		if (!isShadow) {
			Vector3D halfVector = (toLight.getDirection().add(toCam)).normalize();
			color.add(scene.getLight().getColor().multiply(mat.getSpecular()).multiply(Math.pow((Math.max(normal.dotProduct(halfVector), 0)), specularK)));
		} else {
			color = color.multiply(.2);
		}
		
		return color;
	}
	
	public boolean isShadow(Vector3D hitPos, Vector3D hitNormal, List<Sphere> sList) {
		Vector3D dirToLight = scene.getLight().getPosition().toVector().subtract(hitPos);
		
		double t = 0;
		Ray ray = new Ray(hitPos.add(hitNormal.multiply(.0001)), dirToLight);
		for (Sphere s : sList) {
			t = s.intersection(ray);
			if (t != 0.0)
				return true;
		}
		return false;
	}
	
	public Map<Double, Sphere> findNearest(Ray ray, List<Sphere> objList) {
		double distMin = 0;
		double dist = 0;
		Sphere objHit = null;
		
		for (Sphere s : objList) {
			dist = s.intersection(ray);
			
			if (dist != 0 && (objHit == null || dist < distMin)) {
				distMin = dist;
				objHit = s;
			}
		}
		
		Map<Double, Sphere> map = new HashMap<Double, Sphere>();
		map.put(distMin, objHit);
		
		return map;
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public Camera getCamera() {
		return camera;
	}
}