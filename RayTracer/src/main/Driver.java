package main;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import render.Camera;
import render.Engine;
import render.Scene;
import shapes.Sphere;
import utility.Color;
import utility.Light;
import utility.Material;
import utility.Point3D;

public class Driver {
	
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
		
		Scene scene = new Scene(light, objList);
		
		Engine engine = new Engine(scene, camera);
		
		BufferedImage image = engine.render(1600, 900);
		
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		JLabel label = new JLabel(new ImageIcon(image));
		frame.getContentPane().add(label);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			File file = new File("img.png");
			ImageIO.write(image, "png", file);
		} catch(IOException e) {
			System.out.println("Could not write image to file.");
			System.exit(1);
		}
	}

}
