package net.gameobjects;

import net.buffering.Texture;
import net.maths.Calculator;

public class GameObject {
	
	protected double[][][] triangles;
	
	protected double[] position;
	
	private Texture texture;
	
	
	private boolean cachingOfAbsoluteCoordinates = true;
	
	public GameObject(double[][][] triangles, Texture texture) {
		this(triangles);
		this.texture = texture;
	}
	
	public GameObject(double[][][] triangles) {
		this.triangles = triangles;
		position = new double[] {0, 0, 0};
	}
		
	public GameObject move(double deltaX, double deltaY, double deltaZ) {
		position[0] += deltaX;
		position[1] += deltaY;
		position[2] += deltaZ;
		return this;
	}
	
	private double[] trianglesAbsoluteCachedForPosition = new double[3];
	private double[][][] trianglesAbsoluteCachedForTriangleArray; 
	private double[][][] trianglesAbsoluteCached;
	public double[][][] getTrianglesAbsolute() {
		if(cachingOfAbsoluteCoordinates) {
			if(position[0] == 0 && position[1] == 0 && position[2] == 0)
				return triangles;
			
			if(trianglesAbsoluteCachedForPosition[0] == position[0] && trianglesAbsoluteCachedForPosition[1] == position[1] && trianglesAbsoluteCachedForPosition[2] == position[2] && trianglesAbsoluteCachedForTriangleArray == triangles)
				return trianglesAbsoluteCached;
		}
		
		
		this.trianglesAbsoluteCached = new double[triangles.length][][];
		int triangleLen = triangles.length;
		for(int triangleI = 0; triangleI < triangleLen; triangleI++) {
			trianglesAbsoluteCached[triangleI] = new double[][] {
				Calculator.vectorAdd(triangles[triangleI][0], position),
				Calculator.vectorAdd(triangles[triangleI][1], position),
				Calculator.vectorAdd(triangles[triangleI][2], position)
			};
		}
		this.trianglesAbsoluteCachedForPosition[0] = position[0];
		this.trianglesAbsoluteCachedForPosition[1] = position[1];
		this.trianglesAbsoluteCachedForPosition[2] = position[2];
		this.trianglesAbsoluteCachedForTriangleArray = this.triangles;
		return this.trianglesAbsoluteCached;
	}
	
	public double[][][] getTriangles() {
		return triangles;
	}
	
	public double[] getPosition() {
		return this.position;
	}
}
