/*--------------------------------------------------------------------------------------*/
/*  Program: LensDemoApplication                                                        */
/* 	An application that is able to solve an optical system involving one thin lens. It  */
/*  is able to solve the thin lens imaging equation 1/f = 1/v + 1/u to give an answer.  */
/*  Also, it is able to draw a clear optics ray diagram to illustrate graphically how   */
/*  the image is formed by refracting light rays using a thin lens. A brief tutorial is */
/*  implemented to guide the user recognize all typical patterns of how images are 	    */
/*  formed. As an educational program, learning is embeded in the demonstrations.       */
/*--------------------------------------------------------------------------------------*/
/*  Source File:LensDemoCanvas.java                                                     */
/*  This file is the implementation of the canvas where the ray diagram is drawn. The   */
/*  calculation related to how the ray diagram should be drawn is completed here.       */
/*--------------------------------------------------------------------------------------*/
/*  Author: Hanzhen, Lin                                                                */
/*  Date:  May, 8th, 2014                                                               */
/*--------------------------------------------------------------------------------------*/
/*  Input :Mouse coordinate; images from *.jar file; f, u and v values from main window.*/
/*  Output:Optics ray diagram.                                                          */
/*--------------------------------------------------------------------------------------*/
package hanzhen.lin.lensdemo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
//necessary imports.

public class LensDemoCanvas extends JPanel {

	private static final long serialVersionUID = 892163485801692577L;
	static final int SCALE=10;
	static int dotR=2;
	static int textOffSet=15;
	static double lensFactor=0.075;
	static double arrowX=-5,arrowYd=5,arrowYu=-5;
	static Color lightBlue=new Color(0.0f,1.0f,1.0f,1.0f);
	static Color ligthGray=new Color(1.0f,1.0f,1.0f,0.5f);
	static Stroke thinDashed=new BasicStroke(1.0f,BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND,1.0f, new float[]{5.0f}, 7.5f);
	static Stroke thinSolid =new BasicStroke(1.0f,BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND,1.0f);
	static Stroke dashed    =new BasicStroke(1.5f,BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND,1.0f, new float[]{10.0f}, 0.0f);
	static Stroke thinGrid  =new BasicStroke(0.0f,BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND,1.0f);
	//Useful constants related to the shape/color/line style/arrow style/
	//dot style/graphing scale/lens style/text style.
	
	private double f=7.5,u=15,v=15;
	//The crucial value of f, u and v.
	private double objHeight=7.5,objWidth=2.0;
	//The dimension of the picture representing the object
	private int ctrX,ctrY,maxY,maxX;
	//Useful variable to store the coordinate of the center and bottom right corner.
	private BufferedImage imgRO,imgVO,imgRI,imgVI;
	//image of real object, virtual object, real image and virtual image.
	private LensDemoApplication parentDemo;
	//Access to the main program it belongs.
	
