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
	static final int SCALE =1;
	static final double sensorAxisDis=1.0;
	static final double axisCenterDis=1.0;
	static final double validityErrBase=0.618;
	static final double validityDisBase=0.987;
	private int ctrX, ctrY, maxY, maxX;
	static double dotR = 1.0;
	private AffineTransform transform;
    /**
     * Create the panel.
     */
    public MapCanvas() {
    	transform=new AffineTransform();
    }
    public void setDataSource(MapData dataSource){
    	this.dataSource=dataSource;
    }
    public double validity(SingleDistance dist){
    	//return Math.pow(validityErrBase, dist.err)*Math.pow(validityDisBase, dist.dis);
    	return Math.pow(validityErrBase, dist.err)*Math.pow(validityDisBase, dist.dis);
    }
    public void updateAffineTransform(SingleReading reading){
    	transform.setToIdentity();
    	transform.translate(reading.x, reading.y);
    	transform.rotate(reading.ang);
    }
    public Point2D getPoint(SingleDistance dist){
    	double rad=dist.ang*Math.PI/180.0;
    	double x=(dist.dis+sensorAxisDis)*Math.cos(-rad)+axisCenterDis;
    	double y=(dist.dis+sensorAxisDis)*Math.sin(-rad);
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
			//System.out.println("X: "+reading.x+" Y:"+reading.y+" Ang: "+reading.ang);
			g.setColor(Color.red);
			g.fillOval((int)(ctrX+reading.x*SCALE-dotR), (int)(ctrY-reading.y*SCALE-dotR),
					(int)(dotR*2),(int)(dotR*2));
    		for(SingleDistance dist : reading.distances){
    			Point2D p=getPoint(dist);
    			g.setColor(new Color(0,0,0,(float)validity(dist)));
    			g.fillOval((int)(ctrX+p.getX()*SCALE-dotR), (int)(ctrY-p.getY()*SCALE-dotR),
    					(int)(dotR*2), (int)(dotR*2));
    			//System.out.println("trasfromed ( "+p.getX()+" , "+p.getY()+" )");
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
