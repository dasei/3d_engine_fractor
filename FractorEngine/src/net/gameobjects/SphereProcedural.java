package net.gameobjects;

import net.maths.GameObjectFactory;

public class SphereProcedural extends GameObject {
	
	public SphereProcedural(double radius) {
		super(new double[][][] {
			//top
			{{0,0,-1}, {0,1,0}, {1,0,0}}, //right front
			{{0,0,-1}, {-1,0,0}, {0,1,0}}, //left front
			{{0,0,1}, {1,0,0}, {0,1,0}}, //right back
			{{0,0,1}, {0,1,0}, {-1,0,0}}, //left back
			//bottom
			{{0,0,-1}, {1,0,0}, {0,-1,0}}, //right front
			{{0,0,-1}, {0,-1,0}, {-1,0,0}}, //left front
			{{0,0,1}, {0,-1,0}, {1,0,0}}, //right back
			{{0,0,1}, {-1,0,0}, {0,-1,0}} //left back
		});
	}
	
	public void proceed() {
		double[][][] trianglesCurrent = this.getTriangles();
		double[][][] trianglesNew = new double[trianglesCurrent.length*4][][];
		
		double[][][] trianglesCut;
		for(int t = 0; t < trianglesCurrent.length; t++) {
			trianglesCut = GameObjectFactory.splitTriangle(trianglesCurrent[t], true);
			
			//put cut triangles into the new array
			for(int n = 0; n < 4; n++) {
				trianglesNew[t*4 + n] = trianglesCut[n]; 
			}
		}
		
		this.triangles = trianglesNew;
	}
	
}
