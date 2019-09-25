package net.threading;

import net.Engine;
import net.buffering.FramebufferManager;
import net.maths.Calculator;

public class FramebufferRenderingThread extends Thread {
	public void run() {
		
		Engine engine = Engine.getInstance();
		FramebufferManager framebufferManager = engine.getFramebufferManager();
		Calculator calculator = new Calculator();
		
		this.setName("RenderThread");
		
		while(true) {
			
//			calculator.render(engine.getTriangles());
			calculator.render(engine.getGameObjects());
			
			//sleep
			framebufferManager.onThreadFinish(true);			
		}
	}
}
