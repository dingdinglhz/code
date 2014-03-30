import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class ShapePaintingPanel extends JPanel {
	private Color color;
	//to store the color the user chooses.
	private char shape;
	//to store the shape the user chooses.'r' for empty rectangle,
	//'R' for filled rectangle, 'o' for empty oval, 'O' for filled oval.
	private double xPos,yPos;
	private int shapeWidth=25,shapeHeight=25;
	//The dimension of the shape.Default is 25*25;
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
	
	public double getShapeWidth() {
		return shapeWidth;
	}
	public void setShapeWidth(int shapeWidth) {
		this.shapeWidth = shapeWidth;
	}
	public double getShapeHeight() {
		return shapeHeight;
	}
	public void setShapeHeight(int shapeHeight) {
		this.shapeHeight = shapeHeight;
	}
	public boolean ready(){
		return (shape!=0 && color!=null);
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(shape==0 || color==null){
			return;
		}
		g.setColor(color);
		switch(shape){
		case 'r':
			g.drawRect((int)xPos, (int)yPos, shapeWidth, shapeHeight);
			break;
		case 'R':
			g.fillRect((int)xPos, (int)yPos, shapeWidth, shapeHeight);
			break;
		case 'o':
			g.drawOval((int)xPos, (int)yPos, shapeWidth, shapeHeight);
			break;
		case 'O':
			g.fillOval((int)xPos, (int)yPos, shapeWidth, shapeHeight);
			break;
		}
		//System.out.println("upd:("+xPos+","+yPos+") shape"+shape+"color"+color); debugging code.
	}
	public void draw(){
		xPos=getWidth()/2.0-shapeWidth/2.0;
		yPos=getHeight()/2.0-shapeHeight/2.0;
		Graphics g=getGraphics();
		paintComponent(g);
	}
	public void animate(){
		Graphics g=getGraphics();
		for(xPos=0,yPos=0; xPos+shapeWidth<getWidth() && yPos+shapeHeight<getHeight(); 
				xPos+=getWidth()/100.0, yPos+=getHeight()/100.0){
			paintComponent(g);
			
			//System.out.println("for:("+xPos+","+yPos+")"); debugging code.
			try{
				Thread.sleep(10);
			}catch(Exception e){
			}
		}
	}
}
