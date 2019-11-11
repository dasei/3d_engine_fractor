package net.maths;
import java.util.Arrays;

import net.Engine;
import net.buffering.Framebuffer;
import net.buffering.Texture;
import net.gameobjects.GameObject;

public class Calculator {
	
	public static int STAT_TRIANGLES_AMOUNT;
	public static int STAT_TRIANGLES_VISIBLE;
	public static int STAT_TRIANGLES_CLIPPED;
	public static int STAT_TRIANGLES_SKIPPED_Z;
	public static boolean OPTION_ROTATE_AROUND_X1_AXIS = false;// private static double OPTION_ROTATE_AROUND_X1_AXIS_currentRotation = 0;
	public static boolean OPTION_ROTATE_AROUND_X2_AXIS = false;// private static double OPTION_ROTATE_AROUND_X2_AXIS_currentRotation = 0;
	public static boolean OPTION_ROTATE_AROUND_X3_AXIS = false;// private static double OPTION_ROTATE_AROUND_X3_AXIS_currentRotation = 0;
	
	public static final double CLIPPING_Z_MINIMUM = 0.1;
	
	public void render(GameObject[] gameObjects) {
		if(gameObjects == null)
			return;
//		STAT_TRIANGLES_AMOUNT = triangles.length;//TODO
		
		Engine engine = Engine.getInstance();
		double resolutionWidthHalf = engine.resolution.width/2d;		
		Framebuffer framebuffer = engine.getFramebufferManager().getFramebufferToRender();
		
//		double yWave = Math.sin(System.currentTimeMillis()/1000d)*100;
//		rasterizeTriangleBottomSided(framebuffer, 246, 247, 425, 573, 61, 573);
//		rasterizeTriangleBottomSided(framebuffer, 246, 247 + (int) yWave, 425, 500 + (int) yWave, 61, 500 + (int) yWave);
//		rasterizeTriangleTopSided(framebuffer, 50, 10 + (int) yWave, 450, 10 + (int) yWave, 250, 300 + (int) yWave);
		
		
//		GameObjectFactory.displayLightDirectional(triangles, new double[] {0,0,1});
//		GameObjectFactory.displayLightDirectional(triangles, new double[] {Math.sin(System.currentTimeMillis()/1000d), 0, Math.cos(System.currentTimeMillis()/1000d)});
		
//		GameObjectFactory.displayLightPoint(triangles, new double[] {Math.sin(System.currentTimeMillis()/1000d)*17.5, 0, Math.cos(System.currentTimeMillis()/1000d)*17.5*10});
//		GameObjectFactory.displayLightPoint(triangles, new double[] {0, 0, Math.cos(System.currentTimeMillis()/1000d)*100});
//		GameObjectFactory.displayLightPoint(triangles, engine.cameraPosition);
		
		
		//LICHT DER LAMPE
//		GameObjectFactory.displayLightPoint(triangles, new double[] {-4.908, 17.36, 0});
//		if(Math.random()<0.01)
//			GameObjectFactory.randomizeColor(triangles);
		
		
//		rasterizeTriangleBottomSided(framebuffer, Texture.textureDEBUG, 
//				new double[] {0.5,0,1,1,0,1},
//				130, 50, 20, 150, 150, 1, 50, 150, 1
//		);
//		
//		if(1==1)
//			return;
		
		
		
		
		
		int stat_triangles_visible = 0;
		int stat_triangles_clipped = 0;
		int stat_triangles_skipped_z = 0;
		
		// RENDERING PROCEDURE
//		double[][] triangleClipped;
		double[][][] triangles;
		double[][] triangle;
		for(int g = 0; g < gameObjects.length; g++) {
			triangles = gameObjects[g].getTrianglesAbsolute();
			
//			if(Math.random()<0.01)
//				GameObjectFactory.randomizeColor(triangles);
			
			for(int t = 0; t < triangles.length; t++) {
				if(triangles[t] == null)
					continue;
				triangle = copyTriangle(triangles[t]);
				
				GameObjectFactory.displayLightPoint(new double[][][] {triangle}, new double[] {-4.908, 17.36, 0});
	//			double rot = 0.6266468;
	//			rotateVectorX2(triangle[0], rot);
	//			rotateVectorX2(triangle[1], rot);
	//			rotateVectorX2(triangle[2], rot);
				//rotate cube
				if(OPTION_ROTATE_AROUND_X1_AXIS) {
					double rot1 = (System.currentTimeMillis()/1000d)*0.58434534;
					rotateVectorX1(triangle[0], rot1);
					rotateVectorX1(triangle[1], rot1);
					rotateVectorX1(triangle[2], rot1);
				}			
				if(OPTION_ROTATE_AROUND_X2_AXIS) {
					double rot2 = (System.currentTimeMillis()/1000d*0.99);			
					rotateVectorX2(triangle[0], rot2);
					rotateVectorX2(triangle[1], rot2);
					rotateVectorX2(triangle[2], rot2);
				}
				if(OPTION_ROTATE_AROUND_X3_AXIS) {
					double rot3 = System.currentTimeMillis()/1000d;
					rotateVectorX3(triangle[0], rot3);
					rotateVectorX3(triangle[1], rot3);
					rotateVectorX3(triangle[2], rot3);
				}
				
				
				
				
				
				
				
				
				
				//translate cube
	//			transformTriangle3D(triangle, new double[] {0, 0, 5});
	//			transformTriangle3D(triangle, new double[] {50, 50, 0});
				
	//			transformTriangle3D(triangle, new double[] {0, Math.sin(System.currentTimeMillis()/1000d), 0});
				
				
				
				
				
				// Normale Culling
				// AB x AC
				double[] normalVec = crossProduct(
						triangle[1][0] - triangle[0][0],
						triangle[1][1] - triangle[0][1],
						triangle[1][2] - triangle[0][2],
						triangle[2][0] - triangle[0][0],
						triangle[2][1] - triangle[0][1],
						triangle[2][2] - triangle[0][2]
				);
				double[] vecTriangleToCamera = normalize(subtract(engine.cameraPosition, triangle[0]), true);
				// normalVec <scalar> Triangle->Camera <= 0 ? unsichtbar
				if(dotProduct(normalVec, vecTriangleToCamera) <= 0) {			
	//				trianglesRendered[t] = null;
					continue;
				}
				
				//camera and GameObject-position translate (position)			
				triangle[0] = subtract(triangle[0], engine.cameraPosition);
				triangle[1] = subtract(triangle[1], engine.cameraPosition);
				triangle[2] = subtract(triangle[2], engine.cameraPosition);
				
				//camera rotation
				double camHorizontal = engine.cameraRotationHorizontal;
				rotateVectorX2(triangle[0], camHorizontal);
				rotateVectorX2(triangle[1], camHorizontal);
				rotateVectorX2(triangle[2], camHorizontal);
				double camVertical = engine.cameraRotationVertical;
				rotateVectorX1(triangle[0], camVertical);
				rotateVectorX1(triangle[1], camVertical);
				rotateVectorX1(triangle[2], camVertical);
				
				
				stat_triangles_visible++;
				
				
				
				//CLIPPING クリッピング
				double[][][] trianglesClipped = clipTriangle(triangle);
				if(trianglesClipped == null) {
					stat_triangles_skipped_z++;
					continue;
				}
//				System.out.println(toString(trianglesClipped[0]));
				
				if(trianglesClipped[0] != triangle)
					stat_triangles_clipped++;
				
				double[][] triangleCurrent;
				for(int trianglesClippedI = 0; trianglesClippedI < trianglesClipped.length; trianglesClippedI++) {								
					triangleCurrent = trianglesClipped[trianglesClippedI];
					
					// PROJECTION
					// 3D => 2D
					for(int v = 0; v < 3; v++) {
//						if(triangleCurrent[v][2] <= 0)
//							continue;				
						triangleCurrent[v][0] /= triangleCurrent[v][2];
						triangleCurrent[v][1] /= triangleCurrent[v][2];
						
		//				triangle[v][2] = (triangle[v][2]*(20/(20-0.1))) + (triangle[v][2]*((-20*0.1)/(20-0.1))); 
						//TODO add something like x *= screen size ratio => to stop distortion aspect ratio
		//				triangle[v][0] *= engine.resolution.width/(double)engine.resolution.height;
					}
					
					
					//triangle auf framebuffer übertragen
					// 	 ((1+ triangle[...][...])/2 * resolution.width
					//=> ((1+ triangle[...][...]) * (resolution.width/2)
					//=> ((1+ triangle[...][...]) * (resolutionWidthHalf)
					
		//			rasterizeTriangle(framebuffer, (int)triangle[4][0],
		//					(int) ((1+triangle[0][0])*resolutionWidthHalf),
		//					(int) ((1-triangle[0][1])*resolutionWidthHalf),
		//					(int) ((1+triangle[1][0])*resolutionWidthHalf),
		//					(int) ((1-triangle[1][1])*resolutionWidthHalf),
		//					(int) ((1+triangle[2][0])*resolutionWidthHalf),
		//					(int) ((1-triangle[2][1])*resolutionWidthHalf)
		//			);
					
					
					//TODO ADD THIS AGAIN!!!!!!!!!!!!!!!
					rasterizeTriangle(framebuffer, (int)triangle[4][0],
							(int) ((1+triangleCurrent[0][0])*resolutionWidthHalf),
							(int) ((1-triangleCurrent[0][1])*resolutionWidthHalf),
							triangleCurrent[0][2],
							(int) ((1+triangleCurrent[1][0])*resolutionWidthHalf),
							(int) ((1-triangleCurrent[1][1])*resolutionWidthHalf),
							triangleCurrent[1][2],
							(int) ((1+triangleCurrent[2][0])*resolutionWidthHalf),
							(int) ((1-triangleCurrent[2][1])*resolutionWidthHalf),
							triangleCurrent[2][2]
					);
					
//					rasterizeTriangle(framebuffer, Texture.textureDEBUG, triangleCurrent[5],
//							(int) ((1+triangleCurrent[0][0])*resolutionWidthHalf),
//							(int) ((1-triangleCurrent[0][1])*resolutionWidthHalf),
//							triangleCurrent[0][2],
//							(int) ((1+triangleCurrent[1][0])*resolutionWidthHalf),
//							(int) ((1-triangleCurrent[1][1])*resolutionWidthHalf),
//							triangleCurrent[1][2],
//							(int) ((1+triangleCurrent[2][0])*resolutionWidthHalf),
//							(int) ((1-triangleCurrent[2][1])*resolutionWidthHalf),
//							triangleCurrent[2][2]
//					);
					
					
					
					
					
					if(engine.DRAW_WIREFRAME) {
						drawTriangle(framebuffer, engine.WIREFRAME_COLOR_ID,
								(int) ((1+triangleCurrent[0][0])*resolutionWidthHalf),
								(int) ((1-triangleCurrent[0][1])*resolutionWidthHalf),
								(int) ((1+triangleCurrent[1][0])*resolutionWidthHalf),
								(int) ((1-triangleCurrent[1][1])*resolutionWidthHalf),
								(int) ((1+triangleCurrent[2][0])*resolutionWidthHalf),
								(int) ((1-triangleCurrent[2][1])*resolutionWidthHalf)
						);
					}
					
				}
				
			}
		}
		
		STAT_TRIANGLES_VISIBLE = stat_triangles_visible;
		STAT_TRIANGLES_CLIPPED = stat_triangles_clipped;
		STAT_TRIANGLES_SKIPPED_Z = stat_triangles_skipped_z;
	}
	
