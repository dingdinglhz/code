import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.event.MouseAdapter;

@SuppressWarnings("serial")
class TouchTracerPanel extends JPanel{
	private Path2D path;
	Point p;
	static Stroke thick =new BasicStroke(2.0f,BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND,1.0f);
	static Stroke thin  =new BasicStroke(0.0f,BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND,1.0f);
	
	TouchTracerPanel(){
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				p=e.getPoint();
				path=new Path2D.Double();
				path.moveTo(p.x, p.y);
				repaint();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				p=e.getPoint();
				path.lineTo(p.x, p.y);
				repaint();
			}
		});
		
	}
	protected void paintComponent(Graphics g){
		Graphics2D g2d=(Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		//g2d.setBackground(Color.white);
		g2d.setBackground(Color.black);
		g2d.clearRect(0, 0, getWidth(), getHeight());
		if(p!=null){
			g2d.setColor(Color.white);
			g2d.setStroke(thin);
			g2d.drawLine(p.x, 0, p.x, getHeight());
			g2d.drawLine(0, p.y, getWidth(), p.y);
			g2d.drawString("P("+p.x+","+p.y+")", p.x, p.y);
		}
		if(path!=null){
			g2d.setColor(Color.cyan);
			g2d.setStroke(thick);
			g2d.draw(path);
		}
	}
	
}

@SuppressWarnings("serial")
public class TouchTracerTest extends JFrame {

	private TouchTracerPanel contentPane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TouchTracerTest frame = new TouchTracerTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TouchTracerTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new TouchTracerPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
