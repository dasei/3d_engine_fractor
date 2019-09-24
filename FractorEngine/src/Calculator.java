import java.util.Arrays;

public class Calculator {
	
	public static int STAT_TRIANGLES_AMOUNT;
	public static int STAT_TRIANGLES_VISIBLE;
	public static boolean OPTION_ROTATE_AROUND_X1_AXIS = false;// private static double OPTION_ROTATE_AROUND_X1_AXIS_currentRotation = 0;
	public static boolean OPTION_ROTATE_AROUND_X2_AXIS = false;// private static double OPTION_ROTATE_AROUND_X2_AXIS_currentRotation = 0;
	public static boolean OPTION_ROTATE_AROUND_X3_AXIS = false;// private static double OPTION_ROTATE_AROUND_X3_AXIS_currentRotation = 0;
	
	
	public void render(double[][][] triangles) {
		if(triangles == null)
			return;
		STAT_TRIANGLES_AMOUNT = triangles.length;
		
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
		GameObjectFactory.randomizeColor(triangles);
		
		
		// RENDERING PROCEDURE
		int stat_triangles_visible = 0;
		double[][] triangle;
		for(int t = 0; t < triangles.length; t++) {
			if(triangles[t] == null)
				continue;
			triangle = copyTriangle(triangles[t]);
			
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
//			transformTriangle3D(triangle, new double[] {0, 0, 5}); //TODO remove this
//			transformTriangle3D(triangle, new double[] {50, 50, 0}); //TODO remove this
			
			//TODO remove this cool wavy form
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
			
			//camera translate (position)			
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
			
			
//			System.out.println(vecTriangleToCamera);
//			System.out.println();
			stat_triangles_visible++;
			
			
			
			
			
			// Projection
			// 3D => 2D
			for(int v = 0; v < 3; v++) {
				if(triangle[v][2] <= 0)
					continue;				
				triangle[v][0] /= triangle[v][2];
				triangle[v][1] /= triangle[v][2];
				
//				triangle[v][2] = (triangle[v][2]*(20/(20-0.1))) + (triangle[v][2]*((-20*0.1)/(20-0.1))); 
				//TODO add something like x *= screen size ratio => to stop distortion aspect ratio
//				triangle[v][0] *= engine.resolution.width/(double)engine.resolution.height;
			}
			
			if(triangle[0][2] < 0 && triangle[1][2] < 0 && triangle[2][2] < 0)
				continue;
			
			//System.out.println("cross: " + Arrays.toString(crossProduct(0, 1, 0, 1, 1, 0)));
			
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
			rasterizeTriangle(framebuffer, (int)triangle[4][0],
					(int) ((1+triangle[0][0])*resolutionWidthHalf),
					(int) ((1-triangle[0][1])*resolutionWidthHalf),
					triangle[0][2],
					(int) ((1+triangle[1][0])*resolutionWidthHalf),
					(int) ((1-triangle[1][1])*resolutionWidthHalf),
					triangle[1][2],
					(int) ((1+triangle[2][0])*resolutionWidthHalf),
					(int) ((1-triangle[2][1])*resolutionWidthHalf),
					triangle[2][2]
			);
			
//			if(
//					(int) ((1+triangle[0][0])*resolutionWidthHalf) >= 0
//				&&	(int) ((1+triangle[0][0])*resolutionWidthHalf) < 500
//				&& 	(int) ((1-triangle[0][1])*resolutionWidthHalf) >= 0
//				&&	(int) ((1-triangle[0][1])*resolutionWidthHalf) < 500
//			) {
//				System.out.println("triangle onscreen: " +
//						(int) ((1+triangle[0][0])*resolutionWidthHalf)+";"+
//						(int) ((1-triangle[0][1])*resolutionWidthHalf)+";"+
//						(int) ((1+triangle[1][0])*resolutionWidthHalf)+";"+
//						(int) ((1-triangle[1][1])*resolutionWidthHalf)+";"+
//						(int) ((1+triangle[2][0])*resolutionWidthHalf)+";"+
//						(int) ((1-triangle[2][1])*resolutionWidthHalf)
//				);
//				System.out.println("triangle: " + Calculator.toString(triangle));
//				System.out.println("---");
//			}
		}
		
		STAT_TRIANGLES_VISIBLE = stat_triangles_visible;
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
	
	private void drawTriangle(Framebuffer framebuffer, int x1, int y1, int x2, int y2, int x3, int y3) {
		drawLine(framebuffer, x1, y1, x2, y2);
		drawLine(framebuffer, x2, y2, x3, y3);
		drawLine(framebuffer, x3, y3, x1, y1);
	}
	
	private void drawLine(Framebuffer framebuffer, int x1, int y1, int x2, int y2) {		
		double deltaX = (x2-x1);
		double deltaY = (y2-y1);
		
		double m = deltaY/deltaX;
		
		int[] buffer = framebuffer.buffer;
		int bufferWidth = framebuffer.width;
		
		if(Math.abs(deltaX) > Math.abs(deltaY)) {
			//step through the line normally (on x-axis) => f(x)
			double y = y1;
			if(x1 < x2) {
				//rightwards				
				for(int x = x1; x <= x2; x++) {
					buffer[(int)(y+0.5) * bufferWidth + x] = 1;
			       //g.fillRect(x, (int)(y+0.5), 1, 1);
					y += m;
				}
			}else {
				//leftwards
				for(int x = x1; x >= x2; x--) {
//					g.fillRect(x, (int)(y+0.5), 1, 1);
					buffer[(int)(y+0.5) * bufferWidth + x] = 1;
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
//					g.fillRect((int)(x+0.5), y, 1, 1);
					buffer[y * bufferWidth + (int)(x+0.5)] = 1;
					x += m;
				}
			}else {
				//upwards
				for(int y = y1; y >= y2; y--) {
//					g.fillRect((int)(x+0.5), y, 1, 1);
					buffer[y * bufferWidth + (int)(x+0.5)] = 1;
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
		double[][] copy = new double[5][3];
		for(int x1 = 0; x1 < 3; x1++) {
			for(int x2 = 0; x2 < 3; x2++) {
//				System.out.println(Calculator.toString(triangle)  + ", " + x1);
				copy[x1][x2] = triangle[x1][x2];
			}	
		}
		if(triangle.length > 3 && triangle[3] != null && triangle[3].length > 0) {
			copy[3][0] = triangle[3][0];
			copy[3][1] = triangle[3][1];
			copy[3][2] = triangle[3][2];
		}
		if(triangle.length > 4 && triangle[4] != null)
			copy[4][0] = triangle[4][0];
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
}