	/**
	 * given coordinates must be in buffer-space / buffer ready(mirrored y values)
	 */
	private void rasterizeTriangle(Framebuffer framebuffer, int colorIndex, int x1, int y1, int x2, int y2, int x3, int y3) {
//		System.out.println("rasterizing tri: " + x1 + "," + y1 + "; " + x2 + "," + y2 + "; " + x3 + "," + y3);
		
//		if(x1==x2 && x2==x3 && y1==y2 && y2==y3) {
//			System.out.println("jap");
//			framebuffer.buffer[(y1 * framebuffer.width) + x1] = 2;
//			return;
//		}
		//まず、もうTOPかBOTTOMの三角か確認して
		// NOTE: as Y-values are buffer-ready, the following statements are a bit mind boggingly nerve wracking
		//    *: Y values get bigger, the more 'down' the point goes (=> buffer-matrix)
		if(y1 == y2) {
			if(y1 < y3) { // y1 and y2 are 'visually' above y3 (when looked at in the buffer)
				rasterizeTriangleTopSided(framebuffer, colorIndex, x1, y1, x2, y2, x3, y3);
			} else {
				rasterizeTriangleBottomSided(framebuffer, colorIndex, x3, y3, x1, y1, x2, y2);
			}
		} else if(y2 == y3) {
			if(y2 < y1) {
				rasterizeTriangleTopSided(framebuffer, colorIndex, x2, y2, x3, y3, x1, y1);
			} else {
				rasterizeTriangleBottomSided(framebuffer, colorIndex, x1, y1, x2, y2, x3, y3);
			}
		} else if(y3 == y1) {
			if(y3 < y2) {
				rasterizeTriangleTopSided(framebuffer, colorIndex, x3, y3, x1, y1, x2, y2);
			} else {
				rasterizeTriangleBottomSided(framebuffer, colorIndex, x2, y2, x3, y3, x1, y1);
			}
		} else {
//			System.out.println("ws");
			//triangle does not yet have a side thats horizontal => splitting triangle in two
			//1. "sort" triangle, so that y1Sort is the point on top, the other two follow in clockwise order
			int y1Sort, y2Sort, y3Sort;
			int x1Sort, x2Sort, x3Sort;
			if(y1 < y2 && y1 < y3) {
				x1Sort = x1;	y1Sort = y1;
				x2Sort = x2;	y2Sort = y2;
				x3Sort = x3;	y3Sort = y3;
			}else if(y2 < y1 && y2 < y3) {
				x1Sort = x2;	y1Sort = y2;
				x2Sort = x3;	y2Sort = y3;
				x3Sort = x1;	y3Sort = y1;
			}else{
				x1Sort = x3;	y1Sort = y3;
				x2Sort = x1;	y2Sort = y1;
				x3Sort = x2;	y3Sort = y2;
			}
			
//			System.out.println("resorting tri: " + x1Sort + "," + y1Sort + "; " + x2Sort + "," + y2Sort + "; " + x3Sort + "," + y3Sort);
			
			//2. check if the middle point (y-axis) is on the left or right side
			//3. get x coordinate of the new point that will be created to split the triangle
			// => do that by interpolating the other two points( on top and bottom)s X koordinate with the middle points y coodinate
			int pointNewX;
			if(y3Sort > y2Sort) { //middle=right
				pointNewX = x1Sort + (int)( (x3Sort-x1Sort)* ((y2Sort-y1Sort)/(double)(y3Sort-y1Sort)) );
//				pointNewY = y2Sort;
				rasterizeTriangleBottomSided(framebuffer, colorIndex, x1Sort, y1Sort, x2Sort, y2Sort, 
						pointNewX,
						y2Sort
				);
				rasterizeTriangleTopSided(framebuffer,colorIndex, 
						pointNewX,
						y2Sort,
				x2Sort, y2Sort, x3Sort, y3Sort);
			} else { //middle=left
				pointNewX = x1Sort + (int)( (x2Sort-x1Sort)* ((y3Sort-y1Sort)/(double)(y2Sort-y1Sort)) );
//				pointNewY = y3Sort;
				rasterizeTriangleBottomSided(framebuffer, colorIndex, x1Sort, y1Sort,
						pointNewX,
						y3Sort,
				x3Sort, y3Sort);
				rasterizeTriangleTopSided(framebuffer, colorIndex, x3Sort, y3Sort,
						pointNewX,
						y3Sort,
				x2Sort, y2Sort);
			}
			
//			System.out.println("PointNew: " + pointNewX + ", " + pointNewY);
			
		}
	}
	
