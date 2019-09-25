package net.window;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import net.Engine;
import net.gameobjects.SphereProcedural;
import net.maths.Calculator;

public class Window extends JFrame implements KeyListener, MouseListener {
	
	private DrawComp dc;
	
	private boolean[] keyRegister = new boolean[KeyEvent.RESERVED_ID_MAX];
	
	private Cursor cursorBlank;
	
	public Window() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setBackground(DrawComp.COLOR_BACKGROUND);
		
		dc = new DrawComp();
		dc.setPreferredSize(new Dimension(1000, 1000));
		this.add(dc);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		this.addKeyListener(this);
		this.addMouseListener(this);
	}
	
	public void drawFramebuffer() {
		this.repaint();
	}
	
	public void switchMouseCenterPrison() {
		switchMouseCenterPrison(Engine.getInstance().mouseCenterPrisonActivated ^= true);
	}
	public void switchMouseCenterPrison(boolean activate) {
		Engine.getInstance().mouseCenterPrisonActivated = activate;
		if(activate) {
			if(cursorBlank == null)
				cursorBlank = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "blank cursor");
			this.getContentPane().setCursor(cursorBlank);
		} else {
			this.getContentPane().setCursor(Cursor.getDefaultCursor());
		}
	}

	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		this.keyRegister[e.getKeyCode()] = true;
	}
	public void keyReleased(KeyEvent e) {
		this.keyRegister[e.getKeyCode()] = false;
		
		if(e.getKeyCode() == KeyEvent.VK_X) {
			Calculator.OPTION_ROTATE_AROUND_X1_AXIS ^= true;
		}else if(e.getKeyCode() == KeyEvent.VK_Y) {
			Calculator.OPTION_ROTATE_AROUND_X2_AXIS ^= true;
		}else if(e.getKeyCode() == KeyEvent.VK_Z) {
			Calculator.OPTION_ROTATE_AROUND_X3_AXIS ^= true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_F1) {
			switchMouseCenterPrison();
			
//			if(Engine.getInstance().mouseCenterPrisonActivated) {
//				if(cursorBlank == null)
//					cursorBlank = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "blank cursor");
//				this.getContentPane().setCursor(cursorBlank);
//			} else {
//				this.getContentPane().setCursor(Cursor.getDefaultCursor());
//			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_F2) {
			Engine.getInstance().DRAW_WIREFRAME ^= true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_F3) {
			((SphereProcedural) Engine.getInstance().getGameObjects()[0]).proceed();
		}
	}
	
	public boolean[] getKeyRegister() {
		return this.keyRegister;
	}
	
	public DrawComp getDrawComp() {
		return this.dc;
	}

	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			switchMouseCenterPrison();
	}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
