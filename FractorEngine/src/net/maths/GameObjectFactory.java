package net.maths;
import static net.maths.Calculator.copy;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import net.Engine;
import net.gameobjects.GameObject;



public class GameObjectFactory {
	
	public static double[][][] cube() {
		return new double[][][] {
			//-z
			{{0, 0, 0}, {0, 1, 0}, {1, 1, 0}},
			{{0, 0, 0}, {1, 1, 0}, {1, 0, 0}},
			//z
			{{1, 0, 1}, {1, 1, 1}, {0, 1, 1}},
			{{1, 0, 1}, {0, 1, 1}, {0, 0, 1}},
			
			//-y
			{{0, 0, 1}, {0, 0, 0}, {1, 0, 0}},
			{{0, 0, 1}, {1, 0, 0}, {1, 0, 1}},
			//y
			{{0, 1, 0}, {0, 1, 1}, {1, 1, 1}},
			{{0, 1, 0}, {1, 1, 1}, {1, 1, 0}},
			
			//-x
			{{0, 0, 1}, {0, 1, 1}, {0, 1, 0}},
			{{0, 0, 1}, {0, 1, 0}, {0, 0, 0}},
			//x
			{{1, 0, 0}, {1, 1, 0}, {1, 1, 1}},
			{{1, 0, 0}, {1, 1, 1}, {1, 0, 1}}
		};
	}
	
	public static double[][][] cube(double[] center, double edgeLength) {
		double edgeHalf = edgeLength/2;
		return new double[][][] {
			//-z
			{{-edgeHalf+center[0], -edgeHalf+center[1], -edgeHalf+center[2]}, {-edgeHalf+center[0], edgeHalf+center[1], -edgeHalf+center[2]}, {edgeHalf+center[0], edgeHalf+center[1], -edgeHalf+center[2]}},
			{{-edgeHalf+center[0], -edgeHalf+center[1], -edgeHalf+center[2]}, {edgeHalf+center[0], edgeHalf+center[1], -edgeHalf+center[2]}, {edgeHalf+center[0], -edgeHalf+center[1], -edgeHalf+center[2]}},
			//z
			{{edgeHalf+center[0], -edgeHalf+center[1], edgeHalf+center[2]}, {edgeHalf+center[0], edgeHalf+center[1], edgeHalf+center[2]}, {-edgeHalf+center[0], edgeHalf+center[1], edgeHalf+center[2]}},
			{{edgeHalf+center[0], -edgeHalf+center[1], edgeHalf+center[2]}, {-edgeHalf+center[0], edgeHalf+center[1], edgeHalf+center[2]}, {-edgeHalf+center[0], -edgeHalf+center[1], edgeHalf+center[2]}},
			
			//-y
			{{-edgeHalf+center[0], -edgeHalf+center[1], edgeHalf+center[2]}, {-edgeHalf+center[0], -edgeHalf+center[1], -edgeHalf+center[2]}, {edgeHalf+center[0], -edgeHalf+center[1], -edgeHalf+center[2]}},
			{{-edgeHalf+center[0], -edgeHalf+center[1], edgeHalf+center[2]}, {edgeHalf+center[0], -edgeHalf+center[1], -edgeHalf+center[2]}, {edgeHalf+center[0], -edgeHalf+center[1], edgeHalf+center[2]}},
			//y
			{{-edgeHalf+center[0], edgeHalf+center[1], -edgeHalf+center[2]}, {-edgeHalf+center[0], edgeHalf+center[1], edgeHalf+center[2]}, {edgeHalf+center[0], edgeHalf+center[1], edgeHalf+center[2]}},
			{{-edgeHalf+center[0], edgeHalf+center[1], -edgeHalf+center[2]}, {edgeHalf+center[0], edgeHalf+center[1], edgeHalf+center[2]}, {edgeHalf+center[0], edgeHalf+center[1], -edgeHalf+center[2]}},
			
			//-x
			{{-edgeHalf+center[0], -edgeHalf+center[1], edgeHalf+center[2]}, {-edgeHalf+center[0], edgeHalf+center[1], edgeHalf+center[2]}, {-edgeHalf+center[0], edgeHalf+center[1], -edgeHalf+center[2]}},
			{{-edgeHalf+center[0], -edgeHalf+center[1], edgeHalf+center[2]}, {-edgeHalf+center[0], edgeHalf+center[1], -edgeHalf+center[2]}, {-edgeHalf+center[0], -edgeHalf+center[1], -edgeHalf+center[2]}},
			//x
			{{edgeHalf+center[0], -edgeHalf+center[1], -edgeHalf+center[2]}, {edgeHalf+center[0], edgeHalf+center[1], -edgeHalf+center[2]}, {edgeHalf+center[0], edgeHalf+center[1], edgeHalf+center[2]}},
			{{edgeHalf+center[0], -edgeHalf+center[1], -edgeHalf+center[2]}, {edgeHalf+center[0], edgeHalf+center[1], edgeHalf+center[2]}, {edgeHalf+center[0], -edgeHalf+center[1], edgeHalf+center[2]}}
		};
	}
		
