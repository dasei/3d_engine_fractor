package net.gameobjects;

import static net.maths.Calculator.copy;

import net.maths.Calculator;

public class SphereProcedural extends GameObject {
	
	private double radius;
	
	public SphereProcedural(double radius) {
		super(new double[][][] {
			//top
			{{0,0,-radius}, {0,radius,0}, {radius,0,0}}, //right front
			{{0,0,-radius}, {-radius,0,0}, {0,radius,0}}, //left front
			{{0,0,radius}, {radius,0,0}, {0,radius,0}}, //right back
			{{0,0,radius}, {0,radius,0}, {-radius,0,0}}, //left back
			//bottom
			{{0,0,-radius}, {radius,0,0}, {0,-radius,0}}, //right front
			{{0,0,-radius}, {0,-radius,0}, {-radius,0,0}}, //left front
			{{0,0,radius}, {0,-radius,0}, {radius,0,0}}, //right back
			{{0,0,radius}, {-radius,0,0}, {0,-radius,0}} //left back
		});
		this.radius = radius;
	}
	
//	public void proceed() {
//		double[][][] trianglesCurrent = this.getTriangles();
//		double[][][] trianglesNew = new double[trianglesCurrent.length*4][][];
//		
//		double[][][] trianglesCut;
//		for(int t = 0; t < trianglesCurrent.length; t++) {
//			trianglesCut = GameObjectFactory.splitTriangle(trianglesCurrent[t], true);
//			
//			//put cut triangles into the new array
//			for(int n = 0; n < 4; n++) {
//				trianglesNew[t*4 + n] = trianglesCut[n]; 
//			}
//		}
//		
//		this.triangles = trianglesNew;
//	}
	
	public void proceed(int cycles) {
		for(int i = 0; i < cycles; i++) {
			proceed();
		}
	}
	
	public void proceed() {
		int trianglesLen = this.triangles.length;
		double[][][] triangleArrayNew = new double[4*trianglesLen][][];
		
		double[][] triangle;		
		double[] pointAB; double[] pointBC; double[] pointCA;
		for(int triangleI = 0; triangleI < trianglesLen; triangleI++) {
			triangle = this.triangles[triangleI];
			
			pointAB = Calculator.vectorMultiplyOverwrite(							//3. stretch it to match the radius => new sphere gets cURvIeR!
					Calculator.normalize(											//2. normalize it
							Calculator.getCenterPoint(triangle[0], triangle[1]),	//1. get center point between A and B (=> everything is relative to center of this sphere)
					true),
			this.radius);
			
			pointBC = Calculator.vectorMultiplyOverwrite(							//3. stretch it to match the radius => new sphere gets cURvIeR!
					Calculator.normalize(											//2. normalize it
							Calculator.getCenterPoint(triangle[1], triangle[2]),	//1. get center point between B and C (=> everything is relative to center of this sphere)
					true),
			this.radius);
			
			pointCA = Calculator.vectorMultiplyOverwrite(							//3. stretch it to match the radius => new sphere gets cURvIeR!
					Calculator.normalize(											//2. normalize it
							Calculator.getCenterPoint(triangle[2], triangle[0]),	//1. get center point between C and A (=> everything is relative to center of this sphere)
					true),
			this.radius);
			
			triangleArrayNew[triangleI*4] = new double[][] {copy(triangle[0]), copy(pointAB), copy(pointCA)};
			triangleArrayNew[triangleI*4+1] = new double[][] {copy(pointAB), copy(triangle[1]), copy(pointBC)};
			triangleArrayNew[triangleI*4+2] = new double[][] {copy(triangle[2]), copy(pointCA), copy(pointBC)};
			triangleArrayNew[triangleI*4+3] = new double[][] {pointCA, pointAB, pointBC};
			
		}
		this.triangles = triangleArrayNew;
	}
	
}