	/**
	 * given coordinates must be in buffer-space / buffer ready(mirrored y values)
	 */
	private void rasterizeTriangle(Framebuffer framebuffer, int colorIndex, int x1, int y1, double z1, int x2, int y2, double z2, int x3, int y3, double z3) {
//		System.out.println("rasterizing tri: " + x1 + "," + y1 + "; " + x2 + "," + y2 + "; " + x3 + "," + y3);
		
//		if(x1==x2 && x2==x3 && y1==y2 && y2==y3) {
//			System.out.println("jap");
//			framebuffer.buffer[(y1 * framebuffer.width) + x1] = 2;
//			return;
//		}
		
		//まず、もうTOPかBOTTOMの三角か確認して
		// NOTE: as Y-values are buffer-ready, the following statements are a bit mind boggingly nerve wracking
		//    *: Y values get bigger, the more 'down' the point goes (=> buffer-matrix)
		if(y1 == y2) {
			if(y1 < y3) { // y1 and y2 are 'visually' above y3 (when looked at in the buffer)
				rasterizeTriangleTopSided(framebuffer, colorIndex, x1, y1, z1, x2, y2, z2, x3, y3, z3);
			} else {
				rasterizeTriangleBottomSided(framebuffer, colorIndex, x3, y3, z3, x1, y1, z1, x2, y2, z2);
			}
		} else if(y2 == y3) {
			if(y2 < y1) {
				rasterizeTriangleTopSided(framebuffer, colorIndex, x2, y2, z2, x3, y3, z3, x1, y1, z1);
			} else {
				rasterizeTriangleBottomSided(framebuffer, colorIndex, x1, y1, z1, x2, y2, z2, x3, y3, z3);
			}
		} else if(y3 == y1) {
			if(y3 < y2) {
				rasterizeTriangleTopSided(framebuffer, colorIndex, x3, y3, z3, x1, y1, z1, x2, y2, z2);
			} else {
				rasterizeTriangleBottomSided(framebuffer, colorIndex, x2, y2, z2, x3, y3, z3, x1, y1, z1);
			}
		} else {
//			System.out.println("ws");
			//triangle does not yet have a side thats horizontal => splitting triangle in two
			//1. "sort" triangle, so that y1Sort is the point on top, the other two follow in clockwise order
			int x1Sort, x2Sort, x3Sort;
			int y1Sort, y2Sort, y3Sort;
			double z1Sort, z2Sort, z3Sort;
			if(y1 < y2 && y1 < y3) {
				x1Sort = x1;	y1Sort = y1;	z1Sort = z1;
				x2Sort = x2;	y2Sort = y2;	z2Sort = z2;
				x3Sort = x3;	y3Sort = y3;	z3Sort = z3;
			}else if(y2 < y1 && y2 < y3) {
				x1Sort = x2;	y1Sort = y2;	z1Sort = z2;
				x2Sort = x3;	y2Sort = y3;	z2Sort = z3;
				x3Sort = x1;	y3Sort = y1;	z3Sort = z1;
			}else{
				x1Sort = x3;	y1Sort = y3;	z1Sort = z3;
				x2Sort = x1;	y2Sort = y1;	z2Sort = z1;
				x3Sort = x2;	y3Sort = y2;	z3Sort = z2;
			}
			
			
//			System.out.println("resorting tri: " + x1Sort + "," + y1Sort + "; " + x2Sort + "," + y2Sort + "; " + x3Sort + "," + y3Sort);
			
			//2. check if the middle point (y-axis) is on the left or right side
			//3. get x coordinate of the new point that will be created to split the triangle
			// => do that by interpolating the other two points( on top and bottom)s X koordinate with the middle points y coodinate
			int pointNewX;
			double pointNewZ;
			if(y3Sort > y2Sort) { //middle=right
				pointNewX = x1Sort + (int)( (x3Sort-x1Sort)* ((y2Sort-y1Sort)/(double)(y3Sort-y1Sort)) );
				pointNewZ = z1Sort + ( (y2Sort-y1Sort) * ((z3Sort-z1Sort) / (y3Sort - y1Sort))); //point lies on line between Point1 and Point3
//				System.out.println(pointNewZ);
//				pointNewY = y2Sort;
				rasterizeTriangleBottomSided(framebuffer, colorIndex, x1Sort, y1Sort, z1Sort, x2Sort, y2Sort, z2Sort, 
						pointNewX,
						y2Sort,
						pointNewZ
				);
				rasterizeTriangleTopSided(framebuffer,colorIndex, 
						pointNewX,
						y2Sort,
						pointNewZ,
				x2Sort, y2Sort, z2Sort, x3Sort, y3Sort, z3Sort);
			} else { //middle=left
				pointNewX = x1Sort + (int)( (x2Sort-x1Sort)* ((y3Sort-y1Sort)/(double)(y2Sort-y1Sort)) );
				pointNewZ = z1Sort + ( (y3Sort-y1Sort) * ((z2Sort-z1Sort) / (y2Sort - y1Sort))); //point lies on line between Point1 and Point2
//				System.out.println(pointNewZ);
//				pointNewY = y3Sort;
				rasterizeTriangleBottomSided(framebuffer, colorIndex, x1Sort, y1Sort, z1Sort,
						pointNewX,
						y3Sort,
						pointNewZ,
				x3Sort, y3Sort, z3Sort);
				rasterizeTriangleTopSided(framebuffer, colorIndex, x3Sort, y3Sort, z3Sort,
						pointNewX,
						y3Sort,
						pointNewZ,
				x2Sort, y2Sort, z2Sort);
			}
			
//			System.out.println("PointNew: " + pointNewX + ", " + pointNewY);
			
		}
	}
	
	/**
	 * given coordinates must be in buffer-space / buffer ready(mirrored y values)
	 */
	private void rasterizeTriangle(Framebuffer framebuffer, Texture texture, double[] textureCoordinates, int x1, int y1, double z1, int x2, int y2, double z2, int x3, int y3, double z3) {
//		System.out.println("rasterizing tri: " + x1 + "," + y1 + "; " + x2 + "," + y2 + "; " + x3 + "," + y3);
		
		if(x1==x2 && x2==x3 && y1==y2 && y2==y3) {
			framebuffer.buffer[(y1 * framebuffer.width) + x1] = texture.getTextureMatrix()[0];
			return;
		}
		
		//まず、もうTOPかBOTTOMの三角か確認して
		// NOTE: as Y-values are buffer-ready, the following statements are a bit mind boggingly nerve wracking
		//    *: Y values get bigger, the more 'down' the point goes (=> buffer-matrix)
		if(y1 == y2) {
			if(y1 < y3) { // y1 and y2 are 'visually' above y3 (when looked at in the buffer)
				rasterizeTriangleTopSided(framebuffer, texture, x1, y1, z1, textureCoordinates[0], textureCoordinates[1], x2, y2, z2, textureCoordinates[2], textureCoordinates[3], x3, y3, z3, textureCoordinates[4], textureCoordinates[5]);
			} else {
				rasterizeTriangleBottomSided(framebuffer, texture, x3, y3, z3, textureCoordinates[4], textureCoordinates[5], x1, y1, z1, textureCoordinates[0], textureCoordinates[1], x2, y2, z2, textureCoordinates[2], textureCoordinates[3]);
			}
		} else if(y2 == y3) {
			if(y2 < y1) {
				rasterizeTriangleTopSided(framebuffer, texture, x2, y2, z2, textureCoordinates[2], textureCoordinates[3], x3, y3, z3, textureCoordinates[4], textureCoordinates[5], x1, y1, z1, textureCoordinates[0], textureCoordinates[1]);
			} else {
				rasterizeTriangleBottomSided(framebuffer, texture, x1, y1, z1, textureCoordinates[0], textureCoordinates[1], x2, y2, z2, textureCoordinates[2], textureCoordinates[3], x3, y3, z3, textureCoordinates[4], textureCoordinates[5]);
			}
		} else if(y3 == y1) {
			if(y3 < y2) {
				rasterizeTriangleTopSided(framebuffer, texture, x3, y3, z3, textureCoordinates[4], textureCoordinates[5], x1, y1, z1, textureCoordinates[0], textureCoordinates[1], x2, y2, z2, textureCoordinates[2], textureCoordinates[3]);
			} else {
				rasterizeTriangleBottomSided(framebuffer, texture, x2, y2, z2, textureCoordinates[2], textureCoordinates[3], x3, y3, z3, textureCoordinates[4], textureCoordinates[5], x1, y1, z1, textureCoordinates[0], textureCoordinates[1]);
			}
		} else {
//			System.out.println("ws");
			//triangle does not yet have a side thats horizontal => splitting triangle in two
			//1. "sort" triangle, so that y1Sort is the point on top, the other two follow in clockwise order
			int x1Sort, x2Sort, x3Sort;
			int y1Sort, y2Sort, y3Sort;
			double z1Sort, z2Sort, z3Sort;
			
			double tex1xSort; double tex1ySort;
			double tex2xSort; double tex2ySort;
			double tex3xSort; double tex3ySort;
			if(y1 < y2 && y1 < y3) {
				x1Sort = x1;	y1Sort = y1;	z1Sort = z1;
				x2Sort = x2;	y2Sort = y2;	z2Sort = z2;
				x3Sort = x3;	y3Sort = y3;	z3Sort = z3;
				tex1xSort = textureCoordinates[0]; tex1ySort = textureCoordinates[1];
				tex2xSort = textureCoordinates[2]; tex2ySort = textureCoordinates[3];
				tex3xSort = textureCoordinates[4]; tex3ySort = textureCoordinates[5];
			}else if(y2 < y1 && y2 < y3) {
				x1Sort = x2;	y1Sort = y2;	z1Sort = z2;
				x2Sort = x3;	y2Sort = y3;	z2Sort = z3;
				x3Sort = x1;	y3Sort = y1;	z3Sort = z1;
				tex1xSort = textureCoordinates[2]; tex1ySort = textureCoordinates[3];
				tex2xSort = textureCoordinates[4]; tex2ySort = textureCoordinates[5];
				tex3xSort = textureCoordinates[0]; tex3ySort = textureCoordinates[1];
			}else{
				x1Sort = x3;	y1Sort = y3;	z1Sort = z3;
				x2Sort = x1;	y2Sort = y1;	z2Sort = z1;
				x3Sort = x2;	y3Sort = y2;	z3Sort = z2;
				tex1xSort = textureCoordinates[4]; tex1ySort = textureCoordinates[5];
				tex2xSort = textureCoordinates[0]; tex2ySort = textureCoordinates[1];
				tex3xSort = textureCoordinates[2]; tex3ySort = textureCoordinates[3];
			}
			
			
//			System.out.println("resorting tri: " + x1Sort + "," + y1Sort + "; " + x2Sort + "," + y2Sort + "; " + x3Sort + "," + y3Sort);
			
			//2. check if the middle point (y-axis) is on the left or right side
			//3. get x coordinate of the new point that will be created to split the triangle
			// => do that by interpolating the other two points( on top and bottom)s X koordinate with the middle points y coodinate
			int pointNewX;
			double pointNewZ;
			double triangleNewInterpolationFactor;
			double triangleNewX, triangleNewY;
			if(y3Sort > y2Sort) { //middle=right
				pointNewX = x1Sort + (int)( (x3Sort-x1Sort)* ((y2Sort-y1Sort)/(double)(y3Sort-y1Sort)) );
				pointNewZ = z1Sort + ( (y2Sort-y1Sort) * ((z3Sort-z1Sort) / (y3Sort - y1Sort))); //point lies on line between Point1 and Point3
				
				triangleNewInterpolationFactor = (y2Sort-y1Sort)/(double)(y3Sort-y1Sort);
				triangleNewX = tex1xSort + ((tex3xSort-tex1xSort)*(triangleNewInterpolationFactor));
				triangleNewY = tex1ySort + ((tex3ySort-tex1ySort)*(triangleNewInterpolationFactor));
//				System.out.println(pointNewZ);
//				pointNewY = y2Sort;
				rasterizeTriangleBottomSided(framebuffer, texture, x1Sort, y1Sort, z1Sort, tex1xSort, tex1ySort, x2Sort, y2Sort, z2Sort, tex2xSort, tex2ySort, 
						pointNewX,
						y2Sort,
						pointNewZ,
						triangleNewX, triangleNewY
				);
				rasterizeTriangleTopSided(framebuffer,texture, 
						pointNewX,
						y2Sort,
						pointNewZ,
						triangleNewX, triangleNewY,
				x2Sort, y2Sort, z2Sort, tex2xSort, tex2ySort, x3Sort, y3Sort, z3Sort, tex3xSort, tex3ySort);
			} else { //middle=left
				pointNewX = x1Sort + (int)( (x2Sort-x1Sort)* ((y3Sort-y1Sort)/(double)(y2Sort-y1Sort)) );
				pointNewZ = z1Sort + ( (y3Sort-y1Sort) * ((z2Sort-z1Sort) / (y2Sort - y1Sort))); //point lies on line between Point1 and Point2
				
				triangleNewInterpolationFactor = (y3Sort-y1Sort)/(double)(y2Sort-y1Sort);
				triangleNewX = tex1xSort + ((tex2xSort-tex1xSort)*(triangleNewInterpolationFactor));
				triangleNewY = tex1ySort + ((tex2ySort-tex1ySort)*(triangleNewInterpolationFactor));
//				System.out.println(pointNewZ);
//				pointNewY = y3Sort;
				rasterizeTriangleBottomSided(framebuffer, texture, x1Sort, y1Sort, z1Sort, tex1xSort, tex1ySort,
						pointNewX,
						y3Sort,
						pointNewZ,
						triangleNewX, triangleNewY,
				x3Sort, y3Sort, z3Sort, tex3xSort, tex3ySort);
				rasterizeTriangleTopSided(framebuffer, texture, x3Sort, y3Sort, z3Sort, tex3xSort, tex3ySort,
						pointNewX,
						y3Sort,
						pointNewZ,
						triangleNewX, triangleNewY,
				x2Sort, y2Sort, z2Sort, tex2xSort, tex2ySort);
			}
			
//			System.out.println("PointNew: " + pointNewX + ", " + pointNewY);
			
		}
	}
	
