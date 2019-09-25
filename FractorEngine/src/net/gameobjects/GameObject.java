package net.gameobjects;

public class GameObject {
	
	protected double[][][] triangles;
	
	private double[] position;
	
	public GameObject(double[][][] triangles) {
		this.triangles = triangles;
		position = new double[] {0, 0, 0};
	}
	
	public double[][][] getTriangles() {
		return triangles;
	}
}
