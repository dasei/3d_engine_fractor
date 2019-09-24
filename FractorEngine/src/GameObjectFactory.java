import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

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
		
	public static double[][][] readFromFile(File file) {
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
			triangles[t][4][0] = engine.registerColor(new Color(color, color, color));
		}
		return triangles;
	}
}
