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


public class LensDemoCanvas extends JPanel {

	private static final long serialVersionUID = 892163485801692577L;
	static final int SCALE=10;
	static int dotR=3;
	static int textOffSet=15;
	static double lensFactor=0.075;
	static double objHeight=7.5,objWidth=2.0;
	static Color lightBlue=new Color(0.0f,1.0f,1.0f,1.0f);
	double f=7.5,u=15,v=15;
	int ctrX,ctrY,maxY,maxX;
	private BufferedImage imgRO,imgVO,imgRI,imgVI;
	//image of real object, virtual object, real image and virtual image.
	
	static Stroke thinDashed=new BasicStroke(1.0f,BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND,1.0f, new float[]{5.0f}, 7.5f);
	static Stroke thinSolid =new BasicStroke(1.0f,BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND,1.0f);
	static Stroke dashed    =new BasicStroke(2.0f,BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND,1.0f, new float[]{10.0f}, 0.0f);
	
	LensDemoCanvas(){
		URL urlRO = LensDemoCanvas.class.getResource("imgRO.jpg");
		URL urlVO = LensDemoCanvas.class.getResource("imgVO.jpg");
		URL urlRI = LensDemoCanvas.class.getResource("imgRI.jpg");
		URL urlVI = LensDemoCanvas.class.getResource("imgVI.jpg");
		try {
			imgRO=ImageIO.read(urlRO);
			imgVO=ImageIO.read(urlVO);
			imgRI=ImageIO.read(urlRI);
			imgVI=ImageIO.read(urlVI);
			objWidth=objHeight*imgRO.getWidth()/imgRO.getHeight();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Unable to load necessary images.",
					"Image loading failure",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	private void drawLens(Graphics2D g){
		GradientPaint lensPaint=new GradientPaint(ctrX,0,Color.white,ctrX,ctrY,lightBlue,true);
		g.setPaint(lensPaint);
		Path2D lens=new Path2D.Double();
		double control=lensFactor*maxY;
		if(f>0){
			lens.moveTo(ctrX,0);
			lens.quadTo(ctrX+control, ctrY, ctrX, maxY);
			lens.quadTo(ctrX-control, ctrY, ctrX, 0);
		}
		else{
			lens.moveTo(ctrX+control,0);
			lens.quadTo(ctrX, ctrY, ctrX+control, maxY);
			lens.lineTo(ctrX-control, maxY);
			lens.quadTo(ctrX, ctrY, ctrX-control, 0);
		}
		lens.closePath();
		g.fill(lens);
		//draw the converging/diverging lens.
		
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
		int width=(int) (objWidth*SCALE);
		int height=(int) (objHeight*SCALE);
		BufferedImage img;
		if(u>0){
			img=imgRO;
		}else{
			img=imgVO;
		}
		g.drawImage(img,(int) (ctrX-SCALE*u-width/2),ctrY-height,width,height,null);
		g.drawString("Obj", (int) (ctrX-SCALE*u-width/2),ctrY+textOffSet*2);
		//drawRayObjectCenter(g, SCALE*u, objHeight*SCALE);
		
	}
	
	private void drawImage(Graphics2D g){
		int width=(int) (objWidth*SCALE*(-v/u));
		int height=(int) (objHeight*SCALE*(-v/u));
		BufferedImage img;
		if(v>0){
			img=imgRI;
		}else{
			img=imgVI;
		}
		if(v/u>0){
			g.drawString("Img", (int) (ctrX+SCALE*v+width/2),ctrY-textOffSet);
		}else{
			g.drawString("Img", (int) (ctrX+SCALE*v+width/2),ctrY+textOffSet);
		}
		g.drawImage(img,(int) (ctrX+SCALE*v-width/2),ctrY-height,width,height,null);
		//drawRayImageCenter(g,-SCALE*v,objHeight*SCALE*(-v/u));
	}
	
	private void drawRayCenter(Graphics2D g){
		double uX=SCALE*u,uY=objHeight*SCALE;
		double slope=uY/uX;
		Stroke tmpStroke=g.getStroke();
		g.setStroke(thinSolid);
		if(u>0){
			g.drawLine((int)(ctrX-uX), (int)(ctrY-uY),ctrX,ctrY);
			g.drawLine(ctrX, ctrY,  maxX, (int) (ctrY+(maxX-ctrX)*slope));
			g.setStroke(thinDashed);
			g.drawLine(0, (int) (ctrY-ctrX*slope),(int)(ctrX-uX), (int)(ctrY-uY));
		}else{
			g.drawLine(0, (int) (ctrY-ctrX*slope), ctrX, ctrY);
			g.drawLine(ctrX, ctrY, (int)(ctrX-uX), (int)(ctrY-uY));
			g.drawLine((int)(ctrX-uX), (int)(ctrY-uY),maxX, (int) (ctrY+(maxX-ctrX)*slope));
		}
		g.setStroke(tmpStroke);
	}
	private void drawRayParallel(Graphics2D g){
		double uX=SCALE*u,uY=objHeight*SCALE;
		double fX=SCALE*f;
		Stroke tmpStroke=g.getStroke();
		g.setStroke(thinSolid);
		if(u>0){
			g.drawLine((int)(ctrX-uX), (int)(ctrY-uY), ctrX, (int)(ctrY-uY));
			//g.drawLine(ctrX, ctrY,  maxX, (int) (ctrY+(maxX-ctrX)*slope));
			//g.setStroke(thinDashed);
			//g.drawLine(0, (int) (ctrY-ctrX*slope),ctrX,ctrY);
		}else{
			g.drawLine(0, (int)(ctrY-uY), ctrX, (int)(ctrY-uY));
			g.setStroke(thinDashed);
			g.drawLine(ctrX, (int)(ctrY-uY),(int)(ctrX-uX), (int)(ctrY-uY));
			//g.drawLine(ctrX, ctrY, (int)(ctrX-uX), (int)(ctrY-uY));
			//g.drawLine((int)(ctrX-uX), (int)(ctrY-uY),maxX, (int) (ctrY+(maxX-ctrX)*slope));
		}
		if(f>0){
			g.setStroke(thinSolid);
			g.drawLine(ctrX, (int)(ctrY-uY), (int)(ctrX+fX), ctrY);
			double endY=ctrY+(maxX-ctrX-fX)*uY/fX;
			g.drawLine((int)(ctrX+fX), ctrY , maxX, (int)endY);
			endY=ctrY-uY-(ctrX)*uY/fX;
			g.setStroke(thinDashed);
			g.drawLine(0, (int) endY, ctrX,(int)(ctrY-uY));
		}else{
			g.setStroke(thinDashed);
			g.drawLine((int)(ctrX+fX), ctrY, ctrX, (int)(ctrY-uY));
			double endY=ctrY-uY-(ctrX)*uY/fX;
			g.drawLine(0, (int) endY, (int)(ctrX+fX), ctrY);
			g.setStroke(thinSolid);
			endY=ctrY+(maxX-ctrX-fX)*uY/fX;
			g.drawLine(ctrX, (int)(ctrY-uY), maxX, (int)endY);
		}
		g.setStroke(tmpStroke);
	}
	
	private void drawRayFocal(Graphics2D g){
		double uX=SCALE*u,uY=objHeight*SCALE;
		double fX=SCALE*f;
		Stroke tmpStroke=g.getStroke();
		g.setStroke(thinSolid);
		if(u>0){
			g.drawLine((int)(ctrX-uX), (int)(ctrY-uY), ctrX, (int)(ctrY-uY));
			//g.drawLine(ctrX, ctrY,  maxX, (int) (ctrY+(maxX-ctrX)*slope));
			//g.setStroke(thinDashed);
			//g.drawLine(0, (int) (ctrY-ctrX*slope),ctrX,ctrY);
		}else{
			g.drawLine(0, (int)(ctrY-uY), ctrX, (int)(ctrY-uY));
			g.setStroke(thinDashed);
			g.drawLine(ctrX, (int)(ctrY-uY),(int)(ctrX-uX), (int)(ctrY-uY));
			//g.drawLine(ctrX, ctrY, (int)(ctrX-uX), (int)(ctrY-uY));
			//g.drawLine((int)(ctrX-uX), (int)(ctrY-uY),maxX, (int) (ctrY+(maxX-ctrX)*slope));
		}
		if(f>0){
			g.setStroke(thinSolid);
			g.drawLine(ctrX, (int)(ctrY-uY), (int)(ctrX+fX), ctrY);
			double endY=ctrY+(maxX-ctrX-fX)*uY/fX;
			g.drawLine((int)(ctrX+fX), ctrY , maxX, (int)endY);
			endY=ctrY-uY-(ctrX)*uY/fX;
			g.setStroke(thinDashed);
			g.drawLine(0, (int) endY, ctrX,(int)(ctrY-uY));
		}else{
			g.setStroke(thinDashed);
			g.drawLine((int)(ctrX+fX), ctrY, ctrX, (int)(ctrY-uY));
			double endY=ctrY-uY-(ctrX)*uY/fX;
			g.drawLine(0, (int) endY, (int)(ctrX+fX), ctrY);
			g.setStroke(thinSolid);
			endY=ctrY+(maxX-ctrX-fX)*uY/fX;
			g.drawLine(ctrX, (int)(ctrY-uY), maxX, (int)endY);
		}
		g.setStroke(tmpStroke);
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2d=(Graphics2D) g;
		maxY=getHeight();
		maxX=getWidth();
		ctrX=maxX/2;
		ctrY=maxY/2;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		//remember to turn ANTIALIASING on, or the lens will look ugly....
		drawLens(g2d);
		drawObject(g2d);
		drawImage(g2d);
		//drawRayCenter(g2d);
		//drawRayParallel(g2d);
		System.out.println("paintComponent called");
		
		
	}
		
	public void draw(double f,double u,double v){
		System.out.println("draw called f: "+f+" u: "+u+" v: "+v);
		this.f=f;
		this.u=u;
		this.v=v;
		repaint();
	}

}
