import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class DrawComp extends JComponent {
	
	public static final Color COLOR_BACKGROUND = Color.BLACK;
	
	public DrawComp() {
		this.fontStats = new Font("consolas", Font.BOLD, 7);
		this.setOpaque(true);
//		setBackground(Color.BLACK);
	}
	
	public void paintComponent(Graphics g1d) {
//		super.paintComponent(g1d);
		Graphics2D g = (Graphics2D) g1d;
		//BACKGROUND CLEAR		
//		g.setColor(COLOR_BACKGROUND);
//		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		//SCALE
		Dimension resolution = Engine.getInstance().resolution;		
		g.scale(this.getWidth()/(double)resolution.width, this.getHeight()/(double)resolution.height);
		
//		System.out.println("2640? => " + Engine.getInstance().getFramebufferManager().getFramebufferToDraw().buffer[2640]);
		
//		g.setColor(Color.WHITE);
//		if(Math.random() > 0.5) {
//			g.fillRect(10, 10, 1, 2);
//		}
		
		//DRAW
		g.setColor(Color.WHITE);
		
		
		drawFramebuffer(g);
		
		drawStats(g);
		
//		if(this.getMousePosition() != null)
//			System.out.println("x: " + (this.getMousePosition().x/5) + " y:" + (this.getMousePosition().y/5));
		
		Engine.getInstance().getFramebufferManager().onThreadFinish(false);
		
		
//		int widthHalf = this.getWidth()/2;
//		int heightHalf = this.getHeight()/2;
//		int resolutionWidthHalf = resolution.width/2;
//		int resolutionHeightHalf = resolution.height/2;
//		
//		double[][][] trianglesRendered = Engine.getInstance().trianglesRendered;
//		double[][] triangle;
//		for(int t = 0; t < trianglesRendered.length; t++) {
//			triangle = trianglesRendered[t];
//			if(triangle == null)
//				continue;
//			
////			if(t == 0)
////				System.out.println(Arrays.toString(triangle[0]));
////			for(int v = 0; v < 3; v++) {
//			
////			System.out.println("--");
////			System.out.println(triangle[0][0]);
////			System.out.println(triangle[0][1]);
////			System.out.println(triangle[1][0]);
////			System.out.println(triangle[1][1]);
////			
////			System.out.println((int) ((triangle[0][0]/2+0.5)*resolution.width));
////			System.out.println((int) ((triangle[0][1]/2+0.5)*resolution.height));
////			System.out.println((int) ((triangle[1][0]/2+0.5)*resolution.width));
////			System.out.println((int) ((triangle[1][1]/2+0.5)*resolution.height));
//			
//			try {
//			drawTriangle(g,
//					(int) ((1+triangle[0][0])/2*resolution.width),
//					(int) ((1-triangle[0][1])/2*resolution.height),
//					(int) ((1+triangle[1][0])/2*resolution.width),
//					(int) ((1-triangle[1][1])/2*resolution.height),
//					(int) ((1+triangle[2][0])/2*resolution.width),
//					(int) ((1-triangle[2][1])/2*resolution.height)
//			);
//			}catch(Exception e) {
//				e.printStackTrace();
//				System.out.println("Exception at triangle index: " + t);
//			}
////			g.drawLine(
////					(int) ((1+triangle[0][0])/2*resolution.width),
////					(int) ((1-triangle[0][1])/2*resolution.height),
////					(int) ((1+triangle[1][0])/2*resolution.width),
////					(int) ((1-triangle[1][1])/2*resolution.height)
////			);
////			g.drawLine(
////					(int) ((1+triangle[1][0])/2*resolution.width),
////					(int) ((1-triangle[1][1])/2*resolution.height),
////					(int) ((1+triangle[2][0])/2*resolution.width),
////					(int) ((1-triangle[2][1])/2*resolution.height)
////			);
////			g.drawLine(
////					(int) ((1+triangle[2][0])/2*resolution.width),
////					(int) ((1-triangle[2][1])/2*resolution.height),
////					(int) ((1+triangle[0][0])/2*resolution.width),
////					(int) ((1-triangle[0][1])/2*resolution.height)
////			);
////			}	
//			
////			System.out.println("woop");
//		}
//		drawStats(g);
		
//		drawLine(g, 50, 50, 50+(int)(Math.sin(System.currentTimeMillis()/1000d)*50), 50+(int)(Math.cos(System.currentTimeMillis()/1000d)*50));
		
		
		
//		if(timeStart == 0) {
//			timeStart = System.currentTimeMillis();
//		}
//		cycles++;
//		
//		if(cycles % 50 == 0)
//			System.out.println(cycles/((System.currentTimeMillis()-timeStart)/1000d));
	}
	
	private void drawFramebuffer(Graphics2D g) {
		Framebuffer framebuffer = Engine.getInstance().getFramebufferManager().getFramebufferToDraw();
		Engine engine = Engine.getInstance();
		int[] buffer = framebuffer.buffer;
		int bufferWidth = framebuffer.width;
		int bufferHeight = framebuffer.height;
		
		
		int colorIndexLast = -1;
		int colorIndexCurrent;
		for(int x = 0; x < bufferWidth; x++) {
			for(int y = 0; y < bufferHeight; y++) {
				colorIndexCurrent = buffer[y * bufferWidth + x];
				if(colorIndexCurrent == -1)
					continue;
				
				//get color object
				if(colorIndexLast != colorIndexCurrent) {
					colorIndexLast = colorIndexCurrent;
					g.setColor(engine.getColor(colorIndexCurrent));
				}
				
//					if(buffer[y * bufferWidth + x] == 2)
//						g.setColor(Color.BLUE);
//					else
//						g.setColor(Color.WHITE);
				g.fillRect(x, y, 1, 1);
				
//					g.fillRect(x, y, 1, 1);
			}	
		}	
		
		g.setColor(Color.WHITE);
//		drawLine(g, 125, 10, 125, 120);
	}
	
	private final Font fontStats;
	private void drawStats(Graphics g) {
		Engine engine = Engine.getInstance();
		g.setFont(fontStats);
		g.setColor(Color.RED);
//		g.drawString("tri total \t" + Calculator.STAT_TRIANGLES_AMOUNT, 2, fontStats.getSize());
		g.drawString("FPS: " + ((int)Engine.getInstance().FPS_AVERAGE), 2, fontStats.getSize());
		g.drawString("tri vis: " + (int)(Calculator.STAT_TRIANGLES_VISIBLE/(double)Calculator.STAT_TRIANGLES_AMOUNT*100) + "% " + Calculator.STAT_TRIANGLES_VISIBLE + " / " + Calculator.STAT_TRIANGLES_AMOUNT, 2, fontStats.getSize()*2);
		g.drawString("X:" + (double)((int)(engine.cameraPosition[0]*10)/10d) + ",Y:" + (double)((int)(engine.cameraPosition[1]*10)/10d) + ",Z:" + (double)((int)(engine.cameraPosition[2]*10)/10d), 2, fontStats.getSize()*3);
//		g.drawString("rot: " + Math.toDegrees(Engine.getInstance().cameraRotationHorizontal) + "°", 2, fontStats.getSize()*2);
//		g.drawString("visible " + Calculator.STAT_TRIANGLES_VISIBLE + " " + (int)(Calculator.STAT_TRIANGLES_VISIBLE/(double)Calculator.STAT_TRIANGLES_AMOUNT*100) + "%", 2, fontStats.getSize()*2);
	}
	
//	private long timeStart;
//	private int cycles = 0;
	
	private void drawTriangle(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3) {
//		g.drawLine(x1, y1, x2, y2);
//		g.drawLine(x2, y2, x3, y3);
//		g.drawLine(x3, y3, x1, y1);
		drawLine(g, x1, y1, x2, y2);
		drawLine(g, x2, y2, x3, y3);
		drawLine(g, x3, y3, x1, y1);
		
	}
	
	private void drawLine(Graphics g, int x1, int y1, int x2, int y2) {		
		double deltaX = (x2-x1);
		double deltaY = (y2-y1);
		
		double m = deltaY/deltaX;
		
		if(Math.abs(deltaX) > Math.abs(deltaY)) {
			//step through the line normally (on x-axis) => f(x)
			double y = y1;
			if(x1 < x2) {
				//rightwards				
				for(int x = x1; x <= x2; x++) {
					g.fillRect(x, (int)(y+0.5), 1, 1);
					y += m;
				}
			}else {
				//leftwards
				for(int x = x1; x >= x2; x--) {
					g.fillRect(x, (int)(y+0.5), 1, 1);
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
					g.fillRect((int)(x+0.5), y, 1, 1);
					x += m;
				}
			}else {
				//upwards
				for(int y = y1; y >= y2; y--) {
					g.fillRect((int)(x+0.5), y, 1, 1);
					x -= m;
				}
			}
		}		
	}
}