	public static double[][][] readFromFileTriangles(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			ArrayList<double[]> vertices = new ArrayList<double[]>();
			ArrayList<double[]> normals = new ArrayList<double[]>();
			ArrayList<double[][]> triangles = new ArrayList<double[][]>();
			
			String[] string_array_cache_length_4 = new String[4];
			String[] string_array_cache_length_3 = new String[3];			
			double[][] triangle;
			String line;
			while((line = reader.readLine()) != null) {
				string_array_cache_length_4 = line.split(" ");
//				System.out.println("s");
				if(line.startsWith("v ")) {				//vertices
					vertices.add(new double[] {
							Double.parseDouble(string_array_cache_length_4[1]),
							Double.parseDouble(string_array_cache_length_4[2]),
							Double.parseDouble(string_array_cache_length_4[3])
					});
				} else if(line.startsWith("vn ")) {		//normals
					normals.add(new double[] {
							Double.parseDouble(string_array_cache_length_4[1]),
							Double.parseDouble(string_array_cache_length_4[2]),
							Double.parseDouble(string_array_cache_length_4[3])
					});
				} else if(line.startsWith("f ")) {		//faces
					triangle = new double[5][];
					for(int i = 0; i < 3; i++) {
						//read id of vertex and get it from the vertices ArrayList
						string_array_cache_length_3 = string_array_cache_length_4[i+1].split("/");
						triangle[i] = Calculator.copy(vertices.get(-1+Integer.parseInt(string_array_cache_length_3[0])));
					}
					//extract normal id from tripple (last split) .../.../... String
					if(string_array_cache_length_3.length >= 3) {
						triangle[3] = Calculator.copy(normals.get(-1+Integer.parseInt(string_array_cache_length_3[2])));
					}
					triangles.add(triangle);
				}
			}
			
			reader.close();
			
			return triangles.toArray(new double[0][][]);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new double[0][][];
	}
	
	public static GameObject readFromFileGameObject(File file) {
		return new GameObject(readFromFileTriangles(file));
	}
	
	public static GameObject randomizeColor(GameObject gameObject) {
		randomizeColor(gameObject.getTriangles());
		return gameObject;
	}
	
