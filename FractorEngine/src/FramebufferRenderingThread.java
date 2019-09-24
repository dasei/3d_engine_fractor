
public class FramebufferRenderingThread extends Thread {
	public void run() {
		
		Engine engine = Engine.getInstance();
		FramebufferManager framebufferManager = engine.getFramebufferManager();
		Calculator calculator = new Calculator();
		
		this.setName("RenderThread");
		
		while(true) {
			
			calculator.render(engine.getTriangles());
			
			//sleep
			framebufferManager.onThreadFinish(true);			
		}
	}
}
