package hz.lin.sparkiMapper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

public class MapCanvas extends JPanel {
	MapData dataSource;
	static final int SCALE = 10;
	static final double sensorAxisDis=1.0;
	static final double axisCenterDis=1.0;
	static final double validityBase=0.618;
	private int ctrX, ctrY, maxY, maxX;
	static int dotR = 2;
	private AffineTransform transform;
    /**
     * Create the panel.
     */
    public MapCanvas() {

    }
    public double validity(double err){
    	return Math.pow(validityBase, err);
    }
    public void updateAffineTransform(SingleReading reading){
    	transform.setToIdentity();
    	transform.rotate(reading.ang);
    	transform.translate(reading.x, reading.y);
    }
    public Point2D getPoint(SingleDistance dist){
    	double x=(dist.dis+sensorAxisDis)*Math.cos(dist.ang);
    	double y=(dist.dis+sensorAxisDis)*Math.sin(dist.ang)+axisCenterDis;
    	Point2D p=new Point2D.Double(x, y);
    	transform.transform(p, p);
    	return p;
    }
    public void paintMap(Graphics2D g){
    	if(dataSource==null){
    		return; //No data source.
    	}
    	for (SingleReading reading: dataSource.readings) {
			updateAffineTransform(reading);
    		for(SingleDistance dist : reading.distances){
    			Point2D p=getPoint(dist);
    			g.setColor(new Color(1,1,1,(float)validity(dist.err)));
    			g.drawOval((int)(ctrX+p.getX()*SCALE-dotR), (int)(ctrY-p.getY()*SCALE-dotR),
    					dotR*2, dotR*2);
    		}
		}
    }
    
    public void paintComponent(Graphics g){
    	super.paintComponent(g);
    	
    	Graphics2D g2d = (Graphics2D) g;
        maxY = getHeight();
        maxX = getWidth();
        ctrX = maxX / 2;
        ctrY = maxY / 2;
        //g.clearRect(0, 0, maxX, maxY); use original background. no need to clear it.
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        
        paintMap(g2d);
        
        
    }
}
