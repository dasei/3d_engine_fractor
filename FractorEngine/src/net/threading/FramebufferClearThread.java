package net.threading;

import net.Engine;
import net.buffering.FramebufferManager;

public class FramebufferClearThread extends Thread {
	public void run() {
		FramebufferManager framebufferManager = Engine.getInstance().getFramebufferManager();
		
		this.setName("ClearingThread");
		
		while(true) {
//			framebufferManager.getFramebufferToClear().checkIfClear();
			framebufferManager.getFramebufferToClear().clear();
//			framebufferManager.getFramebufferToClear().checkIfClear();
//			System.out.println(framebufferManager.getFramebufferToClear());
			
			// schlafen legen
			framebufferManager.onThreadFinish(true);
		}
	}
}