	/** 
	 * NOTE: all coordinates are in buffer-scope => Y values get bigger, the more 'downwards' one goes
	 * @param x1 top Point x
	 * @param y1 top Point y
	 */
	private void rasterizeTriangleBottomSided(Framebuffer framebuffer, int colorIndex, int x1, int y1, int x2, int y2, int x3, int y3) {
//		System.out.println("rasterizing BOTTOMsided tri: " + x1 + "," + y1 + "; " + x2 + "," + y2 + "; " + x3 + "," + y3);
		
		int[] framebufferBuffer = framebuffer.buffer;
		int framebufferWidth = framebuffer.width;
		
//		if(y1 >= 0 && y1 < framebufferWidth && x1 >= 0 && x1 < framebuffer.height)
//			framebufferBuffer[y1 * framebufferWidth + x1] = 1;
//		if(y2 >= 0 && y2 < framebufferWidth && x2 >= 0 && x2 < framebuffer.height)
//			framebufferBuffer[y2 * framebufferWidth + x2] = 1;
//		if(y3 >= 0 && y3 < framebufferWidth && x3 >= 0 && x3 < framebuffer.height)
//			framebufferBuffer[y3 * framebufferWidth + x3] = 1;
		
		double xLeft = x3; //left border of the triangle, defines beginning of X-Loop's range
		double xRight = x2; //right border of the triangle, defines end of X-Loop's range
		
		double xDeltaLeft = (x1 - x3) / (double)(y3 - y1); // value that gets added to xLeft for every Row (Y-Iteration) that passes
		double xDeltaRight = (x1 - x2) / (double)(y2 - y1); // value that gets added to xRight (if right side of triangle is pointed to the middle, this value will be negative) for every Row (Y-Iteration) that passes
		
//		System.out.println(xLeft + ", " + xRight + ";  " + xDeltaLeft + ", " + xDeltaRight);
		
		int minY = Math.max(0, y1);
//		System.out.println((x1 - x3) + " / " + (y3 - y1) + " = " + xDeltaLeft);
		
		//if the baseline of this triangles lies a bit out of the viewport, some y-rows will be skipped. xDelta still needs to updated for those skipped rows:
		int rowsSkipped;
		if((rowsSkipped = y2 - (framebuffer.height - 1)) > 0) {
			xLeft += rowsSkipped * xDeltaLeft;
			xRight += rowsSkipped * xDeltaRight;
		}
//		System.out.println(rowsSkipped + ", " + xLeft);
		for(int y = Math.min(framebuffer.height-1, y2); y >= minY; y--) {
			for(int x = (int) Math.max(0, xLeft); x < Math.min(framebuffer.width-1, xRight); x++) {
				framebufferBuffer[y * framebufferWidth + x] = colorIndex;
			}
			xLeft += xDeltaLeft;
			xRight += xDeltaRight;
		}		
	}
	
	/** 
	 * NOTE: all coordinates are in buffer-scope => Y values get bigger, the more 'downwards' one goes
	 * @param x1 top Point x
	 * @param y1 top Point y
	 */
	private void rasterizeTriangleBottomSided(Framebuffer framebuffer, int colorIndex, int x1, int y1, double z1, int x2, int y2, double z2, int x3, int y3, double z3) {
//		System.out.println("rasterizing BOTTOMsided tri: " + x1 + "," + y1 + "; " + x2 + "," + y2 + "; " + x3 + "," + y3);
		
		
//		rasterizeTriangleBottomSided(framebuffer, colorIndex, x1, y1, x2, y2, x3, y3);
//		if(1==1)return;
		
		
		int[] framebufferBuffer = framebuffer.buffer;
		double[] framebufferZBuffer = framebuffer.zBuffer;
		int framebufferWidth = framebuffer.width;
		
//		if(y1 >= 0 && y1 < framebufferWidth && x1 >= 0 && x1 < framebuffer.height)
//			framebufferBuffer[y1 * framebufferWidth + x1] = 1;
//		if(y2 >= 0 && y2 < framebufferWidth && x2 >= 0 && x2 < framebuffer.height)
//			framebufferBuffer[y2 * framebufferWidth + x2] = 1;
//		if(y3 >= 0 && y3 < framebufferWidth && x3 >= 0 && x3 < framebuffer.height)
//			framebufferBuffer[y3 * framebufferWidth + x3] = 1;
		
		double xLeft = x3; //left border of the triangle, defines beginning of X-Loop's range
		double xRight = x2; //right border of the triangle, defines end of X-Loop's range
		
		double xDeltaLeft = (x1 - x3) / (double)(y3 - y1); // value that gets added to xLeft for every Row (Y-Iteration) that passes
		double xDeltaRight = (x1 - x2) / (double)(y2 - y1); // value that gets added to xRight (if right side of triangle is pointed to the middle, this value will be negative) for every Row (Y-Iteration) that passes
		
		double zGradientHorizontal = (z2 - z3) / (x2 - x3); //amount of z that gets added for every step in the x-direction		
		double zLeft = z3;
		double zDeltaPerRow = (z1 - z3) / (y3 - y1); //amount of z that gets added for every step in the y-direction (interpolation between Point3 and Point1, splitting it up in the number of rows)
//		System.out.println(xLeft + ", " + xRight + ";  " + xDeltaLeft + ", " + xDeltaRight);
		
		int minY = Math.max(0, y1);
//		System.out.println((x1 - x3) + " / " + (y3 - y1) + " = " + xDeltaLeft);
		
		//if the baseline of this triangles lies a bit out of the viewport, some y-rows will be skipped. xDelta still needs to updated for those skipped rows:
		int rowsSkipped;
		if((rowsSkipped = y2 - (framebuffer.height - 1)) > 0) {
			xLeft += rowsSkipped * xDeltaLeft;
			xRight += rowsSkipped * xDeltaRight;
			zLeft += rowsSkipped * zDeltaPerRow;
		}
//		System.out.println(rowsSkipped + ", " + xLeft);
		double zCurrent;
		int xSkipped;
		int bufferAddres;
		for(int y = Math.min(framebuffer.height-1, y2); y >= minY; y--) {
			zCurrent = zLeft;			
			if((xSkipped = (int) -xLeft) > 0) { //check if some xValues (from the left onwards) got skipped				
				zCurrent += xSkipped * zGradientHorizontal;
			}
			bufferAddres = y * framebufferWidth + ((int) Math.max(0, xLeft));
			for(int x = (int) Math.max(0, xLeft); x < Math.min(framebuffer.width-1, xRight); x++) {
				if(framebufferZBuffer[bufferAddres] > zCurrent) {
					framebufferZBuffer[bufferAddres] = zCurrent;				
					framebufferBuffer[bufferAddres] = colorIndex;
				}
				zCurrent += zGradientHorizontal;
				
				bufferAddres++;
			}
			xLeft += xDeltaLeft;
			xRight += xDeltaRight;
			zLeft += zDeltaPerRow;
		}		
	}
	
