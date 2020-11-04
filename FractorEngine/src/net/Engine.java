package net;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Arrays;

import net.buffering.FramebufferManager;
import net.gameobjects.GameObject;
import net.maths.GameObjectFactory;
import net.window.Window;

public class Engine {
	
	public static void main(String[] args) {
		Engine.getInstance().start();
		
		//TODO add switch for "fps-like" camera controls and camera rotation based (=> pressing 'W' means going in forward direction of camera)
	}
	private static Engine instance;
	public static Engine getInstance() {
		if(instance == null)
			instance = new Engine();
		return instance;
	}
	
	
	
	public final int FPS_TARGET = 10000;
	public boolean DRAW_WIREFRAME = false;
	public final int WIREFRAME_COLOR_ID = registerColor(Color.red);
	
	private Window window;
	
	public Engine() {
		init();
		
		this.window = new Window();
	}
	
	public void start() {
		initWorld();		
		this.framebufferManager.startThreadComplex();		
	}
	
	private void init() {
		this.resolution = new Dimension(1000, 1000);
		
		this.framebufferManager = new FramebufferManager(this.resolution, this.synchronousCode);
		
		this.cameraPosition = new double[] {0, 0, 0};

		try {
			robot = new Robot();
		} catch(AWTException e) {
			e.printStackTrace();
		}
//		this.trianglesRendered = new double[1000][][];
	}
	
	private void initWorld() {
		//addTriangles(GameObjectFactory.cube(new double[] {0, 0, 1}, 2));
		
		//CHAIR:
//		addGameObject(GameObjectFactory.randomizeColor(GameObjectFactory.readFromFileGameObject(new File("H:\\Programmieren\\BlenderFiles\\chair_highres.obj"))));		
//		addGameObject(GameObjectFactory.readFromFileGameObject(new File("objects3d\\chair_highres.obj")));
		
		//DRAGON:
//		addTriangles(GameObjectFactory.randomizeColor(GameObjectFactory.readFromFile(new File("H:\\BlenderAwesomeFiles\\27-blender\\blender\\Dragon_2.5_For_Animations.obj"))));
//		addGameObject(GameObjectFactory.randomizeColor(GameObjectFactory.readFromFileGameObject(new File("objects3d/Dragon_2.5_For_Animations.obj"))));
		
		//millenium falcon
//		addGameObject(GameObjectFactory.randomizeColor(GameObjectFactory.readFromFileGameObject(new File("H:\\Programmieren\\BlenderFiles\\millenium-falcon.obj"))));
		
//		//LAMP
		//GameObjectFactory.displayLightPoint(triangles, new double[] {-4.908, 17.36, 0});
		addGameObject(GameObjectFactory.readFromFileGameObject(new File("objects3d\\lamp.obj")));
		
		//PROCEDURAL-SPHERE
//		addGameObject(new SphereProcedural(2).move(5, 0, 0));
		
		//clipping test
//		addGameObject(new GameObject(new double[][][] {
//			{{-1, 0, -1}, {-1, 0, 10}, {-1, -10, 10}}
//		}));
		
//		//Texturing
//		Texture.textureDEBUG = new Texture(new int[0], 0);
//		addGameObject(new GameObject(new double[][][] {
//			{{0, 1, 2}, {1, -1, 2}, {-1, -1, 2}, {}, {}, {0.5, 0, 1, 1, 0, 1}} 
//		}, Texture.textureDEBUG));
		
		
		
		//GROUND
		addGameObject(GameObjectFactory.readFromFileGameObject(new File("objects3d\\ground.obj")));
		
//		for(int cycle = 0; cycle < 7; cycle++) {
//			double[][][] cache = new double[][][] {{{-5 + (cycle*10), 0, 10}, {0+(cycle*10), 5, 10}, {5+(cycle*10), 0, 10}}};
//			double[][][] cache2;
//			ArrayList<double[][]> cacheTriangles = new ArrayList<double[][]>();
//			
//			for(int i = 0; i < cycle; i++) {
//				for(int t = 0; t < cache.length; t++) {
//					cache2 = GameObjectFactory.splitTriangle(cache[t], false);
//					for(int x = 0; x < cache2.length; x++) {
//						cacheTriangles.add(cache2[x]);
//					}				
//				}
//				cache = cacheTriangles.toArray(new double[0][][]);
//				cacheTriangles.clear();
//			}
//			addTriangles(cache);
//		}
		
//		addTriangles(GameObjectFactory.splitTriangle(
//				
//		, false));
		
		
		//SPHERES
//		addTriangles(GameObjectFactory.readFromFile(new File("H:\\Programmieren\\BlenderFiles\\sphere_highres.obj")));		
//		addTriangles(Calculator.translateTriangles(GameObjectFactory.readFromFile(new File("H:\\Programmieren\\BlenderFiles\\sphere_highres.obj")), new double[] {25, 0, 0}));
		
//		addTriangles(Calculator.translateTriangles(GameObjectFactory.readFromFile(new File("H:\\Programmieren\\BlenderFiles\\wall.obj")), new double[] {5, 0, 0}));
//		addTriangles(new double[][][] {
//			{{0, 0, 0}, {0, 10, 0}, {0, 0, 10}},
//			{{25, 0, 0}, {25, 0, 10}, {25, 10, 0}}
//		});
		
//		addTriangles(GameObjectFactory.displayLightDirectional(GameObjectFactory.readFromFile(new File("H:\\BlenderAwesomeFiles\\27-blender\\blender\\Dragon_2.5_For_Animations.obj")), new double[] {0,0,1}));
		
		//cool triangles
//		addTriangles(new double[][][] {
//			{{-5, -5, 0}, {0, 5, 0}, {5, -5, 0}, {}, {registerColor(Color.red)}},
//			{{-5, -5, 2}, {0, 5, 2}, {5, -5, 2}, {}, {registerColor(Color.YELLOW)}}//,
////			{{-1, -1, 0.5}, {0, 1, -1}, {1, -1, 0.5}, {}, {registerColor(Color.BLUE)}}
//		});
		
		//krasses schieflage zeugs
//		addTriangles(new double[][][] {
//			{{0, 0, 2},{1, 0, 1.8},{0, -1, 1}}
//		});//		
//		addTriangles(new double[][][] {
//			{{-1, 1, 1.9}, {1, 1, 1.9}, {1, -1, 1.9}, {}, {registerColor(Color.RED)}}
//		});
		
//		addTriangles(new double[][][] {{{0, 0, 0}, {1, 0, 0}, {0.5, -1, 0}}}); //top sided
//		addTriangles(new double[][][] {{{0.5, 0, 0}, {1, 1, 0}, {1.5, 0, 0}}}); //bottom sided
		
//		addTriangles(new double[][][] {{{0, 0, 0}, {0, 2, 0}, {1, 1, 0}}}); //schief (mitte rechts)
//		addTriangles(new double[][][] {{{-1, -1, 0}, {-0.5, 0, 0}, {0, -1.5, 0}}}); //schief (mitte links)
		
//		for(int x = 0; x < 40; x++)
//			addTriangles(GameObjectFactory.cube(new double[] {(Math.random()*20) -10, (Math.random()*20) -10, (Math.random()*20) -10}, 1));
		
//		addTriangles(new double[][][] {});
	}

