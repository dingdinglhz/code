import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class ShapePaintingCanvas extends Canvas {
	public ShapePaintingCanvas() {
	}

	/**
	 * Create the panel.
	 */
	private Color color;
	private char shape;
	private int xPos,yPos;
	public void paint(Graphics g){
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth() ,getHeight());
		g.setColor(color);
		switch(shape){
		case 'S':
			g.fillRect(xPos, yPos, 20, 20);
			break;
		case 'R':
			g.fillRect(xPos, yPos, 15, 30);
			break;
		case 'C':
			g.fillRect(xPos, yPos, 25, 25);
			break;
		case 'E':
			g.fillOval(xPos, yPos, 20, 35);
			break;
		}
	}
	public void draw(char shape,Color color){
		if(shape==0 || color==null){
			return;
		}
		this.shape=shape;
		this.color=color;
		xPos=getWidth()/2;
		yPos=getHeight()/2;
		repaint();
	}
	public void animate(char shape,Color color){
		if(shape==0 || color==null){
			return;
		}
		this.shape=shape;
		this.color=color;
		for(xPos=0,yPos=0; xPos<getWidth()-30 && yPos<getHeight()-30; 
				xPos+=getWidth()/20, yPos+=getHeight()/20){
			repaint();
			/*
			{ 
			wait(100);
			} 
			catch(Exception e){e.printStackTrace();}*/
		}
	}
}