	/** 
	 * NOTE: all coordinates are in buffer-scope => Y values get bigger, the more 'downwards' one goes
	 * @param x1 top Point x
	 * @param y1 top Point y
	 */
	private void rasterizeTriangleBottomSided(Framebuffer framebuffer, Texture texture, int x1, int y1, double z1, double tex1x, double tex1y, int x2, int y2, double z2, double tex2x, double tex2y, int x3, int y3, double z3, double tex3x, double tex3y) {
//		System.out.println("rasterizing BOTTOMsided tri: " + x1 + "," + y1 + "; " + x2 + "," + y2 + "; " + x3 + "," + y3);
		
		
//		rasterizeTriangleBottomSided(framebuffer, colorIndex, x1, y1, x2, y2, x3, y3);
//		if(1==1)return;
		
		
		int[] framebufferBuffer = framebuffer.buffer;
		double[] framebufferZBuffer = framebuffer.zBuffer;
		int framebufferWidth = framebuffer.width;
		
//		if(y1 >= 0 && y1 < framebufferWidth && x1 >= 0 && x1 < framebuffer.height)
//			framebufferBuffer[y1 * framebufferWidth + x1] = 1;
//		if(y2 >= 0 && y2 < framebufferWidth && x2 >= 0 && x2 < framebuffer.height)
//			framebufferBuffer[y2 * framebufferWidth + x2] = 1;
//		if(y3 >= 0 && y3 < framebufferWidth && x3 >= 0 && x3 < framebuffer.height)
//			framebufferBuffer[y3 * framebufferWidth + x3] = 1;
		
		//RASTERIZATION
		double xLeft = x3; //left border of the triangle, defines beginning of X-Loop's range
		double xRight = x2; //right border of the triangle, defines end of X-Loop's range
		
		double xDeltaLeft = (x1 - x3) / (double)(y3 - y1); // value that gets added to xLeft for every Row (Y-Iteration) that passes
		double xDeltaRight = (x1 - x2) / (double)(y2 - y1); // value that gets added to xRight (if right side of triangle is pointed to the middle, this value will be negative) for every Row (Y-Iteration) that passes
		
		
		//Z-BUFFERING
		double zGradientHorizontal = (z2 - z3) / (x2 - x3); //amount of z that gets added for every step in the x-direction		
		double zLeft = z3;
		double zDeltaPerRow = (z1 - z3) / (y3 - y1); //amount of z that gets added for every step in the y-direction (interpolation between Point3 and Point1, splitting it up in the number of rows)
//		System.out.println(xLeft + ", " + xRight + ";  " + xDeltaLeft + ", " + xDeltaRight);
		
		
		//TEXTURING
		double textureLeftX = tex3x; //left border of the textures X coord
		double textureLeftY = tex3y; //left border of the textures Y coord
//		double textureRightX = textureCoordinates[2][0]; //right border of the textures X coord
//		double textureRightY = textureCoordinates[2][1]; //right border of the textures Y coord
		
		double textureGradientXHorizontal = (tex2x-textureLeftX) / (x2-x3); //amount that gets added to textureX for every horizontal step
		//TODO bei folgendem kommt IMMER 0 raus! (p2 und p3 sind vom y Wert her ja eh auf dem selben Level
		double textureGradientYHorizontal = (tex2y-textureLeftY) / (x2-x3); //amount that gets added to textureY for every horizontal step
		
		double textureDeltaXPerRow = (tex1x-textureLeftX) / (y3-y1);
		double textureDeltaYPerRow = (tex1y-textureLeftY) / (y3-y1);
		
		int[] textureMatrix = texture.getTextureMatrix();
		int textureMatrixWidth = texture.textureMatrixWidth;
		int textureMatrixHeight = texture.textureMatrixHeight;
		
		
		int minY = Math.max(0, y1);
//		System.out.println((x1 - x3) + " / " + (y3 - y1) + " = " + xDeltaLeft);
		
		//if the baseline of this triangles lies a bit out of the viewport, some y-rows will be skipped. xDelta still needs to updated for those skipped rows:
		int rowsSkipped;
		if((rowsSkipped = y2 - (framebuffer.height - 1)) > 0) {
			xLeft += rowsSkipped * xDeltaLeft;
			xRight += rowsSkipped * xDeltaRight;
			zLeft += rowsSkipped * zDeltaPerRow;
			
			textureLeftX += rowsSkipped * textureDeltaXPerRow;
			textureLeftY += rowsSkipped * textureDeltaYPerRow;
		}
//		System.out.println(rowsSkipped + ", " + xLeft);
		double zCurrent;
		double textureXCurrent;
		double textureYCurrent;
		int xSkipped;
		int bufferAddres;
		for(int y = Math.min(framebuffer.height-1, y2); y >= minY; y--) {
			zCurrent = zLeft;
			textureXCurrent = textureLeftX;
			textureYCurrent = textureLeftY;
			if((xSkipped = (int) -xLeft) > 0) { //check if some xValues (from the left onwards) got skipped				
				zCurrent += xSkipped * zGradientHorizontal;
				
				textureXCurrent += xSkipped * textureGradientXHorizontal;
				textureYCurrent += xSkipped * textureGradientYHorizontal;
			}
			bufferAddres = y * framebufferWidth + ((int) Math.max(0, xLeft));
			for(int x = (int) Math.max(0, xLeft); x < Math.min(framebuffer.width-1, xRight); x++) {
				if(framebufferZBuffer[bufferAddres] > zCurrent) {
					framebufferZBuffer[bufferAddres] = zCurrent;
					if(textureYCurrent >= 0 && textureYCurrent < 1.01 && textureXCurrent >= 0 && textureXCurrent < 1) {
						if(textureYCurrent < 1) {
							framebufferBuffer[bufferAddres] = textureMatrix[(int)((textureYCurrent) * textureMatrixWidth) * textureMatrixWidth + (int)(textureXCurrent * textureMatrixWidth)];
						} else {
							framebufferBuffer[bufferAddres] = textureMatrix[(textureMatrixHeight-1) * textureMatrixWidth + (int)(textureXCurrent * textureMatrixWidth)];
//							System.out.println(textureYCurr);
						}
					} else
						framebufferBuffer[bufferAddres] = 0;
				}
				zCurrent += zGradientHorizontal;
				
				textureXCurrent += textureGradientXHorizontal;
				textureYCurrent += textureGradientYHorizontal;
				
				bufferAddres++;
			}
			xLeft += xDeltaLeft;
			xRight += xDeltaRight;
			zLeft += zDeltaPerRow;
			
			textureLeftX += textureDeltaXPerRow;
			textureLeftY += textureDeltaYPerRow;
		}		
	}
	
