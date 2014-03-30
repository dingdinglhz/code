import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class ShapePaintingPanel extends JPanel {
	private Color color;
	//to store the color the user chooses.
	private char shape;
	//to store the shape the user chooses.'S' for square,
	//'R' for rectangle, 'C' for circle, 'E' for ellipse.
	private int shapeSize=25;//the size of the shape.default is 25;
	private volatile int xPos,yPos;
	static final double RATIO=1.4142;
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public char getShape() {
		return shape;
	}
	public void setShape(char shape) {
		this.shape = shape;
	}
	
	public int getShapeSize() {
		return shapeSize;
	}
	public void setShapeSize(int shapeSize) {
		this.shapeSize = shapeSize;
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//this.setBackground();
		if(shape==0 || color==null){
			return;
		}
		g.setColor(color);
		switch(shape){
		case 'S':
			g.fillRect(xPos, yPos, shapeSize, shapeSize);
			break;
		case 'R':
			g.fillRect(xPos, yPos, (int) (shapeSize*RATIO), (int) (shapeSize/RATIO));
			break;
		case 'C':
			g.fillOval(xPos, yPos, shapeSize, shapeSize);
			break;
		case 'E':
			g.fillOval(xPos, yPos,  (int) (shapeSize*RATIO), (int) (shapeSize/RATIO));
			break;
		}
		System.out.println("upd:("+xPos+","+yPos+") shape"+shape+"color"+color);
	}
	public void draw(){
		xPos=getWidth()/2;
		yPos=getHeight()/2;
		Graphics g=getGraphics();
		paintComponent(g);
	}
	public void animate(){
		Graphics g=getGraphics();
		for(xPos=0,yPos=0; xPos<=getWidth()/2 && yPos<=getHeight()/2; 
				xPos+=getWidth()/100, yPos+=getHeight()/100){
			paintComponent(g);
			//repaint();
			
			System.out.println("for:("+xPos+","+yPos+")");
			try{
				Thread.sleep(10);
			}catch(Exception e){
				
			}
		}
	}
}
