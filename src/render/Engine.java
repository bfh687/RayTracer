package render;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import main.Driver;
import shapes.Sphere;
import utility.Color;
import utility.Material;
import utility.Ray;
import utility.Vector3D;

/**
 * The engine class used to handle rendering of the raytracing algorithm.
 * @author bfh687
 */
public class Engine {
	
	/**
	 * The amount of threads allocated to the render engine.
	 */
	public static final int THREADS = Runtime.getRuntime().availableProcessors();

	/**
	 * The scene.
	 */
	private Scene scene;

	/**
	 * The camera.
	 */
	private Camera camera;
	
	/**
	 * Creates a new Engine object with the given scene and camera.
	 * @param scene The scene to be rendered.
	 * @param camera The camera to view the scene.
	 */
	public Engine(Scene scene, Camera camera) {
		this.scene = scene;
		this.camera = camera;
	}
	
	/**
	 * Renders a image of the camera's current view with the given height,
	 * width, and render resolution.
	 * @param width Width of the rendered image.
	 * @param height Height of the rendered image.
	 * @param resolution Render scaling resolution.
	 * @return A rendered image of the current camera view.
	 * @throws InterruptedException 
	 */
	public BufferedImage render(int width, int height, double resolution) throws InterruptedException {
		if (resolution < .01 || resolution > 1.0) {
			throw new IllegalArgumentException();
		}

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		List<Sphere> objList = scene.getObjList();
		
		ExecutorService pool = Executors.newFixedThreadPool(THREADS);
		
		for (int threadNum = 0; threadNum < THREADS; threadNum++) {
			pool.execute(createRenderThread(threadNum, image, objList, width, height));
		}
		pool.shutdown();
		
		try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return image;
	}	
	
	/**
	 * Gets the engine's scene object.
	 * @return The engine's scene object.
	 */
	public Scene getScene() {
		return scene;
	}
	
	/**
	 * Gets the engine's camera object.
	 * @return The engine's camera object.
	 */
	public Camera getCamera() {
		return camera;
	}
	
	private Runnable createRenderThread(final int threadNum, final BufferedImage image, final List<Sphere> objList, final int width, final int height) {
        return new Runnable() {
        	@Override
			public void run() {
				for (int j = threadNum * (width / THREADS); j < (threadNum + 1) * (width / THREADS); j++) {
					for (int k = 0; k < height; k++) {
						// values for mapping viewing plane to pixels
						float a = (float) j / (float) width;
						float b = (float) k / (float) height;

						Vector3D w = ((camera.getLookFrom()).subtract(camera.getLookTo())).normalize();
						Vector3D u = ((Camera.UP_VECTOR).cross(w)).normalize();
						Vector3D v = w.cross(u);
						
						Vector3D origin = camera.getLookFrom();
						
						double aspectRatio = (double) Driver.WIDTH / (double) Driver.HEIGHT;

					    Vector3D horizontal = u.multiply(aspectRatio);
						Vector3D vertical = v.multiply(1);
						Vector3D startPos = origin.subtract(horizontal.divide(2)).subtract(vertical.divide(2)).subtract(w);
						
						// raytracing 
						Ray ray = new Ray(camera.getLookFrom(), (startPos.add(horizontal.multiply(a)).add(vertical.multiply(b)).subtract(origin)).normalize());
						Color color = raytrace(ray, objList, 0);

						// update image rgb
						image.setRGB(j, k, (int) color.toInteger());
					}
				}
			}
        };
    }
	
	private Color raytrace(Ray ray, List<Sphere> objList, int depth) {
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

	private Color colorAt(Sphere objHit, Vector3D hitPos, Vector3D normal, boolean isShadow) {
		Material mat = objHit.getMaterial();
		Color objColor = mat.colorAt(hitPos);
		
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
	
	private boolean isShadow(Vector3D hitPos, Vector3D hitNormal, List<Sphere> sList) {
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
	
	private Map<Double, Sphere> findNearest(Ray ray, List<Sphere> objList) {
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
}