/*--------------------------------------------------------------------------------------*/
/*  ShapeApplet.java + ShapePaintingPanel.java                                          */
/* 	An applet that displays a shape on the screen. It allows the user to select which of*/
/*  4 different shapes to be draw. It also allows the user to choose the color of the   */
/*  shape and set the height and width of that shape. In addition to simply drawing the */
/*  shape, this applet can also have a simple animation.                                */
/*--------------------------------------------------------------------------------------*/
/*  Author: Hanzhen, Lin                                                                */
/*  Date:  Mar, 30th, 2014                                                              */
/*--------------------------------------------------------------------------------------*/
/*  Input: Graphical user interface (buttons, spinners, radio buttons)                  */
/*  Output:Graphical user interface (labels, canvas panels)                             */
/*--------------------------------------------------------------------------------------*/
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
//useful imports.

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
	//getters and setters for the properties of the shape to be drawn.
	public boolean ready(){
		return (shape!=0 && color!=null);
	}//check if the shape and color is already set or not.
	public void paintComponent(Graphics g){//overwritten paintComponent method.
		//In swing, it is not recommended to use paint(), but paintComponent() instead.
		super.paintComponent(g);//use  paintComponent() from super class first.
		if(!ready()){
			return;
		}//If it is not ready to draw the shape, return.
		g.setColor(color);//set the color of the 'Graphics' object g. 
		switch(shape){//Interpret the shape code to actual shapes and draw them.
		case 'r':
			g.drawRect((int)xPos, (int)yPos, shapeWidth, shapeHeight);
			break;//empty rectangle
		case 'R':
			g.fillRect((int)xPos, (int)yPos, shapeWidth, shapeHeight);
			break;//filled rectangle
		case 'o':
			g.drawOval((int)xPos, (int)yPos, shapeWidth, shapeHeight);
			break;//empty oval
		case 'O':
			g.fillOval((int)xPos, (int)yPos, shapeWidth, shapeHeight);
			break;//filled oval
		}
		//System.out.println("upd:("+xPos+","+yPos+") shape"+shape+"color"+color);
	}
	public void draw(){
		xPos=getWidth()/2.0-shapeWidth/2.0;
		yPos=getHeight()/2.0-shapeHeight/2.0;
		//Find the proper coordinate to draw in the middle of the panel.
		Graphics g=getGraphics();
		paintComponent(g);
		//Find the 'Graphic' object and use that to call paintComponent() to draw.
	}
	public void animate(){
		Graphics g=getGraphics();//Find the 'Graphic' object.
		for(xPos=0,yPos=0; xPos+shapeWidth<getWidth() && yPos+shapeHeight<getHeight(); 
				xPos+=getWidth()/100.0, yPos+=getHeight()/100.0){
			//A for-loop. The shape starts from top left and stops at bottom right.
			paintComponent(g);//draw the shape repeatedly.
			//System.out.println("for:("+xPos+","+yPos+")"); debugging code.
			try{
				Thread.sleep(10);//wait for 0.01s before drawing the next shape.
			}catch(Exception e){
				//a exception is expected and nothing special needs to be done.
			}
		}
	}
}
