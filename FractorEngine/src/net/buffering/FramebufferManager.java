package net.buffering;
import java.awt.Dimension;

import net.Engine;
import net.threading.FramebufferClearThread;
import net.threading.FramebufferRenderingThread;

public class FramebufferManager {
	
	private Framebuffer[] framebuffers; //id of framebuffer, 2d buffer in 1d form
	
	private int framebuffersAmount = 3;
	
	private int framebufferID_RENDER = 0;
	private int framebufferID_DRAW = 1;
	private int framebufferID_CLEAR = 2;
	
	private FramebufferClearThread framebufferClearThread;
	private FramebufferRenderingThread framebufferRenderingThread;
	
	public FramebufferManager(Dimension resolution, Runnable synchronousCode) {
		this.threadlock = new Object();
		this.framebuffers = new Framebuffer[framebuffersAmount];
		for(int i = 0; i < this.framebuffersAmount; i++) {
			this.framebuffers[i] = new Framebuffer(resolution);			
		}
		this.synchronousCode = synchronousCode;
	}
	
	/**
	 * A Runnable defining code that will be synchronously executed by the FramebufferManager when all threads finished their cycle
	 */
	private final Runnable synchronousCode;
	private final Object threadlock;
	private int threadsPaused = 0;
	/**
	 * only to be called by a Thread that uses a Framebuffer from this Manager and that has finished his processing cycle
	 */
	public void onThreadFinish(boolean pauseThread) {
//		System.out.println("called");
		
		synchronized(this.threadlock) {			
			//the Thread calling this method is the last one running
			if(threadsPaused >= this.framebuffersAmount-1) {
				synchronousCode.run();
				
				//shift buffers
				this.shiftBuffers();
				
				//wake up/restart all other threads
				this.threadsPaused = 0;
				
				Engine.getInstance().getWindow().drawFramebuffer();
				this.threadlock.notifyAll();
				
//				System.out.println("Restarted all threads!");
				
			} else {
				//pause thread
				this.threadsPaused++;
				if(pauseThread) {
					try {
						this.threadlock.wait();
					}catch(InterruptedException exc) {}
				}
			}
		}
	}
	
	public void startThreadComplex() {
		framebufferClearThread = new FramebufferClearThread();
		framebufferRenderingThread = new FramebufferRenderingThread();
		
		framebufferClearThread.start();
		framebufferRenderingThread.start();
		Engine.getInstance().getWindow().drawFramebuffer();
	}
	
	private void shiftBuffers() {
		Framebuffer cache = framebuffers[framebuffers.length-1];
		for(int i = framebuffers.length-1; i > 0; i--)
			framebuffers[i] = framebuffers[i-1];
		framebuffers[0] = cache;
	}
	
	public Framebuffer getFramebufferToRender() {
		return framebuffers[framebufferID_RENDER];
	}
	
	public Framebuffer getFramebufferToDraw() {
		return framebuffers[framebufferID_DRAW];
	}
	
	public Framebuffer getFramebufferToClear() {
		return framebuffers[framebufferID_CLEAR];
	}
}
