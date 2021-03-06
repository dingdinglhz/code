package hz.lin.sparkiMapper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Vector;

import javax.swing.JPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapCanvas extends JPanel {
	MapData dataSource;
	
	static final double sensorAxisDis=1.0;
	static final double axisCenterDis=1.0;
	private double validityErrBase=0.618; //base of exponential function
	private double validityDisBase=1.13;   //base of logistic function
	private double validityDisThld=80; //threshold
	private double portion=1.0;
	private double scale =1.0;
	private int ctrX, ctrY, maxY, maxX;
	static double dotR = 1.0;
	private AffineTransform transform;
	private AffineTransform drawingTransform;
	private Path2D triangle;
	private SparkiMapperWindow parent;
    /**
     * Create the panel.
     */
    public MapCanvas() {
    	addMouseListener(new MouseAdapter() {
    		@Override
    		public void mousePressed(MouseEvent e) {
    			System.out.println("Coordinate: ("+e.getX()+","+e.getY()+")");
    			if(transform!=null && drawingTransform!=null){
    				Point2D p=new Point2D.Double(e.getX(),e.getY());
    				try {
						drawingTransform.inverseTransform(p,p);
						transform.inverseTransform(p, p);
					} catch (NoninvertibleTransformException nonInvE) {
						// TODO Auto-generated catch block
						System.out.println(nonInvE);
					}
    				System.out.println("Transformed: ("+p.getX()+","+p.getY()+")");
    				double theta=-Math.toDegrees(Math.atan2(p.getY(), p.getX()));
    				double dist=p.distance(0, 0);
    				System.out.println("Theta: "+theta+", dist:"+dist);
    				if(parent!=null){
    					parent.setManualControlCommand((int)theta,(int)dist);
    				}
    				
    			}
    			
    		}
    	});
    	transform=new AffineTransform();
    	triangle=new Path2D.Double();
    	triangle.moveTo(0.75,0);
    	triangle.lineTo(-0.75,0.75);
    	triangle.lineTo(-0.75,-0.75);
    	triangle.closePath();
    	
    	
    }
    public void setDataSource(MapData dataSource){
    	this.dataSource=dataSource;
    }
    public double validity(SingleDistance dist){
    	//return Math.pow(validityErrBase, dist.err)*Math.pow(validityDisBase, dist.dis);
    	double tmp= Math.pow(validityErrBase, dist.err);
    	tmp*=0.5 / (1+Math.pow(validityDisBase, (dist.dis-validityDisThld) ) );
    	return tmp;
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
    	//g.setBackground(Color.white);
    	//g.clearRect(0, 0, maxX, maxY);
    	if(dataSource==null){
    		return; //No data source.
    	}
    	Vector<SingleReading> readings = dataSource.readings;
		for (int i = 0; i < readings.size()*portion; i++) {
			SingleReading reading = readings.get(i);
			updateAffineTransform(reading);
			//System.out.println("X: "+reading.x+" Y:"+reading.y+" Ang: "+reading.ang);
			g.setColor(Color.red);
			//g.fillOval((int)(ctrX+reading.x*scale-dotR), (int)(ctrY-reading.y*scale-dotR),
			//		(int)(dotR*2),(int)(dotR*2));
			g.fill(drawingTransform.createTransformedShape(transform.createTransformedShape(triangle)));
    		for(SingleDistance dist : reading.distances){
    			Point2D p=getPoint(dist);
    			g.setColor(new Color(0,0,0,(float)validity(dist)));
    			g.fillOval((int)(ctrX+p.getX()*scale-dotR), (int)(ctrY-p.getY()*scale-dotR),
    					(int)(dotR*2), (int)(dotR*2));
    			//System.out.println("trasfromed ( "+p.getX()+" , "+p.getY()+" )");
    		}
		}
		g.setColor(Color.green);
		g.fill(drawingTransform.createTransformedShape(transform.createTransformedShape(triangle)));
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
        
        drawingTransform=new AffineTransform();
        
        drawingTransform.translate(ctrX,ctrY);
        drawingTransform.scale(scale,-scale);
        //drawingTransform.scale(1,-1);
        paintMap(g2d);
        
        
    }
	public void setValidityErrBase(double validityErrBase) {
		this.validityErrBase = validityErrBase;
	}
	public void setValidityDisBase(double validityDisBase) {
		this.validityDisBase = validityDisBase;
	}
	public void setValidityDisThld(double validityDisThld) {
		this.validityDisThld = validityDisThld;
	}
	public void setPortion(double portion) {
		if(portion>1.0){
			return;
		}
		this.portion = portion;
	}
	public void setScale(double scale) {
		this.scale = scale;
	}
	public void setParent(SparkiMapperWindow parent){
		this.parent=parent;
	}
}
