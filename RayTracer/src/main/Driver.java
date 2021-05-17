package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
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
import utility.Vector3D;

/**
 * The driver class used for the GUI view of the raytracer.
 * @author bfh687
 */
public class Driver {
	
	/**
	 * Render scaling resolution.
	 */
	private static double resolution;
	
	/**
	 * The application's entry point.
	 * @param args The command-line arguments.
	 */
	public static void main(String[] args) {
		
		// create a new Light and Camera
		Light light = new Light(new Point3D(25, 50, -25), new Color(1, 1, 1));
		Camera camera = new Camera();
		
		// test object spheres (origin, radius, color)
		List<Sphere> objList = new ArrayList<Sphere>();
		objList.add(new Sphere(new Point3D(-2.1, 0, 0), 1, new Material(new Color(1, 0, 0))));
		objList.add(new Sphere(new Point3D(0, 0, 0), 1, new Material(new Color(0, 1, 0))));
		objList.add(new Sphere(new Point3D(2.1, 0, 0), 1, new Material(new Color(0, 0, 1))));
		objList.add(new Sphere(new Point3D(0, 5, 10), 5, new Material(new Color(1, 1, 1), .05, 1, .5, .5)));
		objList.add(new Sphere(new Point3D(0, -223300001, 0), 223300000, (new Material(new Color(1, 1, 1), new Color(), .05, 1, 1, .4))));
		
		// set default resolution to .2 / 20%
		resolution = 1;
		
		// creates scene and engine, and renders initial image
		Scene scene = new Scene(light, objList);
		Engine engine = new Engine(scene, camera);
		BufferedImage image = engine.render(960, 540, resolution);
		
		// creates new frame and panel
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		frame.add(panel);
		
		// creates a label that holds rendered images
		JLabel label = new JLabel(new ImageIcon(image));
	    panel.add(label);
	    
	    // handles key events for WASD movement
	    frame.addKeyListener(new KeyAdapter() {
	    	@Override
	        public void keyPressed(KeyEvent e) {
	    		int keyCode = e.getKeyCode();
	    		double theta = camera.getTheta();

	    		Vector3D lookFrom = camera.getLookFrom();
	    		Vector3D lookTo = camera.getLookTo();
	    		
	    		if (keyCode == KeyEvent.VK_E ) {
	    			theta -= Math.PI / 12;
	    			camera.setTheta(theta);
		    		label.setIcon(new ImageIcon(engine.render(960, 540, resolution)));		    		
	    		}
	    		
	    		if (keyCode == KeyEvent.VK_Q) {
	    			theta += Math.PI / 12;
	    			camera.setTheta(theta);
	    			label.setIcon(new ImageIcon(engine.render(960, 540, resolution)));	
	    		}
	    		
	    		if (keyCode == KeyEvent.VK_W ) {
	    			camera.setLookFrom(lookFrom.add(new Vector3D(0, 0, 1)));
	    			camera.setLookTo(lookTo.add(new Vector3D(0, 0, 1)));
		    		label.setIcon(new ImageIcon(engine.render(960, 540, resolution)));		    		
	    		}
	    		
	    		if (keyCode == KeyEvent.VK_A) {
	    			camera.setLookFrom(lookFrom.add(new Vector3D(1, 0, 0)));
	    			camera.setLookTo(lookTo.add(new Vector3D(1, 0, 0)));
	    			label.setIcon(new ImageIcon(engine.render(960, 540, resolution)));	
	    		}
	    		
	    		if (keyCode == KeyEvent.VK_S) {
	    			camera.setLookFrom(lookFrom.add(new Vector3D(0, 0, -1)));
	    			camera.setLookTo(lookTo.add(new Vector3D(0, 0, -1)));
	    			label.setIcon(new ImageIcon(engine.render(960, 540, resolution)));	
	    		}
	    		
	    		if (keyCode == KeyEvent.VK_D) {
	    			camera.setLookFrom(lookFrom.add(new Vector3D(-1, 0, 0)));
	    			camera.setLookTo(lookTo.add(new Vector3D(-1, 0, 0)));
	    			label.setIcon(new ImageIcon(engine.render(960, 540, resolution)));	
	    		}
	    		
	        }
	    });
		
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
}