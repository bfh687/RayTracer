package main;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import shapes.Sphere;
import utility.Color;
import utility.Light;
import utility.Material;
import utility.Point3D;
import utility.Ray;
import utility.Vector3D;

public class Driver {
	public static final Light light = new Light(new Point3D(23, 50, -23), new Color(1, 1, 1));
	public static Vector3D camPos = new Vector3D(0, -1, -10);
	public static void main(String[] args) {
		
		int width = 1600;
		int height = 900;
		
		BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		// test object spheres (origin, radius, color)
		List<Sphere> sList = new ArrayList<Sphere>();
		sList.add(new Sphere(new Point3D(-2.1, 0, 0), 1, new Material(new Color(1, 0, 0))));
		sList.add(new Sphere(new Point3D(0, 0, 0), 1, new Material(new Color(0, 1, 0))));
		sList.add(new Sphere(new Point3D(2.1, 0, 0), 1, new Material(new Color(0, 0, 1))));
		sList.add(new Sphere(new Point3D(0, 5, 10), 5, new Material(new Color(1, 1, 1), .05, 1, .5, .5)));
		sList.add(new Sphere(new Point3D(0, -223300001, 0), 223300000, (new Material(new Color(1, 1, 1), new Color(), .05, 1, 1, .4))));

		Vector3D lookFrom = camPos;
		Vector3D lookTo = new Vector3D(0, 0, 0);
		Vector3D vecUp = new Vector3D(0, 1, 0); 

		// ray tracing algorithm
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				
				// values for mapping viewing plane to pixels
				float a = (float) x / (float) width;
				float b = (float) y / (float) height;

				Vector3D w = (lookFrom.subtract(lookTo)).normalize();
				Vector3D u = (vecUp.cross(w));
				Vector3D v = w.cross(u);
				
				Vector3D origin = lookFrom;
				Vector3D horizontal = u.multiply(2);
				Vector3D vertical = v.multiply(1);
				Vector3D startPos = origin.subtract(horizontal.divide(2)).subtract(vertical.divide(2)).subtract(w);
				
				Ray ray = new Ray(camPos, startPos.add(horizontal.multiply(a)).add(vertical.multiply(b)).subtract(origin));
				Color color = rayTrace(ray, sList, 0);
				buffer.setRGB(x, y, (int) color.toInteger());
			}
		}
		
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		JLabel label = new JLabel(new ImageIcon(buffer));
		frame.getContentPane().add(label);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			File file = new File("img.png");
			ImageIO.write(buffer, "png", file);
		} catch(IOException e) {
			System.out.println("Could not write image to file.");
			System.exit(1);
		}
	}

	public static Color rayTrace(Ray ray, List<Sphere> sList, int depth) {
		Color color = new Color();
		
		Map<Double, Sphere> map = findNearest(ray, sList);
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
		
		boolean isShadow = isShadow(hitPos, hitNormal, sList);
		color.add(colorAt(objHit, hitPos, hitNormal, isShadow));
		
		// reflections via recursion
		if (depth < 3) {
			Vector3D newRayPos = hitPos.add(hitNormal.multiply(.0001));
			Vector3D newRayDir = ray.getDirection().subtract((hitNormal.multiply((ray.getDirection().dotProduct(hitNormal)) * 2)));
			Ray newRay = new Ray(newRayPos, newRayDir);
			color.add((rayTrace(newRay, sList, depth + 1)).multiply(objHit.getMaterial().getReflection()));
		}
		
		return color;
	}
	
	public static boolean isShadow(Vector3D hitPos, Vector3D hitNormal, List<Sphere> sList) {
		Vector3D dirToLight = light.getPosition().toVector().subtract(hitPos);
		
		double t = 0;
		Ray ray = new Ray(hitPos.add(hitNormal.multiply(.0001)), dirToLight);
		for (Sphere s : sList) {
			t = s.intersection(ray);
			if (t != 0.0)
				return true;
		}
		return false;
	}
	
	public static Map<Double, Sphere> findNearest(Ray ray, List<Sphere> sList) {
		double distMin = 0;
		double dist = 0;
		Sphere objHit = null;
		
		for (Sphere s : sList) {
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
	
	public static Color colorAt(Sphere objHit, Vector3D hitPos, Vector3D normal, boolean isShadow) {
		Material mat = objHit.getMaterial();
		Color objColor = mat.colorAt(hitPos); mat.getColor();
		
		Vector3D toCam = camPos.subtract(hitPos).normalize();
		Color color = (new Color(0, 0, 0)).multiply(mat.getAmbient());
		int specularK = 50;
		
		Ray toLight = new Ray(hitPos, (light.getPosition().toVector()).subtract(hitPos));
		
		// diffuse shading
		color.add(objColor.multiply(mat.getDiffuse()).multiply(Math.max(normal.dotProduct(toLight.getDirection()), 0)));
		
		// specular shading
		if (!isShadow) {
			Vector3D halfVector = (toLight.getDirection().add(toCam)).normalize();
			color.add(light.getColor().multiply(mat.getSpecular()).multiply(Math.pow((Math.max(normal.dotProduct(halfVector), 0)), specularK)));
		} else {
			color = color.multiply(.2);
		}
		
		return color;
	}
}