	public static double[][][] randomizeColor(double[][][] triangles) {
		Engine engine = Engine.getInstance();
		int randomInt;
		for(int t = 0; t < triangles.length; t++) {
			if(triangles[t].length < 5)
				triangles[t] = Calculator.copyTriangle(triangles[t]);
			
			randomInt = (int)(Math.random()*256);
			if(triangles[t][4] == null)
				triangles[t][4] = new double[1];
//			triangles[t][4][0] = engine.registerColor(new Color(randomInt, randomInt, randomInt));
			triangles[t][4][0] = engine.registerColor(new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256)));
		}
		return triangles;
	}
	
	public static double[] calcNormal(double[][] triangle) {
		return Calculator.crossProduct(
				triangle[1][0] - triangle[0][0],
				triangle[1][1] - triangle[0][1],
				triangle[1][2] - triangle[0][2],
				triangle[2][0] - triangle[0][0],
				triangle[2][1] - triangle[0][1],
				triangle[2][2] - triangle[0][2]
		);
	}
	
	public static double[][][] calcNormals(double[][][] triangles) {
		for(int t = 0; t < triangles.length; t++) {
			if(triangles[t].length > 3 && triangles[t][3] != null && triangles[t][3].length > 0)
				continue;
			
			if(triangles[t].length < 4)
				triangles[t] = Calculator.copyTriangle(triangles[t]);
			
			triangles[t][3] = Calculator.normalize(Calculator.crossProduct(
					triangles[t][1][0] - triangles[t][0][0],
					triangles[t][1][1] - triangles[t][0][1],
					triangles[t][1][2] - triangles[t][0][2],
					triangles[t][2][0] - triangles[t][0][0],
					triangles[t][2][1] - triangles[t][0][1],
					triangles[t][2][2] - triangles[t][0][2]
			), true);
		}
//		System.out.println(Arrays.toString(triangles[0][3]));
//		System.out.println(Arrays.toString(triangles[1][3]));
//		System.out.println("----");
		return triangles;
	}
	
	public static double[][][] displayLightDirectional(double[][][] triangles, double[] lightRayDirection) {
		calcNormals(triangles);		
		Engine engine = Engine.getInstance();
		
		int color;
		for(int t = 0; t < triangles.length; t++) {
			color = (int) ((-Calculator.dotProduct(triangles[t][3], lightRayDirection)+1) * 127.5);
			if(triangles[t][4] == null)
				triangles[t][4] = new double[3];
//			System.out.println(color);
			triangles[t][4][0] = engine.registerColor(new Color(color, color, color));
		}
		return triangles;
	}
	
	public static double[][][] displayLightPoint(double[][][] triangles, double[] lightSourcePoint) {
		calcNormals(triangles);		
		Engine engine = Engine.getInstance();
		
		int color;
		double[] lightRayDirection;
		for(int t = 0; t < triangles.length; t++) {
			lightRayDirection = Calculator.normalize(Calculator.subtract(triangles[t][0], lightSourcePoint), true);
			
			// {[(scalar)-1]/-2}*255
			//={[1-scalar]/2}*255
			//={1-scalar]*127.5
			color = (int) ((1-Calculator.dotProduct(triangles[t][3], lightRayDirection)) * 127.5);
			
			//using distance from light:
			color = (int) Math.min(color, color/(Calculator.vectorLength(Calculator.subtract(triangles[t][0], lightSourcePoint))*0.1));
			
//			color = (int) (Math.sin((-Calculator.dotProduct(triangles[t][3], lightRayDirection))/2*Math.PI/2) *256);
//			if(t == 1)
//				System.out.println(color + "; " + Calculator.dotProduct(triangles[t][3], lightRayDirection));
			if(triangles[t][4] == null)
				triangles[t][4] = new double[3];
//			System.out.println(color);
			triangles[t][4][0] = engine.registerColor(new Color(color, 0, color));
		}
		return triangles;
	}
	
	/**
	 * splits any given triangle into 4 equally sized sub-triangles. These are fully enclosed by the master triangle.
	 * @param cloneAllReferences States if references to verticies and other points should be cloned, to prevent multiple array entries pointing to the same object(point-vector/array) on memory
	 */
	public static double[][][] splitTriangle(double[][] triangle, boolean cloneAllReferences) {
		double[] center12 = Calculator.getCenterPoint(triangle[0], triangle[1]);
		double[] center23 = Calculator.getCenterPoint(triangle[1], triangle[2]);
		double[] center31 = Calculator.getCenterPoint(triangle[2], triangle[0]);
		
		if(cloneAllReferences) {
			return new double[][][] {
				{copy(triangle[0]), copy(center12), copy(center31)},
				{copy(center12), copy(triangle[1]), copy(center23)},
				{copy(triangle[2]), copy(center31), copy(center23)},
				{center31, center12, center23}
			};
		} else {
			return new double[][][] {
				{triangle[0], center12, center31},
				{center12, triangle[1], center23},
				{triangle[2], center31, center23},
				{center31, center12, center23}
			};
		}
	}
}