	/** 
	 * @param x1 top left Point x
	 * @param y1 top left Point y
	 */
	private void rasterizeTriangleTopSided(Framebuffer framebuffer, int colorIndex,  int x1, int y1, int x2, int y2, int x3, int y3) {
//		System.out.println("rasterizing TOPsided tri: " + x1 + "," + y1 + "; " + x2 + "," + y2 + "; " + x3 + "," + y3);
		
		int[] framebufferBuffer = framebuffer.buffer;
		int framebufferWidth = framebuffer.width;
		
		double xLeft = x1; //left border of the triangle, defines beginning of X-Loop's range
		double xRight = x2; //right border of the triangle, defines end of X-Loop's range
		
		double xDeltaLeft = (x3 - x1) / (double)(y3 - y1); // value that gets added to xLeft for every Row (Y-Iteration) that passes
		double xDeltaRight = (x3 - x2) / (double)(y3 - y2); // value that gets added to xRight (if right side of triangle is pointed to the middle, this value will be negative) for every Row (Y-Iteration) that passes
		
//		System.out.println(xLeft + ", " + xRight + ";  " + xDeltaLeft + ", " + xDeltaRight);
		
		//if the baseline of this triangles lies a bit out of the viewport, some y-rows will be skipped. xDelta still needs to updated for those skipped rows:
		int rowsSkipped;
		if((rowsSkipped = -y1) > 0) {
			xLeft += rowsSkipped * xDeltaLeft;
			xRight += rowsSkipped * xDeltaRight;
		}
		
		int maxY = Math.min(framebuffer.height-1, y3);
		for(int y = Math.max(0, y1); y <= maxY; y++) {
			for(int x = (int) Math.max(0, xLeft); x < Math.min(framebuffer.width-1, xRight); x++) {
				framebufferBuffer[y * framebufferWidth + x] = colorIndex;
			}
			xLeft += xDeltaLeft;
			xRight += xDeltaRight;
		}		
	}
	
	/** 
	 * @param x1 top left Point x
	 * @param y1 top left Point y
	 */
	private void rasterizeTriangleTopSided(Framebuffer framebuffer, int colorIndex,  int x1, int y1, double z1, int x2, int y2, double z2, int x3, int y3, double z3) {
//		rasterizeTriangle(framebuffer, colorIndex, x1, y1, x2, y2, x3, y3);
//		if(1==1)
//			return;
//		System.out.println("rasterizing TOPsided tri: " + x1 + "," + y1 + "; " + x2 + "," + y2 + "; " + x3 + "," + y3);
		
		int[] framebufferBuffer = framebuffer.buffer;
		double[] framebufferZBuffer = framebuffer.zBuffer;
		int framebufferWidth = framebuffer.width;
		
		double xLeft = x1; //left border of the triangle, defines beginning of X-Loop's range
		double xRight = x2; //right border of the triangle, defines end of X-Loop's range
		
		double xDeltaLeft = (x3 - x1) / (double)(y3 - y1); // value that gets added to xLeft for every Row (Y-Iteration) that passes
		double xDeltaRight = (x3 - x2) / (double)(y3 - y2); // value that gets added to xRight (if right side of triangle is pointed to the middle, this value will be negative) for every Row (Y-Iteration) that passes
		
		
		double zGradientHorizontal = (z2 - z1) / (x2 - x1); //amount of z that gets added for every step in the x-direction		
		double zLeft = z1;
		double zDeltaPerRow = (z1 - z3) / (y1 - y3); //amount of z that gets added for every step in the y-direction (interpolation between Point3 and Point1, splitting it up in the number of rows)
//		System.out.println(xLeft + ", " + xRight + ";  " + xDeltaLeft + ", " + xDeltaRight);
		
//		System.out.println("zGradientHorizontal: " + zGradientHorizontal);
//		System.out.println("zLeft: " + zLeft);
//		System.out.println("zDeltaPerRow: " + zDeltaPerRow);
//		System.out.println("----");
		
		//if the baseline of this triangles lies a bit out of the viewport, some y-rows will be skipped. xDelta still needs to updated for those skipped rows:
		int rowsSkipped;
		if((rowsSkipped = -y1) > 0) {
			xLeft += rowsSkipped * xDeltaLeft;
			xRight += rowsSkipped * xDeltaRight;
			zLeft += rowsSkipped * zDeltaPerRow;
		}
		
		int maxY = Math.min(framebuffer.height-1, y3);
		double zCurrent;
		int xSkipped;
		int bufferAddres;		
		for(int y = Math.max(0, y1); y <= maxY; y++) {
			zCurrent = zLeft;
			if((xSkipped = (int) -xLeft) > 0) { //check if some xValues (from the left onwards) got skipped				
				zCurrent += xSkipped * zGradientHorizontal;
			}
			bufferAddres = (y * framebufferWidth) + ((int) Math.max(0, xLeft));
			for(int x = (int) Math.max(0, xLeft); x < Math.min(framebuffer.width-1, xRight); x++) {
//				framebufferBuffer[y * framebufferWidth + x] = colorIndex;
				if(framebufferZBuffer[bufferAddres] > zCurrent) {
					framebufferZBuffer[bufferAddres] = zCurrent;				
					framebufferBuffer[bufferAddres] = colorIndex;
				}
				zCurrent += zGradientHorizontal;
				
				bufferAddres++;
			}
			xLeft += xDeltaLeft;
			xRight += xDeltaRight;
			zLeft += zDeltaPerRow;
		}
	}
	
	/** 
	 * @param x1 top left Point x
	 * @param y1 top left Point y
	 */
	private void rasterizeTriangleTopSided(Framebuffer framebuffer, Texture texture, int x1, int y1, double z1, double tex1x, double tex1y, int x2, int y2, double z2, double tex2x, double tex2y, int x3, int y3, double z3, double tex3x, double tex3y) {		
//		rasterizeTriangle(framebuffer, colorIndex, x1, y1, x2, y2, x3, y3);
//		if(1==1)
//			return;
//		System.out.println("rasterizing TOPsided tri: " + x1 + "," + y1 + "; " + x2 + "," + y2 + "; " + x3 + "," + y3);
		
		int[] framebufferBuffer = framebuffer.buffer;
		double[] framebufferZBuffer = framebuffer.zBuffer;
		int framebufferWidth = framebuffer.width;
		
		//RASTERIZATION
		double xLeft = x1; //left border of the triangle, defines beginning of X-Loop's range
		double xRight = x2; //right border of the triangle, defines end of X-Loop's range
		
		double xDeltaLeft = (x3 - x1) / (double)(y3 - y1); // value that gets added to xLeft for every Row (Y-Iteration) that passes
		double xDeltaRight = (x3 - x2) / (double)(y3 - y2); // value that gets added to xRight (if right side of triangle is pointed to the middle, this value will be negative) for every Row (Y-Iteration) that passes
		
		//Z-BUFFERING
		double zGradientHorizontal = (z2 - z1) / (x2 - x1); //amount of z that gets added for every step in the x-direction		
		double zLeft = z1;
		double zDeltaPerRow = (z1 - z3) / (y1 - y3); //amount of z that gets added for every step in the y-direction (interpolation between Point3 and Point1, splitting it up in the number of rows)

		
		//TEXTURING
		double textureLeftX = tex1x; //left border of the textures X coord
		double textureLeftY = tex1y; //left border of the textures Y coord
//		double textureRightX = textureCoordinates[2][0]; //right border of the textures X coord
//		double textureRightY = textureCoordinates[2][1]; //right border of the textures Y coord
		
		double textureGradientXHorizontal = (tex2x-textureLeftX) / (x2-x1); //amount that gets added to textureX for every horizontal step
		double textureGradientYHorizontal = (tex2y-textureLeftY) / (x2-x1); //amount that gets added to textureY for every horizontal step
		
		double textureDeltaXPerRow = (tex3x-textureLeftX) / (y3-y1);
		double textureDeltaYPerRow = (tex3y-textureLeftY) / (y3-y1);
		
		int[] textureMatrix = texture.getTextureMatrix();
		int textureMatrixWidth = texture.textureMatrixWidth;
		int textureMatrixHeight = texture.textureMatrixHeight;
		
		
		
		//if the baseline of this triangles lies a bit out of the viewport, some y-rows will be skipped. xDelta still needs to updated for those skipped rows:
		int rowsSkipped;
		if((rowsSkipped = -y1) > 0) {
			xLeft += rowsSkipped * xDeltaLeft;
			xRight += rowsSkipped * xDeltaRight;
			zLeft += rowsSkipped * zDeltaPerRow;
			
			
			textureLeftX += rowsSkipped * textureDeltaXPerRow;
			textureLeftY += rowsSkipped * textureDeltaYPerRow;
		}
		
		int maxY = Math.min(framebuffer.height-1, y3);
		double zCurrent;
		double textureXCurrent;
		double textureYCurrent;
		int xSkipped;
		int bufferAddres;		
		for(int y = Math.max(0, y1); y <= maxY; y++) {
			zCurrent = zLeft;
			textureXCurrent = textureLeftX;
			textureYCurrent = textureLeftY;
			if((xSkipped = (int) -xLeft) > 0) { //check if some xValues (from the left onwards) got skipped				
				zCurrent += xSkipped * zGradientHorizontal;
				
				textureXCurrent += xSkipped * textureGradientXHorizontal;
				textureYCurrent += xSkipped * textureGradientYHorizontal;
			}
			bufferAddres = (y * framebufferWidth) + ((int) Math.max(0, xLeft));
			for(int x = (int) Math.max(0, xLeft); x < Math.min(framebuffer.width-1, xRight); x++) {
//				framebufferBuffer[y * framebufferWidth + x] = colorIndex;
				if(framebufferZBuffer[bufferAddres] > zCurrent) {
					framebufferZBuffer[bufferAddres] = zCurrent;
					
					if(textureYCurrent >= 0 && textureYCurrent < 1.01 && textureXCurrent >= 0 && textureXCurrent < 1) {
						if(textureYCurrent < 1) {
							framebufferBuffer[bufferAddres] = textureMatrix[(int)((textureYCurrent) * textureMatrixWidth) * textureMatrixWidth + (int)(textureXCurrent * textureMatrixWidth)];
						} else {
							framebufferBuffer[bufferAddres] = textureMatrix[(textureMatrixHeight-1) * textureMatrixWidth + (int)(textureXCurrent * textureMatrixWidth)];
//							System.out.println(textureYCurr);
						}
					} else
						framebufferBuffer[bufferAddres] = 0;
				}
				zCurrent += zGradientHorizontal;
				
				textureXCurrent += textureGradientXHorizontal;
				textureYCurrent += textureGradientYHorizontal;
				
				bufferAddres++;
			}
			xLeft += xDeltaLeft;
			xRight += xDeltaRight;
			zLeft += zDeltaPerRow;
			
			textureLeftX += textureDeltaXPerRow;
			textureLeftY += textureDeltaYPerRow;
		}
	}
	