	LensDemoCanvas(){
		//Constructor of the canvas panel.
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				//System.out.println("drag"+e.getPoint());
				if(parentDemo!=null){
					double tmp=(double)(ctrX-e.getX())/SCALE;
					double heightTmp=(double)(ctrY-e.getY())/SCALE;
					parentDemo.setValueExternal(f, tmp, v, heightTmp, 
							LensDemoApplication.SolveFor.V);
					//repaint();
				}
				//Add mouse drag control to set the size and position of the object.
			}
		});
		URL urlRO = LensDemoCanvas.class.getResource("imgRO.png");
		URL urlVO = LensDemoCanvas.class.getResource("imgVO.png");
		URL urlRI = LensDemoCanvas.class.getResource("imgRI.png");
		URL urlVI = LensDemoCanvas.class.getResource("imgVI.png");
		//URLs that directs to the pictures representing the object/image.
		try {
			imgRO=ImageIO.read(urlRO);
			imgVO=ImageIO.read(urlVO);
			imgRI=ImageIO.read(urlRI);
			imgVI=ImageIO.read(urlVI);
			objWidth=objHeight*imgRO.getWidth()/imgRO.getHeight();
			//Load the images and adjust the height/width ratio.
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Unable to load necessary images.",
					"Image loading failure",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			//Image not found exception.
		}
		
	}
	
	public void setObjectHeight(double objectHeight){
		//A setter for the object's height. Width is automatically calculated.
		objHeight=objectHeight;
		objWidth=objHeight*imgRO.getWidth()/imgRO.getHeight();	 
	}
	
	private void drawArrow(Graphics2D g, int x1,int y1,int x2,int y2){
		//A method that draws an line with arrow from (x1,y1) to (x2,y2);.
		g.drawLine(x1,y1,x2,y2); //Draw the line first.
		int xm=(x1+x2)/2, ym=(y1+y2)/2; //Calculate the midpoint.
		double dx=(x2-x1),dy=(y2-y1); 
		double dz=Math.sqrt(dx*dx+dy*dy);
		double cos=dx/dz,sin=dy/dz;//Calculate values of cos and sin.
		double aXu=cos*arrowX-sin*arrowYu;
		double aYu=sin*arrowX+cos*arrowYu;
		double aXd=cos*arrowX-sin*arrowYd;
		double aYd=sin*arrowX+cos*arrowYd;
		//the offset of arrow is calculate using a rotational matrix
		g.drawLine((int)(xm+aXu),(int)(ym+aYu),xm,ym);
		g.drawLine((int)(xm+aXd),(int)(ym+aYd),xm,ym);
		//draw the arrows according to the calculation made.		
	}
	
	private void drawGrid(Graphics2D g){
		//draw a grid of dashed line in the background.
		Stroke tmpStroke=g.getStroke();
		g.setStroke(thinGrid);
		g.setColor(Color.gray);
		for(int ix=ctrX%SCALE; ix<maxX;ix+=SCALE){
			g.drawLine(ix,0,ix,maxY);
		}
		for(int iy=ctrY%SCALE; iy<maxX;iy+=SCALE){
			g.drawLine(0,iy,maxX,iy);
		}
		g.setStroke(tmpStroke);
	}
	
	private void drawLens(Graphics2D g){
		//Draw the thin lens in the center.
		Path2D lens=new Path2D.Double();
		double control=lensFactor*maxY;
		if(f>0){ //If it is convex lens.
			lens.moveTo(ctrX,0);
			lens.quadTo(ctrX+control, ctrY, ctrX, maxY);
			lens.quadTo(ctrX-control, ctrY, ctrX, 0);
		}
		else{ //Concava lens.
			lens.moveTo(ctrX+control,0);
			lens.quadTo(ctrX, ctrY, ctrX+control, maxY);
			lens.lineTo(ctrX-control, maxY);
			lens.quadTo(ctrX, ctrY, ctrX-control, 0);
		}
		lens.closePath();
		GradientPaint lensPaint=new GradientPaint(ctrX,0,Color.white,ctrX,ctrY,lightBlue,true);
		g.setPaint(lensPaint);
		g.fill(lens);
		g.setColor(lightBlue);
		g.draw(lens);
		//fill the converging/diverging lens with boundary.
		
		Stroke tmpStroke=g.getStroke();
		g.setStroke(dashed);
		g.setColor(Color.black);
		g.drawLine(0, ctrY, maxX, ctrY);
		g.setStroke(tmpStroke);
		g.fillOval((int) (ctrX+SCALE*f-dotR), ctrY-dotR, dotR*2, dotR*2);
		g.drawString("F", (int) (ctrX+SCALE*f), ctrY+textOffSet);
		g.fillOval((int) (ctrX+SCALE*f*2-dotR), ctrY-dotR, dotR*2, dotR*2);
		g.drawString("2F", (int) (ctrX+SCALE*f*2), ctrY+textOffSet);
		g.fillOval((int) (ctrX-SCALE*f-dotR), ctrY-dotR, dotR*2, dotR*2);
		g.drawString("F'", (int) (ctrX-SCALE*f), ctrY+textOffSet);
		g.fillOval((int) (ctrX-SCALE*f*2-dotR), ctrY-dotR, dotR*2, dotR*2);
		g.drawString("2F'", (int) (ctrX-SCALE*f*2), ctrY+textOffSet);
		g.fillOval(ctrX-dotR, ctrY-dotR, dotR*2, dotR*2);
		g.drawString("O", ctrX, ctrY+textOffSet);
		g.setStroke(tmpStroke);
		//drawing the principle axis and label special dots.
	}
	private void drawObject(Graphics2D g){
		//Draw the object by putting a picture in the correct position with correct size.
		g.setColor(Color.black);
		int width=(int) (objWidth*SCALE);
		int height=(int) (objHeight*SCALE);
		BufferedImage img;
		if(u>0){
			img=imgRO;
		}else{
			img=imgVO;
		}//Check if it is real or virtual.
		g.drawImage(img,(int) (ctrX-SCALE*u-width/2),ctrY-height,width,height,null);//draw
		g.drawString("Obj", (int) (ctrX-SCALE*u),ctrY+textOffSet*2); //label
		g.fillOval((int) (ctrX-SCALE*u-dotR/2.0), ctrY-dotR, dotR*2, dotR*2);//dot
	}
	
	private void drawImage(Graphics2D g){
		//Draw the image by putting a picture in the correct position with correct size.
		g.setColor(Color.black);
		int width=(int) (objWidth*SCALE*(-v/u));
		int height=(int) (objHeight*SCALE*(-v/u));
		BufferedImage img;
		if(v>0){
			img=imgRI;
		}else{
			img=imgVI;
		}//Check if it is real or virtual.
		g.drawImage(img,(int) (ctrX+SCALE*v-width/2),ctrY-height,width,height,null);//Draw
		if(v/u>0){
			g.drawString("Img", (int) (ctrX+SCALE*v),ctrY-textOffSet);
		}else{
			g.drawString("Img", (int) (ctrX+SCALE*v),ctrY+textOffSet);
		}//Label (above or below the principle axis)
		g.fillOval((int) (ctrX+SCALE*v-dotR/2.0), ctrY-dotR, dotR*2, dotR*2);//dot
	}
	
	private void drawRayCenter(Graphics2D g){
		//the method that draws the special light ray crossing the optical center.
		double uX=SCALE*u,uY=objHeight*SCALE;
		double slope=uY/uX; //calculate slope.
		Stroke tmpStroke=g.getStroke();
		g.setStroke(thinSolid);
		if(u>0){ //if the object is real
			drawArrow(g,(int)(ctrX-uX), (int)(ctrY-uY),ctrX,ctrY);
			drawArrow(g,ctrX, ctrY,  maxX, (int) (ctrY+(maxX-ctrX)*slope));
			g.setStroke(thinDashed);
			drawArrow(g,0, (int) (ctrY-ctrX*slope),(int)(ctrX-uX), (int)(ctrY-uY));
		}else{  //if the object is virtual
			drawArrow(g,0, (int) (ctrY-ctrX*slope), ctrX, ctrY);
			drawArrow(g,ctrX, ctrY, (int)(ctrX-uX), (int)(ctrY-uY));
			drawArrow(g,(int)(ctrX-uX), (int)(ctrY-uY),maxX, (int) (ctrY+(maxX-ctrX)*slope));
		}
		g.setStroke(tmpStroke); //set the line style back
	}
	private void drawRayParallel(Graphics2D g){
		//the method that draws the special light ray that is parallel to the principle axis
		//and crosses the focus after refraction.
		double uX=SCALE*u,uY=objHeight*SCALE;
		double fX=SCALE*f;
		Stroke tmpStroke=g.getStroke();
		g.setStroke(thinSolid);
		if(u>0){//if the object is real, draw a horizontal line
			drawArrow(g,(int)(ctrX-uX), (int)(ctrY-uY), ctrX, (int)(ctrY-uY));
		}else{//if the object is virtual, draw a partially dashed horizontal line
			drawArrow(g,0, (int)(ctrY-uY), ctrX, (int)(ctrY-uY));
			g.setStroke(thinDashed);
			drawArrow(g,ctrX, (int)(ctrY-uY),(int)(ctrX-uX), (int)(ctrY-uY));
		}
		if(f>0){  //if the lens is convex.
			g.setStroke(thinSolid);
			drawArrow(g,ctrX, (int)(ctrY-uY), (int)(ctrX+fX), ctrY);
			double endY=ctrY+(maxX-ctrX-fX)*uY/fX;
			drawArrow(g,(int)(ctrX+fX), ctrY , maxX, (int)endY);
			endY=ctrY-uY-(ctrX)*uY/fX;
			g.setStroke(thinDashed);
			drawArrow(g,0, (int) endY, ctrX,(int)(ctrY-uY));
		}else{  //if the lens is virtual.
			g.setStroke(thinDashed);
			drawArrow(g,(int)(ctrX+fX), ctrY, ctrX, (int)(ctrY-uY));
			double endY=ctrY-uY-(ctrX)*uY/fX;
			drawArrow(g,0, (int) endY, (int)(ctrX+fX), ctrY);
			g.setStroke(thinSolid);
			endY=ctrY+(maxX-ctrX-fX)*uY/fX;
			drawArrow(g,ctrX, (int)(ctrY-uY), maxX, (int)endY);
		}
		g.setStroke(tmpStroke);
	}
	
	private void drawRayFocus(Graphics2D g){
		//if(u/f<=1 && u/f>=0){return;} //the case 0<= u/f <=1 is ignored for now.
		//this case above is already implemented. 
		double uX=SCALE*u,uY=objHeight*SCALE;
		double fX=SCALE*f;
		Stroke tmpStroke=g.getStroke();
		double slope=uY/(uX-fX);
		double offSetY=ctrY+slope*uX-uY; 
		//get the intersect between the lens and the line from object to F'
		g.setStroke(thinSolid);

		if(u>f && f>0){  //real object & convex lens
			drawArrow(g,(int)(ctrX-uX), (int)(ctrY-uY), (int)(ctrX-fX), ctrY);
			drawArrow(g,(int)(ctrX-fX), ctrY, ctrX, (int) offSetY);
		}
		else if(u<f && u>0 && f>0){ //real object within 1*f & convex lens
			drawArrow(g,(int)(ctrX-uX), (int)(ctrY-uY),ctrX, (int) offSetY);
			g.setStroke(thinDashed);
			drawArrow(g,(int)(ctrX-fX), ctrY,(int)(ctrX-uX), (int)(ctrY-uY));
		}
		else if(u<0 && f>0){  //virtual object & convex lens
			drawArrow(g,0,(int)(ctrY-(ctrX-fX)*slope),(int)(ctrX-fX),ctrY);
			drawArrow(g,(int)(ctrX-fX), ctrY, ctrX, (int) offSetY);
			g.setStroke(thinDashed);
			drawArrow(g,ctrX, (int)offSetY,(int)(ctrX-uX),(int)(ctrY-uY));
		}
		else if(u>0 && f<0){  //real object & concave lens
			drawArrow(g,(int)(ctrX-uX),(int)(ctrY-uY),ctrX,(int)offSetY);
			g.setStroke(thinDashed);
			drawArrow(g,ctrX,(int)offSetY ,(int)(ctrX-fX),ctrY);
		}
		else if(u<f && f<0){  //virtual object & concave lens
			drawArrow(g,0,(int)(ctrY-(ctrX-fX)*slope),ctrX, (int) offSetY);
			g.setStroke(thinDashed);
			drawArrow(g,ctrX, (int) offSetY, (int)(ctrX-fX), ctrY);
			drawArrow(g,(int)(ctrX-fX), ctrY, (int)(ctrX-uX),(int)(ctrY-uY));
		}
		else if(u<0 && u>f && f<0){
			drawArrow(g,0,(int)(ctrY-(ctrX-fX)*slope),ctrX, (int) offSetY);
			g.setStroke(thinDashed);
			drawArrow(g,ctrX, (int) offSetY, (int)(ctrX-uX),(int)(ctrY-uY));
			drawArrow(g,(int)(ctrX-uX),(int)(ctrY-uY), (int)(ctrX-fX), ctrY);
		}
		// draw the ray before refraction.
		g.setStroke(thinDashed);
		drawArrow(g,0,(int)offSetY,ctrX,(int)offSetY);
		g.setStroke(thinSolid);
		drawArrow(g,ctrX,(int)offSetY,maxX,(int)offSetY);
		g.setStroke(tmpStroke);
		// draw the horizontal ray before refraction.
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2d=(Graphics2D) g;
		maxY=getHeight();
		maxX=getWidth();
		ctrX=maxX/2;
		ctrY=maxY/2;
		//g.clearRect(0, 0, maxX, maxY); use original background. no need to clear it.
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		//remember to turn ANTIALIASING on, or the lens will look ugly....
		
		drawLens(g2d);
		drawGrid(g2d);
		drawObject(g2d);
		drawImage(g2d);
		g2d.setColor(Color.red);
		drawRayCenter(g2d);
		g2d.setColor(Color.green);
		drawRayParallel(g2d);
		g2d.setColor(Color.blue);
		drawRayFocus(g2d);
		//draw the ray diagram in separate steps.
		//System.out.println("paintComponent called");

	}
		
	public void draw(double f,double u,double v){
		//System.out.println("draw called f: "+f+" u: "+u+" v: "+v);
		if(f==0 || Double.isInfinite(f) || Double.isNaN(f)){
			getGraphics().drawString( "f is zero/infinite/NaN, which is not able to draw", 50,50);
			System.out.println("Zero/infinite/NaN f encountered inside LensDemoCanvas. Rejected.");
			JOptionPane.showMessageDialog(this.getParent(), "f cannot be equal to zero/infinite/NaN!", 
					"Invalid Focal Length",JOptionPane.WARNING_MESSAGE);
			return;
		}  //Focal length cannot be 0, infinity or NaN.
		if(Double.isInfinite(u) || Double.isNaN(u)){
			getGraphics().drawString( "u is infinite/NaN, which is not able to draw", 50,50);
			JOptionPane.showMessageDialog(this.getParent(), "u is infinite/NaN, which is not able to draw", 
					"Unable to draw diagram due to out-of-range value.",JOptionPane.WARNING_MESSAGE);
			return;
		}  //Distance of object cannot be infinity or NaN.
		this.f=f;
		this.u=u;
		this.v=v;
		//update the values of f,u and v according to new value passed from the main window.
		repaint();
	}
	
	public void setParentDemo(LensDemoApplication parentDemo) {
		this.parentDemo = parentDemo; //establish the access to the main window.
	}

}
