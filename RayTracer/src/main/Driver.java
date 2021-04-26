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

public class Driver {
	
	private static double resolution;
	
	public static void main(String[] args) {
		
		Light light = new Light(new Point3D(25, 50, -25), new Color(1, 1, 1));
		Camera camera = new Camera();
		
		// test object spheres (origin, radius, color)
		List<Sphere> objList = new ArrayList<Sphere>();
		objList.add(new Sphere(new Point3D(-2.1, 0, 0), 1, new Material(new Color(1, 0, 0))));
		objList.add(new Sphere(new Point3D(0, 0, 0), 1, new Material(new Color(0, 1, 0))));
		objList.add(new Sphere(new Point3D(2.1, 0, 0), 1, new Material(new Color(0, 0, 1))));
		objList.add(new Sphere(new Point3D(0, 5, 10), 5, new Material(new Color(1, 1, 1), .05, 1, .5, .5)));
		objList.add(new Sphere(new Point3D(0, -223300001, 0), 223300000, (new Material(new Color(1, 1, 1), new Color(), .05, 1, 1, .4))));

		resolution = 1;
		
		Scene scene = new Scene(light, objList);
		Engine engine = new Engine(scene, camera);
		BufferedImage image = engine.render(1280, 720, resolution);
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		frame.add(panel);
		
		JLabel label = new JLabel(new ImageIcon(image));
	    panel.add(label);
	    
	    frame.addKeyListener(new KeyAdapter() {
	    	@Override
	        public void keyPressed(KeyEvent e) {
	    		int keyCode = e.getKeyCode();
	    		
	    		if (keyCode == KeyEvent.VK_E || keyCode == KeyEvent.VK_Q) {
	    			double change = resolution;
		    		if (keyCode == KeyEvent.VK_E) {
		    			if (resolution < 1) {
		    				resolution += .15;
		    			}
		    		} 
		    		
		    		if (keyCode == KeyEvent.VK_Q) {
		    			if (resolution > .1) {
		    				resolution -= .15;
		    			}
		    		} 
		    		if (change != resolution) {
		    			label.setIcon(new ImageIcon(engine.render(1280, 720, resolution)));
		    		}
	    		} else {
	    		
		    		if (keyCode == KeyEvent.VK_W) {
		    			engine.getCamera().setLookFrom(engine.getCamera().getLookFrom().add(new Vector3D(0, 0, 1)));
		    			engine.getCamera().setLookTo(engine.getCamera().getLookTo().add(new Vector3D(0, 0, 1)));
		    		} 
		    		if (keyCode == KeyEvent.VK_A) {
		    			engine.getCamera().setLookFrom(engine.getCamera().getLookFrom().add(new Vector3D(1, 0, 0)));
		    			engine.getCamera().setLookTo(engine.getCamera().getLookTo().add(new Vector3D(1, 0, 0)));
		    		} 
		    		if (keyCode == KeyEvent.VK_S) {
		    			engine.getCamera().setLookFrom(engine.getCamera().getLookFrom().add(new Vector3D(0, 0, -1)));
		    			engine.getCamera().setLookTo(engine.getCamera().getLookTo().add(new Vector3D(0, 0, -1)));
		    		} 
		    		if (keyCode == KeyEvent.VK_D) {
		    			engine.getCamera().setLookFrom(engine.getCamera().getLookFrom().add(new Vector3D(-1, 0, 0)));
		    			engine.getCamera().setLookTo(engine.getCamera().getLookTo().add(new Vector3D(-1, 0, 0)));
		    		}
		    		
		    		label.setIcon(new ImageIcon(engine.render(1280, 720, .1)));
		    		
	    		}

	        }
	    	
	    	public void keyReleased(KeyEvent e) {
	    		label.setIcon(new ImageIcon(engine.render(1280, 720, resolution)));
	    	}
	    });
		
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
	}

}
