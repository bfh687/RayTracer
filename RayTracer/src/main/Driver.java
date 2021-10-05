package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import render.Camera;
import render.Engine;
import render.Scene;
import shapes.Sphere;
import utility.Color;
import utility.Light;
import utility.Material;
import utility.Point3D;

/**
 * The driver class used for the GUI view of the raytracer.
 * @author bfh687
 */
public class Driver {
	
	/**
	 * Render scaling resolution.
	 */
	public static final double RESOLUTION = 1.0;
	
	/**
	 * Render screen width.
	 */
	public static final int WIDTH = 960 * 2;
	
	/**
	 * Render screen height.
	 */
	public static final int HEIGHT = 480 * 2;
	
	/**
	 * The application's entry point.
	 * @param args The command-line arguments.
	 */
	public static void main(String[] args) throws IOException {
		
		// create a new Light and Camera
		Light light = new Light(new Point3D(0, 50, -25), new Color(1, 1, 1));
		Camera camera = new Camera();
		
		// test object spheres (origin, radius, color)
		List<Sphere> objList = new ArrayList<Sphere>();
		objList.add(new Sphere(new Point3D(-2.1, 0, 0), 1, new Material(new Color(1, 0, 0))));
		objList.add(new Sphere(new Point3D(0, 0, 0), 1, new Material(new Color(0, 1, 0))));
		objList.add(new Sphere(new Point3D(2.1, 0, 0), 1, new Material(new Color(0, 0, 1))));
		objList.add(new Sphere(new Point3D(0, 5, 10), 5, new Material(new Color(1, 1, 1), .05, 1, .5, .5)));
		objList.add(new Sphere(new Point3D(0, -223300001, 0), 223300000, (new Material(new Color(1, 1, 1), new Color(), .05, 1, 1, .4))));

		// creates scene and engine, and renders initial image
		Scene scene = new Scene(light, objList);
		Engine engine = new Engine(scene, camera);
		
		// record time taken to render scene
		long start = System.currentTimeMillis();
		BufferedImage image = engine.render(WIDTH, HEIGHT, RESOLUTION);
		long end = System.currentTimeMillis();   
		System.out.println("Elapsed Time in milli seconds: "+ (end - start));
		
		// draw look-from and look-to onto final render
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(java.awt.Color.WHITE);
		g.drawString("look from: " + camera.getLookFrom(), 10, 15);
		g.drawString("look to: " + camera.getLookTo(), 10, 30);
		
		// creates new frame and panel
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		frame.add(panel);
		
		// creates a label that holds rendered images
		JLabel label = new JLabel(new ImageIcon(image));
	    panel.add(label);
	    
	    // final jframe operations
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
}