	public double deltaTimeSeconds = 0;
	public double FPS_AVERAGE;
	private final Runnable synchronousCode = new Runnable() {
		
		private long timeCycleStart = 0;
		
		public void run() {
//			System.out.println("woop");
			processUserInput();
			
			if(timeCycleStart != 0) {
				deltaTimeSeconds = (System.nanoTime()-timeCycleStart)/1000000000d;
			}
			
			if(deltaTimeSeconds != 0)
				FPS_AVERAGE = (FPS_AVERAGE*(19) + (1/deltaTimeSeconds)) / 20;
			
			if(deltaTimeSeconds < 1d/FPS_TARGET) {
				try {
					Thread.sleep((int) (((1d/FPS_TARGET)-deltaTimeSeconds)*1000));
				}catch(Exception e) {}
			}
			
			timeCycleStart = System.nanoTime();
		}
	};
	
	
	
	
	public double cameraRotationHorizontal = 0;
	public double cameraRotationVertical = 0;
	
	public Dimension resolution;
	private FramebufferManager framebufferManager;
	
//	private double[][][] triangles = new double[0][][];
	private GameObject[] gameObjects = new GameObject[0];
	private int gameObjectsAmount = 0;
//	public double[][][] trianglesRendered;
	
	public double[] cameraPosition;
	public boolean mouseCenterPrisonActivated = false;
	private final double cameraMovementSpeed = 5;
	private final double cameraRotationSpeed = 0.004;
	private Robot robot;	
	private void processUserInput() {
		boolean[] keyRegister = window.getKeyRegister();
		
		double speedMultiplier = cameraMovementSpeed * this.deltaTimeSeconds;
		
		if(keyRegister[KeyEvent.VK_W] && !keyRegister[KeyEvent.VK_S]) {
			this.cameraPosition[0] -= Math.sin(this.cameraRotationHorizontal)*speedMultiplier;
			this.cameraPosition[2] += Math.cos(this.cameraRotationHorizontal)*speedMultiplier;
		}else if(keyRegister[KeyEvent.VK_S] && !keyRegister[KeyEvent.VK_W]) {
			this.cameraPosition[0] += Math.sin(this.cameraRotationHorizontal)*speedMultiplier;
			this.cameraPosition[2] -= Math.cos(this.cameraRotationHorizontal)*speedMultiplier;
		}
		if(keyRegister[KeyEvent.VK_A] && !keyRegister[KeyEvent.VK_D]) {
			this.cameraPosition[2] -= Math.sin(this.cameraRotationHorizontal)*speedMultiplier;
			this.cameraPosition[0] -= Math.cos(this.cameraRotationHorizontal)*speedMultiplier;
		}else if(keyRegister[KeyEvent.VK_D] && !keyRegister[KeyEvent.VK_A]) {
			this.cameraPosition[2] += Math.sin(this.cameraRotationHorizontal)*speedMultiplier;
			this.cameraPosition[0] += Math.cos(this.cameraRotationHorizontal)*speedMultiplier;
		}		
		if(keyRegister[KeyEvent.VK_SPACE] && !keyRegister[KeyEvent.VK_SHIFT]) {
			this.cameraPosition[1] += speedMultiplier;			
		}else if(keyRegister[KeyEvent.VK_SHIFT] && !keyRegister[KeyEvent.VK_SPACE]) {
			this.cameraPosition[1] -= speedMultiplier;
		}
		
		//mouse position
		if(mouseCenterPrisonActivated) {
//			DrawComp dc = window.getDrawComp();
			Point mousePos = window.getMousePosition();
			int centerX = window.getWidth()/2;
			int centerY = window.getHeight()/2;
			if(mousePos != null && !(mousePos.x == centerX && mousePos.y == centerY)) {
//				System.out.println("yup");
//				double rotationSpeedMultiplier = cameraRotationSpeed;
				cameraRotationHorizontal -= (mousePos.x - centerX) * cameraRotationSpeed;
				cameraRotationVertical -= (mousePos.y - centerY) * cameraRotationSpeed;
				
				Point windowLocationOnScreen = window.getLocationOnScreen();
				robot.mouseMove(windowLocationOnScreen.x+centerX, windowLocationOnScreen.y+centerY);
			}
		}
		
		if(cameraRotationVertical > Math.PI/2) cameraRotationVertical = Math.PI/2;
		if(cameraRotationVertical <-Math.PI/2) cameraRotationVertical = -Math.PI/2;
	}
	
