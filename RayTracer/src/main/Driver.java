package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import shapes.Sphere;
import utility.Color;
import utility.Light;
import utility.Material;
import utility.Point3D;
import utility.Ray;
import utility.Vector3D;

public class Driver {
	
	public static void main(String[] args) {
		
		int width = 1280;
		int height = 640;
		
		BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		boolean rendered = false;

		// camera position = where rays originate from
		Vector3D camPos = new Vector3D(0, 0, -100);
		
		// light object                             init -10
		Light light = new Light(new Point3D(25, 25, -10), new Color(1, 1, 1));
		
		// test object spheres (origin, radius, color)
		List<Sphere> sList = new ArrayList<Sphere>();
		Sphere sphere = new Sphere(new Point3D(-1.1, 0, 40), .5, new Material(new Color(1, 0, 0)));  
		Sphere sphere2 = new Sphere(new Point3D(0, 0, 40), .5, new Material(new Color(0, 1, 0)));
		Sphere sphere3 = new Sphere(new Point3D(1.1, 0, 40), .5, new Material(new Color(0, 0, 1)));
		Sphere sphere4 = new Sphere(new Point3D(2.2, 0, 40), .5, new Material(new Color(0, 1, 1)));
		Sphere sphere5 = new Sphere(new Point3D(-2.2, 0, 40), .5, new Material(new Color(1, 0, 1)));
		sList.add(sphere);
		sList.add(sphere2);
		sList.add(sphere3);
		sList.add(sphere4);
		sList.add(sphere5);
		
		// ray tracing algorithm
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				// define a 2 x 2 orthonormal basis, corresponding to aspect ratio
				// x >> -2 to 2, will be functionable as Vector3D(4, 0, 0);
				// y >> -1 to 1, will be functionable as Vector3D(0, 2, 0);
				// origin, Vector3D(-2, -1, 100);
				// TODO: implement aspect ratio
				
				Vector3D horizontal = new Vector3D(4, 0, 0);
				Vector3D vertical = new Vector3D(0, 2, 0);
				Vector3D startPos = new Vector3D(-2, -1, 100);
				
				// values for mapping viewing plane to pixels
				float u = (float) x / (float) width;
				float v = (float) y / (float) height;

				Ray ray = new Ray(camPos, startPos.add(horizontal.multiply(u)).add(vertical.multiply(v)));

				// set default color to black 
				Color color = new Color(1, 1, 1);
				
				// for each sphere in the
				for (Sphere s : sList) {
					double t = s.intersection(ray);
					Vector3D hitPosition = ray.getOrigin().add(ray.getDirection().multiply(t));
					Vector3D hitNormal = s.normal(hitPosition);
					
					// calculate the color at the given hit position
					Material mat = s.getMaterial();
					Color objColor = mat.colorAt(hitPosition);
					Vector3D toCam = camPos.subtract(hitPosition).normalize();
					Color defColor = (new Color(0, 0, 0)).multiply(mat.getAmbient());
					int specularK = 50;
					
					Ray toLight = new Ray(hitPosition, (light.getPosition().toVector()).subtract(hitPosition));
					
					// diffuse shading
					defColor.add(objColor.multiply(mat.getDiffuse()).multiply(Math.max(hitNormal.dotProduct(toLight.getDirection()), 0)));
					
					// specular shading
					Vector3D halfVector = (toLight.getDirection().add(toCam)).normalize();
					defColor.add(light.getColor().multiply(mat.getSpecular()).multiply(  Math.pow((Math.max(hitNormal.dotProduct(halfVector), 0)), specularK)));

					if (s.intersection(ray) != 0.0) {
						color = defColor;
						rendered = true;
					}
				}
				buffer.setRGB(x, y, (int) color.toInteger());
			}
		}
		
		// print whether or not the sphere is intersected
		System.out.println(rendered);
		
		// attempt to write image to file, catch exception if fails
		try {
			File file = new File("img.png");
			ImageIO.write(buffer, "png", file);
		} catch(IOException e) {
			System.out.println("Could not write image to file.");
			System.exit(1);
		}
	}
}
