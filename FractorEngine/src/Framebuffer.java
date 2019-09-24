import java.awt.Dimension;

public class Framebuffer {
	public int[] buffer;
	public double[] zBuffer;
	
	public final int width, height;
	
	public Framebuffer(Dimension resolution) {
		this(resolution.width, resolution.height);
	}
	
	public Framebuffer(int width, int height) {
		this.buffer = new int[width * height];
		this.zBuffer = new double[width * height];
		this.width = width;
		this.height = height;
		this.clear();
	}
	
	public void clear() {
		for(int i = 0; i < buffer.length; i++) {
//			if(this.buffer[i] != 0)
			this.buffer[i] = -1;
			this.zBuffer[i] = Double.POSITIVE_INFINITY;
		}
	}
	
	public void checkIfClear() {
		for(int i = 0; i < buffer.length; i++)
			if(buffer[i] != 0) {
				System.out.println("NOT CLEAR (i:" + i + ")");
				return;
			}
		System.out.println("CLEAR");
	}
}
