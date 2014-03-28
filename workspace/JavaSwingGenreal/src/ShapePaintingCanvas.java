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
	volatile Color color;
	volatile char shape;
	private volatile int xPos,yPos;
	public void update(Graphics g){
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
			g.fillOval(xPos, yPos, 25, 25);
			break;
		case 'E':
			g.fillOval(xPos, yPos, 20, 35);
			break;
		}
		System.out.println("upd:("+xPos+","+yPos+") shape"+shape+"color"+color);
	}
	public void draw(char shape,Color color){
		if(shape==0 || color==null){
			return;
		}
		//this.shape=shape;
		//this.color=color;
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
			System.out.println("for:("+xPos+","+yPos+")");
			/*try{
				Thread.sleep(100);
			}catch(Exception e){
				
			}*/
			/*try
			{ 
			 wait(1000);
			} 
			catch(Exception e){}*/
		}
	}
}