	private void drawTriangle(Framebuffer framebuffer, int colorID, int x1, int y1, int x2, int y2, int x3, int y3) {
		drawLine(framebuffer, colorID, x1, y1, x2, y2);
		drawLine(framebuffer, colorID, x2, y2, x3, y3);
		drawLine(framebuffer, colorID, x3, y3, x1, y1);
	}
	
	private void drawLine(Framebuffer framebuffer, int colorID, int x1, int y1, int x2, int y2) {double deltaX = (x2-x1);
		double deltaY = (y2-y1);
		
		double m = deltaY/deltaX;
		
		int[] buffer = framebuffer.buffer;		
		int bufferWidth = framebuffer.width;
		int bufferHeight = framebuffer.height;
		
		if(Math.abs(deltaX) > Math.abs(deltaY)) {
			//step through the line normally (on x-axis) => f(x)
			double y = y1;
			if(x1 < x2) {
				//rightwards				
				for(int x = x1; x <= x2; x++) {
					if(!(x < 0 || x >= bufferWidth || y < 0 || y+0.5 >= bufferHeight))						
						buffer[(int)(y+0.5) * bufferWidth + x] = colorID;
			       //g.fillRect(x, (int)(y+0.5), 1, 1);
					y += m;
				}
			}else {
				//leftwards
				for(int x = x1; x >= x2; x--) {
					if(!(x < 0 || x >= bufferWidth || y < 0 || y+0.5 >= bufferHeight))
						buffer[(int)(y+0.5) * bufferWidth + x] = colorID;
//					g.fillRect(x, (int)(y+0.5), 1, 1);
					y -= m;
				}
			}
		} else {
			//handle function as f(y)
			m = 1/m;
			double x = x1;
			if(y1 < y2) {
				//downwards (positive y-axis)				
				for(int y = y1; y <= y2; y++) {
					if(!(x < 0 || x+0.5 >= bufferWidth || y < 0 || y >= bufferHeight))
						buffer[y * bufferWidth + (int)(x+0.5)] = colorID;
//					g.fillRect((int)(x+0.5), y, 1, 1);
					x += m;
				}
			}else {
				//upwards
				for(int y = y1; y >= y2; y--) {
					if(!(x < 0 || x+0.5 >= bufferWidth || y < 0 || y >= bufferHeight))
						buffer[y * bufferWidth + (int)(x+0.5)] = colorID;
//					g.fillRect((int)(x+0.5), y, 1, 1);
					x -= m;
				}
			}
		}		
	}
	
	public static final double[][][] translateTriangles(double[][][] triangles, double[] transform) {
		for(int t = 0; t < triangles.length; t++) {
			translateTriangle(triangles[t], transform);
		}
		return triangles;
	}
	
	public static final void translateTriangle(double[][] array, double[] transform) {
		array[0][0] += transform[0];
		array[0][1] += transform[1];
		array[0][2] += transform[2];
		array[1][0] += transform[0];
		array[1][1] += transform[1];
		array[1][2] += transform[2];
		array[2][0] += transform[0];
		array[2][1] += transform[1];
		array[2][2] += transform[2];
	}
	
	public static double[] copy(double[] orig) {
		double[] copy = new double[orig.length];
		for(int x1 = 0; x1 < copy.length; x1++) {
			copy[x1] = orig[x1];
		}
		return copy;
	}
	
	public static double[][] copy(double[][] orig) {
		double[][] copy = new double[orig.length][orig[0].length];
		for(int x1 = 0; x1 < copy.length; x1++) {
			for(int x2 = 0; x2 < copy[0].length; x2++) {
				copy[x1][x2] = orig[x1][x2];
			}	
		}
		return copy;
	}
	
	public static double[][][] copy(double[][][] orig) {
		double[][][] copy = new double[orig.length][orig[0].length][orig[0][0].length];
		for(int x1 = 0; x1 < copy.length; x1++) {
			for(int x2 = 0; x2 < copy[0].length; x2++) {
				for(int x3 = 0; x3 < copy[0][0].length; x3++) {
					copy[x1][x2][x3] = orig[x1][x2][x3];
				}
			}	
		}
		return copy;
	}
	
	public static double[][] copyTriangle(double[][] triangle) {
		double[][] copy = new double[6][];
		for(int x1 = 0; x1 < 3; x1++) {
			copy[x1] = new double[3];
			for(int x2 = 0; x2 < 3; x2++) {
//				System.out.println(Calculator.toString(triangle)  + ", " + x1);
				copy[x1][x2] = triangle[x1][x2];
			}	
		}
		if(triangle.length > 3 && triangle[3] != null && triangle[3].length > 0) {
			copy[3] = new double[3];
			copy[3][0] = triangle[3][0];
			copy[3][1] = triangle[3][1];
			copy[3][2] = triangle[3][2];
		}
		if(triangle.length > 4 && triangle[4] != null && triangle[4].length > 0) {
			copy[4] = new double[1];
			copy[4][0] = triangle[4][0];
		}
		
		if(triangle.length > 5 && triangle[5] != null) {
			copy[5] = new double[6];
			copy[5][0] = triangle[5][0];
			copy[5][1] = triangle[5][1];
			copy[5][2] = triangle[5][2];
			copy[5][3] = triangle[5][3];
			copy[5][4] = triangle[5][4];
			copy[5][5] = triangle[5][5];
		}
		return copy;
	}
	
	public void rotateVectorX3(double[] vec, double theta) {
		double x = vec[0];
		vec[0] = (vec[0]*Math.cos(theta)) - (vec[1]*Math.sin(theta));
		vec[1] = (x*Math.sin(theta)) + (vec[1]*Math.cos(theta));
	}
	
	public void rotateVectorX1(double[] vec, double theta) {
		double y = vec[1];
		vec[1] = (vec[1]*Math.cos(theta)) - (vec[2]*Math.sin(theta));
		vec[2] = (y*Math.sin(theta)) + (vec[2]*Math.cos(theta));
	}
    
	public void rotateVectorX2(double[] vec, double theta) {
		double x = vec[0];
		vec[0] = (vec[0]*Math.cos(theta)) + (vec[2]*Math.sin(theta));
		vec[2] = (vec[2]*Math.cos(theta)) - (x*Math.sin(theta));
	}
	
	public static double dotProduct(double[] vec1, double[] vec2) {
		return (vec1[0]*vec2[0]) + (vec1[1]*vec2[1]) + (vec1[2]*vec2[2]);
	}
	
	public double[] crossProduct(double[] vec1, double[] vec2) {
		return crossProduct(vec1[0], vec1[1], vec1[2], vec2[0], vec2[1], vec2[2]);
//		return new double[] {
//				(vec1[1]*vec2[2]) - (vec1[2]*vec2[1]),
//				(vec1[2]*vec2[0]) - (vec1[0]*vec2[2]),
//				(vec1[0]*vec2[1]) - (vec1[1]*vec2[0])
//		};
	}
	public static double[] crossProduct(double a1, double a2, double a3, double b1, double b2, double b3) {
		return new double[] {
				(a2*b3) - (a3*b2),
				(a3*b1) - (a1*b3),
				(a1*b2) - (a2*b1)
		};
	}
	
	/**
	 * @returns "vec1 - vec2"
	 */
	public static double[] subtract(double[] vec1, double[] vec2) {
		return new double[] {
			vec1[0] - vec2[0],
			vec1[1] - vec2[1],
			vec1[2] - vec2[2]
		};
	}
	
	/**
	 * Subtracts vec2 from vec1 be changing vec1s actual values => no new vector array created
	 * @returns "vec1 - vec2"
	 */
	public static double[] subtractOverwriteVec1(double[] vec1, double[] vec2) {
		vec1[0] -= vec2[0];
		vec1[1] -= vec2[1];
		vec1[2] -= vec2[2];
		return vec1;
	}
	
