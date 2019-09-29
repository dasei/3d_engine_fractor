package net.buffering;

import java.awt.Color;

import net.Engine;

public class Texture {
	
	public static Texture textureDEBUG;
	
	private int[] textureMatrix;
	public final int textureMatrixWidth;
	public final int textureMatrixHeight;
	
	
	public Texture(int[] textureMatrix, int textureMatrixWidth) {
//		this.textureMatrix = textureMatrix;
//		this.textureMatrixWidth = textureMatrixWidth;
		
		Engine engine = Engine.getInstance();
//		this.textureMatrixWidth = 2;
//		this.textureMatrix = new int[] {
//				engine.registerColor(Color.red),engine.registerColor(Color.green),
//				engine.registerColor(Color.blue),engine.registerColor(Color.yellow)
//		};
		this.textureMatrixWidth = 3;
		this.textureMatrix = new int[] {
				engine.registerColor(Color.red),engine.registerColor(Color.green),engine.registerColor(Color.pink),
				engine.registerColor(Color.blue),engine.registerColor(Color.yellow),engine.registerColor(Color.CYAN),
				engine.registerColor(Color.DARK_GRAY),engine.registerColor(Color.MAGENTA),engine.registerColor(Color.ORANGE)
		};
		
		
		
		
		this.textureMatrixHeight = this.textureMatrix.length/this.textureMatrixWidth;
	}
	
	public int[] getTextureMatrix() {
		return textureMatrix;
	}
}