	private synchronized void addGameObject(GameObject gameObject) {
		if(gameObjectsAmount >= gameObjects.length) {
			gameObjects = Arrays.copyOf(gameObjects, Math.max(1, gameObjects.length*2));
		}
		gameObjects[gameObjectsAmount] = gameObject;
		gameObjectsAmount++;
	}
	
//	private void addTriangles(double[][][] triangles) {
//		
//		double[][][] newArray = new double[(this.triangles!=null?this.triangles.length:0) + triangles.length][][];
//		int t = 0;
//		if(this.triangles != null)
//			for(; t < this.triangles.length; t++)
//				newArray[t] = this.triangles[t];
//		
//		for(int tNew = 0; tNew < triangles.length; tNew++)
//			newArray[t + tNew] = triangles[tNew];
//		
//		this.triangles = newArray;
//		
//		//adjust trianglesRendered Array
////		trianglesRendered = new double[this.triangles.length][][];
//	}
	
	private Color[] colorRegister;
	public Color getColor(int index) {
		return this.colorRegister[index];
	}
	public int registerColor(Color color) {
		if(colorRegister == null)
			colorRegister = new Color[] {Color.WHITE};
		//check if this color already exists
		final int registerLen = this.colorRegister.length;
		int i = 0;
		for(; i < registerLen; i++) {
			if(this.colorRegister[i] == null)
				break;
			if(this.colorRegister[i].getRGB() == color.getRGB())
				return i;
		}
		//insert color into array
		if(i >= registerLen) {
			//lengthen array
			this.colorRegister = Arrays.copyOf(this.colorRegister, registerLen*2);			
		}
		this.colorRegister[i] = color;
		return i;
	}
	
//	public double[][][] getTriangles() {
//		return this.triangles;
//	}
	
	public GameObject[] getGameObjects() {
		return gameObjects;
	}
	
	public FramebufferManager getFramebufferManager() {
		return this.framebufferManager;
	}
	
	public Window getWindow() {
		return this.window;
	}
}