	public static double[] vectorMultiplyOverwrite(double[] vec1, double factor) {
		vec1[0] *= factor;
		vec1[1] *= factor;
		vec1[2] *= factor;
		return vec1;
	}
	
	public static double[] normalize(double[] vec, boolean overwrite) {
		double[] tar;
		if(overwrite)
			tar = vec;
		else
			tar = new double[3];
		
		double length = vectorLength(vec);
		tar[0] = vec[0] / length;
		tar[1] = vec[1] / length;
		tar[2] = vec[2] / length;
		return tar;
	}
	
	public static double vectorLength(double[] vec) {
		return Math.sqrt((vec[0]*vec[0])+(vec[1]*vec[1])+(vec[2]*vec[2]));
	}
	
	public static String toString(double[][] array) {
		String out = "{";
		for(int x1 = 0; x1 < array.length; x1++) {			
			out += Arrays.toString(array[x1]);
			if(x1 < array.length-1)
				out += ", ";
		}
		return out + "}";
	}
	
	public static String toString(double[][][] array) {
		String out = "{";
		for(int x1 = 0; x1 < array.length; x1++) {
			out += "{";
			for(int x2 = 0; x2 < array.length; x2++) {
				out += "{" + Arrays.toString(array[x1]) + "}";
			}
			out += "}";
		}
		return out + "}";
	}
	
	/**
	 * returns a point in which is exactly in between two given points
	 */
	public static double[] getCenterPoint(double[] point1, double[] point2) {
		return new double[] {
				point1[0] + ((point2[0] - point1[0])/2),
				point1[1] + ((point2[1] - point1[1])/2),
				point1[2] + ((point2[2] - point1[2])/2)
		};
	}
	
	public static double[] vectorAdd(double[] vec1, double[] vec2) {
		return new double[] {
				vec1[0] + vec2[0],
				vec1[1] + vec2[1],
				vec1[2] + vec2[2]
		};
	}
	
	private static double[][][] clipTriangle(double[][] triangle) {
		if(triangle[0][2] > CLIPPING_Z_MINIMUM) {
			if(triangle[1][2] > CLIPPING_Z_MINIMUM) {
				if(triangle[2][2] > CLIPPING_Z_MINIMUM) {
					return new double[][][] {triangle};
				} else {
					return clipTriangle2PointsVisible(triangle[2], triangle[0], triangle[1], (triangle.length>5&&triangle[5]!=null&&triangle[5].length>0 ? triangle[5] : null));
				}				
			} else {
				if(triangle[2][2] > CLIPPING_Z_MINIMUM) {
					return clipTriangle2PointsVisible(triangle[1], triangle[2], triangle[0], (triangle.length>5&&triangle[5]!=null&&triangle[5].length>0 ? triangle[5] : null));
				} else {
					return clipTriangle1PointVisible(triangle[1], triangle[2], triangle[0], (triangle.length>5&&triangle[5]!=null&&triangle[5].length>0 ? triangle[5] : null));
				}
			}			
		} else {//if(triangle[0][2] < CLIPPING_Z_MINIMUM && triangle[1][2] < CLIPPING_Z_MINIMUM && triangle[2][2] < CLIPPING_Z_MINIMUM)
			if(triangle[1][2] > CLIPPING_Z_MINIMUM) {
				if(triangle[2][2] > CLIPPING_Z_MINIMUM) {
					return clipTriangle2PointsVisible(triangle[0], triangle[1], triangle[2], (triangle.length>5&&triangle[5]!=null&&triangle[5].length>0 ? triangle[5] : null));
				} else {
					return clipTriangle1PointVisible(triangle[2], triangle[0], triangle[1], (triangle.length>5&&triangle[5]!=null&&triangle[5].length>0 ? triangle[5] : null));
				}				
			} else {
				if(triangle[2][2] > CLIPPING_Z_MINIMUM) {
					return clipTriangle1PointVisible(triangle[0], triangle[1], triangle[2], (triangle.length>5&&triangle[5]!=null&&triangle[5].length>0 ? triangle[5] : null));
				} else {
					//三角のポイントがすべてカメラの後ろで
					return null;
				}
			}
		}
	}
	
	private static double[][][] clipTriangle2PointsVisible(double[] pointToClip, double[] point2, double[] point3, double[] textureCoordinates) {
		double pointNew12InterpolationFactor = (-pointToClip[2]+CLIPPING_Z_MINIMUM)/(point2[2]-pointToClip[2]); //=> Distance from z=Z_MIN Plane to pointToClip  /  Distance from pointToClip to P2
		double[] pointNew12 = new double[] {(point2[0]-pointToClip[0])*pointNew12InterpolationFactor + pointToClip[0], (point2[1]-pointToClip[1])*pointNew12InterpolationFactor + pointToClip[1], CLIPPING_Z_MINIMUM};
		double pointNew13InterpolationFactor = (-pointToClip[2]+CLIPPING_Z_MINIMUM)/(point3[2]-pointToClip[2]); //=> Distance from z=Z_MIN Plane to pointToClip  /  Distance from pointToClip to P2
		double[] pointNew13 = new double[] {(point3[0]-pointToClip[0])*pointNew13InterpolationFactor + pointToClip[0], (point3[1]-pointToClip[1])*pointNew13InterpolationFactor + pointToClip[1], CLIPPING_Z_MINIMUM};
		
		if(textureCoordinates == null) {		
			return new double[][][] {
				{pointNew12, point2, point3},
				{pointNew13, copy(pointNew12), copy(point3)}
			};
		} else {
			final double texturePointNew12X = textureCoordinates[0] + ((textureCoordinates[2]-textureCoordinates[0])*pointNew12InterpolationFactor);
			final double texturePointNew12Y = textureCoordinates[1] + ((textureCoordinates[3]-textureCoordinates[1])*pointNew12InterpolationFactor);
			final double texturePointNew13X = textureCoordinates[0] + ((textureCoordinates[4]-textureCoordinates[0])*pointNew13InterpolationFactor);
			final double texturePointNew13Y = textureCoordinates[1] + ((textureCoordinates[5]-textureCoordinates[1])*pointNew13InterpolationFactor);
			return new double[][][] {
				{pointNew12, point2, point3, {}, {}, new double[] {texturePointNew12X, texturePointNew12Y, textureCoordinates[2], textureCoordinates[3], textureCoordinates[4], textureCoordinates[5]}},
				{pointNew13, copy(pointNew12), copy(point3), {}, {}, new double[] {texturePointNew13X, texturePointNew13Y, texturePointNew12X, texturePointNew12Y, textureCoordinates[4], textureCoordinates[5]}}
			};
		}
	}
	
	private static double[][][] clipTriangle1PointVisible(double[] pointToClip1, double[] pointToClip2, double[] point3, double[] textureCoordinates) {
		double pointNew13InterpolationFactor = (-pointToClip1[2]+CLIPPING_Z_MINIMUM)/(point3[2]-pointToClip1[2]); //=> Distance from z=Z_MIN Plane to pointToClip1  /  Distance from pointToClip1 to P3
		double[] pointNew13 = new double[] {(point3[0]-pointToClip1[0])*pointNew13InterpolationFactor + pointToClip1[0], (point3[1]-pointToClip1[1])*pointNew13InterpolationFactor + pointToClip1[1], CLIPPING_Z_MINIMUM};
		double pointNew23InterpolationFactor = (-pointToClip2[2]+CLIPPING_Z_MINIMUM)/(point3[2]-pointToClip2[2]); //=> Distance from z=Z_MIN Plane to pointToClip2  /  Distance from pointToClip2 to P3
		double[] pointNew23 = new double[] {(point3[0]-pointToClip2[0])*pointNew23InterpolationFactor + pointToClip2[0], (point3[1]-pointToClip2[1])*pointNew23InterpolationFactor + pointToClip2[1], CLIPPING_Z_MINIMUM};
		
		if(textureCoordinates == null) {
			return new double[][][] {
				{pointNew13, pointNew23, point3}
			};
		} else {
			final double texturePointNew13X = textureCoordinates[0] + ((textureCoordinates[4]-textureCoordinates[0])*pointNew13InterpolationFactor);
			final double texturePointNew13Y = textureCoordinates[1] + ((textureCoordinates[5]-textureCoordinates[1])*pointNew13InterpolationFactor);
			final double texturePointNew23X = textureCoordinates[2] + ((textureCoordinates[4]-textureCoordinates[2])*pointNew23InterpolationFactor);
			final double texturePointNew23Y = textureCoordinates[3] + ((textureCoordinates[5]-textureCoordinates[3])*pointNew23InterpolationFactor);
			return new double[][][] {
				{pointNew13, pointNew23, point3, {}, {}, new double[] {texturePointNew13X, texturePointNew13Y, texturePointNew23X, texturePointNew23Y, textureCoordinates[4], textureCoordinates[5]}}
			};
		}
	}
}
